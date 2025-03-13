package com.mhy.hyappicon.demo;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By Mahongyin
 * Date    2025/3/13 15:07
 */
public class Utils {

    static void getMetaData(Context c) {
        try {
            // 获取PackageManager实例
            PackageManager packageManager = c.getPackageManager();
            // 获取当前应用的包名
            String packageName = c.getPackageName();
            // 获取应用的PackageInfo对象
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
            // 获取应用的版本号
            int versionCode = packageInfo.versionCode;
            // 获取应用的版本名称
            String versionName = packageInfo.versionName;
            // 获取应用的标签（名称）
            CharSequence appLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
            Log.d("PackageInfo", "Version Code: " + versionCode);
            Log.d("PackageInfo", "Version Name: " + versionName);
            Log.d("PackageInfo", "App Label: " + appLabel);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void getPermission(Context c) {
        try {
            // 获取PackageManager实例
            PackageManager packageManager = c.getPackageManager();
            // 获取当前应用的包名
            String packageName = c.getPackageName();
            // 获取应用的PackageInfo对象，并包含权限信息
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            // 获取应用声明的权限数组
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String permission : permissions) {
                    Log.d("Permissions", "Permission: " + permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 清单里默认 enabled 的那个
     *
     * @param context
     */
     static List<ComponentName> getMainLauncher(Context context) {
        // 获取配置了特定 intent-filter 的 ComponentName
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        //boolean hasResolve = intent.resolveActivity(context.getPackageManager()) != null;
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        return getComponentNames(context, resolveInfos);
    }

    @NonNull
    private static List<ComponentName> getComponentNames(Context context, List<ResolveInfo> resolveInfos) {
        List<ComponentName> componentNames = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfos) {
            if (resolveInfo.activityInfo != null) {
                if (resolveInfo.activityInfo.isEnabled()) {
                    ComponentName componentName = new ComponentName(
                            resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name);
                    componentNames.add(componentName);
                    Log.d("ComponentName", componentName.flattenToString());
                }
            }
        }
        return componentNames;
    }

}
