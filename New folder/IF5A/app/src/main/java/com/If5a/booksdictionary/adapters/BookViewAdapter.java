package com.If5a.booksdictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.If5a.booksdictionary.R;
import com.If5a.booksdictionary.models.Book;
import com.If5a.booksdictionary.utilities.ItemClicklistener;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class BookViewAdapter extends RecyclerView.Adapter<BookViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Book> data = new ArrayList<>();
    private ItemClicklistener itemClicklistener;

    public BookViewAdapter(ItemClicklistener<Book> itemClickListener){
        this.itemClicklistener = itemClickListener;
}

    public void setData(ArrayList<Book> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();

        Glide.with(holder.itemView.getContext())
                .load(data.get(position).getS())
                .placeholder(R.drawable.ic__menu_book_24)
                .into(holder.ivImages);

        holder.tvTitle.setText(data.get(pos).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClicklistener.onItemClick(data.get(position), pos);
         }
});


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView ivImages;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            ivImages = itemView.findViewById(R.id.iv_book_thumbnail);

        }
    }

}