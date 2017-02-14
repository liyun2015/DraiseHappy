package com.or.goodlive.ui.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class UbanListView extends ListView {
	public UbanListView(Context context) {
		super(context);
	}
	public UbanListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public UbanListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
	* 设置不滚动
	*/
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
	int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
	MeasureSpec.AT_MOST);
	super.onMeasure(widthMeasureSpec, expandSpec);
	}

}