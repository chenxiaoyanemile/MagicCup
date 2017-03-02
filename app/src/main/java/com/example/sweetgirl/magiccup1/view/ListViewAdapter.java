package com.example.sweetgirl.magiccup1.view;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sweetgirl.magiccup1.R;
import com.example.sweetgirl.magiccup1.model.Scene;
import com.jit.lib.SmartImageView;


public class ListViewAdapter extends BaseAdapter {

	private Context mContext;
	private List<Scene> sceneList;
    private int id;
    private LayoutInflater mInflater=null;

	public ListViewAdapter(int item,Context context, List<Scene> sceneList) {
		super();
        this.id=item;
		this.mContext = context;
		this.sceneList = sceneList;
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (sceneList == null) {
			return 0;
		} else {
			return sceneList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.scene1_list_item,null);
			//[1]找到控件
			holder.name = (TextView) convertView.findViewById(R.id.scene1_tv_name);
			holder.depiction = (TextView) convertView.findViewById(R.id.scene1_tv_dep);
			holder.image = (SmartImageView) convertView.findViewById(R.id.scene1_iv_photo);

			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		//[2]显示数据
		holder.name.setText(sceneList.get(position).getName());
		holder.depiction.setText(sceneList.get(position).getDepiction());
		//[3]显示图片
		String imageUrl=sceneList.get(position).getImage();
		holder.image.setImageUrl(imageUrl);

		return convertView;
	}
	private static class ViewHolder{
		private TextView name;
		private TextView depiction;
		SmartImageView image;
	}

}
