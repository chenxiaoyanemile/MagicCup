package com.example.sweetgirl.magiccup1.ui.recycler.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.sweetgirl.magiccup1.util.L;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有的item都对应一个ViewHolder
 */

public class MultiTypeAdapter extends RecyclerAdapter{

    private final String TAG = "MultiTypeAdapter";
    private List<Object> mViewsData;
    private Map<Integer, Integer> mPositionViewType;  //position --> ViewType
    private ViewHolderManager mViewHolderManager;


    public MultiTypeAdapter(Context context) {
        super(context);
        mViewsData = new ArrayList<>();
        mPositionViewType = new HashMap<>();
        mViewHolderManager = new ViewHolderManager();
    }

    public <T> void add(Class<? extends BaseViewHolder<T>> viewHolder, T data) {
        if (isShowNoMore) {
            return;
        }
        isLoadingMore = false;
        mViewsData.add(data);
        mViewHolderManager.addViewHolder(viewHolder);
        int viewType = mViewHolderManager.getViewType(viewHolder);
        mPositionViewType.put(mViewCount - 1, viewType);//mViewCount从1开始
        mViewCount++;
        notifyDataSetChanged();
    }

    public <T> void addAll(Class<? extends BaseViewHolder<T>> viewHolder, T[] data) {
        addAll(viewHolder, Arrays.asList(data));
    }

    public <T> void addAll(Class<? extends BaseViewHolder<T>> viewHolder, List<T> data) {
        if (isShowNoMore || data.size() == 0) {
            return;
        }
        isLoadingMore = false;
        mViewsData.addAll(data);
        mViewHolderManager.addViewHolder(viewHolder);
        int viewType = mViewHolderManager.getViewType(viewHolder);
        for (int i = 0; i < data.size(); i++) {
            mPositionViewType.put(mViewCount - 1, viewType);//mViewCount从1开始
            mViewCount++;
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if (mViewsData == null || mViewsData.size() == 0) {
            return;
        }
        mViewsData.clear();
        mViewCount = 1;
        notifyDataSetChanged();

        isRefreshing = false;
        isShowNoMore = false;
        isLoadingMore = false;
        mLoadMoreView.setVisibility(View.GONE);
        mNoMoreView.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mViewCount - 1) {
            return STATUS_TYPE;
        }
        return mPositionViewType.get(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        log("onCreateViewHolder -- viewType : " + viewType);

        if (viewType == STATUS_TYPE) {

            return new BaseViewHolder(mStatusView);
        }
        Class clazzViewHolder = mViewHolderManager.getViewHolder(viewType);
        try {
            //这里只适配了ViewHolder构造函数只有ViewGroup.class参数或者无参情况的构造函数
            BaseViewHolder holder;
            Constructor constructor = clazzViewHolder.getDeclaredConstructor(new Class[]{ViewGroup.class});
            constructor.setAccessible(true);
            holder = (BaseViewHolder) constructor.newInstance(new Object[]{parent});
            if (holder == null) {
                constructor = clazzViewHolder.getDeclaredConstructor();
                holder = (BaseViewHolder) constructor.newInstance();
            }
            holder.onItemViewClick(mViewsData);
            L.i(TAG,"执行try");
            return holder;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "onCreateBaseViewHolder : " + e.getMessage());
        }

        return null;

    }

    @Override
    public BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        log("onBindViewHolder -- position : " + position);
        if (position == 0 && mViewCount == 1) return;
        if (position == mViewCount - 1) { //显示加载更多
            isLoadEnd = true;
            if (loadMoreAble && mLoadMoreAction != null && !isShowNoMore && !isLoadingMore) {
                mLoadMoreView.setVisibility(View.VISIBLE);
                mLoadMoreAction.onAction();
                isLoadingMore = true;
            }
        } else {
            holder.setData(mViewsData.get(position));
            holder.itemView.setTag(mViewsData.get(position));
        }

    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}
