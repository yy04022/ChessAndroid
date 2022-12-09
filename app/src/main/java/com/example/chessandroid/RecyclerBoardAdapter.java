package com.example.chessandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerBoardAdapter extends RecyclerView.Adapter<RecyclerBoardAdapter.MyViewHolder> {
    public ArrayList<Piece> piece_order;
    private OnBoardListener mOnBoardListener;
    private int num=0;

    public RecyclerBoardAdapter(ArrayList<Piece> piece_order, OnBoardListener onBoardListener){
        this.piece_order = piece_order;
        this.mOnBoardListener = onBoardListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView pieceImage;
        OnBoardListener onBoardListener;

        public MyViewHolder(final View view, OnBoardListener onBoardListener) {
            super(view);
            //if(num%2==0){
                pieceImage = view.findViewById(R.id.square_background_white);
            //}else{
            //    pieceImage = view.findViewById(R.id.square_background_black);
            //}
            //num++;
            this.onBoardListener = onBoardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onBoardListener.onBoardClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerBoardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        //if(num%2==0){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_white, parent, false);
        //}else{
        //    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_black, parent, false);
        //}
        return new MyViewHolder(itemView, mOnBoardListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerBoardAdapter.MyViewHolder holder, int position) {
        Integer pieceVal = piece_order.get(position).getChessPiece();
        holder.pieceImage.setImageResource(pieceVal);
    }

    @Override
    public int getItemCount() {
        return piece_order.size();
    }

    public interface OnBoardListener{
        void onBoardClick(int position);
    }

}
