package com.example.typingapp;

import android.icu.math.BigDecimal;
import android.os.Build;
import android.support.annotation.RequiresApi;

class TypingTimer {
    private long start;

    void start() {
        start = System.currentTimeMillis();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    float end() {
        BigDecimal end = new BigDecimal(System.currentTimeMillis() - start);
        BigDecimal thousand = new BigDecimal("1000");
        BigDecimal time = end.divide(thousand, 2, BigDecimal.ROUND_HALF_UP);

        return time.floatValue();
    }
}
