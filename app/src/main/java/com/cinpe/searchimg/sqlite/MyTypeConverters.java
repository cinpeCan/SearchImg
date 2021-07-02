package com.cinpe.searchimg.sqlite;

import androidx.databinding.ObservableDouble;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableLong;
import androidx.room.TypeConverter;

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
    public static long getObservableLong(ObservableLong observableLong) {
        return observableLong.get();
    }

    @TypeConverter
    public static ObservableLong setObservableLong(long i) {
        return new ObservableLong(i);
    }

    @TypeConverter
    public static int getObservableInt(ObservableInt observableInt) {
        return observableInt.get();
    }

    @TypeConverter
    public static ObservableInt setObservableInt(int i) {
        return new ObservableInt(i);
    }

    @TypeConverter
    public static double getObservableDouble(ObservableDouble observableDouble) {
        return observableDouble.get();
    }

    @TypeConverter
    public static ObservableDouble setObservableDouble(double i) {
        return new ObservableDouble(i);
    }

    @TypeConverter
    public static String getObservableField(ObservableField<String> observableField) {
        return observableField.get();
    }

    @TypeConverter
    public static ObservableField<String> setObservableField(String t) {
        return new ObservableField<>(t);
    }

}
