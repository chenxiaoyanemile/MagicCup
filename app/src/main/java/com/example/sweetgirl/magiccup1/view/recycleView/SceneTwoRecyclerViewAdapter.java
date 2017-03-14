package com.example.sweetgirl.magiccup1.view.recycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.sweetgirl.magiccup1.Bean.Item;
import com.example.sweetgirl.magiccup1.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *  2017/3/14.
 */

public class SceneTwoRecyclerViewAdapter extends RecyclerView.Adapter<SceneTwoRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private int previousPosition=0;

    private ArrayList<Item> items=new ArrayList<>();

    public SceneTwoRecyclerViewAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance(context);
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
        notifyItemMoved(0,items.size());
    }


    /**
     *相当于getView 方法中创建view和ViewHolder
     * * @param
     *
     * */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView=layoutInflater.inflate(R.layout.scene2_list_item, parent,false);
        ViewHolder viewHolder=new ViewHolder(itemView);
        return viewHolder;
    }

    /**
     * 相当于getView 方法中创建view和ViewHolder
     * @param
     *
     */
    @Override
    public void  onBindViewHolder(ViewHolder holder, int position){
        // String data=datas.get(position);
        //holder.mText.setText(data);
        //TextImage currentTextImage=textImages.get(position);
        Item item=items.get(position);
        holder.mText.setText(item.getName());


        holder.itemView.setTag(position);

        String url=item.getImage();
        loadImages(url,holder);

    }


    private void loadImages(String urlThumbnail, final ViewHolder holder) {
        if (!urlThumbnail.equals(Constants.NA)) {
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.mImage.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    /**
     *相当于getView 方法中创建view和ViewHolder
     * @param
     */
    @Override
    public int  getItemCount(){
        //return datas.size();
        return items.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
         TextView mText;
         ImageView mImage;

        public ViewHolder(View itemView){
            super(itemView);
            mText = (TextView)itemView.findViewById(R.id.scene2_item_text);
            mImage =(ImageView)itemView.findViewById(R.id.scene2_item_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v,(int)v.getTag());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        public void OnItemClick(View view,int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
