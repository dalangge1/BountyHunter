package com.example.bountyhunter.view.fragment

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.LocationSource
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.example.bountyhunter.R
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_map.*


class FragmentMap : Fragment(), LocationSource, AMapLocationListener {

    private var aMap: AMap? = null
    private var mLocationClient: AMapLocationClient? = null
    private var mListener: OnLocationChangedListener? = null
    private var location: LatLng? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        aMap = amap_view.map

        amap_view.onCreate(savedInstanceState)
        RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe {
            initAMap()
        }.dispose()


    }

    override fun onResume() {
        super.onResume()
        amap_view.onResume()
        aMap = amap_view.map

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    private fun initAMap() {
        aMap?.mapType = AMap.MAP_TYPE_NORMAL
        val myLocationStyle = MyLocationStyle() //初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER) //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(true)
        aMap?.myLocationStyle = myLocationStyle //设置定位蓝点的Style
        aMap?.isTrafficEnabled = true
        //实例化UiSettings类对象

        //实例化UiSettings类对象
        val myUiSettings: UiSettings? = aMap?.uiSettings

        // 设置定位监听

        // 设置定位监听
        aMap?.setLocationSource(this)
        // 设置默认定位按钮是否显示
        // 设置默认定位按钮是否显示
        myUiSettings?.isMyLocationButtonEnabled = true
        // 可触发定位并显示当前位置
        // 可触发定位并显示当前位置
        aMap?.isMyLocationEnabled = true
        //显示罗盘
        //显示罗盘
        myUiSettings?.isCompassEnabled = true
        //控制比例尺控件是否显示
        //控制比例尺控件是否显示
        myUiSettings?.isScaleControlsEnabled = true
        //不显示比例控制按钮
        //不显示比例控制按钮
        myUiSettings?.isZoomControlsEnabled = false


        aMap?.addOnPOIClickListener {
            Toast.makeText(context, "$it, ${it.name}", Toast.LENGTH_SHORT).show()
        }
        aMap?.addOnMapClickListener {
            Toast.makeText(context, "$it, ${it.latitude}, ${it.longitude}", Toast.LENGTH_SHORT).show()
        }


    }

    override fun activate(p0: OnLocationChangedListener?) {
        mListener = p0
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = AMapLocationClient(context)
            //初始化定位参数
            val mLocationOption = AMapLocationClientOption()
            //设置定位监听
            mLocationClient?.setLocationListener(this)
            //设置为高精度定位模式
            mLocationOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            //目前使用多次定位，设置定位间隔为10s
            val myLocationStyle = MyLocationStyle()
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE)
            // mLocationOption.setOnceLocation(true);
            //设置定位参数
            myLocationStyle.interval(10000)
            aMap?.myLocationStyle = myLocationStyle
            mLocationClient?.setLocationOption(mLocationOption)
        }
    }

    override fun deactivate() {
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制

    }

    override fun onPause() {
        super.onPause()
        amap_view.onPause()
        deactivate()
        mFirstLocate = true

    }

    override fun onLocationChanged(aMapLocation: AMapLocation?) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                location = LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())
                if (mFirstLocate) {
                    mFirstLocate = false
                    addLocCircle(location, aMapLocation.getAccuracy().toDouble()) //添加定位精度圆
                    addLocationMarker(location) //添加定位图标
                    mSensorHelper.setCurrentMarker(locMarker) //定位图标旋转
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, MAP_ZOOM))
                } else {
                    mCircle.setCenter(location)
                    mCircle.setRadius(aMapLocation.getAccuracy().toDouble())
                    locMarker.setPosition(location)
                }
            } else {
                Log.e(TAG, "onLocationChanged: 定位遭遇服务器报错，查看详情：")
                ErrorHandler.handleLocateError(aMapLocation.getErrorCode(), aMapLocation.getErrorInfo())
            }
        }
    }
}