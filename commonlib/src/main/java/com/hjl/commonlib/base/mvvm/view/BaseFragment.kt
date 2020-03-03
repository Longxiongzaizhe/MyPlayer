package com.hjl.commonlib.base.mvvm.view

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.tencent.bugly.proguard.r

/**
 * Description // Fragment 基类
 * Date 2020/3/3 13:36
 * created by long
 */
abstract class BaseFragment : Fragment() {

    protected val TAG: String = this.javaClass.simpleName

    lateinit var mContext: BaseActivity

    private var isViewCreated: Boolean = false // 界面是否已创建完成
    protected var isVisibleToUser: Boolean = false // 是否对用户可见
    private var isDataLoaded: Boolean = false// 数据是否已请求, isNeedReload()返回false的时起作用
    private var isFragmentHidden: Boolean = true // 记录当前fragment的是否隐藏

    @LayoutRes
    abstract fun initLayoutResID(): Int

    protected fun initDataBinding(view: View) {

    }

    abstract fun initData()

    abstract fun initView()

    abstract fun initLoad()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(initLayoutResID(), container,false)
        initDataBinding(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }


    /**
     * show()、hide()场景下，尝试请求数据
     */
    protected fun tryLoadData1() {

        //
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            initLoad()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }


    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private fun dispatchParentHiddenState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseFragment && !child.isHidden) {
                child.tryLoadData1()
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     */
    private fun isParentHidden(): Boolean {
        val fragment: Fragment? = parentFragment
        if (fragment == null) {
            return false
        } else if (fragment is BaseFragment && !fragment.isHidden) {
            return false
        }
        return true
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentHidden = hidden
        if (!hidden) {
            tryLoadData1()
        }
    }



    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     */
    @Suppress("DEPRECATION")
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        tryLoadData()
    }


    /**
     * ViewPager场景下，尝试请求数据
     */
    protected fun tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            initLoad()
            isDataLoaded = true
            dispatchParentVisibleState()
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private fun isParentVisible(): Boolean {
        val fragment: Fragment? = parentFragment
        return fragment == null || (fragment is BaseFragment && fragment.isVisibleToUser)
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseFragment && child.isVisibleToUser) {
                child.tryLoadData()
            }
        }
    }


    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     */
    protected fun isNeedReload(): Boolean = false

    override fun onDestroy() {
        isViewCreated = false
        isVisibleToUser = false
        isDataLoaded = false
        isFragmentHidden = true

        super.onDestroy()
    }
}