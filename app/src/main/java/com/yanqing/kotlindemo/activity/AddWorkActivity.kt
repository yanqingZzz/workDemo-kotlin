package com.yanqing.kotlindemo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.db.constant.DbConstant
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.db.entity.WorkEntity
import com.yanqing.kotlindemo.db.helper.WorkDbHelper
import com.yanqing.kotlindemo.logic.CategoryLogic

/**
 * 后面可以考虑对事件的属性进行优化，比如：增加录音，增加图片功能
 */
class AddWorkActivity : ToolbarActivity(), CategoryLogic.OnGetCategoryListListener {
    private val mTitleInput by lazy { findViewById<EditText>(R.id.add_title_input) }
    private val mSubTitleInput by lazy { findViewById<EditText>(R.id.add_sub_title_input) }
    private val mContentInput by lazy { findViewById<EditText>(R.id.add_content_input) }
    private val mFavorite by lazy { findViewById<ImageView>(R.id.add_favorite) }
    private val mSaveButton by lazy { findViewById<TextView>(R.id.add_save) }
    private val mCategoryParent by lazy { findViewById<LinearLayout>(R.id.add_category_container) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addBackArrowEnable()
        title = "添加"
        setFavoriteListener()
        setSaveListener()
        getCategoryList()
    }

    override fun initContentLayout(): View {
        return LayoutInflater.from(this).inflate(R.layout.activity_add_work, null)
    }

    private fun setFavoriteListener() {
        mFavorite.setOnClickListener {
            mFavorite.isSelected = !mFavorite.isSelected
        }
    }

    private fun setSaveListener() {
        mSaveButton.setOnClickListener {
            val title = mTitleInput.text.toString()
            val subTitle = mSubTitleInput.text.toString()
            val content = mContentInput.text.toString()
            if (TextUtils.isEmpty(title)) {
                showToast("标题不能为空")
                return@setOnClickListener
            }

            val work = WorkEntity()
            work.title = title
            work.subTitle = subTitle
            work.category = getSelectedCategory()
            work.favorite = mFavorite.isSelected
            work.content = content
            work.timeStamp = System.currentTimeMillis()

            WorkDbHelper.getInstance(this).insert(work)

            showToast("已保存")

            reset()
        }
    }

    private fun reset() {
        mTitleInput.text.clear();
        mSubTitleInput.text.clear()
        mFavorite.isSelected = false
        mContentInput.text.clear()
        mCategoryParent.getChildAt(0).isSelected = true
        for (i in 1..mCategoryParent.childCount) {
            mCategoryParent.getChildAt(i)?.isSelected = false
        }

        val intent = Intent()
        intent.putExtra(Constant.REFRESH, true)
        setResult(Activity.RESULT_OK, intent)
    }

    private fun getCategoryList() {
        CategoryLogic.getCategoryData(this, this)
    }

    override fun onSuccess(data: ArrayList<CategoryEntity>) {
        for (entity in data) {
            val categoryView = LayoutInflater.from(this).inflate(R.layout.add_work_categoty_item, mCategoryParent, false) as TextView
            categoryView.text = entity.category
            categoryView.setOnClickListener(mCategoryItemClickListener)
            mCategoryParent.addView(categoryView)
        }
        mCategoryParent.getChildAt(0)?.isSelected = true
    }

    override fun onFailed(page: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mCategoryItemClickListener = View.OnClickListener {
        for (i in 0..mCategoryParent.childCount) {
            mCategoryParent.getChildAt(i)?.isSelected = false
        }
        it.isSelected = true
    }

    private fun getSelectedCategory(): String {
        for (i in 0..mCategoryParent.childCount) {
            if (mCategoryParent.getChildAt(i)?.isSelected!!) {
                return (mCategoryParent.getChildAt(i) as TextView).text.toString()
            }
        }
        return DbConstant.CATEGORY_DEFAULT[DbConstant.CATEGORY_DEFAULT.size - 1]
    }
}