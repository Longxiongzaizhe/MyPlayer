package com.hjl.testmodule

import android.annotation.SuppressLint
import android.databinding.ObservableDouble
import android.util.Log
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.hjl.commonlib.base.BaseMultipleActivity
import com.hjl.module_main.router.RApp
import com.hjl.module_main.router.RLocal
import com.hjl.testmodule.base.Course
import com.hjl.testmodule.base.Student
import io.reactivex.Observable
import io.reactivex.Observable.create
import io.reactivex.ObservableOnSubscribe
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java_test.*




class RxJavaTestActivity : BaseMultipleActivity(), View.OnClickListener {
    override fun getLayoutId(): Int {
        return R.layout.activity_rx_java_test
    }

    var observer: Observer<String> = object : Observer<String> {
        override fun onSubscribe(d: Disposable) {
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

    override fun initView() {
        test_btn_one.setOnClickListener(this)
        test_btn_two.setOnClickListener(this)
        test_btn_three.setOnClickListener(this)
        test_btn_four.setOnClickListener(this)
        flatmap.setOnClickListener {
            flatmapTest()
        }
    }

    @SuppressLint("CheckResult")
    fun flatmapTest(){

        var studentsList = ArrayList<Student>()
        var student1 = Student("yi","1")
        var student2 = Student("er","2")
        studentsList.add(student1)
        studentsList.add(student2)

        var coureList = ArrayList<Course>()
        coureList.add(Course("couse1","1"))
        coureList.add(Course("couse2","2"))

        var coureList2 = ArrayList<Course>()
        coureList2.add(Course("couse3","3"))
        coureList2.add(Course("couse4","4"))

        student1.courseList = coureList
        student2.courseList = coureList2

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.test_btn_one -> observable.safeSubscribe(observer)

            R.id.test_btn_two -> create(ObservableOnSubscribe<Int> { e ->
                e!!.onNext(1)
                e.onNext(2)
                e.onNext(3)
                e.onError(object : Throwable("on error"){})
            }).subscribe(object : Observer<Int>{

                override fun onComplete() {
                    Log.d("Observer","showComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("Observer","onSubscribe")
                }

                override fun onNext(value: Int) {
                    Log.d("Observer", "onNext$value")
                }

                override fun onError(e: Throwable) {
                    Log.d("Observer","showError")
                }

            })

            R.id.test_btn_three -> observable.
                    subscribeOn(Schedulers.newThread()). // 新线程执行耗时操作
                    observeOn(AndroidSchedulers.mainThread()). // 主线程进行UI更新等
                    subscribe(observer)
            R.id.test_btn_four -> ARouter.getInstance().build(RLocal.LOCAL_FRAGMENT).navigation()

        }
    }
}
