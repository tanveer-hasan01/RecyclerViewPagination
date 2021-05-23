package com.datasysbd.recyclerviewpagination;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    Context context;
    ArrayList<ModelData> list;

    public Adapter(Context context, ArrayList<ModelData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view,parent,false);


        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Holder holder, int position) {

        holder.name.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
      return    list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);


            imageView=itemView.findViewById(R.id.image_view);
            name=itemView.findViewById(R.id.name);

        }

    }
}
