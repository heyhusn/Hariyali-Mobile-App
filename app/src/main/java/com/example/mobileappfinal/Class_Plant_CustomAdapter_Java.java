package com.example.mobileappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Class_Plant_CustomAdapter_Java extends BaseAdapter {

    private Context context;
    private ArrayList<Class_Plant> addedPlants;
    @Override
    public int getCount() {
        return addedPlants.size();
    }

    @Override
    public Object getItem(int position) {
        return addedPlants.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_list_item_plants, parent, false);
        }
        TextView plantName= convertView.findViewById(R.id.plant_name);
        TextView plantDes= convertView.findViewById(R.id.plant_description);

        plantName.setText(addedPlants.get(position).getName());
        plantDes.setText(addedPlants.get(position).getDescription());
        return convertView;
    }

    public Class_Plant_CustomAdapter_Java(Context context, ArrayList<Class_Plant> addedPlants) {
        this.context = context;
        this.addedPlants = addedPlants;
    }
}
