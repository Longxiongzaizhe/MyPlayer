package com.hjl.commonlib.network.retrofitmvp;

import com.hjl.commonlib.utils.RxSchedulers;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Description BaseMvpModelImpl Y: create ApiServer
 * Author long
 * Date 2020/2/28 16:24
 */
public abstract class BaseMvpModel<Y> implements IBaseMvpModel {

    protected Y apiServer;
    protected ArrayList<Disposable> requestList ;

    public BaseMvpModel(){

    }

    @Override
    public void onCreateModel() {
        apiServer = ApiRetrofit.getInstance().createApiServer(createApiService());
        requestList = new ArrayList<>();
    }

    public <T> void requestNetwork(Observable<HttpBaseResult<T>> observable, HttpObserverAdapter<T> observer) {
        observable.map(new ResultFilter()).compose(RxSchedulers.io_main()).subscribe(observer);
        requestList.add(observer);
    }

    @Override
    public void onDestroyModel() {
        for (Disposable disposable : requestList){
            disposable.dispose();
        }
    }

    public abstract Class<Y> createApiService();

    public abstract String setTAG();

}
