package com.thinkcore.dialog.progress;

import android.app.ProgressDialog;
import android.content.Context;

class TDialog extends ProgressDialog {
    private Context mContext;

    public TDialog(Context context) {
        super(context);
        mContext = context;
    }

    public TDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public Context getContextByActivity() {
        return mContext;
    }
}
