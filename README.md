# MultiSnapRecyclerView

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/logo.png">

## Gradle

```groovy

dependencies {
    implementation 'com.github.takusemba:multisnaprecyclerview:x.x.x'
}

```
<br/>

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/gravity.gif" align="right" width="30%">

## Features
![Build Status](https://app.bitrise.io/app/a27c356998242bdf/status.svg?token=13Mi9qAas0Zm81ono5VWRw&branch=master)
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

<strong>Make sure to use LinearManger.</strong> Other managers are not supported.

<br/>
<br/>

<img src="https://github.com/TakuSemba/MultiSnapRecyclerView/blob/master/arts/snap_count.gif" align="left" width="30%">

## Usage
There are two ways to use MultiSnapRecyclerView.

You can either use MultiSnapRecyclerView in your layout.

```xml
<com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:msrv_gravity="start" or center, end
        app:msrv_interval="2" items to scroll over
        app:msrv_ms_per_inch="100" /> // speed of scrolling through.
```

Or you can simply attach MultiSnapHelper to your normal RecyclerView.

```kt
val multiSnapHelper = MultiSnapHelper(gravity, interval, speedMsPerInch)
multiSnapHelper.attachToRecyclerView(recyclerView)
```

<br/>

## Sample
Clone this repo and check out the [app](https://github.com/TakuSemba/MultiSnapRecyclerView/tree/master/app) module.

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
