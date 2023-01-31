package com.example.xinlishequ2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class reply extends AppCompatActivity {
    private EditText replytext;
    private ImageView replyback;
    private Button replybutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        initView();
        replyback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        replybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(replytext.getText().toString().isEmpty()){
                    Toast.makeText(reply.this, "请输入内容", Toast.LENGTH_SHORT).show();
                }else {
                        Post po=new Post();
                    po.setReply(replytext.getText().toString());
                    po.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                replytext.setText("");
                                Toast.makeText(reply.this, "发布回复成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }     else {
                                Toast.makeText(reply.this, "发布回复失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        replyback=findViewById(R.id.replyback);
        replybutton=findViewById(R.id.replybutton);
        replytext=findViewById(R.id.replytext);
    }
}
