package com.yanqing.kotlindemo.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.constant.Constant
import com.yanqing.kotlindemo.db.entity.CategoryEntity
import com.yanqing.kotlindemo.logic.CategoryLogic

class AddCategoryActivity : ToolbarActivity(), CategoryLogic.OnGetCategoryListener {

    private val mCategoryInput by lazy { findViewById<EditText>(R.id.add_category_input) }
    private val mCategorySave by lazy { findViewById<EditText>(R.id.add_category_save) }

    private var mCategoryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData(savedInstanceState)
        mCategorySave.setOnClickListener(mSaveClickListener)
    }

    override fun initContentLayout(): View {
        return LayoutInflater.from(this).inflate(R.layout.activity_add_category, null)
    }

    override fun onSuccess(id: Int, categoryEntity: CategoryEntity) {
        mCategoryInput.setText(categoryEntity.category!!)
    }

    override fun onFailed(id: Int) {
        mCategoryId = -1
    }

    private fun initData(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //获取保存的对应参数
        } else {
            mCategoryId = intent?.getIntExtra(Constant.CATEGORY_ID, -1)!!
        }

        if (mCategoryId > -1) {
            CategoryLogic.findCategoryById(this, mCategoryId, this)
        }
    }

    private val mSaveClickListener = View.OnClickListener {

        val categoryTitle = mCategoryInput.text.toString()
        if (TextUtils.isEmpty(categoryTitle)) {
            showToast("名称不能为空")
            return@OnClickListener
        }

        val categoryEntity = CategoryEntity()
        categoryEntity.category = categoryTitle
        categoryEntity.timeStamp = System.currentTimeMillis()
        categoryEntity.canEdit = true
        categoryEntity.id = mCategoryId

        if (mCategoryId > 0) {
            CategoryLogic.update(this, categoryEntity)
        } else {
            CategoryLogic.insert(this, categoryEntity)
        }

        showToast("保存成功")

        reset()
    }

    private fun reset() {
        mCategoryId = -1
        mCategoryInput.text.clear()


        val intent = Intent()
        intent.putExtra(Constant.REFRESH, true)
        setResult(Activity.RESULT_OK, intent)
    }
}