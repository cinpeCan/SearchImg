package com.cinpe.searchimg;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cinpe.searchimg.database.model.BasePojo;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.cinpe.searchimg", appContext.getPackageName());
    }

    @Test
    public void testLiveDateEqual(){
        BasePojo basePojo=BasePojo.builder().uId(UUID.randomUUID().toString()).name("哈哈哈").nameEn("hhh").build();

        MutableLiveData<BasePojo> a=new MutableLiveData<>(basePojo);
        MutableLiveData<BasePojo> b=new MutableLiveData<>(basePojo);

        Log.i("[是否相等]", "[是否相等]"+Objects.equals(a.getValue(),b.getValue()));
        assertEquals(a, b);
    }
}