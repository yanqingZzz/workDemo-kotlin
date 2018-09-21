package com.yanqing.kotlindemo.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.activity.AddWorkActivity
import com.yanqing.kotlindemo.adapter.BaseAdapter
import com.yanqing.kotlindemo.adapter.HomeFragmentAdapter
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.logic.CategoryLogic
import com.yanqing.kotlindemo.logic.HomeLogic

class HomeFragment : BaseFragment(), HomeLogic.OnGetHomeListListener {
    companion object {
        const val TAG = "HomeFragment"
        private const val REQUEST_CODE_ADD_WORK = 1000

        fun newInstance(): HomeFragment {
            val args = Bundle()
            args.putInt("example", 0)

            val homeFragment = HomeFragment()
            homeFragment.arguments = args
            return homeFragment
        }
    }


    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: HomeFragmentAdapter? = null
    private var mAddFab: FloatingActionButton? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view?.findViewById(R.id.home_recycler_view)
        mAddFab = view?.findViewById(R.id.home_add_fab)

        mRecyclerView?.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAddFab?.setOnClickListener(mAddClickListener)

        mAdapter = HomeFragmentAdapter(context)
        mRecyclerView?.adapter = mAdapter;
        mRecyclerView?.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        mAdapter?.setItemClickListener(mItemClickListener);
        mAdapter?.setItemFavoriteClickListener(mItemFavoriteClickListener);
        mAdapter?.setStatus(BaseAdapter.STATUS_LOADING)
        getListData()
    }

    private fun getListData() {
        HomeLogic.getHomeListData(context, this)
    }

    private fun getListByPage(page: Int) {
        HomeLogic.getHomeListDataByPage(context, page, this)
    }

    override fun onSuccess(data: ArrayList<WorkEntity>, categoryList: ArrayList<CategoryEntity>) {
        mAdapter?.setItemData(data, categoryList)
    }

    override fun onSuccessByPage(data: ArrayList<WorkEntity>, hasMore: Boolean, page: Int) {
        mAdapter?.setItemDataByPage(data, hasMore)
    }

    override fun onFailed(page: Int) {
        if (page == 0) {
            mAdapter?.setStatus(BaseAdapter.STATUS_ERROR)
        } else {
            mAdapter?.setStatus(BaseAdapter.STATUS_ERROR_MORE)
        }
    }

    private val mAddClickListener = View.OnClickListener {
        startActivityForResult(Intent(context, AddWorkActivity::class.java), REQUEST_CODE_ADD_WORK)
    }

    private val mItemClickListener = View.OnClickListener {
        val position = it.getTag() as Int
        showToast("点击了第" + position + "个item")
    }

    private val mItemFavoriteClickListener = View.OnClickListener {
        val position = it.tag as Int
        it.isSelected = !it.isSelected
        mAdapter?.setItemFavorite(position, it.isSelected)
        HomeLogic.updateItemFavorite(context, mAdapter?.getItemData(position)!!)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_WORK && resultCode == Activity.RESULT_OK) {
            val refresh = data?.getBooleanExtra(Constant.REFRESH, false)
            if (refresh!!) {
                mAdapter?.reset()
                getListData()
            }
        }
    }
}