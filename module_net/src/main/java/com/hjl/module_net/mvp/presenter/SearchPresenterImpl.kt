package com.hjl.module_net.mvp.presenter

import com.hjl.commonlib.network.retrofit.HttpObserver
import com.hjl.commonlib.utils.RxSchedulers
import com.hjl.module_net.mvp.contract.NetSearchContract
import com.hjl.module_net.net.vo.HotSearchVo

/**
 *
 * created by long on 2019/10/22
 */
class SearchPresenterImpl : NetBasePresenter<NetSearchContract.INetSearchView>(),NetSearchContract.INetSearchPresenter{

    override fun getHotSearch() {
        addDisposable(apiServer.hotSearch,object :HttpObserver<HotSearchVo>(){
            override fun onSuccess(o: HotSearchVo?) {
                view.getHotSearchSuccess(o)
            }

            override fun onError(msg: String?) {
                view.getHotSearchFail(msg)
            }

        })
    }

    // showtype=14&keyword={keyword}&pagesize={pagesize}&page={page}&highlight=em&tag_aggr=1&tagtype=全部
    // &plat=0&sver=5&correct=1&api_ver=1&version=9108&area_code=1&tag=1&with_res_tag=1

    override fun search(keyWord: String?, pageIndex: String?, pageSize: String?) {
        val disposable =
                apiServer.searchSong("14",keyWord,pageSize,pageIndex,"em",
                        "1","全部","0","5","1","1",
                        "9108","1","1","1").subscribe({
                    view.onSearchSuccess(it)
                },{
                    view.onSearchFail(it.message)
                })
        addDisposable(disposable)
    }

    override fun getAssociativeWord(keyWord: String?) {
        val disposable =
                apiServer.getAssociativeWord("0","302",keyWord,"1").compose(RxSchedulers.io_main())
                        .subscribe({
                            view.onGetAssociativeWordSuccess(it)
                        },{
                            view.onGetAssociativeWordFail(it.message)
                        })
        addDisposable(disposable)
    }





}