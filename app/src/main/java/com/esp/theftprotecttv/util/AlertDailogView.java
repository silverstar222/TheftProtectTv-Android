package com.esp.theftprotecttv.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.esp.theftprotecttvstage.R;

public class AlertDailogView {
    // Handle Ok Button by current activity
    public static final int BUTTON_OK = 1;
    // Handle Cancel Button by current activity
    public static final int BUTTON_CANCEL = 2;
    public static Dialog dialog;

    public static Dialog showAlert(Context context, String message,
                                   boolean isImgIndicator) {
        return showAlert(context, context.getString(R.string.Alert), message,
                context.getString(R.string.ok), false, "", isImgIndicator,
                null, 1, -1, false);
    }

    public static Dialog showAlert(Context context, String message,
                                   String btnText, boolean isImgIndicator) {
        return showAlert(context, context.getString(R.string.Alert), message,
                btnText, false, "", isImgIndicator, null, 1, -1, false);
    }

    public static Dialog showAlert(Context context, String title,
                                   String message, String btnText, boolean isImgIndicator, int img) {
        return showAlert(context, title, message, btnText, false, "",
                isImgIndicator, null, 1, img, false);
    }

    public static Dialog showAlert(Context context, String message,
                                   String btnText, boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener) {
        return showAlert(context, context.getString(R.string.Alert), message,
                btnText, false, "", isImgIndicator, clickListener, 1, -1, false);
    }

    public static Dialog showAlert(Context context, String message,
                                   String btnText, boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener, int tag) {
        return showAlert(context, context.getString(R.string.Alert), message,
                btnText, false, "", isImgIndicator, clickListener, tag, -1,
                false);
    }

    public static Dialog showAlert(Context context, String message,
                                   String btnText, boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener, int tag, int img) {

        return showAlert(context, context.getString(R.string.Alert), message,
                btnText, false, "", isImgIndicator, clickListener, tag, img,
                false);
    }

    public static Dialog showAlert(Context context, String title,
                                   String message, String btnText, boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener) {
        return showAlert(context, title, message, btnText, false, "",
                isImgIndicator, clickListener, 1, -1, false);
    }

    public static Dialog showAlert(Context context, String title,
                                   String message, String btnTitle_1, boolean isCancelButton,
                                   String btnTitle_2, boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener, int tag,
                                   boolean isInput) {
        return showAlert(context, title, message, btnTitle_1, isCancelButton,
                btnTitle_2, isImgIndicator, clickListener, tag, -1, isInput);
    }

    public static Dialog showAlert(Context context, String title,
                                   String btnTitle_1, boolean isCancelButton, String btnTitle_2,
                                   boolean isImgIndicator,
                                   OnCustPopUpDialogButoonClickListener clickListener, int tag,
                                   boolean isInput) {
        return showAlert(context, title, "", btnTitle_1, isCancelButton,
                btnTitle_2, isImgIndicator, clickListener, tag, -1, isInput);
    }

    public static Dialog showAlert(Context context, String message,
                                   String btnTitle_1, boolean isCancelButton, String btnTitle_2,
                                   boolean isImgIndicator,
                                   final OnCustPopUpDialogButoonClickListener clickListener,
                                   final int tag) {
        return showAlert(context, context.getString(R.string.Alert), message,
                btnTitle_1, isCancelButton, btnTitle_2, isImgIndicator,
                clickListener, tag, -1, false);
    }

    // Create Custom popup Alert dailog here
    public static Dialog showAlert(final Context context, String title,
                                   String message, String btnTitle_1, boolean isCancelButton,
                                   String btnTitle_2, boolean isImgIndicator,
                                   final OnCustPopUpDialogButoonClickListener clickListener,
                                   final int tag, int img, final boolean isInput) {

        Animation animation = AnimationUtils.loadAnimation(context,
                R.anim.push_up);
        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (dialog != null) {
            dialog.dismiss();
        }

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dailog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        TextView txt_message = (TextView) dialog.findViewById(R.id.txtMessage);

        final Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        final Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        ImageView imgIndicator = (ImageView) dialog
                .findViewById(R.id.imgIndicator);
        final LinearLayout layoutFirst = (LinearLayout) dialog
                .findViewById(R.id.layoutFirst);
        LinearLayout llCancel = (LinearLayout) dialog
                .findViewById(R.id.llCancel);

        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);

        if (title != null && !title.equals(""))
            txtTitle.setText(title);

        if (isInput) {
            txt_message.setVisibility(View.GONE);
        }
        txt_message.setText(message);
        btnOk.setText(btnTitle_1);
        btnCancel.setText(btnTitle_2);

        if (isImgIndicator) {
            if (img != -1)
                imgIndicator.setBackgroundResource(img);
            else
                imgIndicator.setBackgroundResource(R.drawable.connection_error);
            imgIndicator.setVisibility(View.VISIBLE);
        } else
            imgIndicator.setVisibility(View.GONE);
        btnOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.OnButtonClick(tag, AlertDailogView.BUTTON_OK);

                Pref.setValue(context, Config.PREF_IS_INTERNET_DIALOG_OPEN, 0);
                dialog.dismiss();
            }
        });

        if (isCancelButton) {
            llCancel.setVisibility(View.VISIBLE);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (clickListener != null)
                        clickListener.OnButtonClick(tag,
                                AlertDailogView.BUTTON_CANCEL);

                    Pref.setValue(context, Config.PREF_IS_INTERNET_DIALOG_OPEN, 0);
                    dialog.dismiss();
                }
            });

        } else {
            llCancel.setVisibility(View.GONE);
        }

        layoutFirst.setAnimation(animation);
        layoutFirst.setVisibility(View.VISIBLE);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutFirst.clearAnimation();
            }
        });

        return dialog;
    }

    // define Listener here
    public interface OnCustPopUpDialogButoonClickListener {
        public abstract void OnButtonClick(int tag, int buttonIndex);
    }
}