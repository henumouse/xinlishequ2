package com.example.xinlishequ2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xinlishequ2.Bean.Intenttest;
import com.example.xinlishequ2.Bean.Post;
import com.example.xinlishequ2.R;
import com.example.xinlishequ2.activity.Login;
import com.example.xinlishequ2.activity.RecieveCom;
import com.example.xinlishequ2.activity.Recive;
import com.example.xinlishequ2.activity.reply;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Post> data;
    private final int N_TYPE=0;
    private final int F_TYPE=1;
    private int MAX_NUM=15;//预加载15条数据
    private Boolean isfootview=true;
    public  HomeAdapter(Context context, List<Post> data){
        this.context=context;
        this.data=data;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ord_item,viewGroup,false);
        View footview=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.foot_item,viewGroup,false);
        if(i==F_TYPE){

            return new RecyclerViewHolder(footview,F_TYPE);

        }else {
            return new RecyclerViewHolder(view, N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(isfootview && (getItemViewType(i))==F_TYPE){
            final RecyclerViewHolder recyclerViewHolder=(RecyclerViewHolder) viewHolder;
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
            final RecyclerViewHolder recyclerViewHolder=(RecyclerViewHolder) viewHolder;
            Post post=data.get(i);

            recyclerViewHolder.username.setText(post.getName());
            recyclerViewHolder.nickname.setText(post.getNickname());
            recyclerViewHolder.content.setText(post.getContent());
            recyclerViewHolder.time.setText(post.getCreatedAt());
            recyclerViewHolder.reply.setText(post.getReply());
//            recyclerViewHolder.replybutton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "0000", Toast.LENGTH_SHORT).show();
//                    int position=recyclerViewHolder.getAdapterPosition();
//                    if(BmobUser.getCurrentUser( BmobUser.class)!=null){
//                        //String id=post.getObjectId();
//                        post.setReply(data.get(position).getObjectId());
//                        BmobQuery<Post> bmobQuery=new BmobQuery<>();
//                        bmobQuery.addWhereEqualTo("objectId",id);
//                        bmobQuery.findObjects(new FindListener<Post>() {
//                            @Override
//                            public void done(List<Post> list, BmobException e) {
//                                  post.setReply(recyclerViewHolder.replytext.getText().toString());
//                            }
//                        });
//                        Intent in = new Intent(context, reply.class);
//                        context.startActivity(in);
                    //}
                //}
            //});

            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=recyclerViewHolder.getAdapterPosition();
                    if(BmobUser.getCurrentUser( BmobUser.class)!=null){
                        Intent in=new Intent(context, Recive.class);
                        in.putExtra("username",post.getName());
                        in.putExtra("content",post.getContent());
                        in.putExtra("time",post.getCreatedAt());
                        in.putExtra("reply",post.getReply());
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

        public TextView username,nickname,content,time,reply;
        public TextView Loading;
//        public Button replybutton;
//        public EditText replytext;




        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if(view_type==N_TYPE){
                username=itemview.findViewById(R.id.username);
                nickname=itemview.findViewById(R.id.nickname);
                content=itemview.findViewById(R.id.content);
                time=itemview.findViewById(R.id.time);
                reply=itemview.findViewById(R.id.reply);
                //replybutton=itemview.findViewById(R.id.replybutton);
                //replytext=itemview.findViewById(R.id.replytext1);
            }else if (view_type==F_TYPE){
                Loading=itemview.findViewById(R.id.footText);
            }
        }
    }
}
