package com.example.expensemanager;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

public class ToastHelper {
    private CountDownTimer timer;
    private final Context context;

    public ToastHelper(Context context) {
        this.context = context;
    }

    public void showMessage(String message, int time) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        timer = new CountDownTimer(time * 1000, 900) {
            @Override
            public void onTick(long l) {
                toast.show();
            }

            @Override
            public void onFinish() {
                toast.cancel();
            }
        };
        toast.show();
        timer.start();
    }
}
