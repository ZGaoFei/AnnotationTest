package com.zgf.annotationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zgf.annotationtest.auto.MainActivity$$ApiAnnotation;
import com.zgf.daggerlib.MyClass;
import com.zgf.daggerlib.annotation.ApiAnnotation;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyClass().test();
            }
        });

        test();
    }

    @ApiAnnotation(author = "zgfei", date = "20201127", version = 1)
    public void test() {
        MainActivity$$ApiAnnotation apiAnnotation = new MainActivity$$ApiAnnotation();
        String message = apiAnnotation.getMessage();

        Log.e("zgf", "======message========" + message);
    }
}
