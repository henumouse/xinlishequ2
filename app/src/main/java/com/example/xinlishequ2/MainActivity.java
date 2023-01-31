 package com.example.xinlishequ2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.xinlishequ2.Adapter.SectionsPagerAdapter;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.Fragment.FragmentChat;
import com.example.xinlishequ2.Fragment.FragmentHome;
import com.example.xinlishequ2.Fragment.FragmentMine;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {
    // private TextView username,nickname;
    private ViewPager viewpager;
    private BottomNavigationBar bottomNavigationBar;
    private List<Fragment> fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewpager=findViewById(R.id.viewpager);
        bottomNavigationBar=findViewById(R.id.bottom);
        initView();
//        username=findViewById(R.id.username);
//        nickname=findViewById(R.id.nickname);
//        BmobUser user=BmobUser.getCurrentUser(User.class);
//        String id=user.getObjectId();
//        BmobQuery<User> myuser= new BmobQuery<User>();
//        myuser.getObject(id, new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if(e==null){
//                    username.setText(user.getUsername());
//                    nickname.setText(user.getNickname());
//                }else {
//                    Toast.makeText(MainActivity.this,"查询失败",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

    }

    private void initView() {
        initViewPager();
        initBottomNavigationBar();

    }

    private void initBottomNavigationBar() {
        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar.setBarBackgroundColor(R.color.white).setActiveColor(R.color.colorBasel).setInActiveColor(R.color.green_normal);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.homepage_fill,"首页").setInactiveIconResource(R.drawable.homepage))
                .addItem(new BottomNavigationItem(R.drawable.mobilephone_fill,"书籍").setInactiveIconResource(R.drawable.mobilephone_fill))
                .addItem(new BottomNavigationItem(R.drawable.mine_fill,"我的").setInactiveIconResource(R.drawable.mine)).setFirstSelectedPosition(0)
                .initialise();
    }

    private void initViewPager() {
        viewpager.setOffscreenPageLimit(3);
        fragments=new ArrayList<Fragment>();
        fragments.add(new FragmentHome());
        fragments.add(new FragmentChat());
        fragments.add(new FragmentMine());

        viewpager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(),fragments));
        viewpager.addOnPageChangeListener(this);
        viewpager.setCurrentItem(0);


    }

    @Override
    public void onTabSelected(int position) {
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        bottomNavigationBar.selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}