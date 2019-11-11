package com.hjl.module_net.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hjl.module_net.R
import com.hjl.module_net.net.vo.NetFunctionBean

/**
 *
 * created by long on 2019/11/11
 */
class NetFunctionAdapter(datalist : MutableList<NetFunctionBean>) : BaseQuickAdapter<NetFunctionBean,BaseViewHolder>(R.layout.item_net_function,datalist) {

    override fun convert(helper: BaseViewHolder?, item: NetFunctionBean?) {
        helper?.setText(R.id.function_tv,item?.name)
        helper?.setImageResource(R.id.function_iv, item!!.cover)
    }

}