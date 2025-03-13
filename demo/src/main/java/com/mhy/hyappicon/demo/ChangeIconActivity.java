package com.mhy.hyappicon.demo;


import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mhy.hyappicon.HyAppIconUtils;

/**
 * Created By Mahongyin
 * Date    2025/3/13 21:20
 */
public class ChangeIconActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //"应用换标 过渡页" 直接进入主页了
        startActivity(new Intent(this, IndexActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 换图标会清栈，那让他清理独立栈。
        ComponentName componentName5 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconD");
        HyAppIconUtils.changeAppIcon(this,componentName5,null);
    }

    @Override
    public void finish() {
        overridePendingTransition(0, 0);
        super.finish();
    }
}
