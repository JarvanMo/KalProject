package com.jarvanmo.kal.dialog;

import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;

import com.jarvanmo.kal.util.Preconditions;


/**
 * Created by mo on 16-3-26.
 * builder
 */
public class SimpleDialogBuilder {

    private FragmentActivity fragmentActivity;
    private CharSequence message = "";
    private CharSequence positiveText = "";
    private CharSequence negativeText = "";
    private CharSequence neutralText = "";
    private CharSequence title = "";
    private boolean isSingle = false;
    private boolean isCancelable = true;
    private AlertDialogFragment alertDialogFragment;
    private DialogFragmentListener listener;
    private PositiveListener mPositiveListener;
    private NegativeListener mNegativeListener;
    private int mTitleTextSize = -1;
    private int mMessageTextSize = -1;
    private int mButtonTextSize = -1;


    public SimpleDialogBuilder(FragmentActivity activity) {
        this.fragmentActivity = activity;
    }

    public SimpleDialogBuilder message(CharSequence message) {
        this.message = message;
        return this;
    }


    public SimpleDialogBuilder message(int msgId) {

        message(Preconditions.checkNotNull(fragmentActivity).getString(msgId));
        return this;
    }


    public SimpleDialogBuilder positiveText(CharSequence positiveText) {
        this.positiveText = positiveText;
        return this;
    }

    public SimpleDialogBuilder positiveText(@StringRes int positiveTextId) {
        return positiveText(Preconditions.checkNotNull(fragmentActivity).getString(positiveTextId));
    }

    public SimpleDialogBuilder negativeText(CharSequence negativeText) {
        this.negativeText = negativeText;
        return this;
    }

    public SimpleDialogBuilder negativeText(@StringRes int negativeTextId) {
        return negativeText(Preconditions.checkNotNull(fragmentActivity).getString(negativeTextId));
    }

    public SimpleDialogBuilder neutralText(CharSequence neutralText) {
        this.neutralText = neutralText;
        return this;
    }

    public SimpleDialogBuilder neutralText(@StringRes int neutralTextId) {
        return neutralText(Preconditions.checkNotNull(fragmentActivity).getString(neutralTextId));
    }

    public SimpleDialogBuilder title(CharSequence title) {
        this.title = title;
        return this;
    }

    public SimpleDialogBuilder title(@StringRes int titleId) {
        return title(Preconditions.checkNotNull(fragmentActivity).getString(titleId));
    }


    /***
     * it doesn't work now
     **/
    @Deprecated
    public SimpleDialogBuilder listen(DialogFragmentListener listener) {
        this.listener = listener;
        return this;
    }

    public SimpleDialogBuilder titleTextSize(int titleTextSize) {
        mTitleTextSize = titleTextSize;
        return this;
    }

    public SimpleDialogBuilder messageTextSize(int messageTextSize) {
        mMessageTextSize = messageTextSize;
        return this;
    }

    public SimpleDialogBuilder buttonTextSize(int buttonTextSize) {
        mButtonTextSize = buttonTextSize;
        return this;
    }

    public SimpleDialogBuilder onPositive(PositiveListener positiveListener) {
        mPositiveListener = positiveListener;
        return this;
    }

    public SimpleDialogBuilder onNegative(NegativeListener negativeListener) {
        this.mNegativeListener = negativeListener;
        return this;
    }

    public SimpleDialogBuilder singleButton() {
        isSingle = true;
        return this;
    }

    public SimpleDialogBuilder cancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        return this;
    }

    public AlertDialogFragment show() {

        alertDialogFragment = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putCharSequence(AlertDialogFragment.KEY_MESSAGE, message);
        bundle.putCharSequence(AlertDialogFragment.KEY_TITLE, title);
        bundle.putCharSequence(AlertDialogFragment.KEY_POSITIVE_TEXT, positiveText);
        bundle.putCharSequence(AlertDialogFragment.KEY_NEUTRAL_TEXT, neutralText);
        bundle.putInt(AlertDialogFragment.KEY_TITLE_TEXT_SIZE, mTitleTextSize);
        bundle.putInt(AlertDialogFragment.KEY_MESSAGE_TEXT_SIZE, mMessageTextSize);
        bundle.putInt(AlertDialogFragment.KEY_BUTTON_TEXT_SIZE, mButtonTextSize);
        bundle.putCharSequence(AlertDialogFragment.KEY_NEGATIVE_TEXT, negativeText);
        bundle.putBoolean(AlertDialogFragment.KEY_IS_SINGLE, isSingle);
        alertDialogFragment.setArguments(bundle);
        alertDialogFragment.setCancelable(isCancelable);
        alertDialogFragment.setListener(listener);
        alertDialogFragment.setPositiveListener(mPositiveListener);
        alertDialogFragment.setNegativeListener(mNegativeListener);
        if (!fragmentActivity.isFinishing()) {
            alertDialogFragment.show(fragmentActivity.getSupportFragmentManager(), message.toString());
        }
        return alertDialogFragment;
    }

    public boolean isShowing() {
        return alertDialogFragment != null && alertDialogFragment.isShowing();
    }

    public void dismiss() {
        if (alertDialogFragment != null && alertDialogFragment.isShowing()) {
            alertDialogFragment.dismiss();
        }
    }
}
