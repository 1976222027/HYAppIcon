package com.mhy.hyappicon;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * =====================================
 * 作    者：mahongyin
 * 项    目：HYAppIcon
 * 目    录：com.mhy.permission
 * 创建日期：2024/8/1 下午3:02
 * 描    述：动态设置桌面图标
 * 使    用：
 * =====================================
 */
public class HyAppIconUtils {
    private static List<ComponentName> mComponentNameList;
    private static ComponentName mDefaultEnable;

    /**
     * 先把所有桌面图标目标给我
     *
     * @param nameList      所有图标的目标
     * @param defaultEnable 清单文件中默认启用的那个
     */
    public static void initAllIconComponentName(@NonNull List<ComponentName> nameList, @NonNull ComponentName defaultEnable) {
        mDefaultEnable = defaultEnable;
        mComponentNameList = new ArrayList<>();
        mComponentNameList.addAll(nameList);
    }

    /**
     * @param context 当前启动页？
     * @param enable  待启用的目标
     *  原理就是找出那个启用的给他禁用，再把目标启用
     */
    public static void changeAppIcon(Context context, ComponentName enable) {
        if (mComponentNameList == null || mDefaultEnable == null) {
            throw new RuntimeException("请先调用initAllComponentName方法初始化传入所有图标的目标");
        }
        int flag = PackageManager.DONT_KILL_APP;
        PackageManager packageManager = context.getPackageManager();
        for (ComponentName componentName : mComponentNameList) {
            if (componentName == enable) {//要启用的目标不做禁用 跳过
                continue;
            }
            //找到当前启用中的，将其禁用掉
            int disableState = packageManager.getComponentEnabledSetting(componentName);
            if (mDefaultEnable == componentName) {//默认启用的那个，enable 和 default 都是启用状态，还有默认状态的干扰
                if (disableState != PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {//不等于就要设置
                    //明确非禁用时
                    Log.i("应用换标disable ", componentName.getShortClassName());
                    packageManager.setComponentEnabledSetting(componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, flag);
                }
            } else {//其他的，状态disable 和 default 都是禁用状态
                if (disableState == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {//是启用状态就要禁用
                    //明确是启用状态时
                    Log.i("应用换标disable ", componentName.getShortClassName());
                    packageManager.setComponentEnabledSetting(componentName,
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, flag);
                }
            }
        }
        //启用目标图标
        int enableState = packageManager.getComponentEnabledSetting(enable);
        //默认启用的 default就是启用 。默认禁用的 default就是禁用
        if (enableState != PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {//不等于就要设置
            //明确非可用时
            packageManager.setComponentEnabledSetting(enable,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED, /*flag*/0);//最后启用的可以直接
            Log.i("应用换标enable ", enable.getShortClassName());
        }
    }

    /**
     * 目标图标是否是启用状态
     * 当前桌面图标状态是否和目标一致？
     * 注意： || COMPONENT_ENABLED_STATE_DEFAULT 一定要加，并且只加在清单配置 enable = true 的那个！！！
     */
    public static boolean isEnableIcon(Context context, ComponentName componentName) {
        PackageManager packageManager = context.getPackageManager();
        int com1State = packageManager.getComponentEnabledSetting(componentName);
        if (componentName == mDefaultEnable) {
            return com1State == PackageManager.COMPONENT_ENABLED_STATE_ENABLED || com1State == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;
        } else {
            return com1State == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
        }
    }

    /**其他 想自己禁用/启用的时候用这个，但不提倡
     * @param disableComponentName  需要禁用的 待禁用的目标 必须是当前服役的，已避免启用多个造成桌面多个图标，而没正确禁用正服役的
     * @param enableComponentName 需要启用的
     */
    @Deprecated
    public static void changeAppIcon(Context context, ComponentName disableComponentName, ComponentName enableComponentName) {
        PackageManager packageManager = context.getPackageManager();
        packageManager.setComponentEnabledSetting(disableComponentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        packageManager.setComponentEnabledSetting(enableComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,  0);//启用
    }
}
