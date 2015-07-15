package com.alamkanak.weekview.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CascadableListView extends LinearLayout implements AdapterView.OnItemClickListener{
    private static final int LEFT_LISTVIEW_ID = 0x21313;
    private static final int RIGHT_LISTVIEW_ID = 0x21322;

    private ListView leftListView;
    private ListView rightListView;

    public CascadableListView(Context context) {
        super(context);
        initView(context);
    }

    public CascadableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void onChildrenItemClick(AdapterView.OnItemClickListener listener) {
        rightListView.setOnItemClickListener(listener);
    }

    public void setAdapter(CascadableAdapter cascadableAdapter) {
        AdapterGenerator gen = new AdapterGenerator(cascadableAdapter);
        leftListView.setAdapter(gen.createParentAdapter());
        rightListView.setAdapter(gen.createChildAdapter());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //左边list view被点击，会触发右边list view对数据的更新
        if (view.getTag() == LEFT_LISTVIEW_ID) {
            CascadableAdapter.CascadableItem parent = (CascadableAdapter.CascadableItem)adapterView.getItemAtPosition(i);
            ListAdapter listAdapter = rightListView.getAdapter();
            if (listAdapter instanceof BaseListAdapter) {
                BaseListAdapter<CascadableAdapter.ChildItem> adapter = (BaseListAdapter<CascadableAdapter.ChildItem>)listAdapter;
                adapter.updateAdapter(parent.getChildItems());
            }
        }
    }

    private void initView(Context context) {
        setOrientation(LinearLayout.HORIZONTAL);

        leftListView = new ListView(context);
        leftListView.setTag(LEFT_LISTVIEW_ID);
        LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        leftParams.weight = 1;
        leftListView.setLayoutParams(leftParams);
        leftListView.setVerticalScrollBarEnabled(false);
        leftListView.setHorizontalScrollBarEnabled(false);
        leftListView.setOnItemClickListener(this);
        addView(leftListView);

        rightListView = new ListView(context);
        rightListView.setTag(RIGHT_LISTVIEW_ID);
        LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        rightParams.weight = 1;
        rightListView.setLayoutParams(rightParams);
        rightListView.setVerticalScrollBarEnabled(false);
        rightListView.setHorizontalScrollBarEnabled(false);
        addView(rightListView);
    }
}
