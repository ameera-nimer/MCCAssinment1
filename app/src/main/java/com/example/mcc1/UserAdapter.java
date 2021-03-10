package com.example.mcc1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> mArrayList;
    private OnItemClickListener listener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView user_name1;
        private final TextView user_address1;
        private final TextView user_number1;
        private final Button btn_delete;

        public ViewHolder(View view) {
            super(view);
            btn_delete = view.findViewById(R.id.btn_delete);
            user_name1 = (TextView) view.findViewById(R.id.user_name1);
            user_address1 = (TextView) view.findViewById(R.id.user_address1);
            user_number1 = (TextView) view.findViewById(R.id.user_number1);
        }


    }


    public UserAdapter(ArrayList<User> mArrayList, OnItemClickListener listener) {
        this.mArrayList = mArrayList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.user_name1.setText("" + mArrayList.get(position).getName());
        holder.user_address1.setText("" + mArrayList.get(position).getAddress());
        holder.user_number1.setText("" + mArrayList.get(position).getNumber());
        holder.btn_delete.setOnClickListener(v -> {
            listener.onclick(v, position, "Delete");
        });
    }


    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    interface OnItemClickListener {
        void onclick(View v, int postion, String tag);
    }
}
