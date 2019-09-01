package com.example.testmodule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.commonlib.base.BaseMultipleActivity
import kotlinx.android.synthetic.main.activity_rx_java_test.*

import android.R.attr.tag
import android.util.Log
import io.reactivex.*
import io.reactivex.Observable.create
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class RxJavaTestActivity : BaseMultipleActivity(), View.OnClickListener {

    var observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable?) {
            Log.d("observer", "onSubscribe!")
        }

        override fun onComplete() {
            Log.d("observer", "Completed!")
        }

        override fun onNext(s: String) {
            Log.d("observer", "Item: $s")
            var sb = StringBuffer(rx_test_tv.text)
            sb.append("观察者处理线程：")
            sb.append(Thread.currentThread().name)
            sb.append("\n")
            rx_test_tv.text = sb.toString()
        }

        override fun onError(e: Throwable) {
            Log.d("observer", "Error!")
        }

    }

    var observable : Observable<String> = create { e ->
        e?.onNext("hello")
        e?.onNext("rx")
        e?.onNext("java")
        var sb = StringBuffer(rx_test_tv.text)
        sb.append("被观察者所在线程：")
        sb.append(Thread.currentThread().name)
        sb.append("\n")
        rx_test_tv.text = sb.toString()
    }

    override fun initTitle() {
        mTitleCenterTv.text = "测试组件—— RxJava"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java_test)

        test_btn_one.setOnClickListener(this)
        test_btn_two.setOnClickListener(this)
        test_btn_three.setOnClickListener(this)
        test_btn_four.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.test_btn_one -> observable.safeSubscribe(observer)

            R.id.test_btn_two -> create(object : ObservableOnSubscribe<Int>{
                override fun subscribe(e: ObservableEmitter<Int>?) {
                    e!!.onNext(1)
                    e.onNext(2)
                    e.onNext(3)
                    e.onError(object : Throwable("on error"){})
                }
            }).subscribe(object : Observer<Int>{
                override fun onComplete() {
                    Log.d("Observer","onComplete")
                }

                override fun onSubscribe(d: Disposable?) {
                    Log.d("Observer","onSubscribe")
                }

                override fun onNext(value: Int?) {
                    Log.d("Observer", "onNext$value")
                }

                override fun onError(e: Throwable?) {
                    Log.d("Observer","onError")
                }

            })

            R.id.test_btn_three -> observable.
                    subscribeOn(Schedulers.newThread()). // 新线程执行耗时操作
                    observeOn(AndroidSchedulers.mainThread()). // 主线程进行UI更新等
                    subscribe(observer)

        }
    }
}