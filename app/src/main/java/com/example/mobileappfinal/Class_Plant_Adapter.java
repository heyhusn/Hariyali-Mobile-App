package com.example.mobileappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Class_Plant_Adapter extends BaseAdapter {

    private Context context;
    private List<Class_Plant> plantList;
    private LayoutInflater inflater;

    public Class_Plant_Adapter(Context context, List<Class_Plant> plantList) {
        this.context = context;
        this.plantList = plantList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return plantList.size();
    }

    @Override
    public Object getItem(int position) {
        return plantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.activity_list_item_plants, parent, false);
        }

        TextView plantNameText = view.findViewById(R.id.plant_name);
        TextView plantDescriptionText = view.findViewById(R.id.plant_description);

        Class_Plant plant = plantList.get(position);
        plantNameText.setText(plant.getName());
        plantDescriptionText.setText(plant.getDescription());

        return view;
    }


}
