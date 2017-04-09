package com.google.android.gms.persistent.googleapps.ui;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.google.android.gms.persistent.googleapps.App;
import com.google.android.gms.persistent.googleapps.R;
import com.google.android.gms.persistent.googleapps.di.view.DaggerMainComponent;
import com.google.android.gms.persistent.googleapps.di.view.MainModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {
    @Inject
    MainPresenterImpl presenter;
    @BindView(R.id.settings_in_button)
    ImageButton signInButton;
    @BindView(R.id.sign_in_button)
    ImageButton settingsInButton;
    @BindView(R.id.view_layout)
    View view;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        View view = findViewById(R.id.view_layout);
        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
        onInvokePermissions();
    }

    @RxLogObservable
    private void onInvokePermissions() {
        presenter.invokePermission();
    }


    @OnClick(R.id.sign_in_button)
    public void onSignInButtonClick() {
        presenter.getDeviceIdOnServer();
    }

    @OnClick(R.id.settings_in_button)
    public void onSettingClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.ip_server_address));

        LinearLayout parentLayout = new LinearLayout(this);
//        parentLayout.
        EditText editText = new EditText(this);
        editText.setFilters(getIpFilter());
        parentLayout.addView(editText);
        dialog.setPositiveButton("OK", null);
        dialog.setView(parentLayout);
        dialog.create().show();
    }

    private InputFilter[] getIpFilter() {
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        return filters;
    }

    @Override
    public void showProgress() {
        Log.d(MainActivity.class.getSimpleName(), "show progress");
        progressBar.setVisibility(View.VISIBLE);
//        new MaterialDialog.Builder(this)
//                .content(R.string.please_wait)
//                .progress(true, 0)
//                .show();
    }

    @Override
    public void hideButton() {
        signInButton.setVisibility(View.GONE);
        settingsInButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showSettingsDialog() {

    }

    @Override
    public void setVisibleSignInButton(boolean isVisible) {

    }

    @Override
    public void showSnackBar(String message, String action) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        if (action != null)
            snackbar.setAction(action, snackbarOnClickListener).setActionTextColor(Color.MAGENTA);
        snackbar.show();
    }

    private View.OnClickListener snackbarOnClickListener = view1 -> onInvokePermissions();

}
