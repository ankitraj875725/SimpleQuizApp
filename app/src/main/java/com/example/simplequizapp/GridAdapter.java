package com.example.simplequizapp;


import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;

public class GridAdapter extends ArrayAdapter {

    ArrayList<TopicItem> topicList = new ArrayList<>();

    public GridAdapter(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
        topicList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.grid_item, null);
        TextView textView = (TextView) v.findViewById(R.id.topicsNameView);
        ImageView imageView = (ImageView) v.findViewById(R.id.topicImageView);
        textView.setText(topicList.get(position).getTopicName());
        imageView.setImageResource(topicList.get(position).getTopicImage());
        return v;

    }

}