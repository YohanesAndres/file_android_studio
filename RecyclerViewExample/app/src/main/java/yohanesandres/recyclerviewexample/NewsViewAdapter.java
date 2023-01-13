package yohanesandres.recyclerviewexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.viewHolder> {
    private List<News> mData;
    private OnItemClickListener onItemClickListener;

    public NewsViewAdapter(List<News> mData, OnItemClickListener onItemClickListener) {
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
    }

    public void addNewsData(List<News> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new  viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.bind(mData.get(position), onItemClickListener);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvDescription;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }

        public void bind(News item, OnItemClickListener onItemClickListener){
            tvTitle.setText(item.getTitle());
            tvDescription.setText(item.getDescription());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(item, getAdapterPosition());
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(News item, int position);
    }
}
