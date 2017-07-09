package com.altice.calculadoradetiempos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MainActivity extends AppCompatActivity {

    private TextView WelcomeMsg;
    private RadioButton EnglishBtn;
    private RadioButton EspanishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WelcomeMsg = (TextView) findViewById(R.id.WelcomeMsg);
        EnglishBtn = (RadioButton) findViewById(R.id.EnRadioBtn);
        EspanishBtn = (RadioButton) findViewById(R.id.EspRadioBtn);
        EspanishBtn.setChecked(true);
        Intent currentIntent = getIntent();
        String lang = currentIntent.getStringExtra("language");
        if (lang!=null&&!lang.isEmpty()) {
            if (lang.equals("en")) EnglishBtn.setChecked(true);
        }

    }

    public void runApp(View view) {
        Intent intent = new Intent(MainActivity.this, AppMainActivity.class);
        startActivity(intent);

    }

    public void English(View view) {
        setLocale("en");

    }

    public void Spanish(View view) {
        setLocale("es");

    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("language", lang);
        startActivity(refresh);
        finish();
    }
}
