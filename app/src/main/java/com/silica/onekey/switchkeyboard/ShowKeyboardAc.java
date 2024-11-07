package com.silica.onekey.switchkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ShowKeyboardAc extends Activity {

    public static final String DELAY_SHOW_KEY = "DELAY_SHOW_KEY";

    private long delay = 200L;

    private InputMethodManager imeManager;
    private View rootView;
    private DialogState mState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mState = DialogState.NONE;
        setContentView(R.layout.empty);
        rootView = findViewById(R.id.root_view);
        super.onCreate(savedInstanceState);
        imeManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if (getIntent() != null) {
            delay = getIntent().getLongExtra(DELAY_SHOW_KEY, delay);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mState == DialogState.PICKING) {
            mState = DialogState.CHOSEN;
        } else if (mState == DialogState.CHOSEN) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (imeManager != null) {
                    imeManager.showInputMethodPicker();
                }
                mState = DialogState.PICKING;
            }
        }, delay);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Close the back activity
        finish();
    }

    enum DialogState {
        NONE, PICKING, CHOSEN
    }
}
