package com.cinpe.searchimg.database;

import android.net.Uri;

import androidx.room.TypeConverter;

import java.util.Calendar;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/1
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class MyTypeConverters {


    @TypeConverter
    public static String saveLoad(Uri uri) {
        return uri==null? null:uri.toString();
    }

    @TypeConverter
    public static Uri loadUri(String s) {
        try {
            return Uri.parse(s);
        }catch (Exception e){
            return null;
        }
    }

    @TypeConverter
    public Long calendarToDatestamp(Calendar calendar) {
        return calendar==null? null:calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar datestampToCalendar(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    //@TypeConverter
    //public static long getObservableLong(ObservableLong observableLong) {
    //    return observableLong.get();
    //}
    //
    //@TypeConverter
    //public static ObservableLong setObservableLong(long i) {
    //    return new ObservableLong(i);
    //}
    //
    //@TypeConverter
    //public static int getObservableInt(ObservableInt observableInt) {
    //    return observableInt.get();
    //}
    //
    //@TypeConverter
    //public static ObservableInt setObservableInt(int i) {
    //    return new ObservableInt(i);
    //}
    //
    //@TypeConverter
    //public static double getObservableDouble(ObservableDouble observableDouble) {
    //    return observableDouble.get();
    //}
    //
    //@TypeConverter
    //public static ObservableDouble setObservableDouble(double i) {
    //    return new ObservableDouble(i);
    //}
    //
    //@TypeConverter
    //public static String getObservableField(ObservableField<String> observableField) {
    //    return observableField.get();
    //}
    //
    //@TypeConverter
    //public static ObservableField<String> setObservableField(String t) {
    //    return new ObservableField<>(t);
    //}
    //
    //@TypeConverter
    //public static Uri getObservableField(ObservableField<Uri> observableField) {
    //    return observableField.get();
    //}
    //
    //@TypeConverter
    //public static ObservableField<Uri> setObservableField(Uri t) {
    //    return new ObservableField<>(t);
    //}

}
