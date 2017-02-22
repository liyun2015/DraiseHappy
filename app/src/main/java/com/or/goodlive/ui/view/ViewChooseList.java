package com.or.goodlive.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.or.goodlive.R;
import com.or.goodlive.module.ColumnBean;
import com.or.goodlive.ui.adapter.ColumnItemAdapter;
import com.or.goodlive.ui.adapter.ColumnOwnItemAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/1/9.
 */

public class ViewChooseList {
    private ViewChooseList.OnSelectListener mOnSelectListener;
    private MyGridView gv_select_top;
    private MyGridView gv_select_btom;
    private List<ColumnBean> columnListBeen=new ArrayList<>();
    private List<ColumnBean> columnListOwnBeen=new ArrayList<>();
    private String[] columnStr = new String[]{"独家", "历史", "航空", "娱乐", "手机","历史", "航空", "娱乐", "手机","历史", "航空", "娱乐", "手机"};
    private ColumnItemAdapter columnItemAdapter;
    private Context mContext;
    private int width;
    private PopupWindow popupWindow;
    private ColumnOwnItemAdapter columnOwnItemAdapter;

    public ViewChooseList(Context context) {
        this.mContext = context;
        init(context);
    }
    public ViewChooseList(Context context, int width,List<ColumnBean> columnListOwnBeen) {
        this.mContext = context;
        this.width= width;
        this.columnListOwnBeen= columnListOwnBeen;
        init(context);
    }



    public interface OnSelectListener {
        void getValue(String mValue);
    }
    public void setOnSelectListener(ViewChooseList.OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
    private void init(Context context) {
        mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_choose_list, null);
        gv_select_top = (MyGridView)view.findViewById(R.id.gv_select_top);
        gv_select_btom = (MyGridView) view.findViewById(R.id.gv_select_btom);

        popupWindow = new PopupWindow(view, width, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        // 刷新状态
        popupWindow.update();
        TextView edit_btn = (TextView)view.findViewById(R.id.edit_btn);
        //编辑
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //取消
        TextView cancel_action = (TextView)view.findViewById(R.id.cancel_action_btn);
        cancel_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        initData();
    }


    // 下拉式 弹出 pop菜单 parent 右下角
    public void showAsDropDown(View parent) {
        popupWindow.showAsDropDown(parent,0,0);
    }

    // 隐藏菜单
    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }
    public void clearData(){
        initData();
    }
    private void initData() {
        getAreaData();
    }


    private void getAreaData() {

        for (int i = 0; i < columnStr.length; i++) {
            ColumnBean columnBean = new ColumnBean();
            columnBean.setValue(columnStr[i]);
            columnListBeen.add(i, columnBean);
        }
        if (columnItemAdapter == null) {
            columnItemAdapter = new ColumnItemAdapter(mContext, columnListBeen);
            gv_select_btom.setAdapter(columnItemAdapter);
        } else {
            columnItemAdapter.changeData(columnListBeen);
        }
        if (columnOwnItemAdapter == null) {
            columnOwnItemAdapter = new ColumnOwnItemAdapter(mContext, columnListOwnBeen);
            gv_select_top.setAdapter(columnOwnItemAdapter);
        } else {
            columnOwnItemAdapter.changeData(columnListOwnBeen);
        }
        gv_select_btom.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                columnListBeen.get(position).setClick(true);
                for (int i=0;i<columnListBeen.size();i++){
                    if(position!=i){
                        columnListBeen.get(i).setClick(false);
                    }
                }
                columnItemAdapter.changeData(columnListBeen);
            }
        });
    }

    public boolean isShowing() {
        if(popupWindow!=null){
            return popupWindow.isShowing();
        }else{
            return false;
        }
    }
}
