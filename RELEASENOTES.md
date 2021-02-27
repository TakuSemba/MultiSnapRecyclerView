Release notes
==========

Version 2.0.4 **(2021-2-27)**
----------------------------
 - Update Kotlin v1.4.31.

Version 2.0.3 **(2020-9-3)**
----------------------------
 - Update dependencies.

Version 2.0.2 **(2020-2-12)**
----------------------------
 - Update Kotlin v1.3.50.
 - Fix not snapping to the both edge.

Version 2.0.1 **(2019-11-9)**
----------------------------
 - Simplify the logic in MultiSnapHelper

Version 2.0.0 **(2019-11-9)**
----------------------------
 - Migrate to AndroidX. After this release, your app have to be migrated to AndroidX.
 - Update sdk version to 28
 - Increase min sdk version to 14
 - Require Kotlin v1.3.11.
 - MultiSnapHelper can also be used with the normal RecyclerView.
 
 ```kt
 val multiSnapHelper = MultiSnapHelper(gravity, interval, speedMsPerInch)
 multiSnapHelper.attachToRecyclerView(recyclerView)
 ```
 
 - Rename `msrv_milliseconds_per_inch` attribute to `msrv_speed_ms_per_inch`.
 - Rename `msrv_snap_count` attribute to `msrv_interval`.
 - Simplify SnapHelper logic to make it more readable.

Version 1.2.0 **(2018-2-10)**
----------------------------
 - Control speed of scrolling through.

Version 1.1.0 **(2017-9-30)**
----------------------------
 - Add listener to return snapped position in OnSnapListener.

Version 1.0.0 **(2017-8-4)**
----------------------------
 - Initial Release.