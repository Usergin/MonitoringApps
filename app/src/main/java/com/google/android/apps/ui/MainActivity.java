package com.google.android.apps.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.google.android.apps.App;
import com.google.android.apps.R;
import com.google.android.apps.data_collection.AboutDevice;
import com.google.android.apps.di.view.DaggerMainComponent;
import com.google.android.apps.di.view.MainModule;
import com.google.android.apps.service.LocationService;
import com.google.android.apps.service.SyncService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainView {
    public final static int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 11;
    @Inject
    MainPresenterImpl presenter;
    @Inject
    AboutDevice aboutDevice;
    @BindView(R.id.settings_in_button)
    ImageButton settingsInButton;
    @BindView(R.id.sign_in_button)
    ImageButton signInButton;
    @BindView(R.id.system_app_button)
    ImageButton system_app_button;
    @BindView(R.id.view_layout)
    View view;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent())
                .mainModule(new MainModule(this))
                .build().inject(this);
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        onInvokePermissions();
    }

    private void onInvokePermissions() {
        presenter.invokePermission();
    }

    @OnClick(R.id.sign_in_button)
    public void onSignInButtonClick() {
        presenter.getDeviceIdOnServer();
    }

    @OnClick(R.id.settings_in_button)
    public void onSettingClick() {
        presenter.onClickSettingsMenu();
    }

    @OnClick(R.id.system_app_button)
    public void onSystemAppClick() {
        presenter.onClickMakeSystemApp();
    }

    @Override
    public void onPostResume() {
        super.onPostResume();
        presenter.onTestSubject();

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
                        for (String split : splits) {
                            if (Integer.valueOf(split) > 255) {
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
        Timber.d("showProgress");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideButton() {
        signInButton.setVisibility(View.GONE);
        settingsInButton.setVisibility(View.GONE);
        system_app_button.setVisibility(View.GONE);
    }

    @Override
    public void showButton() {
        signInButton.setVisibility(View.VISIBLE);
        settingsInButton.setVisibility(View.VISIBLE);
        system_app_button.setVisibility(View.VISIBLE);
    }

    @Override
    public void killActivity() {
        Timber.d("killActivity");
        this.finish();
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSettingsDialog(String ip, String port) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(dialog.getContext());
        View alertView = layoutInflater.inflate(R.layout.enter_ip_address_layout, null);
        dialog.setView(alertView);

        EditText inputIpAddress = (EditText) alertView.findViewById(R.id.input_ip);
        inputIpAddress.setText(ip);
        inputIpAddress.setFilters(getIpFilter());
        EditText inputPort = (EditText) alertView.findViewById(R.id.input_port);
        inputPort.setText(port);
        dialog.setPositiveButton("OK", (dialog1, which) -> {
            int port1 = Integer.valueOf(inputPort.getText().toString());
            if (port1 > 65536) {
                TextInputLayout til = (TextInputLayout) findViewById(R.id.input_layout_port);
                til.setError(getString(R.string.error_in_port));
            } else {
                presenter.setNewSocketServer(inputIpAddress.getText().toString(), inputPort.getText().toString());
                dialog1.dismiss();
            }
        });
        dialog.create().show();
    }

    @Override
    public void showSnackBar(String message, String action) {
        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        if (action != null)
            snackbar.setAction(action, snackbarOnClickListener).setActionTextColor(Color.MAGENTA);
        snackbar.show();
    }

    @Override
    public void startService() {
        Intent syncIntent = new Intent(this, SyncService.class);
        Intent locationIntent = new Intent(this, LocationService.class);
        startService(syncIntent);
        startService(locationIntent);
    }

    private View.OnClickListener snackbarOnClickListener = view1 -> onInvokePermissions();

}
