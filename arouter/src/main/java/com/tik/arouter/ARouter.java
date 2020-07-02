package com.tik.arouter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 *
 **/
public class ARouter {
    private static ARouter aRouter = new ARouter();
    private Map<String, Class<? extends Activity>> map;
    private Context context;

    private ARouter() {
        map = new HashMap<>();
    }

    public static ARouter getInstance() {
        return aRouter;
    }

    public void init(Context context) {
        this.context = context;
        List<String> classNames = getClassName("com.tik.arouter.util");
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                //判断这个类是否是IRouter的子类
                if (IRouter.class.isAssignableFrom(aClass)) {
                    IRouter iRouter = (IRouter) aClass.newInstance();
                    iRouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addActivity(String key, Class<? extends Activity> clazz) {
        if (key != null && clazz != null && !map.containsKey(key)) {
            map.put(key, clazz);
        }
    }

    public void jumpActivity(String key, Bundle bundle) {
        if (key == null) return;
        Class<? extends Activity> activityClass = map.get(key);
        if (activityClass != null) {
            Intent intent = new Intent();
            intent.setClass(context, activityClass);
            context.startActivity(intent);
        }
    }

    /**
     * 通过包名获取这个包下的所有类名
     *
     * @param packageName arouter中间类的包名
     * @return
     */
    private List<String> getClassName(String packageName) {
        List<String> classList = new ArrayList<>();
        String path = null;
        try {
            //通过包管理器获取应用信息类，获取apk完整路径
            String pkgName = context.getApplicationInfo().packageName;
            path = context.getPackageManager().getApplicationInfo(pkgName, 0).sourceDir;
            //根据apk的完整路径获取到编译后的dex文件目录
            DexFile dexFile = new DexFile(path);
            //获取编译后的dex文件中的所有class
            Enumeration<String> entries = dexFile.entries();
            while (entries.hasMoreElements()) {
                //通过遍历所有的class的包名
                String name = entries.nextElement();
                //判断是否符合包名
                if (name.contains(packageName)) {
                    classList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classList;
    }
}
