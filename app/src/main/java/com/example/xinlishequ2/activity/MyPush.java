package com.example.xinlishequ2.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xinlishequ2.Adapter.MyPushAdapter;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;
import com.google.android.material.appbar.AppBarLayout;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class MyPush extends AppCompatActivity {
    private SwipeMenuRecyclerView mypushrv;
    private ImageView mypushback;
    private TextView mypusherror;
    private SwipeRefreshLayout mypushswipe;
     List<Post> data;
     MyPushAdapter myPushAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypush);
        Bmob.initialize(MyPush.this,"09bd3d0dc44ebd9e2180a6b4e998bd37");
        initView();
        Refresh();
        mypushrv.setSwipeMenuCreator(swipeMenuCreator);
        mypushrv.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        mypushswipe.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);

        mypushswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
        mypushback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void Refresh() {
        User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Post> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("auther",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                 mypushswipe.setRefreshing(false);
                if(e==null){
                    data=list;
                    if(data.size()>0){
                        mypushswipe.setVisibility(View.VISIBLE);
                        mypushrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));

                        myPushAdapter=new MyPushAdapter(MyPush.this,data);
                        mypushrv.setLayoutManager(new LinearLayoutManager(MyPush.this));
                        mypushrv.setAdapter(myPushAdapter);
                    }else {
                        mypusherror.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MyPush.this, "????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // ????????????????????????
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // ???????????????
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyPush.this)
                    .setTextColor(Color.WHITE)
                    .setBackgroundColor(Color.RED)
                    .setText("??????")
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };

    // ?????????????????????
    SwipeMenuItemClickListener swipeMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // ??????????????????????????????????????????????????????Item???????????????????????????
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection();//????????????????????????
            final int adapterPosition = menuBridge.getAdapterPosition();//    recyclerView???Item???position???
            int position = menuBridge.getPosition();// ?????????RecyclerView???Item??????Position???

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

                Post post = new Post();
                post.setObjectId(data.get(adapterPosition).getObjectId());
                post.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            data.remove(adapterPosition);//??????item
                            myPushAdapter.notifyDataSetChanged();
                            Toast.makeText(MyPush.this, "????????????", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MyPush.this, "????????????", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    };


    private void initView() {
        mypushback=findViewById(R.id.mypushback);
        mypushswipe=findViewById(R.id.mypushswipe);
        mypushrv=findViewById(R.id.mypushrv);
        mypusherror=findViewById(R.id.mypusherror);
    }
}
