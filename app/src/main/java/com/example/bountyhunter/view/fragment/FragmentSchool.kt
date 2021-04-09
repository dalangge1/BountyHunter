package com.example.bountyhunter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseFragment
import com.example.bountyhunter.model.SettingUtil
import com.example.bountyhunter.network.*
import com.example.bountyhunter.view.adapter.SchoolAdapter
import kotlinx.android.synthetic.main.fragment_school.*


class FragmentSchool : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_school, container, false)
    }

    var isChangingSchool: Boolean = false

    val adapter = SchoolAdapter {
        SettingUtil.settingData?.apply {
            if (schoolId == -1 || isChangingSchool) {
                userSchoolId = it.schoolId
                userSchoolName = it.schoolName
            }
            schoolId = it.schoolId
            schoolName = it.schoolName
        }
        SettingUtil.save()
        findNavController().navigate(R.id.action_fragmentSchool_pop)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isChangingSchool = arguments?.getBoolean("change", false) == true
        btn_create_school.setOnClickListener {
            Toast.makeText(requireContext(), "暂不支持手动入驻，请联系管理员入驻~", Toast.LENGTH_SHORT).show()
        }

        rv_school.layoutManager = LinearLayoutManager(requireContext())
        rv_school.adapter = adapter

        drag_head_view.onRefreshAction = {
            refresh()
        }
        drag_head_view.refresh()

    }

    private fun refresh() {
        val o = ApiGenerator.getApiService(Api::class.java)
            .getAllSchool()
            .setSchedulers()
            .checkApiError()
            .doOnError {
                context?.let {
                    Toast.makeText(it, "网络请求失败！", Toast.LENGTH_SHORT).show()
                }
            }
            .doFinally {
                drag_head_view.finishRefresh()
            }
            .safeSubscribeBy {
                adapter.submitList(it)
            }
    }

}