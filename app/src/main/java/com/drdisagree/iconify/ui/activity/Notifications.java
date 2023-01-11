package com.drdisagree.iconify.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.drdisagree.iconify.Iconify;
import com.drdisagree.iconify.R;
import com.drdisagree.iconify.common.References;
import com.drdisagree.iconify.config.PrefConfig;
import com.drdisagree.iconify.overlaymanager.NotificationManager;
import com.drdisagree.iconify.ui.fragment.LoadingDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Objects;

public class Notifications extends AppCompatActivity {

    private static final String[] NOTIFICATION_KEY = new String[]{"IconifyComponentNF1.overlay",
            "IconifyComponentNF2.overlay", "IconifyComponentNF3.overlay", "IconifyComponentNF4.overlay",
            "IconifyComponentNF5.overlay", "IconifyComponentNF6.overlay", "IconifyComponentNF7.overlay",
            "IconifyComponentNF8.overlay", "IconifyComponentNF9.overlay", "IconifyComponentNF10.overlay",
            "IconifyComponentNF11.overlay", "IconifyComponentNF12.overlay", "IconifyComponentNF13.overlay",
            "IconifyComponentNF14.overlay"};

    LoadingDialog loadingDialog;
    private ViewGroup container;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        // Header
        CollapsingToolbarLayout collapsing_toolbar = findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle(getResources().getString(R.string.activity_title_notification));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Loading dialog while enabling or disabling pack
        loadingDialog = new LoadingDialog(this);

        // Notifications list items
        container = (ViewGroup) findViewById(R.id.notification_list);
        ArrayList<Object[]> notif_list = new ArrayList<>();

        // Notifications add items in list
        notif_list.add(new Object[]{"Default", R.drawable.notif_default});
        notif_list.add(new Object[]{"Layers", R.drawable.notif_layers});
        notif_list.add(new Object[]{"Thin Outline", R.drawable.notif_thin_outline});
        notif_list.add(new Object[]{"Bottom Outline", R.drawable.notif_bottom_outline});
        notif_list.add(new Object[]{"Neumorph", R.drawable.notif_neumorph});
        notif_list.add(new Object[]{"Stack", R.drawable.notif_stack});
        notif_list.add(new Object[]{"Side Stack", R.drawable.notif_side_stack});
        notif_list.add(new Object[]{"Outline", R.drawable.notif_outline});
        notif_list.add(new Object[]{"Leafy Outline", R.drawable.notif_leafy_outline});
        notif_list.add(new Object[]{"Lighty", R.drawable.notif_lighty});
        notif_list.add(new Object[]{"Neumorph Outline", R.drawable.notif_neumorph_outline});
        notif_list.add(new Object[]{"Cyberponk", R.drawable.notif_cyberponk});
        notif_list.add(new Object[]{"Cyberponk v2", R.drawable.notif_cyberponk_v2});
        notif_list.add(new Object[]{"Thread Line", R.drawable.notif_thread_line});

        addItem(notif_list);

        // Enable onClick event
        for (int i = 0; i < container.getChildCount(); i++) {
            enableOnClickListener(container.getChildAt(i).findViewById(R.id.notification_child),
                    container.getChildAt(i).findViewById(R.id.list_button_enable_notif),
                    container.getChildAt(i).findViewById(R.id.list_button_disable_notif),
                    NOTIFICATION_KEY[i], i);
        }

