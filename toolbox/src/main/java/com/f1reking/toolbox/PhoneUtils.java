package com.f1reking.toolbox;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HuangYH on 2016/1/27.
 */
public class PhoneUtils {

    public PhoneUtils() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 直接拨打电话
     *
     * @param phoneNumber 电话号码
     */
    public static void call(Context context, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel://" + phoneNumber)));
    }

    /**
     * 跳转到拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void callDetail(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + phoneNumber)));
    }

    /**
     * 发送短信
     *
     * @param phoneNumber 电话号码
     * @param content 短信内容
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 唤醒屏幕并解锁
     * uses-permission android:name="android.permission.WAKE_LOCK"
     * uses-permission android:name="android.permission.DISABLE_KEYGUARD"
     */
    public static void wakeUpAndUnlock(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = keyguardManager.newKeyguardLock("unLock");
        //解锁
        kl.disableKeyguard();
        //获取电源管理器对象
        PowerManager mowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        //获取PowerManager.WakeLock对象
        PowerManager.WakeLock wl =
            mowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "lock");
        //点亮屏幕
        wl.acquire();
        //释放
        wl.release();
    }

    /**
     * 判断当前手机是否处于锁屏（睡眠）状态
     */
    public static boolean isSleeping(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSleeping = keyguardManager.inKeyguardRestrictedInputMode();
        return isSleeping;
    }

    /**
     * 获取当前设备的MAC地址
     */
    public static String getMacAddress(Context context) {
        String macAddress;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        macAddress = wifiInfo.getMacAddress();
        if (null == macAddress) {
            return "";
        }
        macAddress = macAddress.replace(":", "");
        return macAddress;
    }

    private static Pattern mobilePattern;

    /**
     * 移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
     * 联通：130、131、132、145、155、156、175、176、185、186
     * 电信：133、153、173、177、180、181、189
     * 全球星：1349
     * 虚拟运营商：170
     */
    public static boolean isMobileNum(String mobileNo) {
        if (mobilePattern == null) {
            mobilePattern =
                Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-8])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$");
        }
        return mobilePattern.matcher(mobileNo).matches();
    }

    /**
     * 对电话、手机号进行校验
     */
    public static boolean isPhoneNum(String phoneNum) {
        boolean isValid = false;
        String expression = "(^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$)";
        CharSequence inputStr = phoneNum;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }
}
