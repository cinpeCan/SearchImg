package com.cinpe.searchimg.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.cinpe.searchimg.APP;
import com.google.common.base.CaseFormat;

import java.io.File;

/**
 * @description: 描述
 * @author: Cinpecan
 * @e-mail: Cinpecan@outlook.com
 * @date: 2021/7/21
 * @version: 1.00
 * @tips: 补充/提示
 **/
public class UsuUtil {

    public static <T extends ViewDataBinding> int getDataBindRes(Context context, Class<T> t) {
        String s = t.getSimpleName();
        return context.getResources().getIdentifier(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                TextUtils.substring(s, 0, s.length() - 7)), "layout", context.getPackageName());

    }

    public static void showToast(CharSequence s) {
        Toast.makeText(APP.getsInstance(), s, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(CharSequence s, boolean needLong) {
        Toast.makeText(APP.getsInstance(), s, needLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取文件路径.(不含后缀)
     */
    public static String getDirAbsPath() {
        String VOICE_PATH;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            VOICE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "1";
        } else {
            VOICE_PATH = APP.getsInstance().getCacheDir().getAbsolutePath();
        }
        return VOICE_PATH;
    }
}
