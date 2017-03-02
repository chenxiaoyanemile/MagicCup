package com.example.sweetgirl.magiccup1.ui;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sweetgirl.magiccup1.Bean.Item;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.BaseViewHolder;

/**
 * 第二幕
 */

public class ItemViewHolder extends BaseViewHolder<Item> {

    private TextView mText;
    private ImageView mImage;

    public ItemViewHolder(ViewGroup parent) {
        super(parent, R.layout.scene2_list_item);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        mText = findViewById(R.id.scene2_item_text);
        mImage = findViewById(R.id.scene2_item_image);
    }

    @Override
    public void setData(Item object) {
        super.setData(object);
        mText.setText(object.name);
        Glide.with(itemView.getContext())
                .load(object.image)
                .into(mImage);
    }

}
