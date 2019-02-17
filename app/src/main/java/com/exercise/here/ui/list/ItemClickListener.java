package com.exercise.here.ui.list;

import com.exercise.here.data.Taxi;

public interface ItemClickListener {
    void onItemClick(Taxi item, int position);
    void onLongItemClick(Taxi item, int position);
}
