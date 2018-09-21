package com.yanqing.kotlindemo.fragment

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.activity.AddCategoryActivity
import com.yanqing.kotlindemo.adapter.BaseAdapter
import com.yanqing.kotlindemo.adapter.CategoryGridAdapter
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.constant.DialogMessage
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.fragment.dialogfragment.CommonMessageDialogFragment
import com.yanqing.kotlindemo.logic.CategoryLogic
import com.yanqing.kotlindemo.recycler.CategoryItemDecoration
import kotlin.collections.ArrayList

class CategoryFragment : BaseFragment(), CategoryLogic.OnGetCategoryListListener {
    companion object {
        const val TAG = "CategoryFragment"
        private const val REQUEST_CODE_ADD_CATEGORY = 1001

        fun newInstance(): CategoryFragment {
            val homeFragment = CategoryFragment();
            return homeFragment
        }
    }


    private val mCategoryGridView by lazy { view?.findViewById<RecyclerView>(R.id.category_grid) }
    private var mAdapter: CategoryGridAdapter? = null
    private val mCategoryFunctionLayout by lazy { view?.findViewById<LinearLayout>(R.id.category_title_bar_right_menu) }
    private val mCategoryFunctionSelect by lazy { view?.findViewById<ImageView>(R.id.category_title_select_all) }
    private val mCategoryFunctionDelete by lazy { view?.findViewById<ImageView>(R.id.category_title_delete) }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = CategoryGridAdapter(context)

        val layoutManager = GridLayoutManager(context, 3)
        layoutManager.spanSizeLookup = CategorySpanSize(mAdapter)
        mCategoryGridView?.layoutManager = layoutManager
        mCategoryGridView?.addItemDecoration(CategoryItemDecoration(context))
        mAdapter?.setItemClickListener(mItemClickListener)
        mAdapter?.setItemLongClickListener(mItemLongClickListener)
        mAdapter?.setAddClickListener(mAddItemClickListener)
        mCategoryGridView?.adapter = mAdapter

        mCategoryFunctionSelect?.setOnClickListener(mSelectAllClickListener)
        mCategoryFunctionDelete?.setOnClickListener(mDeleteClickListener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCategoryData()
    }

    private fun getCategoryData() {
        mAdapter?.reset()
        CategoryLogic.getCategoryData(context, this)
    }

    override fun onSuccess(data: ArrayList<CategoryEntity>) {
        mAdapter?.setCategoryData(data)
    }

    override fun onFailed(page: Int) {
        mAdapter?.setStatus(BaseAdapter.STATUS_ERROR)
    }

    private val mItemClickListener = View.OnClickListener {
        val position = it.tag
        if (position != null && position is Int) {
            val category = mAdapter?.getItem(position)
            CategoryLogic.categoryClick(context, category?.id!!)
        }
    }

    //可以使用lambda表达式优化
    private val mItemLongClickListener = object : View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            val position = v?.tag
            if (position != null && position is Int) {
                val popupMenu = PopupMenu(context, v)
                popupMenu.inflate(R.menu.category_item_long_click_menu)
                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item?.itemId) {
                            R.id.detail -> {
                            }
                            R.id.delete -> {
                                val category = mAdapter?.getItem(position)
                                if (category?.canEdit!!) {
                                    mAdapter?.deleteItem(position)
                                    mAdapter?.notifyDataSetChanged()
                                    CategoryLogic.delete(context, category)
                                } else {
                                    showToast("默认分类，不能删除！")
                                }
                            }
                            R.id.edit -> {
                                val category = mAdapter?.getItem(position)
                                if (category?.canEdit!!) {
                                    val intent = Intent(context, AddCategoryActivity::class.java)
                                    intent.putExtra(Constant.CATEGORY_ID, category.id)
                                    startActivityForResult(intent, REQUEST_CODE_ADD_CATEGORY)
                                } else {
                                    showToast("默认分类，不能修改！")
                                }
                            }
                            R.id.delete_all -> {
                                startCheckPattern()
                            }
                        }
                        return true
                    }
                })
                popupMenu.show()
            }
            return true
        }
    }

    private fun startCheckPattern() {
        mAdapter?.startCheckPattern()

        mCategoryFunctionLayout?.visibility = View.VISIBLE

    }

    private fun stopCheckPattern() {
        mAdapter?.stopCheckPattern()

        mCategoryFunctionLayout?.visibility = View.GONE
    }

    private val mAddItemClickListener = View.OnClickListener {
        startActivityForResult(Intent(context, AddCategoryActivity::class.java), REQUEST_CODE_ADD_CATEGORY)
    }

    private val mSelectAllClickListener = View.OnClickListener {
        mAdapter?.selectAll()
    }

    private val mDeleteClickListener = View.OnClickListener {
        val deleteList = mAdapter?.getCheckItem()
        if (deleteList?.size!! > 0) {
            showCommonMessageDialogFragment(DialogMessage.COMMON_MESSAGE_TAG, "确认删除选中分类？",
                    object : CommonMessageDialogFragment.OnCommonMessageDialogInterface {
                        override fun onConfirm(dialog: DialogInterface?, which: Int) {
                            CategoryLogic.delete(context, deleteList)
                            stopCheckPattern()
                        }

                        override fun onCancel(dialog: DialogInterface?, which: Int) {
                            stopCheckPattern()
                        }
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_CATEGORY && resultCode == Activity.RESULT_OK) {
            val refresh = data?.getBooleanExtra(Constant.REFRESH, false)
            if (refresh!!) {
                getCategoryData()
            }
        }
    }

    class CategorySpanSize(adapter: CategoryGridAdapter?) : GridLayoutManager.SpanSizeLookup() {
        private val mAdapter = adapter
        override fun getSpanSize(position: Int): Int {
            val type = mAdapter?.getItemViewType(position)
            when (type) {
                CategoryGridAdapter.STATUS_ADD,
                CategoryGridAdapter.STATUS_NONE -> {
                    return 1
                }
                else -> return 3
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mAdapter?.isCheckPattern()!!) {
            stopCheckPattern()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}