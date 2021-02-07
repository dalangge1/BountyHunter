package com.example.bountyhunter.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps2d.AMap
import com.example.bountyhunter.R
import kotlinx.android.synthetic.main.fragment_map.*


class FragmentMap : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val aMap = amap_view.map
        aMap.isTrafficEnabled = true;// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.mapType = AMap.MAP_TYPE_SATELLITE;// 卫星地图模式
//        AMapLocationClient.setApiKey("5ef4d35b16490a0722a78d6d4ba536fa")
        amap_view.onCreate(savedInstanceState);
        Log.e("sandyzhang","initmap")
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

}