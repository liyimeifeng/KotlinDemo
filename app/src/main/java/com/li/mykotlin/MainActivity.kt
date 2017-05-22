package com.li.mykotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        tv.text = "这是一个Kotlin Demo";
        tv.setOnClickListener { finish() }
        login.setOnClickListener {
            login()
        }
        if (username_edittext.text == null || password_edittext.text == null){
            login.isEnabled = false;
        }
    }

    fun login(){
            login.text="正在进行登陆跳转";
    }
}
