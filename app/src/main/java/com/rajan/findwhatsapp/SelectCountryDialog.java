package com.rajan.findwhatsapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectCountryDialog extends Dialog {
    private int select = 0;
    private Activity context;
    private ListView listView;
    private SearchView searchView;
    private List<Country> mList;
    private List<Country> filteredData;
    private View llRoot;
    private OnItemSelectListener listener;
    private String selectedText;
    private SelectCountryAdapter listAdapter;

    public SelectCountryDialog(Activity context, List<Country> list,int pos, String selectedText) {
        super(context, R.style.MaterialDialogSheet);
        this.context = context;
        this.mList = list;
        this.filteredData = list;
        this.selectedText = selectedText;
        this.select = pos;

    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, 800);
        getWindow().setGravity(Gravity.BOTTOM);
        setContentView(R.layout.dialog_select_country);
        setCancelable(true);

        listView = findViewById(R.id.listView);
        llRoot = findViewById(R.id.llRoot);
        searchView = findViewById(R.id.searchView);
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.ic_search);
        searchIcon.setBackgroundColor(context.getResources().getColor(R.color.white));

        ImageView searchButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchButton.setImageResource(R.drawable.ic_search);
        searchButton.setBackgroundColor(context.getResources().getColor(R.color.white));

        //changing edit text color
        EditText searchEdit = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchEdit.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
        }

        ImageView clearIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        clearIcon.setImageResource(R.drawable.ic_clear);
        clearIcon.setBackgroundColor(context.getResources().getColor(R.color.white));

        listAdapter = new SelectCountryAdapter(context, filteredData, selectedText);
        listView.setAdapter(listAdapter);
        listView.setSelection(select);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                llRoot.setBackgroundColor(context.getResources().getColor(R.color.white));
                if (searchView != null)
                    searchView.setIconified(false);

            }
        });


        SearchView.OnQueryTextListener searchOnQueryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Country> list = mList;
                int count = list.size();
                final ArrayList<Country> nlist = new ArrayList<>(count);
                String filterableString;
                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getName();
                    if (filterableString.toLowerCase().contains(newText.toLowerCase())) {
                        nlist.add(list.get(i));
                    }
                }

                filteredData = nlist;
                listAdapter = new SelectCountryAdapter(context, filteredData, selectedText);
                listView.setAdapter(listAdapter);
                return false;
            }
        };
        searchView.setOnQueryTextListener(searchOnQueryTextListener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener != null && !Validator.isNullOrEmpty(filteredData) && filteredData.size() > i) {
                    listener.onItemClick(filteredData.get(i), i);
                }
            }
        });
    }

    public interface OnItemSelectListener {
        void onItemClick(Country text, int position);
    }
}