package com.mhy.hyappicon.demo;

import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mhy.hyappicon.HyAppIconUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================
 * 作    者：mahongyin
 * 项    目：HYAppIcon
 * 目    录：com.mhy.hyappicon.demo
 * 创建日期：2024/8/12 上午10:35
 * 描    述：
 * 使    用：
 * =====================================
 */
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.other_activity).setVisibility(View.GONE);
        findViewById(R.id.current_icon).setVisibility(View.GONE);

        ComponentName componentName6 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconE");
        ComponentName componentName5 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconD");
        ComponentName componentName4 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconC");
        ComponentName componentName3 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconB");
        ComponentName componentName2 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconA");
        ComponentName componentName1 = new ComponentName(this, "com.mhy.hyappicon.demo.LaunchActivity");

        List<ComponentName> list = new ArrayList<>();
        list.add(componentName6);
        list.add(componentName5);
        list.add(componentName4);
        list.add(componentName3);
        list.add(componentName2);
        list.add(componentName1);
        HyAppIconUtils.initAllIconComponentName(list, componentName1);


        findViewById(R.id.btn_change_icon6).setOnClickListener(v -> changeIcon(componentName6));
        findViewById(R.id.btn_change_icon5).setOnClickListener(v -> changeIcon(componentName5));
        findViewById(R.id.btn_change_icon4).setOnClickListener(v -> changeIcon(componentName4));
        findViewById(R.id.btn_change_icon3).setOnClickListener(v -> changeIcon(componentName3));
        findViewById(R.id.btn_change_icon2).setOnClickListener(v -> changeIcon(componentName2));
        findViewById(R.id.btn_change_icon).setOnClickListener(v -> changeIcon(componentName1));
    }

    private void changeIcon(ComponentName componentName) {
        HyAppIconUtils.changeAppIcon(this, componentName, null);
    }
}
