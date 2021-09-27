package kr.loplab.gnss05.common;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import kr.loplab.gnss05.R;


public class DialogUtils {
	/**
     * Circle ProgressDialog(defaule title：prompt)
     *
     * @param messageId R.string.id
     */
    public static ProgressDialog showProgressDialog(Context ctx,
                                                    int messageId) {
        return showProgressDialog(ctx, "Information",
        		ctx.getString(messageId), false);
    }
    
    /**
     * Circle ProgressDialog
     *
     * @param title         title
     * @param message       message
     * @param indeterminate bool
     */
    public static ProgressDialog showProgressDialog(Context ctx,
                                                    CharSequence title, CharSequence message, boolean indeterminate) {
    	ProgressDialog mProgressDlg = new ProgressDialog(ctx);
        @SuppressLint("InflateParams") View v = LayoutInflater.from(ctx).inflate(R.layout.dialog_progress, null);

        mProgressDlg.setView(v);
        if (title != null && !title.toString().isEmpty()) {
            mProgressDlg.setTitle(title);
        }
        if (message != null && !message.toString().isEmpty()) {
            mProgressDlg.setMessage(message);
        }
        mProgressDlg.setCancelable(false);
        mProgressDlg.setIndeterminate(indeterminate);
        mProgressDlg.show();
        return mProgressDlg;
    }
}
