package masterous.example.emdeepyfoodee.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import masterous.example.emdeepyfoodee.R;
import masterous.example.emdeepyfoodee.activities.DetailActivity;
import masterous.example.emdeepyfoodee.models.Food;
import masterous.example.emdeepyfoodee.models.ResponseMeal;
import masterous.example.emdeepyfoodee.utils.ItemClickListener;

public class FoodViewAdapter extends RecyclerView.Adapter<FoodViewAdapter.ViewHolder> {
    private List<ResponseMeal> data = new ArrayList<>();
    private ItemClickListener<ResponseMeal> itemClickListener;

    public FoodViewAdapter(ItemClickListener<ResponseMeal> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<ResponseMeal> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();

        Glide.with(holder.itemView.getContext())
                .load(data.get(pos).getStrMealThumb())
                .placeholder(R.drawable.ic_broken_image_24)
                .into(holder.ivFood);
        holder.tvFoodName.setText(data.get(pos).getStrMeal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(data.get(pos), pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFood;
        private TextView tvFoodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivFood = itemView.findViewById(R.id.iv_food);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
        }
    }
}
