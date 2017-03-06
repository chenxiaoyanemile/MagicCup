package com.example.sweetgirl.magiccup1.ui.recycler.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * SW 2017--3--4
 */

public abstract class SingleItemClickListener implements RecyclerView.OnItemTouchListener {

    private View childView;
    private RecyclerView touchView;
    public SingleItemClickListener(Context context, final OnItemClickListener mListener) {
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    mListener.onItemClick(childView, touchView.getChildPosition(childView));
                }
                return true;
            }
            @Override
            public void onLongPress(MotionEvent ev) {
                if (childView != null && mListener != null) {
                    mListener.onLongClick(childView, touchView.getChildPosition(childView));
                }
            }
        });
    }

    GestureDetector mGestureDetector;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongClick(View view, int posotion);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        touchView = recyclerView;
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }
}