package com.testcore.ui.reveallayout;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

import com.testcore.R;

import java.lang.reflect.Field;


public class MultiChildActivity extends AppCompatActivity {

    private RevealLayout mRevealLayout;
    private boolean mIsAnimationSlowDown = false;
    private boolean mIsBaseOnTouchLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reveallay_activity_multi);

        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);

        initRevealLayout();
    }

    private void initRevealLayout() {
        if (mIsBaseOnTouchLocation) {
            mRevealLayout.setOnClickListener(null);
            mRevealLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        Log.d("SingleChildActivity", "x: " + event.getX() + ", y: " + event.getY());
                        if (mIsAnimationSlowDown) {
                            mRevealLayout.next((int) event.getX(), (int) event.getY(), 2000);
                        } else {
                            mRevealLayout.next((int) event.getX(), (int) event.getY());
                        }
                        return true;
                    }
                    return false;
                }
            });
        } else {
            mRevealLayout.setOnTouchListener(null);
            mRevealLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsAnimationSlowDown) {
                        mRevealLayout.next(2000);
                    } else {
                        mRevealLayout.next();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.slow) {
            item.setChecked(!item.isChecked());
            mIsAnimationSlowDown = item.isChecked();
            return true;
        } else if (item.getItemId() == R.id.touch) {
            item.setChecked(!item.isChecked());
            mIsBaseOnTouchLocation = item.isChecked();
            initRevealLayout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
