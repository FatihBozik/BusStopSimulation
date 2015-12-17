package com.fatihbozik.busstopsimulation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fatihbozik.busstopsimulation.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class HashMapAdapter extends BaseAdapter {
    Map<Integer, String> map;
    private Context mContext;
    Iterator it;

    public HashMapAdapter(Context mContext, HashMap<Integer, String> data) {
        this.mContext = mContext;
        map = new TreeMap<>(data);
        it = map.entrySet().iterator();
    }

    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return map.size();
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView;
        TextView txt;

        if (convertView == null) {
            listView = inflater.inflate(R.layout.row_listview, null);
            txt = (TextView) listView.findViewById(R.id.busStopTxt);

            if (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                txt.setText(pair.getValue().toString());
            }
        } else {
            listView = convertView;
        }

        return listView;
    }
}