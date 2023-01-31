package com.example.xinlishequ2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.xinlishequ2.Bean.Comunity;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class RecieveCom extends AppCompatActivity {
    private TextView reccomname,reccominfo,reccomuser;
    private ImageView reccomback,reccomcollect;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recievecom);
        initView();
        getinfo();
        getisrelated();

        reccomcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = getIntent();
                String Id = in.getStringExtra("id");
                BmobQuery<Comunity> bmobQuery = new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<Comunity>() {
                    @Override
                    public void done(Comunity comunity, BmobException e) {

                        if (comunity.getIsrelated().equals("0")){
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Comunity com = new Comunity();
                            comunity.setObjectId(Id);
                            comunity.setIsrelated("1");
                            BmobRelation relation = new BmobRelation();
                            relation.add(user);
                            comunity.setRelation(relation);
                            comunity.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        reccomcollect.setImageResource(R.drawable.shoucang_black);
                                        Toast.makeText(RecieveCom.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(RecieveCom.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            //取消收藏
                            Intent in = getIntent();
                            String Id = in.getStringExtra("id");
                            User user = BmobUser.getCurrentUser(User.class);
                            Comunity com = new Comunity();
                            comunity.setObjectId(Id);
                            comunity.setIsrelated("0");
                            BmobRelation relation = new BmobRelation();
                            relation.remove(user);
                            comunity.setRelation(relation);
                            comunity.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        reccomcollect.setImageResource(R.drawable.sc_normal);
                                        Toast.makeText(RecieveCom.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(RecieveCom.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });


            }
        });
        reccomback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getisrelated() {
        Intent in=getIntent();
        String Id=in.getStringExtra("id");
        BmobQuery<Comunity> bmobQuery=new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<Comunity>() {
            @Override
            public void done(Comunity comunity, BmobException e) {
                if (comunity.getIsrelated().equals("1")){
                    reccomcollect.setImageResource(R.drawable.shoucang_black);
                }else {

                }
            }
        });
    }

    private void getinfo() {
        Intent intent=getIntent();
        String name=intent.getStringExtra("username");
        String info=intent.getStringExtra("info");
        String user=intent.getStringExtra("onw");
        reccomname.setText(name);
        reccominfo.setText(info);
        reccomuser.setText(user);

    }

    private void initView() {
        reccomback=findViewById(R.id.reccomback);
        reccomname=findViewById(R.id.reccomname);
        reccominfo=findViewById(R.id.reccominfo);
        reccomcollect=findViewById(R.id.reccomcollect);
        reccomuser=findViewById(R.id.reccomuser);
    }
}
