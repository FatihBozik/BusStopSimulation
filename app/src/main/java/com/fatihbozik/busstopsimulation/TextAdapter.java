package com.fatihbozik.busstopsimulation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    private int[] busStopsDistances;

    public TextAdapter(Context c, int[] busStopsDistances) {
        mContext = c;
        this.busStopsDistances = busStopsDistances;
    }

    public int getCount() {
        return busStopsDistances.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridViewItem;
        TextView txt1, txt2;

        if (convertView == null) {
            // get layout from row_gridview.xml
            gridViewItem = inflater.inflate(R.layout.row_gridview, null);

            txt1 = (TextView) gridViewItem.findViewById(R.id.txt1);
            txt1.setText(String.format("%d.Durak ile %d.Durak arası", position + 1, position + 2));
            txt2 = (TextView) gridViewItem.findViewById(R.id.txt2);
            txt2.setText(busStopsDistances[position] + " m");
        } else {
            gridViewItem = convertView;
        }
        return gridViewItem;
    }
}