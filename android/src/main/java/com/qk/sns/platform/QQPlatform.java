package com.qk.sns.platform;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.qk.sns.common.ConstantsQK;
import com.qk.sns.common.Utils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

import java.util.Date;

public class QQPlatform {

    public static QQPlatform qqPlatform = null;
    private ReactApplicationContext ctx;
    public Tencent mTencent;
    private String appId;

    private QQPlatform(ReactApplicationContext ctx, String appId) {
        this.ctx = ctx;
        this.appId = appId;
        mTencent = Tencent.createInstance(appId, ctx);
    }

    public static void initInstance(ReactApplicationContext ctx, String appId) {
        if (qqPlatform == null) {
            qqPlatform = new QQPlatform(ctx, appId);
        }
    }

    public static QQPlatform getInstance() {
        if (qqPlatform == null) {
            throw new NullPointerException("QQPlatform must be init, " +
                    "please invoke initInstance()");
        } else {
            return qqPlatform;
        }
    }

    public void login() {
        mTencent.login(ctx.getCurrentActivity(), "get_simple_userinfo", qqListener);
    }

    public void logout() {
        mTencent.logout(ctx);
    }

    public IUiListener qqListener = new IUiListener() {
        @Override
        public void onComplete(Object o) {
            Log.i(ConstantsQK.TAG + "_QQ", o.toString());
            WritableMap resultMap = Arguments.createMap();
            resultMap.putString("type", "qq");
            try {
                JSONObject jsonObject = (JSONObject) o;

                resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_SUCCESS);
                resultMap.putString("openid", jsonObject.getString(Constants.PARAM_OPEN_ID));
                resultMap.putString("access_token", jsonObject.getString(Constants.PARAM_ACCESS_TOKEN));
                resultMap.putString("oauth_consumer_key", appId);
                resultMap.putDouble("expires_in", (new Date().getTime() + jsonObject.getLong(Constants.PARAM_EXPIRES_IN)));
            } catch (Exception e) {
                resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_ERROR);
                resultMap.putString("msg", e.getMessage());
            }

            Utils.sendData(ctx, ConstantsQK.EVENT_NAME_QQ, resultMap);
        }

        @Override
        public void onError(UiError uiError) {
            WritableMap resultMap = Arguments.createMap();
            resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_ERROR);
            resultMap.putString("msg", "operate qq " + uiError.errorDetail);

            Utils.sendData(ctx, ConstantsQK.EVENT_NAME_QQ, resultMap);
        }

        @Override
        public void onCancel() {
            WritableMap resultMap = Arguments.createMap();
            resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_CANCEL);
            resultMap.putString("msg", "operate qq cancel");

            Utils.sendData(ctx, ConstantsQK.EVENT_NAME_QQ, resultMap);
        }
    };

    public void destory() {
        if (qqPlatform != null) {
            qqPlatform = null;
        }
    }
}
