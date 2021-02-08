package com.example.bountyhunter.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.maps.AMap
import com.amap.api.maps.model.MyLocationStyle
import com.example.bountyhunter.R
import kotlinx.android.synthetic.main.fragment_map.*


class FragmentMap : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val aMap = amap_view.map
        aMap.mapType = AMap.MAP_TYPE_NORMAL
        amap_view.onCreate(savedInstanceState)
        val myLocationStyle = MyLocationStyle() //初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER) //连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。

        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。

        myLocationStyle.showMyLocation(true)
        aMap.myLocationStyle = myLocationStyle //设置定位蓝点的Style

        aMap.isMyLocationEnabled = true

        Log.e("sandyzhang", "initmap")


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

}