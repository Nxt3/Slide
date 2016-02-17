package me.ccrama.redditslide.Activities;

import android.content.DialogInterface;
import android.os.Bundle;

import com.afollestad.materialdialogs.AlertDialogWrapper;

import me.ccrama.redditslide.R;


/**
 * Created by l3d00m on 2/17/2015.
 */
public class SettingsBackup extends BaseActivityAnim {

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
                        finish();
                    }
                })
                .show();

        //TODO decide what should be done in fdroid version and what not
    }


}