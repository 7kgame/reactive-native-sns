import { NativeModules, DeviceEventEmitter } from 'react-native';
const { RNSns } = NativeModules;

DeviceEventEmitter.addListener(RNSns.EVENT_NAME_QQ, resp => {
    let callback = targetCallback;
    targetCallback = undefined;
    callback && callback(resp);
});

DeviceEventEmitter.addListener(RNSns.EVENT_NAME_WX, resp => {
    let callback = targetCallback;
    targetCallback = undefined;
    callback && callback(resp);
});

let targetCallback = undefined;
function handleResponseData() {
    return new Promise((resolve, reject) => {

        targetCallback = result => {
            if (result.code === RNSns.RESPONSE_CODE_SUCCESS) {
                resolve(result);
            } else {
                reject(result);
            }
        }
    });
}

export function qqLogin() {
    return RNSns._qqLogin()
        .then(() => handleResponseData());
}

export function qqLogout() {
    RNSns._qqLogout();
}

export function wxLogin() {
    return RNSns._wxLogin()
        .then(() => handleResponseData());
}

