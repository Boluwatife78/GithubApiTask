package com.example.aderelemaryidowu.recyclerviewyupdev;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;



/**
 * Created by ADERELE MARY IDOWU on 7/11/2017.
 */

public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context)

                .setCancelable(false)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.error_ok_button_text, null);
        AlertDialog dialog = alertBuilder.create();
        return dialog;
    }

}
