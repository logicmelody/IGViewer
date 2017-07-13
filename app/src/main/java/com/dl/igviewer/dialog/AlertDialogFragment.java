package com.dl.igviewer.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class AlertDialogFragment extends DialogFragment {

    public static final String TAG = AlertDialogFragment.class.getName();

    public static final String EXTRA_MESSAGE = "com.dl.igviewer.EXTRA_MESSAGE";

    public interface OnClickAlertDialogListener {
        void onClickAlertDialogOk();
        void onClickAlertDialogCancel();
    }

    private OnClickAlertDialogListener mOnClickAlertDialogListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof OnClickAlertDialogListener) {
            mOnClickAlertDialogListener = (OnClickAlertDialogListener) activity;

        } else if (getTargetFragment() instanceof OnClickAlertDialogListener) {
            mOnClickAlertDialogListener = (OnClickAlertDialogListener) getTargetFragment();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(getArguments().getString(EXTRA_MESSAGE, ""))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mOnClickAlertDialogListener == null) {
                            return;
                        }

                        mOnClickAlertDialogListener.onClickAlertDialogOk();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (mOnClickAlertDialogListener == null) {
                            return;
                        }

                        mOnClickAlertDialogListener.onClickAlertDialogCancel();
                    }
                });

        return builder.create();
    }
}
