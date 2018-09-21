package com.yanqing.kotlindemo.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.yanqing.kotlindemo.R
import com.yanqing.kotlindemo.fragment.*
import com.yanqing.kotlindemo.views.DockBarView

class MainActivity : BaseActivity(), DockBarView.OnDockBarItemClickListener {
    private val mDockBarView: DockBarView by lazy { findViewById<DockBarView>(R.id.dock_bar) }
    private var mCurrentFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDockBarView.setOnDockBarItemClickListener(this)
        mDockBarView.setItemSelected(DockBarView.DOCK_BAR_HOME)
    }

    override fun onDockBarItemClick(index: Int) {
        val itemIndex: Int
        val tag: String
        val itemFragment: BaseFragment
        when (index) {
            DockBarView.DOCK_BAR_HOME -> {
                itemIndex = DockBarView.DOCK_BAR_HOME;
                tag = HomeFragment.TAG;
                val fragment = supportFragmentManager.findFragmentByTag(tag);
                itemFragment = checkFragment(fragment) {
                    HomeFragment.newInstance()
                }
            }
            DockBarView.DOCK_BAR_CATEGORY -> {
                itemIndex = DockBarView.DOCK_BAR_CATEGORY;
                tag = CategoryFragment.TAG;
                val fragment = supportFragmentManager.findFragmentByTag(tag);
                itemFragment = checkFragment(fragment) {
                    CategoryFragment.newInstance()
                }
            }
            DockBarView.DOCK_BAR_SEARCH -> {
                itemIndex = DockBarView.DOCK_BAR_SEARCH;
                tag = SearchFragment.TAG;
                val fragment = supportFragmentManager.findFragmentByTag(tag);
                itemFragment = checkFragment(fragment) {
                    SearchFragment.newInstance()
                }
            }
            DockBarView.DOCK_BAR_PERSON -> {
                itemIndex = DockBarView.DOCK_BAR_PERSON;
                tag = PersonCenterFragment.TAG;
                val fragment = supportFragmentManager.findFragmentByTag(tag);
                itemFragment = checkFragment(fragment) {
                    PersonCenterFragment.newInstance()
                }
            }

            else -> {
                itemIndex = DockBarView.DOCK_BAR_HOME;
                tag = HomeFragment.TAG;
                val fragment = supportFragmentManager.findFragmentByTag(tag);
                itemFragment = checkFragment(fragment) {
                    HomeFragment.newInstance()
                }
            }
        }

        showFragment(itemIndex, itemFragment, tag)
    }

    private fun showFragment(index: Int, fragment: BaseFragment, tag: String) {
        mCurrentFragment = fragment
        mDockBarView.setItemSelected(index)
        supportFragmentManager.inTransaction {
            replace(R.id.main_container, fragment, tag)
        }
    }

    private fun checkFragment(fragment: Fragment?, newFragment: () -> BaseFragment): BaseFragment {
        when (fragment == null) {
            true -> return newFragment()
            false -> return fragment as BaseFragment
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (mCurrentFragment?.onKeyDown(keyCode, event)!!) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
