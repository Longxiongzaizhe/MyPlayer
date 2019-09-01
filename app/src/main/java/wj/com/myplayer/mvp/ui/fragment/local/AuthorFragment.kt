package wj.com.myplayer.mvp.ui.fragment.local

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.commonlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_author.*
import wj.com.myplayer.R
import wj.com.myplayer.daodb.MediaAuthorEntity
import wj.com.myplayer.daodb.MediaAuthorManager
import wj.com.myplayer.mvp.adapter.AuthorAdapter

class AuthorFragment : BaseFragment() {

    var adapter : AuthorAdapter? = null
    var datalist : List<MediaAuthorEntity> = MediaAuthorManager.get().getAll()

    override fun initView(view: View?) {

        mMultipleStatusView.showLoading()
        adapter = AuthorAdapter(datalist)

    }

    override fun initData() {
        author_rv.layoutManager = LinearLayoutManager(context)
        author_rv.adapter = adapter
        mMultipleStatusView.showContent()

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_author
    }
}