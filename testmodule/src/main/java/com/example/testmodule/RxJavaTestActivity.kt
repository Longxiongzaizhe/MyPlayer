package com.example.testmodule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.commonlib.base.BaseMultipleActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_rx_java_test.*

import android.R.attr.tag
import android.util.Log
import io.reactivex.Observable.create
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.reactivestreams.Subscriber
import javax.xml.transform.Templates


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
        }

        override fun onError(e: Throwable) {
            Log.d("observer", "Error!")
        }
    }

    var observable : Observable<String> = create(object : ObservableOnSubscribe<String>{
        override fun subscribe(e: ObservableEmitter<String>?) {
            e?.onNext("hello")
            e?.onNext("rx")
            e?.onNext("java")
        }

    })

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
            R.id.test_btn_one -> Unit
            R.id.test_btn_two -> Unit
        }
    }
}
