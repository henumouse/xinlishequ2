package com.example.xinlishequ2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xinlishequ2.Adapter.ChatAdapter;
import com.example.xinlishequ2.Bean.Comunity;
import com.example.xinlishequ2.R;
import com.example.xinlishequ2.activity.PushComunity;
import com.example.xinlishequ2.activity.PushContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class FragmentChat extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    List<Comunity> data;
    private ChatAdapter chatAdapter;
    private FloatingActionButton add,addcontent,addcomunity;
    private RelativeLayout rvlayout;
    private Boolean isshow=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentchat,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Refresh();
        srlayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isshow){
                addcontent.setVisibility(View.VISIBLE);
                addcomunity.setVisibility(View.VISIBLE);
                isshow=true;
                }else {
                    addcontent.setVisibility(View.GONE);
                    addcomunity.setVisibility(View.GONE);
                    isshow=false;
                }
            }
        });
        addcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PushContent.class));
            }
        });
        addcomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PushComunity.class));
            }
        });

    }

    private void Refresh() {
        BmobQuery<Comunity> com=new BmobQuery<Comunity>();
        com.order("-createdAt");
        com.setLimit(1000);
        com.findObjects(new FindListener<Comunity>() {
            @Override
            public void done(List<Comunity> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e==null){
                    data=list;
                    chatAdapter=new ChatAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(chatAdapter);
                }else {
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        rv=getActivity().findViewById(R.id.rvchat);
        srlayout=getActivity().findViewById(R.id.swipechat);
        add=getActivity().findViewById(R.id.add);
        addcontent=getActivity().findViewById(R.id.addcontent);
        addcomunity=getActivity().findViewById(R.id.addcomunity);
        rvlayout=getActivity().findViewById(R.id.rvlayout);
    }
}
