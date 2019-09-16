package com.example.bishuliko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ListCategoryView> {
    private Context mContext;
    private int mResource;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ListCategoryView> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        String category = getItem(position).getCategory();
//        int img = getItem(position).getImg();
//        ListCategoryView listCategoryView = new ListCategoryView(img, category);
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        convertView = inflater.inflate(mResource,parent,false);
//        TextView textView = convertView.findViewById(R.id.list_text);
//        ImageView imageView = convertView.findViewById(R.id.list_img);
//        textView.setText(category);
//        imageView.setImageResource(img);
//        return convertView;
//    }
}
