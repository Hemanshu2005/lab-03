package com.example.listycitylab3;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class EditCityDialog extends DialogFragment {

    public interface Listener {
        void onCityEdited(int position, String newName, String newProvince);
    }

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS  = "arg_pos";

    // Factory method (recommended pattern)
    public static EditCityDialog newInstance(City city, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY, city);    // City implements Serializable
        args.putInt(ARG_POS, position);
        EditCityDialog fragment = new EditCityDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(requireContext())
                .inflate(R.layout.fragment_edit_city, null, false);

        EditText nameEt = root.findViewById(R.id.edit_text_city_text);
        EditText provEt = root.findViewById(R.id.edit_text_province_text);

        City c = (City) requireArguments().getSerializable(ARG_CITY);
        int position = requireArguments().getInt(ARG_POS);

        if (c != null) {
            nameEt.setText(c.getName());
            provEt.setText(c.getProvince());
        }

        return new AlertDialog.Builder(requireContext())
                .setTitle("Edit City")
                .setView(root)
                .setPositiveButton("Save", (d, which) -> {
                    String newName = nameEt.getText().toString().trim();
                    String newProv = provEt.getText().toString().trim();

                    if (getActivity() instanceof Listener) {
                        ((Listener) getActivity()).onCityEdited(position, newName, newProv);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
