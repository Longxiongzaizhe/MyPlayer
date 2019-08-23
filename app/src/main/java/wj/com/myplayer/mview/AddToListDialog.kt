package wj.com.myplayer.mview

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.commonlib.utils.DensityUtil
import com.example.commonlib.utils.RecycleViewVerticalDivider
import com.example.commonlib.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_add_to_list.*
import wj.com.myplayer.R
import wj.com.myplayer.daoDB.MediaListEntity
import wj.com.myplayer.daoDB.MediaListManager
import wj.com.myplayer.daoDB.MediaRelEntity
import wj.com.myplayer.daoDB.MediaRelManager
import wj.com.myplayer.view.adapter.SimpleMusicListAdapter

class AddToListDialog(context:Context,var musicId: Long) : Dialog(context, R.style.BaseDialogStyle) {

    var data: List<MediaListEntity> = MediaListManager.getInstance().allList!!
    var adapter : SimpleMusicListAdapter? = null
    var relManager = MediaRelManager.getInstance()

    init {

        val view = LayoutInflater.from(context).inflate(R.layout.layout_add_to_list,null,false)
        setContentView(view)
        list_add_cancel.setOnClickListener {dismiss()}

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        list_add_rv.layoutManager = LinearLayoutManager(context)
        list_add_rv.addItemDecoration(RecycleViewVerticalDivider(context,1,
                context.resources.getColor(R.color.common_divider_line_color),DensityUtil.dp2px(15f),0,true))
        adapter = SimpleMusicListAdapter(data)
        adapter!!.setOnItemClickListener { adapter, view, position ->
            relManager.insert(MediaRelEntity(null,data.get(position).id,musicId))
            ToastUtil.showSingleToast("添加成功")
            dismiss()
        }
        list_add_rv.adapter = adapter
    }

    fun initView(){
        val window = window
        val lp = window.attributes
        lp.gravity = Gravity.BOTTOM
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp.height = DensityUtil.dp2px(300f)
        window.attributes = lp
    }

}