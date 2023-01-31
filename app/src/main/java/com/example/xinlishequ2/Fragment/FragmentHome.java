package com.example.xinlishequ2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.xinlishequ2.Adapter.HomeAdapter;
import com.example.xinlishequ2.Bean.Post;
//import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

import static cn.bmob.v3.BmobUser.logOut;

public class FragmentHome extends Fragment {
    private RecyclerView rv;
    private SwipeRefreshLayout srlayout;
    private TextView HelloHome,homeusername,hy;
    List<Post> data;
    private HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Bmob.initialize(getActivity(),"09bd3d0dc44ebd9e2180a6b4e998bd37");

        Refresh();

        srlayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Refresh();
            }
        });
        //user加载   XXX欢迎您
        logOut();
        //BmobUser bu=new BmobUser();
        BmobUser bu = BmobUser.getCurrentUser(User.class);
        String userid = bu.getObjectId();
        BmobQuery<User> us = new BmobQuery<>();
        us.getObject(userid, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e==null){
                    homeusername.setText(user.getUsername());
                }else {
                    homeusername.setText(" ");
                    hy.setText(" ");
                }
            }
        });

    }

    private void Refresh() {
        BmobQuery<Post> Po= new BmobQuery<Post>();
        Po.order("-updatedAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                srlayout.setRefreshing(false);
                if(e==null){
                    data=list;
                    homeAdapter=new HomeAdapter(getActivity(),data);
                    rv.setLayoutManager(new LinearLayoutManager(getActivity()));
                    rv.setAdapter(homeAdapter);
                }else {
                    srlayout.setRefreshing(false);
                    Toast.makeText(getActivity(),"获取数据失败"+e,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        rv=getActivity().findViewById(R.id.recycleview);
        srlayout=getActivity().findViewById(R.id.swipe);
        HelloHome=getActivity().findViewById(R.id.HelloHome);
        homeusername=getActivity().findViewById(R.id.homeuser);
        hy=getActivity().findViewById(R.id.hy);

    }
}
