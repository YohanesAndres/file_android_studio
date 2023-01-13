package masterous.example.emdeepyfoodee.adapters;

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
import masterous.example.emdeepyfoodee.models.Ingredient;
import masterous.example.emdeepyfoodee.utils.Constant;
import masterous.example.emdeepyfoodee.utils.ItemClickListener;

public class IngredientViewAdapter extends RecyclerView.Adapter<IngredientViewAdapter.ViewHolder> {
    private List<Ingredient> data = new ArrayList<>();
    private ItemClickListener<Ingredient> itemClickListener;

    public IngredientViewAdapter(ItemClickListener<Ingredient> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAbsoluteAdapterPosition();
        Glide.with(holder.itemView.getContext())
                .load(Constant.THEMEALDB_INGREDIENT_PATH_URL + data.get(pos).getThumbnail())
                .placeholder(R.drawable.ic_broken_image_24)
                .into(holder.ivThumnail);
        holder.tvTitle.setText(data.get(pos).getMeasure() + " " + data.get(pos).getIngredient());
        holder.ivThumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(data.get(pos), pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivThumnail;
        private TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivThumnail = itemView.findViewById(R.id.iv_thumbnail);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
