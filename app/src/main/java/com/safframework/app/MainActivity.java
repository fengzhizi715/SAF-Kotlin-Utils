package com.safframework.app;

import android.app.Activity;
import android.os.Bundle;

import com.safframework.utils.SAFUtils;

/**
 * Created by tony on 2017/2/26.
 */

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SAFUtils.makeLogTag(this.getClass());
    }
}
