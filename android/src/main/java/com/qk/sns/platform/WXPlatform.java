package com.qk.sns.platform;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.qk.sns.common.ConstantsQK;
import com.qk.sns.common.Utils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPlatform {

    public static WXPlatform wxPlatform = null;
    public IWXAPI wxApi;
    private ReactApplicationContext reactContext;

    private WXPlatform(ReactApplicationContext ctx, String appId) {
        reactContext = ctx;
        wxApi = WXAPIFactory.createWXAPI(ctx, appId, false);
    }

    public static WXPlatform getInstance() {
        if (wxPlatform == null) {
            throw new NullPointerException("WXPlatform must be init, " +
                    "please invoke initInstance()");
        } else {
            return wxPlatform;
        }
    }

    public static WXPlatform initInstance(ReactApplicationContext ctx, String appId) {
        if (wxPlatform == null) {
            wxPlatform = new WXPlatform(ctx, appId);
        }

        return wxPlatform;
    }

    public void login() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "none";
        wxApi.sendReq(req);
    }

    /**
     * 微信回调方法
     */
    public IWXAPIEventHandler wxListener = new IWXAPIEventHandler() {
        @Override
        public void onReq(BaseReq baseReq) {
            Log.i(ConstantsQK.TAG + "_WX", "onReq");
        }

        @Override
        public void onResp(BaseResp baseResp) {
            Log.i(ConstantsQK.TAG + "_WX", "onResp");
            if (baseResp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
                SendAuth.Resp resp = (SendAuth.Resp) baseResp;
                String code = resp.code;
                Log.i(ConstantsQK.TAG + "_WX", "onResp code=" + code);

                WritableMap resultMap = Arguments.createMap();
                if (code != null && code.trim().length() > 0) {
                    resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_SUCCESS);
                    resultMap.putString("wx_code", code);
                } else {
                    resultMap.putInt("code", ConstantsQK.RESPONSE_CODE_ERROR);
                    resultMap.putString("msg", "get weixin code failed");
                }

                Utils.sendData(reactContext, ConstantsQK.EVENT_NAME_WX, resultMap);
            }
        }
    };

    public void destory() {
        if (wxPlatform != null) {
            wxPlatform = null;
        }
    }
}
