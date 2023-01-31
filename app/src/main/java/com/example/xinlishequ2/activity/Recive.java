package com.example.xinlishequ2.activity;

import android.content.Intent;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Recive extends AppCompatActivity {
    private TextView username,time,content,replytext;
    private ImageView back,rec_collect;
    private EditText replyet;
    private Button replybutton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);

        initView();
        initDate();
        getisrelated();
        //监听返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //回复功能
        replybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=getIntent();
                String Id=in.getStringExtra("id");
                BmobQuery<Post> bmobQuery=new BmobQuery<>();
                User user= BmobUser.getCurrentUser(User.class);
                Post po=new Post();
                po.setObjectId(Id);
                po.setReply(replyet.getText().toString());
                po.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(Recive.this, "回复成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Recive.this, "回复失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        rec_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=getIntent();
                String Id=in.getStringExtra("id");
                BmobQuery<Post> bmobQuery=new BmobQuery<>();
                bmobQuery.getObject(Id, new QueryListener<Post>() {
                    @Override
                    public void done(Post post, BmobException e) {
                        if (post.getIsrelated().equals("0")){
                            Intent in=getIntent();
                            String Id=in.getStringExtra("id");
                            User user= BmobUser.getCurrentUser(User.class);
                            Post po=new Post();
                            po.setObjectId(Id);
                            po.setIsrelated("1");
                            BmobRelation relation=new BmobRelation();
                            relation.add(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_collect.setImageResource(R.drawable.shoucang_black);
                                        Toast.makeText(Recive.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Recive.this, "收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Intent in=getIntent();
                            String Id=in.getStringExtra("id");
                            User user= BmobUser.getCurrentUser(User.class);
                            Post po=new Post();
                            po.setObjectId(Id);
                            po.setIsrelated("0");
                            BmobRelation relation=new BmobRelation();
                            relation.remove(user);
                            po.setRelation(relation);
                            po.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e==null){
                                        rec_collect.setImageResource(R.drawable.sc_normal);
                                        Toast.makeText(Recive.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Recive.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

    }

    private void getisrelated() {
        Intent in=getIntent();
        String Id=in.getStringExtra("id");
        BmobQuery<Post> bmobQuery=new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<Post>() {
            @Override
            public void done(Post post, BmobException e) {
                    if (post.getIsrelated().equals("1")){
                        rec_collect.setImageResource(R.drawable.shoucang_black);
                    }else {

                    }
            }
        });
    }

    private void initDate() {
        Intent a = getIntent();
        String usernamea = a.getStringExtra("username");
        String contenta = a.getStringExtra("content");
        String timea = a.getStringExtra("time");
        String reply=a.getStringExtra("reply");

        username.setText(usernamea);
        content.setText(contenta);
        time.setText(timea);
        replytext.setText(reply);

    }

    private void initView() {
        username=findViewById(R.id.username);
        content=findViewById(R.id.content);
        time=findViewById(R.id.time);
        back=findViewById(R.id.back);
        rec_collect=findViewById(R.id.rec_collect);
        replytext=findViewById(R.id.replytext);
        replyet=findViewById(R.id.replyet);
        replybutton=findViewById(R.id.replybutton);

    }
}
