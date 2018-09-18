package com.yanqing.kotlindemo.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.activity.AddCategoryActivity
import com.yanqing.kotlindemo.adapter.BaseAdapter
import com.yanqing.kotlindemo.adapter.CategoryGridAdapter
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.logic.CategoryLogic

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
        mAdapter?.setAddClickListener(mAddItemClickListener)
        mCategoryGridView?.adapter = mAdapter
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

    }

    private val mAddItemClickListener = View.OnClickListener {
        startActivityForResult(Intent(context, AddCategoryActivity::class.java), REQUEST_CODE_ADD_CATEGORY)
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

    class CategoryItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
        private val mPadding = context.resources.getDimension(R.dimen.category_item_padding).toInt()

        override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
            val position = parent?.getChildAdapterPosition(view)
            val location = position!! % 3
            when (location) {
                0 -> {
                    outRect?.left = mPadding * 2 / 3
                    outRect?.right = mPadding / 3
                }
                1 -> {
                    outRect?.left = mPadding / 2
                    outRect?.right = mPadding / 2
                }
                2 -> {
                    outRect?.left = mPadding / 3
                    outRect?.right = mPadding * 2 / 3
                }
            }

            outRect?.top = mPadding / 2
            outRect?.bottom = mPadding / 2
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
}