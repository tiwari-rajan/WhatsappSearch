package com.rajan.findwhatsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ImageView ivCountry;
    private EditText tvCountry;
    private Country mCountry;
    private EditText tetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tetNumber = findViewById(R.id.tetNumber);
        final EditText tetMessage = findViewById(R.id.tetMessage);
        final ImageView ivNumber = findViewById(R.id.iv_number);
        final ImageView ivMessage = findViewById(R.id.iv_message);

        tvCountry = findViewById(R.id.tvCountry);
        ivCountry = findViewById(R.id.ivCountry);
        Button search = findViewById(R.id.btn);
        setSupportActionBar(toolbar);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tetNumber.getText() == null || TextUtils.isEmpty(tetNumber.getText().toString().trim())) {
                    showSnackBar("* Please enter phone number");
                    return;
                }

                AppUtils.openWhatsAppChat(MainActivity.this, mCountry.getdCode(), getText(tetNumber), getText(tetMessage));
            }
        });
        tetMessage.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivMessage.setSelected(true);
                } else {
                    ivMessage.setSelected(false);
                }
            }
        });
        tetNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ivNumber.setSelected(true);
                } else {
                    ivNumber.setSelected(false);
                }
            }
        });
        tvCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCountryList();
            }
        });
        tvCountry.setText("+(91) India");
        mCountry = new Country(1);
        mCountry.setdCode(91);
        mCountry.setName("India");
        ivCountry.setImageDrawable(getResources().getDrawable(R.drawable.in));

    }

    private void setCountryList() {
        ArrayList<Country> countries;
        if (!Validator.isNullOrEmpty(AppPrefs.getAppPrefs(this).getCountryPref().getList())) {
            countries = AppPrefs.getAppPrefs(this).getCountryPref().getList();
        } else {
            AppPrefs.getAppPrefs(this).setCountryPref(JSONConstants.countryListJson);
            countries = AppPrefs.getAppPrefs(this).getCountryPref().getList();
        }

        if (countries == null)
            return;

        final SelectCountryDialog bottomDialog = new SelectCountryDialog(this, countries, countries.indexOf(mCountry), getText(tvCountry));
        bottomDialog.setOnItemSelectListener(new SelectCountryDialog.OnItemSelectListener() {
            @Override
            public void onItemClick(Country country, int position) {
                if (bottomDialog.isShowing() && !isFinishing()) {
                    bottomDialog.dismiss();
                }
                if (Validator.isNull(country))
                    return;
                mCountry = country;

                tvCountry.setText("+(" + country.getdCode() + ") " + country.getName());

                int res = getResources().getIdentifier(country.getFlag(), "drawable", getPackageName());
                ivCountry.setImageResource(res);
                ivCountry.setSelected(false);
            }
        });
        bottomDialog.show();
    }

    public String getText(EditText view) {
        if (view != null && view.getText() != null)
            return view.getText().toString().trim();
        return "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_share:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Download " + getResources().getString(R.string.app_name) + " Application");
                String message = "\n" + getResources().getString(R.string.app_name) + " App has been recommended to you by friend. Please access this link to download it\n\n";
                message = message + "https://play.google.com/store/apps/details?id= \n\n";
                i.putExtra(Intent.EXTRA_TEXT, message);
                startActivity(Intent.createChooser(i, "Share App"));
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void showSnackBar(final String message) {
        AppUtils.hideSoftKeyboard(this);
        Snackbar snackbar = Snackbar.make(tetNumber, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.getLayoutParams().width = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) sbView.getLayoutParams();
        params.gravity = Gravity.TOP | Gravity.END;
        params.width = CoordinatorLayout.LayoutParams.WRAP_CONTENT;
        params.rightMargin = AppUtils.getDp2Px(this, 25);
        params.topMargin = AppUtils.getDp2Px(this, 100);
        sbView.setBackgroundColor(getResources().getColor(R.color.white));
        textView.setTextColor(getResources().getColor(R.color.red));
        sbView.setLayoutParams(params);
        snackbar.setDuration(2000);
        snackbar.show();
    }
}
