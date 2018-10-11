package com.jarvanmo.kal.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import android.widget.Button;
import android.widget.TextView;

import com.jarvanmo.kal.R;


/**
 * Created by mo on 16-3-26.
 * <p>
 * fragment
 */
public class AlertDialogFragment extends DialogFragment {

    static final String KEY_MESSAGE = "message";
    static final String KEY_TITLE = "title";
    static final String KEY_POSITIVE_TEXT = "positiveText";
    static final String KEY_NEGATIVE_TEXT = "negativeText";
    static final String KEY_NEUTRAL_TEXT = "neutralText";
    static final String KEY_IS_SINGLE = "isSingle";
    static final String KEY_TITLE_TEXT_SIZE = "title_text_size";
    static final String KEY_MESSAGE_TEXT_SIZE = "message_text_size";
    static final String KEY_BUTTON_TEXT_SIZE = "button_text_size";
    private AlertDialog mDialog;

    private DialogFragmentListener listener;

    private PositiveListener mPositiveListener;
    private NegativeListener mNegativeListener;


    private boolean isSingle = false;

    @Override
    public void onStart() {
        super.onStart();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Bundle bundle = getArguments();
        CharSequence msg = bundle.getCharSequence(KEY_MESSAGE, "");
        CharSequence positiveText = bundle.getCharSequence(KEY_POSITIVE_TEXT, "");
        CharSequence negativeText = bundle.getCharSequence(KEY_NEGATIVE_TEXT, "");
        CharSequence title = bundle.getCharSequence(KEY_TITLE, "");


        int titleTextSize = bundle.getInt(KEY_TITLE_TEXT_SIZE, -1);
        int messageTextSize = bundle.getInt(KEY_MESSAGE_TEXT_SIZE, -1);
        int buttonTextSize = bundle.getInt(KEY_BUTTON_TEXT_SIZE, -1);

        isSingle = bundle.getBoolean(KEY_IS_SINGLE);

        if ("".equals(positiveText)) {
            positiveText = getString(R.string.default_positive_text);
        }

        if ("".equals(negativeText)) {
            negativeText = getString(R.string.default_negative_text);
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(msg);
        builder.setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
                dismiss();
                if (mPositiveListener != null) {
                    mPositiveListener.onPositiveClick();
                }
            }
        });

        if (!"".equals(title)) {
            builder.setTitle(title);
        }

        if (!isSingle) {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mDialog.dismiss();
                    dismiss();
                    if (mNegativeListener != null) {
                        mNegativeListener.onNegativeClick();
//                    listener.onNegative();
                    }
                }
            });

        }
        mDialog = builder.create();
        mDialog.show();

        if (titleTextSize != -1) {
            int titleId = getResources().getIdentifier("alertTitle", "id", "android");
            if (titleId > 0) {
                TextView dialogTitle = (TextView) mDialog.findViewById(titleId);
                if (dialogTitle != null) {
                    dialogTitle.setTextSize(titleTextSize);
                }
            }
        }

        if (messageTextSize != -1) {
            int messageId = getResources().getIdentifier("message", "id", "android");
            if (messageId > 0) {
                TextView messageTextView = (TextView) mDialog.findViewById(messageId);
                if (messageTextView != null) {
                    messageTextView.setTextSize(messageTextSize);

                }
            }
        }

        if (buttonTextSize != -1) {
            final Button positiveBtn = mDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            if (positiveBtn != null) {
                positiveBtn.setTextSize(buttonTextSize);
            }


            final Button negativeBtn = mDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            if (negativeBtn != null) {
                negativeBtn.setTextSize(buttonTextSize);
            }

            final Button neutralBtn = mDialog.getButton(DialogInterface.BUTTON_NEUTRAL);
            if (neutralBtn != null) {
                neutralBtn.setTextSize(buttonTextSize);
            }
        }


        return mDialog;
    }

    public void setListener(DialogFragmentListener listener) {
        this.listener = listener;
    }

    public void setPositiveListener(PositiveListener listener) {
        mPositiveListener = listener;
    }

    public void setNegativeListener(NegativeListener listener) {
        mNegativeListener = listener;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    public boolean isShowing() {
        return mDialog != null && mDialog.isShowing();
    }
}
