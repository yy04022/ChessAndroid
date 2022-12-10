package com.example.chessandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    Context context;
    //int[] piece;
    ArrayList<Piece> piece;

    LayoutInflater inflater;

    public BoardAdapter(Context context, ArrayList<Piece> piece) {
        this.context = context;
        this.piece = piece;
    }

    @Override
    public int getCount() {
        return piece.size();
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
        if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView = inflater.inflate(R.layout.square_white,null);
        }

        ImageView imageView = convertView.findViewById(R.id.square_background_white);
        imageView.setImageResource(piece.get(position).getChessPiece());

        return convertView;
    }
}
