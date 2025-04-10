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
        overridePendingTransition(0, 0);
        //"应用换标 过渡页" 直接进入主页了
        startActivity(new Intent(this, IndexActivity.class));
        // 换图标会清栈，那让他清理独立栈。
        ComponentName componentName5 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconD");
        HyAppIconUtils.changeAppIcon(this, componentName5);
        //finish();
        finishAffinity();//关闭所有亲和活动 该独立栈
        overridePendingTransition(0, 0);
    }
}
