package com.example.xinlishequ2.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.xinlishequ2.Fragment.Fragment_Comunity2;
import com.example.xinlishequ2.Fragment.Fragment_Push3;
import com.example.xinlishequ2.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentStatePagerItemAdapter;

public class MyCollect extends AppCompatActivity {
    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private FragmentPagerItemAdapter fragadapter;
    private ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cativity_mycollect);
        initView();
        viewPager.setOffscreenPageLimit(3);
        initTab();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initTab() {
        String[] tabs=new String[]{"帖子","论坛"};
        FragmentPagerItems pages=new FragmentPagerItems(MyCollect.this);
        for(int i=0;i<tabs.length;i++){
            Bundle args=new Bundle();
            args.putString("name",tabs[i]);

        }
        pages.add(FragmentPagerItem.of(tabs[0], Fragment_Push3.class));
        pages.add(FragmentPagerItem.of(tabs[1], Fragment_Comunity2.class));
        viewPager.removeAllViews();
        fragadapter=new FragmentPagerItemAdapter(getSupportFragmentManager(),pages);
        viewPager.setAdapter(fragadapter);
        smartTabLayout.setViewPager(viewPager);

    }

    private void initView() {
        smartTabLayout=findViewById(R.id.mycollecttab);
        viewPager=findViewById(R.id.mycollectvp);
        back=findViewById(R.id.mycolletcback);
    }
}
