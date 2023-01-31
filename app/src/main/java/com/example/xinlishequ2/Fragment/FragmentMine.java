package com.example.xinlishequ2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.xinlishequ2.Bean.User;
import com.example.xinlishequ2.R;
import com.example.xinlishequ2.activity.Login;
import com.example.xinlishequ2.activity.MyCollect;
import com.example.xinlishequ2.activity.MyComunity;
import com.example.xinlishequ2.activity.MyPush;
import com.example.xinlishequ2.activity.Setting;
import com.example.xinlishequ2.activity.myInfo;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class FragmentMine extends Fragment {
    private TextView username,nickname;
    private Button loginout;
    private LinearLayout myinfo;
    private LinearLayout mypush,mycomunity,mycollect,setting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmine,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //加载信息
        getMyinfo();
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的信息页面
                startActivity(new Intent(getActivity(), myInfo.class));
            }
        });
        mypush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyPush.class));
            }
        });
        mycomunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyComunity.class));
            }
        });
        mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyCollect.class));
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Setting.class));
            }
        });
    }

    private void getMyinfo() {
        //BmobUser bu=new BmobUser();
        BmobUser bu=BmobUser.getCurrentUser(User.class);
        String Id=bu.getObjectId();
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.getObject(Id, new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if(e==null){
                    username.setText(user.getUsername());
                }else {
                    Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        username=getActivity().findViewById(R.id.username);
        loginout=getActivity().findViewById(R.id.loginout);
        myinfo=getActivity().findViewById(R.id.myinfo);
        mypush=getActivity().findViewById(R.id.mypush);
        mycomunity=getActivity().findViewById(R.id.mycomunity);
        mycollect=getActivity().findViewById(R.id.mycollect);
        setting=getActivity().findViewById(R.id.setting);

    }
}
