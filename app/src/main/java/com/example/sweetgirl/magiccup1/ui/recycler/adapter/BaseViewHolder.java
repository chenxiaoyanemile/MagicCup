package com.example.sweetgirl.magiccup1.ui.recycler.adapter;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * recycleView
 */
public class BaseViewHolder<T> extends RecyclerView.ViewHolder{

    private final String TAG = "RecyclerView_BaseViewHolder";


    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, int layoutId) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
        onInitializeView();
    }

    public void onInitializeView() {

    }

    public <T extends View> T findViewById(@IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }

    public void setData(final T object) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.OnItemClick(v);
                }
            }
        });
    }

    public void onItemViewClick(T object) {

    }
    public interface OnItemClickListener {
        public void OnItemClick(View view);
    }
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}

