package com.example.listycitylab3;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements EditCityDialog.Listener, AddCityFragment.AddCityDialogListener {

    private ArrayList<City> dataList;
    private CityArrayAdapter cityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView cityList = findViewById(R.id.city_list);

        // Seed data (you can replace with your own)
        String[] cities = {"Edmonton", "Vancouver", "Toronto"};
        String[] provinces = {"AB", "BC", "ON"};

        dataList = new ArrayList<>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }

        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        // Tap a row to EDIT that city
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            City selected = dataList.get(position);
            EditCityDialog dialog = EditCityDialog.newInstance(selected, position);
            dialog.show(getSupportFragmentManager(), "edit_city");
        });

        // FloatingActionButton to ADD a new city
        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v ->
                new AddCityFragment().show(getSupportFragmentManager(), "AddCity")
        );
    }

    /* ===== Callbacks from dialogs ===== */

    // From EditCityDialog: update existing row
    @Override
    public void onCityEdited(int position, String newName, String newProvince) {
        City c = dataList.get(position);
        c.setName(newName);
        c.setProvince(newProvince);
        cityAdapter.notifyDataSetChanged();
    }

    // From AddCityFragment: append new row
    @Override
    public void addCity(City city) {
        dataList.add(city);
        cityAdapter.notifyDataSetChanged();
    }
}
