package proyek.akhir.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherViewAdapter extends RecyclerView.Adapter<WeatherViewAdapter.ViewHolder> {
    private ArrayList<WeatherItems> mData = new ArrayList<>();

    public WeatherViewAdapter()
    {

    }

    public void setData(ArrayList<WeatherItems> items)
    {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(WeatherItems item)
    {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData()
    {
        mData.clear();
    }



    @NonNull
    @Override
    public WeatherViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewAdapter.ViewHolder holder, int position) {
        holder.tvCityName.setText(mData.get(position).getName());
        holder.tvTemperature.setText(mData.get(position).getTemperature());
        holder.tvDescription.setText(mData.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCityName, tvTemperature, tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tv_city);
            tvTemperature = itemView.findViewById(R.id.tv_suhu);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}
