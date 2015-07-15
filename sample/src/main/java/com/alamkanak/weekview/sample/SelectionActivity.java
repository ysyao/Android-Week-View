package com.alamkanak.weekview.sample;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.sample.R;

import java.util.List;

public class SelectionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        CascadableListView listView = (CascadableListView) findViewById(R.id.cascadable_listview);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class NumberAdapter extends CascadableAdapter {

        public NumberAdapter(Context context, List<CascadableItem> items) {
            super(context, items);
        }

        @Override
        public View getParentView(int parentPosition, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public View getChildView(int childPosition, View view, ViewGroup viewGroup) {
            return null;
        }

        @Override
        public CascadableItem getParentItem(int i) {
            return getParentItems().get(i);
        }

        @Override
        public ChildItem getChildItem(int i, int j) {
            return getParentItem(i).getChildItems().get(j);
        }

        @Override
        public long getParentItemId(int i) {
            return i;
        }

        @Override
        public long getChildItemId(int i, int j) {
            return j;
        }
    }
}
