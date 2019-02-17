package com.exercise.here.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.exercise.here.R;
import com.exercise.here.data.Taxi;
import com.exercise.here.util.TextUtils;
import com.exercise.here.viewmodel.MainScreenViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxisRecyclerViewAdapter extends RecyclerView.Adapter<TaxisRecyclerViewAdapter.TaxiViewHolder>{

    private final ItemClickListener listener;
    private final List<Taxi> data = new ArrayList<>();

    public TaxisRecyclerViewAdapter(MainScreenViewModel viewModel, LifecycleOwner lifecycleOwner, ItemClickListener clickListener) {
        this.listener = clickListener;
        viewModel.getTaxis().observe(lifecycleOwner, this::updateItems);
        setHasStableIds(true);
    }

    private void updateItems(List<Taxi> taxis) {
        data.clear();
        if (taxis != null) {
            data.addAll(taxis);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public TaxiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_taxi_item, parent, false);
        return new TaxiViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaxiViewHolder holder, int position) {
        holder.bind(new WeakReference(holder.itemView.getContext()), data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    static final class TaxiViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.logo)
        ImageView logo;

        @BindView(R.id.name)
        TextView stationName;

        @BindView(R.id.eta)
        TextView eta;

        private Taxi taxi;

        TaxiViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if(taxi != null) {
                    listener.onItemClick(taxi, getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(v -> {
                if(taxi != null) {
                    listener.onLongItemClick(taxi, getAdapterPosition());
                }
                return true;
            });

        }

        void bind(WeakReference<Context> c, Taxi taxi) {
            this.taxi = taxi;

            Glide.with(c.get())
                    .load(taxi.getImageUrl())
                    .into(logo);
            stationName.setText(taxi.getStationName());
            eta.setText(TextUtils.timeConversion(taxi.getEta()));
        }
    }
}
