package com.duzzi.sdk.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;


/**
 * Created by xuebinliu on 2015/8/1.
 * <p>
 * 屏幕及设备参数获取工具类
 */
public final class DeviceUtil {

    private static final String TAG = DeviceUtil.class.getSimpleName();

    private static String PHONE_IMEI = null;
    private static String KEY_DEVICE_ID = "KEY_DEVICE_ID";

    public static boolean isNeedCreateShortcut() {
        return !getNotNeedCreateShortcutDeviceList().contains(Build.MODEL);
    }

    public static boolean isAfterAndroidO() {
        return Build.VERSION.SDK_INT >= 26;
    }

    public static void closeAndroidPDialog() {
        if (Build.VERSION.SDK_INT > 27) {
            try {
                Class aClass = Class.forName("android.content.pm.PackageParser$Package");
                Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
                declaredConstructor.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Class cls = Class.forName("android.app.ActivityThread");
                Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
                declaredMethod.setAccessible(true);
                Object activityThread = declaredMethod.invoke(null);
                Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
                mHiddenApiWarningShown.setAccessible(true);
                mHiddenApiWarningShown.setBoolean(activityThread, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取不需要创建快捷方式的手机
     *
     * @return
     */
    private static HashSet<String> getNotNeedCreateShortcutDeviceList() {
        final HashSet<String> set = new HashSet<String>();
        set.add("ZTE N939Sc");
        set.add("Coolpad 9976D");
        return set;
    }

    public static String getTopActivity(Activity context) {
        try {
            final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            final List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
            if (runningTaskInfos != null) {
                return (runningTaskInfos.get(0).topActivity).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     *
     * @return px
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return px
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取手机imei
     * <p>
     * 说明:imei可以确保设备标识唯一性,但是需要权限;若用户未授予权限,采用其他备用方案
     * imei>serial>androidId>伪id
     * <p>
     * todo 2019/7/15 android Q 非系统应用已无法获取到imei,需要采取新的方案
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
        if (!TextUtils.isEmpty(PHONE_IMEI)) {
            return PHONE_IMEI;
        }

        String string = SettingPref.getString(KEY_DEVICE_ID, "");
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string.trim())) {
            PHONE_IMEI = string;
            DLog.d(TAG, "SettingPref imei: " + PHONE_IMEI);
            return PHONE_IMEI;
        }

        try {
            TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            if (mTelephonyManager != null) {
                if (Build.VERSION.SDK_INT >= 26) {
                    try {
                        Method method = mTelephonyManager.getClass().getMethod("getImei", new Class[0]);
                        method.setAccessible(true);
                        PHONE_IMEI = (String) method.invoke(mTelephonyManager, new Object[0]);
                    } catch (Exception e) {
                        DLog.w(TAG, "e: " + e);
                    }

                    if (TextUtils.isEmpty(PHONE_IMEI)) {
                        PHONE_IMEI = mTelephonyManager.getDeviceId();
                    }
                } else {
                    PHONE_IMEI = mTelephonyManager.getDeviceId();
                }
                if (TextUtils.isEmpty(PHONE_IMEI)) {
                    PHONE_IMEI = getSimImei();
                }
            }
            if (PHONE_IMEI != null) {
                PHONE_IMEI = PHONE_IMEI.toLowerCase();
            }
            DLog.d(TAG, "imei: " + PHONE_IMEI);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //serial
        if (TextUtils.isEmpty(PHONE_IMEI)) {
            PHONE_IMEI = getSerial();
            DLog.d(TAG, "getSerial: " + PHONE_IMEI);
        }
        //android Id
        if (TextUtils.isEmpty(PHONE_IMEI)) {
            PHONE_IMEI = getAndroidId(context);
            DLog.d(TAG, "getAndroidId: " + PHONE_IMEI);
        }
        //伪设备id
        if (TextUtils.isEmpty(PHONE_IMEI)) {
            PHONE_IMEI = getPseudoDeviceId();
            DLog.d(TAG, "getPseudoDeviceId: " + PHONE_IMEI);
        }
        SettingPref.putString(KEY_DEVICE_ID, PHONE_IMEI);
        return PHONE_IMEI;
    }

    private static String getPseudoDeviceId() {
        String m_szDevIDShort = "pseudo"; //13 digits
        try {
            m_szDevIDShort = m_szDevIDShort + //we make this look like a valid IMEI
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.CPU_ABI.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return m_szDevIDShort;
    }

    private static String getAndroidId(Context context) {
        String string = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string.trim())) {
            DLog.d(TAG, "androidID: " + string);
            return string;
        } else {
            return null;
        }
    }

    @SuppressLint("MissingPermission")
    private static String getSerial() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PHONE_IMEI = Build.MANUFACTURER + Build.MODEL + Build.getSerial();
            } else {
                PHONE_IMEI = Build.MANUFACTURER + Build.MODEL + Build.SERIAL;
            }
            if (!TextUtils.isEmpty(PHONE_IMEI)) {
                PHONE_IMEI = PHONE_IMEI.trim().toLowerCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PHONE_IMEI;
    }

    private static String getSimImei() {
        Class<?> clazz;
        try {
            clazz = Class.forName("android.telephony.TelephonyManager");
            Method method = clazz.getDeclaredMethod("getSecondary", Object.class);
            method.setAccessible(true);
            TelephonyManager telManager = (TelephonyManager) method.invoke(clazz);
            if (telManager != null) {
                return telManager.getDeviceId();
            }
        } catch (Throwable e) {
            DLog.w("Util", "getSimImei -> " + e.getMessage());
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {

        }

        int versionCode = 0;
        if (info != null) {
            versionCode = info.versionCode;
        }

        return versionCode;
    }

    public static String getCurrentProcessName(final Context context) {
        final ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningAppProcessInfo> processes = am.getRunningAppProcesses();
        final int myPid = android.os.Process.myPid();

        String processName = null;
        if (processes != null) {
            for (ActivityManager.RunningAppProcessInfo process : processes) {
                if (myPid == process.pid) {
                    processName = process.processName;
                    break;
                }
            }
        }

        return processName;
    }

    public static boolean isMainProcess(final Context context) {
        final String processName = getCurrentProcessName(context);
        Log.d(TAG, "isMainProcess: " + processName);
        return TextUtils.equals(context.getPackageName(), processName) || TextUtils.isEmpty(processName);
    }

    public static boolean isDealProcess(final Context context) {
        final String processName = getCurrentProcessName(context);
        Log.d(TAG, "isMainProcess: " + processName);
        return TextUtils.equals("com.dengtacj.stock:deal", processName) || TextUtils.isEmpty(processName);
    }

    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String device_id = tm.getDeviceId();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }

            if (TextUtils.isEmpty(device_id)) {
                device_id = getAndroidId(context);
            }

            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putToSystemClipboard(final Context context, final CharSequence content) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, content));
    }

