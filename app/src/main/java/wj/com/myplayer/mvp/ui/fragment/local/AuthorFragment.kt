package wj.com.myplayer.mvp.ui.fragment.local

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.commonlib.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_author.*
import wj.com.myplayer.R
import wj.com.myplayer.daodb.MediaDaoManager
import wj.com.myplayer.mvp.adapter.AuthorAdapter

class AuthorFragment : BaseFragment() {

    var adapter : AuthorAdapter? = null
    var datalist : List<String>? = null

    override fun initView(view: View?) {

        datalist = MediaDaoManager.getInstance().allAuthor
        adapter = AuthorAdapter(datalist!!)




    }

    override fun initData() {
        author_rv.layoutManager = LinearLayoutManager(context)
        author_rv.adapter = adapter
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_author
    }
}