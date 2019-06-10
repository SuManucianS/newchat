package com.example.chatrealtime.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatrealtime.Model.Chats;
import com.example.chatrealtime.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private Context context;
    private List<Chats> chat;
    public static final int MSG_BACKGROUND_LEFT = 0;
    public static final int MSG_BACKGROUND_RIGHT = 1;
    private String img_url;
    FirebaseUser firebaseUser;
    public MessageAdapter(Context context, List<Chats> chat, String img_url) {
        this.context = context;
        this.chat = chat;
        this.img_url = img_url;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == MSG_BACKGROUND_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {
        Chats chats = chat.get(i);
        viewHolder.tv_showmessage.setText(chats.getMessage());
        if (img_url.equals("default")){
            viewHolder.profile_imgprofile.setImageResource(R.drawable.dogemeeme);
        }else
            Glide.with(context).load(img_url).into(viewHolder.profile_imgprofile);
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_showmessage;
        public ImageView profile_imgprofile;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_showmessage = itemView.findViewById(R.id.showmessage);
            profile_imgprofile = itemView.findViewById(R.id.profile_imguser);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_BACKGROUND_RIGHT;
        }else {
            return MSG_BACKGROUND_LEFT;
        }
    }
}
