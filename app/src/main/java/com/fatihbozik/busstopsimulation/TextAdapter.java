package com.fatihbozik.busstopsimulation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TextAdapter extends BaseAdapter {
    private Context mContext;
    private int[] busStopsDistances;
    private HashMap<Integer, Integer> kacDakika;
    private int type;
    Iterator it;

    public TextAdapter(Context c, int[] busStopsDistances, int type) {
        mContext = c;
        this.busStopsDistances = busStopsDistances;
        this.type = type;
    }

    public TextAdapter(Context c, HashMap<Integer, Integer> kacDakika, int type) {
        mContext = c;
        this.kacDakika = kacDakika;
        this.type = type;
        it = kacDakika.entrySet().iterator();
    }

    public int getCount() {
        if (type == 2) {
            return kacDakika.size();
        } else {
            return busStopsDistances.length;
        }
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
            txt2 = (TextView) gridViewItem.findViewById(R.id.txt2);
            if (type == 1) {
                txt1.setText(String.format("%d.Durak ile %d.Durak arası", position + 1, position + 2));
                txt2.setText(busStopsDistances[position] + " m");
                return gridViewItem;
            } else if (type == 0) {
                txt1.setText("Otobüs" + (position + 1));

                if (busStopsDistances[position] == 0) {
                    txt2.setText("Simulasyon başladığında\n");
                } else {
                    txt2.setText("Simulasyon başladıktan \n" + busStopsDistances[position] + " dk sonra");
                }
                return gridViewItem;
            } else {

                if (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    txt1.setText("Otobüs" + ((int)pair.getKey() + 1));
                    txt2.setText(pair.getValue() + " dk sonra");
                }

//                for (Map.Entry<Integer, Integer> entry : kacDakika) {
//                    int key = entry.getKey();
//                    int value = entry.getValue();
//                }
//
//                txt1.setText("Otobüs" + (position + 1));
//                txt2.setText(kacDakika.get(position) + " dk sonra");
            }

            Log.d("BusActivity::txt1", txt1.getText().toString());
            Log.d("BusActivity::txt2", txt2.getText().toString());

        } else {
            gridViewItem = convertView;
        }

        return gridViewItem;
    }
}