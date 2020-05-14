package com.example.userapproom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewholder> {

    public List<User> users= new ArrayList<>();

    private OnItemClickListener listener;


    public interface OnItemClickListener{
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;

    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new MyViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        User currentUser = users.get(position);

        holder.id.setText(String.valueOf(currentUser.getId()));
        holder.name.setText(currentUser.getName());
    }

    public void setUsers(List<User> users){
        this.users = users ;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
            return users.size();
    }

    public User getUserAt(int adapterPosition) {
        return users.get(adapterPosition);
    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView id;
        private TextView name;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_text_view);
            name = itemView.findViewById(R.id.name_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION && listener != null){

                listener.onItemClick(users.get(position));
            }
        }
    }
}
