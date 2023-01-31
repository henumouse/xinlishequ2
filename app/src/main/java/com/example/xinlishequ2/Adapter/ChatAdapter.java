package com.example.xinlishequ2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xinlishequ2.Bean.Comunity;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.R;
import com.example.xinlishequ2.activity.RecieveCom;
import com.example.xinlishequ2.activity.Recive;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int N_TYPE=0;
    private final int F_TYPE=1;
    private int MAX_NUM=15;
    private Context context;
    private List<Comunity> data;
    private Boolean isfootview=true;

    public ChatAdapter(Context context,List<Comunity> data){
        this.context=context;
        this.data=data;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comunity_item,viewGroup,false);
        View footview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if(i==F_TYPE){

            return new ChatAdapter.RecyclerViewHolder(footview,F_TYPE);

        }else {
            return new ChatAdapter.RecyclerViewHolder(view, N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(isfootview && (getItemViewType(i))==F_TYPE){
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder=(ChatAdapter.RecyclerViewHolder) viewHolder;
            recyclerViewHolder.Loading.setText("加载中......");
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MAX_NUM+=8;
                    notifyDataSetChanged();
                }
            },2000);
        }else{
            final ChatAdapter.RecyclerViewHolder recyclerViewHolder=(ChatAdapter.RecyclerViewHolder) viewHolder;
            Comunity comunity=data.get(i);
            recyclerViewHolder.c_name.setText(comunity.getName());
            recyclerViewHolder.c_info.setText(comunity.getInfo());
            recyclerViewHolder.c_user.setText(comunity.getOwner());
            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerViewHolder.getAdapterPosition();
                    Intent in=new Intent(context, RecieveCom.class);
                    in.putExtra("username",comunity.getName());
                    in.putExtra("info",comunity.getInfo());
                    in.putExtra("onw",comunity.getOwner());
                    in.putExtra("id",data.get(position).getObjectId());
                    context.startActivity(in);
                }
            });
        }
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView c_name,c_info,c_user;
        public TextView Loading;



        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if(view_type==N_TYPE){
                c_name=itemview.findViewById(R.id.c_name);
                c_info=itemview.findViewById(R.id.c_info);
                c_user=itemview.findViewById(R.id.c_user);
            }else if (view_type==F_TYPE){
                Loading=itemview.findViewById(R.id.footText);
            }
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
}
