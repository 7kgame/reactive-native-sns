
# react-native-sns

## Getting started

`$ npm install react-native-sns --save`

### Mostly automatic installation

`$ react-native link react-native-sns`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-sns` and add `RNSns.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNSns.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.qk.sns.RNSnsPackage;` to the imports at the top of the file
  - Add `new RNSnsPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-sns'
  	project(':react-native-sns').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-sns/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-sns')
  	```


## Usage
```javascript
import RNSns from 'react-native-sns';

// TODO: What to do with the module?
RNSns;
```
  