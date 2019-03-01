package com.qk.sns.common;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class Utils {
    /**
     *  向js发送事件
     * @param ctx
     * @param eventName
     * @param resultMap
     */
    public static void sendData(ReactApplicationContext ctx,
                                String eventName, ReadableMap resultMap) {
        ctx.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, resultMap);
    }
}