    /**
     * 获取剪切板内容
     *
     * @param context
     * @return
     */
    public static CharSequence getClipboardText(final Context context) {
        final ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            final ClipData clipData = clipboardManager.getPrimaryClip();
            if (clipData != null && clipData.getItemCount() > 0) {
                final ClipData.Item item = clipData.getItemAt(0);
                if (item != null) {
                    return item.getText();
                }
            }
        }
        return null;
    }

    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        DLog.d(TAG, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext() + " dest_context=" + destContext);
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void clearPreloadedDrawables() {
        // 只能在主线程调用
        if (Looper.myLooper() != Looper.getMainLooper()) {
            return;
        }

        try {
            final Field field = Resources.class.getDeclaredField("sPreloadedDrawables");

            if (field != null) {
                field.setAccessible(true);

                final Object object = field.get(Resources.class);

                if (object != null) {
                    if (object instanceof LongSparseArray) {
                        final LongSparseArray<?> sPreloadedDrawables = (LongSparseArray<?>) object;

                        if (sPreloadedDrawables != null) {
                            sPreloadedDrawables.clear();
                        }
                    } else if (object instanceof LongSparseArray[]) {
                        final LongSparseArray<?>[] sPreloadedDrawables =
                                (LongSparseArray<?>[]) object;

                        if (sPreloadedDrawables != null && sPreloadedDrawables.length > 0) {
                            final int count = sPreloadedDrawables.length;

                            for (int i = 0; i < count; ++i) {
                                sPreloadedDrawables[i].clear();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Nothing to do
        }
    }

    public static void showInputMethod(final View view, final int flags) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, flags);
    }

    public static void showInputMethod(final View view) {
        showInputMethod(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideInputMethod(final View view) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), /*InputMethodManager.HIDE_NOT_ALWAYS*/0);
    }

    @TargetApi(19)
    public static void setTranslucentStatus(boolean on, Window win) {
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static void setFullScreen(Activity activity) {
        if (activity != null) {
            // 隐藏标题栏
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 隐藏状态栏
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static void enableTranslucentStatus(final Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            enableTranslucentStatusAboveM(activity, color);
        }
    }

    public static void setStatusbarTextStyle(final Activity activity, boolean isLight) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (isLight) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        }
    }

    public static void enableTranslucentStatusBelowLollipop(final Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            DeviceUtil.setTranslucentStatus(true, activity.getWindow());
        }
//        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintColor(color);//通知栏所需颜色
//        tintManager.setNavigationBarTintEnabled(true);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void enableTranslucentStatusAboveM(final Activity activity, int color) {
        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(color);
        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void enableTranslucentStatusAboveLollipop(final Activity activity, int color) {
        Window window = activity.getWindow();
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(color);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            ViewCompat.setFitsSystemWindows(mChildView, true);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void enableDialogTranslucentStatus(final Window window, int statusBarColor) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //TODO Android 5.0以下的暂时没有适配
            return;
        }

        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
//        window.setStatusBarColor(getStatusbarColor(DengtaApplication.getApplication()));
        window.setStatusBarColor(statusBarColor);
    }

    public static void setTaskDescription(final Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= 21) {
            activity.setTaskDescription(new ActivityManager.TaskDescription(null, null, color));
        }
    }

    public static boolean isSystemApp(final PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * 检测某个service服务是否在运行
     *
     * @param context
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (serviceList == null || serviceList.isEmpty())
            return false;
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) && TextUtils.equals(
                    serviceList.get(i).service.getPackageName(), context.getPackageName())) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    // 华为设备检测
    public static boolean isHwDevice() {
        final String MANUFACTURER = Build.MANUFACTURER;
        if (MANUFACTURER != null) {
            if (MANUFACTURER.contains("HUAWEI") || MANUFACTURER.contains("huawei")) {
                return true;
            }
        }

        return false;
    }

    // 小米设备检测
    public static boolean isXmDevice() {
        final String MANUFACTURER = Build.MANUFACTURER;
        if (MANUFACTURER != null) {
            if (MANUFACTURER.contains("Xiaomi")) {
                return true;
            }

            if (MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取签名的SHA1
     *
     * @param context
     * @return
     */
    public static String getSHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printMaxMemoryInfo(Context context) {
        if (context == null) return;
        try {
            Runtime rt = Runtime.getRuntime();
            long maxMemory = rt.maxMemory();
            Log.e("MaxMemory:", Long.toString(maxMemory / (1024 * 1024)));
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            Log.e("MemoryClass:", Long.toString(activityManager.getMemoryClass()));
            Log.e("LargeMemoryClass:", Long.toString(activityManager.getLargeMemoryClass()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否是全面屏
     *
     * @param context
     * @return
     */
    public static boolean isFullScreenDevice(Context context) {
        if (context == null) return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            Display display = windowManager.getDefaultDisplay();
            Point point = new Point();
            display.getRealSize(point);
            float width, height;
            if (point.x < point.y) {
                width = point.x;
                height = point.y;
            } else {
                width = point.y;
                height = point.x;
            }
            return height / width >= 1.9f;
        }
        return false;
    }


    /**
     * 移动数据网络是否开启
     *
     * @return
     */
    public static boolean isMobileDataEnabled(@NonNull Context context) {
        try {
            TelephonyManager tm =
                    (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) return false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return tm.isDataEnabled();
            }
            @SuppressLint("PrivateApi")
            Method getMobileDataEnabledMethod =
                    tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            Log.e(TAG, "getMobileDataEnabled: ", e);
        }


        return false;
    }

    public static boolean isAppInstall(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            final PackageManager packageManager = context.getPackageManager();
            List<PackageInfo> info = packageManager.getInstalledPackages(0);
            if (info == null || info.isEmpty())
                return false;
            for (int i = 0; i < info.size(); i++) {
                if (packageName.equals(info.get(i).packageName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isScreenAutoRotate(Context context) {
        int gravity = 0;
        try {
            gravity = Settings.System.getInt(context.getContentResolver(),
                    Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return gravity == 1;
    }


    public static Integer[] getWidthAndHeight(Window window) {
        if (window == null) {
            return new Integer[]{0, 0};
        }
        Integer[] integer = new Integer[2];
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        } else {
            window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
        integer[0] = dm.widthPixels;
        integer[1] = dm.heightPixels;
        return integer;
    }


}
