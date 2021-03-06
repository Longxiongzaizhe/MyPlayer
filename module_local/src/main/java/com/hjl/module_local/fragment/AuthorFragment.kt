package com.hjl.module_local.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hjl.commonlib.base.BaseFragment
import com.hjl.commonlib.utils.DensityUtil
import com.hjl.commonlib.utils.RecycleViewVerticalDivider
import com.hjl.commonlib.utils.RxSchedulers
import com.hjl.module_local.R
import com.hjl.module_local.adapter.AuthorAdapter
import com.hjl.module_main.constant.FlagConstant
import com.hjl.module_main.constant.MediaConstant
import com.hjl.module_main.daodb.MediaAuthorEntity
import com.hjl.module_main.daodb.MediaAuthorManager
import com.hjl.module_main.daodb.MediaDaoManager
import com.hjl.module_main.router.RApp
import com.hjl.module_main.router.RLocal
import com.hjl.module_main.router.RMain
import com.hjl.module_main.service.MusicService
import io.reactivex.Observable
import kotlinx.android.synthetic.main.local_fragment_album.*
import kotlinx.android.synthetic.main.local_fragment_author.*
import java.util.*

@Route(path = RLocal.AUTHOR_FRAGMENT)
class AuthorFragment : BaseFragment(), BaseQuickAdapter.OnItemClickListener {


    var adapter : AuthorAdapter? = null
    var datalist  = MediaAuthorManager.get().loadAll()
    val TAG = "AuthorFragment"


    companion object{
        fun newInstance(bundle: Bundle): AuthorFragment {
            val authorFragment = AuthorFragment()
            authorFragment.arguments = bundle
            return authorFragment
        }

        fun newInstance(mBinder: MusicService.MusicBinder): AuthorFragment {
            val bundle = Bundle()
            bundle.putSerializable(FlagConstant.BINDER,mBinder)
            return newInstance(bundle)
        }
    }

    override fun initView(view: View?) {

        mMultipleStatusView.showLoading()
        adapter = AuthorAdapter(datalist).apply { onItemClickListener = this@AuthorFragment}


    }

    override fun initData() {
        author_rv.layoutManager = LinearLayoutManager(context)
        author_rv.adapter = adapter
        adapter?.setEmptyView(R.layout.layout_no_content,author_rv)
        mMultipleStatusView.showContent()
        var itemDivider : RecycleViewVerticalDivider = RecycleViewVerticalDivider(context,2,
                resources.getColor(R.color.common_divider_line_color),DensityUtil.dp2px(68f),0)
        author_rv.addItemDecoration(itemDivider)

    }

    override fun notifyDataChange() {
        super.notifyDataChange()

        mMultipleStatusView?.showLoading()
        val disposable = Observable.create<String> {

            MediaAuthorManager.get().deleteAll()
            for (author in MediaDaoManager.getInstance().allAuthor) {
                MediaAuthorManager.get().insert(author)
            }

            datalist.clear()
            datalist.addAll(MediaAuthorManager.get().loadAll())
            it.onNext(FlagConstant.RXJAVA_KEY_01)
        }.compose(RxSchedulers.io_main()).subscribe {
            adapter?.notifyDataSetChanged()
            mMultipleStatusView?.showContent()
        }

        addDisposable(disposable)





    }

    override fun getLayoutId(): Int {
        return R.layout.local_fragment_author
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        ARouter.getInstance().build(RMain.RMusicList)
                .withInt(FlagConstant.INTENT_KEY01,MediaConstant.LIST_AUTHOR)
                .withString(FlagConstant.INTENT_KEY02,datalist[position].name)
                .navigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e(TAG,"onDestroyView")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(TAG,"onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.e(TAG,"onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        Log.e(TAG,"onAttach")
        super.onAttach(context)
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG,"onPause")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG,"onResume")
    }

    override fun onStop() {
        super.onStop()
        Log.e(TAG,"onStop")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e(TAG,"onDetach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG,"onDestroy")
    }
}