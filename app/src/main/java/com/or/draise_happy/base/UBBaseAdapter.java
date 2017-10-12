package com.or.draise_happy.base;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * UBBaseAdapter Adapter基类
 * Created by dawabos on 2016/7/26.
 * Email dawabo@163.com
 */
public abstract class UBBaseAdapter<T, Q> extends BaseAdapter {

	public Context mContext;
	public List<T> list;//
	public Q view;


	public UBBaseAdapter(Context context, List<T> list, Q view) {
		this.mContext = context;
		this.list = list;
		this.view = view;
	}

	public UBBaseAdapter(Context context, List<T> list) {
		this.mContext = context;
		this.list = list;
		
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	


}
