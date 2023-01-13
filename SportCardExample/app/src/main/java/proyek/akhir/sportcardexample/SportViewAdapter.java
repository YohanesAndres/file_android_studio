package proyek.akhir.sportcardexample;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SportViewAdapter extends RecyclerView.Adapter<SportViewAdapter.ViewHolder> {
    private List<Sport> mData;
    private Context mContext;

    public SportViewAdapter(List<Sport> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SportViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportViewAdapter.ViewHolder holder, int position) {
        Sport sport = mData.get(position);
        holder.bindTo(sport);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvInfo;
        private ImageView ivSport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvInfo = itemView.findViewById(R.id.tv_subtitle);
            ivSport = itemView.findViewById(R.id.iv_sport);

            itemView.setOnClickListener(this);
        }

        void bindTo(Sport sport)
        {
            tvTitle.setText(sport.getTitle());
            tvInfo.setText(sport.getInfo());
            Glide.with(mContext).load(sport.getImageResource()).into(ivSport);
        }

        @Override
        public void onClick(View view) {
            Sport sport = mData.get(getAdapterPosition());
            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra("TITLE", sport.getTitle());
            intent.putExtra("IMAGE_RESOURCE", sport.getImageResource());
            mContext.startActivity(intent);
        }
    }
}
