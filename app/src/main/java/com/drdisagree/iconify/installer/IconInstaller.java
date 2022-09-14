package com.drdisagree.iconify.installer;

import android.widget.Toast;

import com.drdisagree.iconify.Iconify;
import com.drdisagree.iconify.SplashActivity;
import com.drdisagree.iconify.utils.OverlayUtils;
import com.topjohnwu.superuser.Shell;

import java.io.File;

public class IconInstaller {

    public static void install_pack(int n) {
        disable_others(n);
        enable_pack(n);
    }

    protected static void enable_pack(int n) {

        String[] paths = {"/system/product/overlay/IconifyComponentIPAS" + n + ".apk", "/system/product/overlay/IconifyComponentIPSUI" + n + ".apk"};

        for (String path : paths) {
            if (new File(path).exists()) {

                String overlay = (path.replaceAll("/system/product/overlay/", "")).replaceAll("apk", "overlay");

                try {
                    Shell.cmd("cmd overlay enable --user current " + overlay).exec();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    public static void disable_pack(int n) {

        String[] paths = {"/system/product/overlay/IconifyComponentIPAS" + n + ".apk", "/system/product/overlay/IconifyComponentIPSUI" + n + ".apk"};

        for (String path : paths) {
            if (new File(path).exists()) {

                String overlay = (path.replaceAll("/system/product/overlay/", "")).replaceAll("apk", "overlay");

                try {
                    Shell.cmd("cmd overlay disable --user current " + overlay).exec();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }

    protected static void disable_others(int n) {

        for (int i = 1; i <= 4; i++) {
            if (i != n) {
                String[] paths = {"/system/product/overlay/IconifyComponentIPAS" + i + ".apk", "/system/product/overlay/IconifyComponentIPSUI" + i + ".apk"};

                for (String path : paths) {
                    if (new File(path).exists()) {

                        String overlay = (path.replaceAll("/system/product/overlay/", "")).replaceAll("apk", "overlay");

                        try {
                            Shell.cmd("cmd overlay disable --user current " + overlay).exec();
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}