        refreshBackground();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Function to check for layout changes
    private void refreshLayout(LinearLayout layout) {
        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout child = container.getChildAt(i).findViewById(R.id.notification_child);
            if (!(child == layout)) {
                container.getChildAt(i).findViewById(R.id.list_button_enable_notif).setVisibility(View.GONE);
                container.getChildAt(i).findViewById(R.id.list_button_disable_notif).setVisibility(View.GONE);
            }
        }
    }

    // Function to check for bg drawable changes
    @SuppressLint("SetTextI18n")
    private void refreshBackground() {
        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout child = container.getChildAt(i).findViewById(R.id.notification_child);
            TextView title = child.findViewById(R.id.notif_title);
            if (PrefConfig.loadPrefBool(Iconify.getAppContext(), NOTIFICATION_KEY[i])) {
                title.setText(title.getText().toString().replace(' ' + getResources().getString(R.string.opt_applied), "") + ' ' + getResources().getString(R.string.opt_applied));
                title.setTextColor(getResources().getColor(R.color.colorSuccess));
            } else {
                title.setText(title.getText().toString().replace(' ' + getResources().getString(R.string.opt_applied), ""));
                title.setTextColor(getResources().getColor(R.color.textColorPrimary));
            }
        }
    }

    // Function to disable other packs if one is applied
    private void disable_others(String pack) {
        for (int i = 0; i < References.TOTAL_NOTIFICATIONS; i++)
            PrefConfig.savePrefBool(Iconify.getAppContext(), NOTIFICATION_KEY[i], pack.equals(NOTIFICATION_KEY[i]));
    }

    // Function for onClick events
    private void enableOnClickListener(LinearLayout layout, Button enable, Button disable, String key, int index) {
        // Set onClick operation for options in list
        layout.setOnClickListener(v -> {
            refreshLayout(layout);
            if (!PrefConfig.loadPrefBool(Iconify.getAppContext(), key)) {
                disable.setVisibility(View.GONE);
                if (enable.getVisibility() == View.VISIBLE) {
                    enable.setVisibility(View.GONE);
                    layout.findViewById(R.id.notif_arrow).setForeground(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_expand_arrow));
                } else {
                    enable.setVisibility(View.VISIBLE);
                    layout.findViewById(R.id.notif_arrow).setForeground(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_collapse_arrow));
                }
            } else {
                enable.setVisibility(View.GONE);
                if (disable.getVisibility() == View.VISIBLE) {
                    disable.setVisibility(View.GONE);
                    layout.findViewById(R.id.notif_arrow).setForeground(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_expand_arrow));
                } else {
                    disable.setVisibility(View.VISIBLE);
                    layout.findViewById(R.id.notif_arrow).setForeground(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_collapse_arrow));
                }
            }
        });

        // Set onClick operation for Enable button
        enable.setOnClickListener(v -> {
            refreshLayout(layout);
            // Show loading dialog
            loadingDialog.show(getResources().getString(R.string.loading_dialog_wait));

            @SuppressLint("SetTextI18n") Runnable runnable = () -> {
                disable_others(key);
                NotificationManager.install_pack(index + 1);

                runOnUiThread(() -> {
                    PrefConfig.savePrefBool(Iconify.getAppContext(), key, true);

                    new Handler().postDelayed(() -> {
                        // Hide loading dialog
                        if (loadingDialog != null)
                            loadingDialog.hide();

                        // Change name to " - applied"
                        TextView title = layout.findViewById(R.id.notif_title);
                        title.setText(title.getText().toString().replace(' ' + getResources().getString(R.string.opt_applied), "") + ' ' + getResources().getString(R.string.opt_applied));
                        title.setTextColor(getResources().getColor(R.color.colorSuccess));

                        // Change button visibility
                        enable.setVisibility(View.GONE);
                        disable.setVisibility(View.VISIBLE);
                        refreshBackground();

                        Toast.makeText(Iconify.getAppContext(), getResources().getString(R.string.toast_applied), Toast.LENGTH_SHORT).show();
                    }, 2000);
                });
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });

        // Set onClick operation for Disable button
        disable.setOnClickListener(v -> {
            // Show loading dialog
            loadingDialog.show(getResources().getString(R.string.loading_dialog_wait));

            Runnable runnable = () -> {
                NotificationManager.disable_pack(index + 1);

                runOnUiThread(() -> {
                    PrefConfig.savePrefBool(Iconify.getAppContext(), key, false);

                    new Handler().postDelayed(() -> {
                        // Hide loading dialog
                        loadingDialog.hide();

                        // Change name back to original
                        TextView title = layout.findViewById(R.id.notif_title);
                        title.setText(title.getText().toString().replace(' ' + getResources().getString(R.string.opt_applied), ""));
                        title.setTextColor(getResources().getColor(R.color.textColorPrimary));

                        // Change button visibility
                        disable.setVisibility(View.GONE);
                        enable.setVisibility(View.VISIBLE);
                        refreshBackground();

                        Toast.makeText(Iconify.getAppContext(), getResources().getString(R.string.toast_disabled), Toast.LENGTH_SHORT).show();
                    }, 2000);
                });
            };
            Thread thread = new Thread(runnable);
            thread.start();
        });
    }

    private void addItem(ArrayList<Object[]> pack) {
        for (int i = 0; i < pack.size(); i++) {
            View list = LayoutInflater.from(this).inflate(R.layout.list_option_notification, container, false);
            list.setBackground(ContextCompat.getDrawable(Notifications.this, (int) pack.get(i)[1]));

            TextView name = list.findViewById(R.id.notif_title);
            name.setText((String) pack.get(i)[0]);

            ImageView collapse_expand = list.findViewById(R.id.notif_arrow);
            collapse_expand.setForeground(ContextCompat.getDrawable(Notifications.this, R.drawable.ic_expand_arrow));

            container.addView(list);
        }
    }

    @Override
    public void onDestroy() {
        loadingDialog.hide();
        super.onDestroy();
    }
}