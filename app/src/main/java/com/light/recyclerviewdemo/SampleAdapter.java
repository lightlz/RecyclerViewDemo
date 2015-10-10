package com.light.recyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by light on 15/10/3.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.ViewHolder> {

    private Context mCon;

    public SampleAdapter(Context mCon) {
        this.mCon = mCon;
        mDataSet = new ArrayList<String>();

        for(int i=0;i<30;i++){
            mDataSet.add(""+i);
        }
    }

    private List<String> mDataSet;

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mCon).inflate(R.layout.item_recycler,parent,false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)holder.tv.getLayoutParams();
        params.height = getHeight(position);
        holder.tv.setLayoutParams(params);

        /**
         * 先设置了监听器，当有点击事件的时候，回调onitemclick传递view和position
         */
        if(mOnItemClickListener != null){
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.tv,position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv ;

        public ViewHolder(View itemView) {
            super(itemView);

            tv = (TextView)itemView.findViewById(R.id.item_tv);

        }
    }

    private int getHeight(int position){

        int[] height = {240,320,480};
        return height[position%3];
    }

    public void insertItem(int position){
        mDataSet.add(position,"insert");
        notifyItemInserted(position);
    }
    public void remove(int positon){
        mDataSet.remove(positon);
        notifyItemRemoved(positon);
    }
}
