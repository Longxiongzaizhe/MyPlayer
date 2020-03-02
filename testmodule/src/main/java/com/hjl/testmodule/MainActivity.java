package com.hjl.testmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hjl.testmodule.base.Course;
import com.hjl.testmodule.base.Student;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flatTest();
            }
        });

    }

    private void flatTest(){

        Disposable disposable = Observable.create((ObservableOnSubscribe<Student>) emitter -> {

            Student student = new Student("wujun", "123");
            Student student1 = new Student("long", "321");

            ArrayList<Course> data = new ArrayList<>();
            data.add(new Course("c1", "03"));
            data.add(new Course("c2", "04"));

            ArrayList<Course> data2 = new ArrayList<>();
            data2.add(new Course("c3", "01"));
            data2.add(new Course("c4", "02"));

            student.setCourseList(data);
            student1.setCourseList(data2);


            emitter.onNext(student);
            emitter.onNext(student1);
        }).flatMap(new Function<Student, ObservableSource<Course>>() {

            @Override
            public ObservableSource<Course> apply(Student student) throws Exception {
                return Observable.fromIterable(student.getCourseList());
            }
        }).subscribe(new Consumer<Course>() {
            @Override
            public void accept(Course s) throws Exception {
                Log.d("TAG", "accept: " + s.getId());
            }
        });


    }
}
