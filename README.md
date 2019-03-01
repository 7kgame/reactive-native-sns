# react-native-sns

## Getting started

`$ npm install react-native-sns --save`

### Mostly automatic installation

`$ react-native link react-native-sns`

## Usage
```javascript
import * as LoginApi from 'react-native-sns';
```
## QQ
```javascript
LoginApi.qqLogin()
  .then(result => {
    /**
    * result = {
    *   code: 200,
    *   openid: xxx,
    *   access_token: xxx,
    *   expires_in: xxx
    * }
    */
    console.log('login qq result ', result.code);
  })
  .catch(err => {
    /**
    * err = {
    *   code: 101(qq登录异常) or 102(取消qq登录)
    *   msg: xxx
    * }
    */
    console.log('login qq err', err);
  })
  .catch(err => {
    /**
    * err: qq start fail
    */
    console.log('login qq fail', err);
  });
```
## WeiXin
```javascript
LoginApi.wxLogin()
  .then(result => {
    /**
    * result= {
    *   code: 200,
    *   wx_code: xxx
    * }
    */
    console.log('login weixin result=', result);
  })
  .catch(err => {
    /**
    * err = {
    *   code: 101,
    *   msg: "get weixin code failed"
    * }
    */
    console.log('login weixin err', err);
  });
```
