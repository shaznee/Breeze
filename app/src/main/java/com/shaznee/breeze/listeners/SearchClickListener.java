package com.shaznee.breeze.listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by SHAZNEE on 12-Jun-16.
 */
public class SearchClickListener implements RecyclerView.OnItemTouchListener {

//    private Context context;
    private OnItemClickListener onItemClickListener;
    private GestureDetector gestureDetector;

    public SearchClickListener(Context context, OnItemClickListener onItemClickListener) {
//        this.context = context;
        this.onItemClickListener = onItemClickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && onItemClickListener != null && gestureDetector.onTouchEvent(e)) {
            onItemClickListener.onItemClick(childView, view.getChildLayoutPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {}

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
}
