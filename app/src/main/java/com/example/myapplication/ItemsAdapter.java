package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>
{
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    List<String> items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String> items, OnLongClickListener longClickListener)
    {
        this.items = items;
        this.longClickListener = longClickListener;
    }
    @NonNull
    @Override
    // responsible for creating each view
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    { // use layout inflator to inflate a view
      View todoView =   LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(todoView);
    }

    @Override
    // responsible for taking data out of a particular position and binding it to a view Holder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // grab the item at the position
        // bind the item
        String item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    // container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{
      TextView tvitem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvitem = itemView.findViewById((android.R.id.text1));
        }
       // update the view inside the viewHolder with this data
        public void bind(String item)
        {
            tvitem.setText(item);
            tvitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Notify the listener which position was long pressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
        }
    }
}
