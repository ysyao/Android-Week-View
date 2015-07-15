package com.alamkanak.weekview.sample;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HomeActivity extends ActionBarActivity implements OnItemClickListener{
    private ListView mLV;
    private static final String[] UI_PLUGINS = {"Week View", "Scrollable Table Layout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mLV = (ListView) findViewById(R.id.listview);
        mLV.setOnItemClickListener(this);
        mLV.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, UI_PLUGINS));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent;
        switch (i) {
            case 0:
                intent = new Intent(this, WeekViewActivity.class);
                startActivity(intent);
                break;
            case 1:
//                intent = new Intent(this, ScrollableTableLayoutActivity.class);
//                startActivity(intent);
                break;
        }
    }
}
