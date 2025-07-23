package com.example.mobileappfinal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PlantCustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PlantClass> addedPlants;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.custom_gridview, parent, false);
        }
        ImageView imageView= convertView.findViewById(R.id.plantimage);
        TextView plantName= convertView.findViewById(R.id.plantname);

        imageView.setImageURI(addedPlants.get(position).getImageUri());
        plantName.setText(addedPlants.get(position).getPlantName());
        return convertView;
    }

    public PlantCustomAdapter(Context context, ArrayList<PlantClass> addedPlants) {
        this.context = context;
        this.addedPlants = addedPlants;
    }
}
