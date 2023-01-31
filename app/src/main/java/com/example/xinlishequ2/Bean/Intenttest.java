package com.example.xinlishequ2.Bean;

import android.content.Context;
import android.content.Intent;

import com.example.xinlishequ2.activity.reply;

public class Intenttest extends Intent {
    private String ID;

    public Intenttest(Context context, Class<reply> replyClass,String ID) {
        super();
        this.ID=ID;
    }
}
