package com.fatihbozik.busstopsimulation;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    private int[] busStopsDistances;

    public TextAdapter(Context c, int[] busStopsDistances) {
        mContext = c;
        this.busStopsDistances = busStopsDistances;
    }

    public int getCount() {
        return 2 * busStopsDistances.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            textView = new TextView(mContext);
            GridView.LayoutParams layoutParams =
                    new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(layoutParams);
        } else {
            textView = (TextView) convertView;
        }

        Log.d("Fatih", position + "");
        if(position % 2 == 0) {
            String str = String.format("%d.Durak ile %d.Durak arası", (position / 2) + 1, (position / 2) + 2);
            textView.setText(str);
        }
        else {
            textView.setText(String.valueOf(busStopsDistances[(position - 1)/2]) + " m");
        }
        return textView;
    }
}