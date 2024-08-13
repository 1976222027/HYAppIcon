package com.mhy.hyappicon.demo;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
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
 * 创建日期：2024/8/12 上午9:57
 * 描    述：
 * 使    用：
 * =====================================
 */
public class LaunchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backPressed();
            }
        });
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
        findViewById(R.id.other_activity).setOnClickListener(v -> startActivity(new Intent(LaunchActivity.this, IndexActivity.class)));


        findViewById(R.id.current_icon).setOnClickListener(
                v -> {
                    currentName();
                });
    }

    private void currentName() {
        //原主的Activity.getComponentName()就是作用在他身上可用的那个别名
        ComponentName componentName = LaunchActivity.this.getComponentName();
        ((Button) findViewById(R.id.current_icon)).setText(componentName.getShortClassName());
        Log.e("HyAppIcon", "原主的getComponentName():" + componentName.getShortClassName());
    }

    /**
     * 退出时自动做替换
     */
    public void backPressed() {
//        ComponentName componentName2 = new ComponentName(this, "com.mhy.hyappicon.demo.faviconA");
//        //换启动图标
//        HyAppIconUtils.changeAppIcon(this, componentName2);
        finish();
        System.exit(0);//有时候在退出时 主动杀掉进程会增进android10以下设备快些换标
    }

    private void changeIcon(ComponentName componentName) {
        HyAppIconUtils.changeAppIcon(this, componentName, null);
        //********************************************
        //最后,解决低版本手机不能立即生效的问题
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            //使10以下立即生效，只能在栈根调用，否则会有问题，或者自己主动清栈
            System.exit(0);
        }
        //********************************************
    }
}
