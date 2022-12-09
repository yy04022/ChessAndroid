package com.example.chessandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BoardAdapter extends BaseAdapter {
    Context context;
    int[] piece;

    LayoutInflater inflater;

    public BoardAdapter(Context context, int[] piece) {
        this.context = context;
        this.piece = piece;
    }

    @Override
    public int getCount() {
        return piece.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        //}
        //if(convertView==null){
            convertView = inflater.inflate(R.layout.square_white,null);
        //}

        ImageView imageView = convertView.findViewById(R.id.square_background_white);
        imageView.setImageResource(piece[position]);

        return convertView;
    }
}
