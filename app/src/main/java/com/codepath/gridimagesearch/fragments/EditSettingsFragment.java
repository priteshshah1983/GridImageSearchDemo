package com.codepath.gridimagesearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.gridimagesearch.R;
import com.codepath.gridimagesearch.models.Settings;
// ...

public class EditSettingsFragment extends DialogFragment {

    private static final String TAG = "DialogFragment";

    public static final String EXTRA_SETTINGS = "com.codepath.gridimagesearch.settings";
    public static final String EXTRA_TITLE = "com.codepath.gridimagesearch.title";

    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private EditText etSiteFilter;

    public interface EditSettingsDialogListener {
        void onFinishEditDialog(Settings settings);
    }

    public EditSettingsFragment() {
        // Empty constructor required for DialogFragment
    }

    public static EditSettingsFragment newInstance(Settings settings, String title) {
        EditSettingsFragment fragment = new EditSettingsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(EXTRA_TITLE, title);
        arguments.putParcelable(EXTRA_SETTINGS, settings);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_settings, container);

        customizeTitle();
        setupViews(view);
        loadLastKnownSettings();

        return view;
    }

    private void loadLastKnownSettings() {
        Settings settings = getArguments().getParcelable(EXTRA_SETTINGS);
        if (settings != null) {
            Log.d(TAG, settings.toString());
            spImageSize.setSelection(((ArrayAdapter) spImageSize.getAdapter()).getPosition(settings.getImageSize()));
            spColorFilter.setSelection(((ArrayAdapter) spColorFilter.getAdapter()).getPosition(settings.getColorFilter()));
            spImageType.setSelection(((ArrayAdapter) spImageType.getAdapter()).getPosition(settings.getImageType()));
            etSiteFilter.setText(settings.getSiteFilter());
        }
    }

    private void setupViews(View view) {
        spImageSize = (Spinner) view.findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) view.findViewById(R.id.spColorFilter);
        spImageType = (Spinner) view.findViewById(R.id.spImageType);
        etSiteFilter = (EditText) view.findViewById(R.id.etSiteFilter);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from UI
                String imageSize = spImageSize.getSelectedItem().toString();
                String colorFilter = spColorFilter.getSelectedItem().toString();
                String imageType = spImageType.getSelectedItem().toString();
                String siteFilter = etSiteFilter.getText().toString();

                // Create a new Settings object
                Settings settings = new Settings();
                settings.setImageSize(imageSize);
                settings.setColorFilter(colorFilter);
                settings.setImageType(imageType);
                settings.setSiteFilter(siteFilter);

                // Notify listener
                EditSettingsDialogListener listener = (EditSettingsDialogListener) getActivity();
                listener.onFinishEditDialog(settings);

                dismiss();
            }
        });
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void customizeTitle() {
        String title = getArguments().getString(EXTRA_TITLE, "");
        getDialog().setTitle(title);
    }
}
