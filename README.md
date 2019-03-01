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
        code: 200
    * }
    */
    console.log('login qq result ', result.code);
  })
  .catch(err => {
    console.log('login qq err', err);
  })
  .catch(err => {
    console.log('login qq fail', err);
  });
```
## WeiXin
```javascript
LoginApi.wxLogin()
  .then(result => {
    console.log('login weixin result=', result);
  })
  .catch(err => {
    console.log('login weixin err', err);
  });
```
