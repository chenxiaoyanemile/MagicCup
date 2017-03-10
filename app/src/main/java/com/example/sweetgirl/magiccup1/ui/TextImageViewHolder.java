package com.example.sweetgirl.magiccup1.ui;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sweetgirl.magiccup1.Bean.TextImage;
import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.ui.recycler.adapter.BaseViewHolder;



/**
 * 场景1的holder
 */

public class TextImageViewHolder extends BaseViewHolder<TextImage>  {
    private TextView mDep;
    private TextView mText;
    private ImageView mImage;



    public TextImageViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_scene1);

    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        mText = findViewById(R.id.scene1_tv_name);
        mImage = findViewById(R.id.scene1_iv_photo);
        mDep=findViewById(R.id.scene1_tv_dep);
    }

    @Override
    public void setData(TextImage object) {
        super.setData(object);
        mText.setText(object.name);
        mDep.setText(object.dep);
        Glide.with(itemView.getContext())
                .load(object.image)
                .into(mImage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (onItemClickListener!=null){
                    onItemClickListener.OnItemClick(v);
                }*/
            }
        });
    }

    public View getItemView(){
        return itemView;
    }

  /*  public interface OnItemClickListener {

        public void OnItemClick(View view);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){

        this.onItemClickListener=onItemClickListener;
    }*/

}
