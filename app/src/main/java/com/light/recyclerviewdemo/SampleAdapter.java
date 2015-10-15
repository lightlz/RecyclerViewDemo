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
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

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

    public interface OnLoadingMoreListener{
        void onLoadingMore(int position);
    }
    private OnLoadingMoreListener onLoadingMoreListener;
    public void setOnLoadingMoreListener(OnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM){
            return new ViewHolder(LayoutInflater.from(mCon).inflate(R.layout.item_recycler,parent,false));
        }else if(viewType == TYPE_FOOTER){
            return new ViewHolder(LayoutInflater.from(mCon).inflate(R.layout.layout_footer,parent,false));
        }
        return null;
    }

//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        return new ViewHolder(LayoutInflater.from(mCon).inflate(R.layout.item_recycler,parent,false));
//    }

    @Override
    public int getItemViewType(int position) {
        if(position+1 == getItemCount()){
            return TYPE_FOOTER;
        }else{
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if(position +1 != getItemCount()) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.tv.getLayoutParams();
            params.height = getHeight(position);
            holder.tv.setLayoutParams(params);

            /**
             * 先设置了监听器，当有点击事件的时候，回调onitemclick传递view和position
             */
            if (mOnItemClickListener != null) {
                holder.tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.tv, position);
                    }
                });
            }
        }

        if(onLoadingMoreListener != null && position == getItemCount()-1){
            onLoadingMoreListener.onLoadingMore(position);
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

    public void insertItems(int position){
        int end = position+15;
        for(int i=position;i<end;i++){
            mDataSet.add(i, "insert");
        }

        notifyItemInserted(position);
    }
}
