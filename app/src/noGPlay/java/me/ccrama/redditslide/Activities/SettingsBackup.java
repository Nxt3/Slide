package me.ccrama.redditslide.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.ccrama.redditslide.R;
import me.ccrama.redditslide.SettingValues;
import me.ccrama.redditslide.util.ZipUtil;


/**
 * Created by l3d00m on 2/17/2015.
 */
public class SettingsBackup extends BaseActivityAnim {

    MaterialDialog progress;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applyColorTheme();
        setContentView(R.layout.activity_settings_sync);
        setupAppBar(R.id.toolbar, R.string.settings_title_backup, true, true);

        new AlertDialogWrapper.Builder(SettingsBackup.this)
                .setTitle("F-Droid version")
                .setMessage("Sorry, it isn't possible to backup to Google Drive on the F-Droid version.")
                .setCancelable(false)
                .setNeutralButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .show();

        if (SettingValues.tabletUI) {

            findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress = new MaterialDialog.Builder(SettingsBackup.this)
                            .title(R.string.backup_backing_up)
                            .content(R.string.misc_please_wait)
                            .progress(true, 1)
                            .cancelable(false)
                            .build();
                    progress.show();

                    doBackup();
                }

            });


            findViewById(R.id.restore).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    progress = new MaterialDialog.Builder(SettingsBackup.this)
                            .title(R.string.backup_restoring)
                            .content(R.string.misc_please_wait)
                            .cancelable(false)
                            .progress(true, 1)
                            .build();
                    progress.show();


                    doRestore();

                }
            });
        } else {
            new AlertDialogWrapper.Builder(SettingsBackup.this)
                    .setTitle(R.string.general_pro)
                    .setMessage(R.string.general_pro_msg)
                    .setCancelable(false)
                    .setPositiveButton(R.string.btn_sure, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            try {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=me.ccrama.slideforreddittabletuiunlock")));
                            } catch (android.content.ActivityNotFoundException anfe) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=me.ccrama.slideforreddittabletuiunlock")));
                            }
                        }
                    }).setNegativeButton(R.string.btn_no_danks, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            }).show();
        }
    }

    private void doBackup() {
        List<String> prefsFiles = new ArrayList<>();
        File prefsDirectory = new File(getApplicationInfo().dataDir, "shared_prefs");

        if (prefsDirectory.exists() && prefsDirectory.isDirectory()) {

            String[] list = prefsDirectory.list();

            for (final String s : list) {
                if (!s.contains("com.google")) {
                    final String filePath = getApplicationInfo().dataDir + File.separator + "shared_prefs" + File.separator + s;
                    prefsFiles.add(filePath);
                }
            }
        }

        //fixme needs to use another directory
        ZipUtil.zipFiles(prefsFiles, Environment.getDataDirectory(), "slideBackup.zip");
    }

    private void doRestore() {
        final String outputPath = getApplicationInfo().dataDir + File.separator + "shared_prefs" + File.separator;
        final String zipPath = Environment.getDataDirectory().getAbsolutePath() + "slideBackup.zip";
        ZipUtil.unzipFiles(outputPath, zipPath);
    }

}