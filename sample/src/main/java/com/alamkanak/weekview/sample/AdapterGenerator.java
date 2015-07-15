package com.alamkanak.weekview.sample;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterGenerator {
    private CascadableAdapter mAdapter;
    public AdapterGenerator(CascadableAdapter adapter) {
        this.mAdapter = adapter;
    }

    public BaseListAdapter<CascadableAdapter.CascadableItem> createParentAdapter() {
        return new BaseListAdapter<CascadableAdapter.CascadableItem>(mAdapter.getAdapterContext(), mAdapter.getParentItems()) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return mAdapter.getParentView(i, view, viewGroup);
            }
        };
    }

    public BaseListAdapter<CascadableAdapter.ChildItem> createChildAdapter() {
        return new BaseListAdapter<CascadableAdapter.ChildItem>(mAdapter.getAdapterContext(), mAdapter.getParentItem(0).getChildItems()) {
            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return mAdapter.getChildView(i, view, viewGroup);
            }
        };
    }

}
