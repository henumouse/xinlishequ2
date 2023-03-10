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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xinlishequ2.Adapter.MyComunityAdapter;
import com.example.xinlishequ2.Adapter.MyPushAdapter;
import com.example.xinlishequ2.Bean.Comunity;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;
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

public class MyComunity extends AppCompatActivity {
    private SwipeMenuRecyclerView mycomunityrv;
    private ImageView mycomunityback;
    private TextView mycomunityerror;
    private SwipeRefreshLayout mycomunityswipe;
    private List<Comunity> data;
    private MyComunityAdapter myComunityAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycomunity);
        Bmob.initialize(MyComunity.this,"09bd3d0dc44ebd9e2180a6b4e998bd37");
        initView();
        Refresh();
        //gaidong
        mycomunityrv.setSwipeMenuCreator(swipeMenuCreator);
        mycomunityrv.setSwipeMenuItemClickListener(swipeMenuItemClickListener);
        mycomunityswipe.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        mycomunityback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mycomunityswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
    }

    private void Refresh() {
        User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Comunity> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("user",user);
        bmobQuery.order("-createdAt");
        bmobQuery.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                mycomunityswipe.setRefreshing(false);
                if(e==null){
                    data=list;
                    if(data.size()>0){
                        mycomunityswipe.setVisibility(View.VISIBLE);
                        mycomunityrv.addItemDecoration(new DefaultItemDecoration(Color.GRAY));
//                        mycomunityrv.setSwipeMenuCreator(swipeMenuCreator);
//                        mycomunityrv.setSwipeMenuItemClickListener(swipeMenuItemClickListener);

                        myComunityAdapter=new MyComunityAdapter(MyComunity.this,data);
                        mycomunityrv.setLayoutManager(new LinearLayoutManager(MyComunity.this));
                        mycomunityrv.setAdapter(myComunityAdapter);
                    }else {
                        mycomunityerror.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(MyComunity.this, "????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }// ????????????????????????
    SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        // ???????????????
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(MyComunity.this)
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

                Comunity comunity = new Comunity();
                comunity.setObjectId(data.get(adapterPosition).getObjectId());
                comunity.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            data.remove(adapterPosition);//??????item
                            myComunityAdapter.notifyDataSetChanged();
                            Toast.makeText(MyComunity.this, "????????????", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MyComunity.this, "????????????", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

        }
    };

    private void initView() {
        mycomunityback=findViewById(R.id.mycomunityback);
        mycomunityswipe=findViewById(R.id.mycomunityswipe);
        mycomunityrv=findViewById(R.id.mycomunityrv);
        mycomunityerror=findViewById(R.id.mycomunityerror);
    }
}
