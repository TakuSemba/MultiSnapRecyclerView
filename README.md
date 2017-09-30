# MultiSnapRecyclerView

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/logo.png">

## Gradle

```groovy

dependencies {
    compile 'com.github.takusemba:multisnaprecyclerview:1.1.0'
}

```
<br/>

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/gravity.gif" align="right" width="300">

## Features
![Platform](http://img.shields.io/badge/platform-android-green.svg?style=flat)
![Download](https://api.bintray.com/packages/takusemba/maven/multisnaprecyclerview/images/download.svg)
![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)
![API](https://img.shields.io/badge/API-13%2B-brightgreen.svg?style=flat)

This is an Android Library for multiple snapping of RecyclerView.
MultiSnapRecyclerView easily provides a snapping feature to your recycler view. 
What this does are clean, neat, and powerful.
<br/>
- [x] gravitated snapping to `start` `end` and `center`.
- [x] `snap count` to specify the number of items to scroll over.
- [x] supports horizontal and vertical scrolling.
- [x] listener to be called when snapped.
- [x] sample code.
- [ ] support reverse layout.
- [ ] test code.

<strong>Make sure to use LinearManger.</strong> Other managers are not supported.

<br/>
<br/>
<br/>
<br/>

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/snap_count.gif" align="left" width="300">

## Usage
Use MultiSnapRecyclerView in your xml file.

```xml
<com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:msrv_gravity="start" or center, end
        app:msrv_snap_count="2" /> items to scroll over

```

and simply set a layout manager.

```java
LinearLayoutManager layoutManager = new LinearLayoutManager(this);
multiSnapRecyclerView.setLayoutManager(layoutManager);
multiSnapRecyclerView.setAdapter(adapter);
multiSnapRecyclerView.setOnSnapListener(new OnSnapListener() {
    @Override
    public void snapped(int position) {
        // do something with the position of the snapped view
    }
});
```

<br/>
<br/>

## Sample
Clone this repo and check out the [app](https://github.com/TakuSemba/MultiSnapRecyclerView/tree/master/app) module.

## Change Log

### Version: 1.1.0

  * reuturn snapped position in OnSnapListener
  
### Version: 1.0.0

  * Initial Build


## Author

* **Taku Semba**
    * **Github** - (https://github.com/takusemba)
    * **Twitter** - (https://twitter.com/takusemba)
    * **Facebook** - (https://www.facebook.com/takusemba)

## Licence
```
Copyright 2017 Taku Semba.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
