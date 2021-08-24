package com.example.caketouch.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateGet {
    public static long Time(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
