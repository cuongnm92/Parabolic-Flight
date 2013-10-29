package com.vnu.parabolicflight.itemlist;

import java.util.List;

import com.vnu.parabolicflight.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter implements OnClickListener {
    private Context context;

    private List<StringData> listStringData;

    public DataAdapter(Context context, List<StringData> listStringData) {
        this.context = context;
        this.listStringData = listStringData;
    }

    public int getCount() {
        return listStringData.size();
    }

    public Object getItem(int position) {
        return listStringData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        StringData entry = listStringData.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);
        }
        
        TextView tvTime = (TextView) convertView.findViewById(R.id.row_time);
        tvTime.setText(String.valueOf(entry.getTime()));

        TextView tvData = (TextView) convertView.findViewById(R.id.row_data);
        tvData.setText(String.valueOf(entry.getData()));        
        return convertView;
    }

    @Override
    public void onClick(View view) {
        StringData entry = (StringData) view.getTag();
        listStringData.remove(entry);
        // listStringData.remove(view.getId());
        notifyDataSetChanged();
    }
}