package com.alamkanak.weekview.sample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class CascadableAdapter {

    public static class ChildItem {

    }

    public static class CascadableItem {
        private List<ChildItem> childItems;
        public CascadableItem( List<ChildItem> childItems) {
            this.childItems = childItems;
        }

        public List<ChildItem> getChildItems() {
            return childItems;
        }

        public void setChildItems(List<ChildItem> childItems) {
            this.childItems = childItems;
        }
    }

    private Context context;
    private List<CascadableItem> items;
    public CascadableAdapter(Context context, List<CascadableItem> items) {
        this.context = context;
        this.items = items;
    }

    public abstract View getParentView(int parentPosition, View view, ViewGroup viewGroup);
    public abstract View getChildView(int childPosition, View view, ViewGroup viewGroup);
    public abstract CascadableItem getParentItem(int i);
    public abstract ChildItem getChildItem(int i, int j);
    public abstract long getParentItemId(int i);
    public abstract long getChildItemId(int i, int j);
    public Context getAdapterContext() {
        return context;
    }
    public List<CascadableItem> getParentItems() {
        return items;
    }
    public List<ChildItem> getChildItemsByParentItem(CascadableItem parentItem) {
        return parentItem.getChildItems();
    }
}
