package com.example.xinlishequ2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xinlishequ2.Bean.Comunity;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.R;
import com.example.xinlishequ2.activity.Login;
import com.example.xinlishequ2.activity.MyComunity;
import com.example.xinlishequ2.activity.Recive;

import java.util.List;

import cn.bmob.v3.BmobUser;

public class MyComunityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Comunity> data;
    private Context context;
    public MyComunityAdapter(Context context,List<Comunity> data){
        this.context=context;
        this.data=data;
    }
    private final int N_TYPE=0;
    private final int F_TYPE=1;
    private int MAX_NUM=15;//预加载15条数据
    private Boolean isfootview=true;
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mycomunity_item,viewGroup,false);
        View footview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if(i==F_TYPE){

            return new MyComunityAdapter.RecyclerViewHolder(footview,F_TYPE);

        }else {
            return new MyComunityAdapter.RecyclerViewHolder(view, N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (isfootview&& (getItemViewType(i))==F_TYPE){
            final MyComunityAdapter.RecyclerViewHolder recyclerViewHolder=(MyComunityAdapter.RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中......");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MAX_NUM+=8;
                    notifyDataSetChanged();
                }
            },2000);
        }else {
            final MyComunityAdapter.RecyclerViewHolder recyclerViewHolder=(MyComunityAdapter.RecyclerViewHolder) viewHolder;
            Comunity comunity=data.get(i);
            recyclerViewHolder.username.setText(comunity.getName());
            recyclerViewHolder.info.setText(comunity.getInfo());
            recyclerViewHolder.owner.setText(comunity.getOwner());

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerViewHolder.getAdapterPosition();
                    if(BmobUser.getCurrentUser( BmobUser.class)!=null){
                        Intent in=new Intent(context, Recive.class);
                        in.putExtra("username",comunity.getName());
                        in.putExtra("info",comunity.getInfo());
                        in.putExtra("owner",comunity.getOwner());
                        in.putExtra("id",data.get(position).getObjectId());
                        context.startActivity(in);
                    }else {
                        Toast.makeText(context, "请登录..", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, Login.class));
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position==MAX_NUM-1){
            return F_TYPE;
        }else {
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if(data.size()<MAX_NUM){
            return data.size();
        }
        return MAX_NUM;
    }
    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView username,info,owner;
        public TextView Loading;



        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if(view_type==N_TYPE){
                username=itemview.findViewById(R.id.mycomunityname);
                info=itemview.findViewById(R.id.mycomunityinfo);
                owner=itemview.findViewById(R.id.mycomunityowner);
                //time=itemview.findViewById(R.id.mypushtime);
            }else if (view_type==F_TYPE){
                Loading=itemview.findViewById(R.id.footText);
            }
        }
    }
}
