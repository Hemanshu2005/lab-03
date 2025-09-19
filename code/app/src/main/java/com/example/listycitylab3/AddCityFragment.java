package com.example.listycitylab3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // ⬇️ Unchained, 2-line inflate (most robust)
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.fragment_edit_city, null, false);

        EditText cityEt = view.findViewById(R.id.edit_text_city_text);
        EditText provEt = view.findViewById(R.id.edit_text_province_text);

        return new AlertDialog.Builder(requireContext())
                .setTitle("Add a city")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Add", (d, w) -> {
                    String cityName = cityEt.getText().toString().trim();
                    String provinceName = provEt.getText().toString().trim();
                    if (listener != null) {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
