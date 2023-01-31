package com.example.xinlishequ2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xinlishequ2.MainActivity;
import com.example.xinlishequ2.R;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;


public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //延时操作计时器
        Timer timer=new Timer();
        timer.schedule(timetest,2000);
        Bmob.initialize(this,"09bd3d0dc44ebd9e2180a6b4e998bd37");
    }
    TimerTask timetest=new TimerTask() {
        @Override
        public void run() {
            startActivity(new Intent(Splash.this, MainActivity.class));
            BmobUser bmobUser=BmobUser.getCurrentUser(BmobUser.class);
            if(bmobUser!=null){
                startActivity(new Intent(Splash.this, MainActivity.class));
                finish();
            }else {
                //没有登陆，跳转登陆页面
                startActivity(new Intent(Splash.this,Login.class));
                finish();
            }
        }
    };

}

