package com.example.chatrealtime.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatrealtime.MessageActivity;
import com.example.chatrealtime.Model.User;
import com.example.chatrealtime.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private boolean chatstus;
    public UserAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
        //this.chatstus = chatstus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user = users.get(i);
        viewHolder.tv_usernameprofile.setText(user.getUsername());
        if (user.getImageURL().equals("default")){
            viewHolder.imgprofile.setImageResource(R.drawable.dogemeeme);
        }else
        {
            Glide.with(context).load(user.getImageURL()).into(viewHolder.imgprofile); // set image neu tren db co url
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });
        /*
        if (chatstus){
            if (user.getStatus().equals("online")){
                viewHolder.status_on.setVisibility(View.VISIBLE);
                viewHolder.status_off.setVisibility(View.GONE);
            }else {
                viewHolder.status_on.setVisibility(View.GONE);
                viewHolder.status_off.setVisibility(View.VISIBLE);
            }
        }else {
            viewHolder.status_on.setVisibility(View.GONE);
        }
        */
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_usernameprofile;
        public ImageView imgprofile;
        public ImageView status_on, status_off;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_usernameprofile = itemView.findViewById(R.id.tvusernameprofile);
            imgprofile = itemView.findViewById(R.id.profile_imguser);
            status_on = itemView.findViewById(R.id.status_user_on);
            status_off = imgprofile.findViewById(R.id.status_user_off);
        }
    }
}
