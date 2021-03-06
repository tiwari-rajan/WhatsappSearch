package com.rajan.findwhatsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class SelectCountryAdapter extends BaseAdapter {
    private final List<Country> mList;
    private Activity context;
    private String selectedText;

    public SelectCountryAdapter(Activity context, List<Country> list, String selectedText) {
        this.selectedText = selectedText;
        this.context = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_select_country, parent, false);
        }

        final TextView tvName = convertView.findViewById(R.id.tv_name);
        final TextView tvCode = convertView.findViewById(R.id.tv_code);
        final ImageView ivFlag = convertView.findViewById(R.id.iv_flag);
        if (mList.get(position) != null) {
            Country doctor = mList.get(position);
            tvName.setText(doctor.getName());
            int res = context.getResources().getIdentifier(doctor.getFlag(), "drawable", context.getPackageName());
            ivFlag.setImageResource(res);

            tvCode.setText("+(" + String.valueOf(doctor.getdCode()) + ")");

            if (selectedText.toLowerCase().contains(doctor.getName().toLowerCase())) {
                tvName.setSelected(true);
                tvCode.setSelected(true);
            } else {
                tvName.setSelected(false);
                tvCode.setSelected(false);
            }
        }
        return convertView;
    }
}