
package com.qk.sns;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.qk.sns.common.ConstantsQK;
import com.qk.sns.platform.QQPlatform;
import com.qk.sns.platform.WXPlatform;
import com.tencent.tauth.Tencent;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class RNSnsModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    private String qqAppId;
    private String wxAppId;

    public RNSnsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        ApplicationInfo appInfo;
        try {
            appInfo = reactContext.getPackageManager().getApplicationInfo(reactContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            throw new Error(e);
        }
        if (!appInfo.metaData.containsKey("QQ_APPID")) {
            throw new Error("meta-data QQ_APPID not found in AndroidManifest.xml");
        }
        if (!appInfo.metaData.containsKey("WX_APPID")) {
            throw new Error("meta-data WX_APPID not found in AndroidManifest.xml");
        }
        qqAppId = appInfo.metaData.get("QQ_APPID").toString();
        wxAppId = appInfo.metaData.get("WX_APPID").toString();
    }

    @Override
    public void initialize() {
        QQPlatform.initInstance(reactContext, qqAppId);
        WXPlatform.initInstance(reactContext, wxAppId);
        getReactApplicationContext().addActivityEventListener(activityEventListener);
    }

    @Override
    public void onCatalystInstanceDestroy() {
        super.onCatalystInstanceDestroy();
        QQPlatform.getInstance().destory();
        WXPlatform.getInstance().destory();
    }

    @Override
    public String getName() {
        return "RNSns";
    }

    @Nullable
    @Override
    public Map<String, Object> getConstants() {
        Map constMap = new HashMap();
        constMap.put("TAG", ConstantsQK.TAG);
        constMap.put("EVENT_NAME_QQ", ConstantsQK.EVENT_NAME_QQ);
        constMap.put("EVENT_NAME_WX", ConstantsQK.EVENT_NAME_WX);
        constMap.put("RESPONSE_CODE_SUCCESS", ConstantsQK.RESPONSE_CODE_SUCCESS);
        constMap.put("RESPONSE_CODE_ERROR", ConstantsQK.RESPONSE_CODE_ERROR);
        constMap.put("RESPONSE_CODE_CANCEL", ConstantsQK.RESPONSE_CODE_CANCEL);
        return constMap;
    }

    @ReactMethod
    public void _qqLogin(Promise promise) {
        if (!QQPlatform.getInstance().mTencent.isSessionValid()) {
            QQPlatform.getInstance().login();
            promise.resolve(null);
        } else {
            promise.reject("qq start fail");
        }
    }

    @ReactMethod
    public void _qqLogout() {
        QQPlatform.getInstance().logout();
    }

    public static void handleIntent(Intent intent) {
        WXPlatform.getInstance().wxApi.handleIntent(intent,
                WXPlatform.getInstance().wxListener);
    }

    @ReactMethod
    public void _wxLogin(Promise promise) {
        WXPlatform.getInstance().login();
        promise.resolve(null);
    }

    private ActivityEventListener activityEventListener = new ActivityEventListener() {
        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            Tencent.onActivityResultData(requestCode, resultCode, data, QQPlatform.getInstance().qqListener);
        }

        @Override
        public void onNewIntent(Intent intent) {
        }
    };

}