package com.example.bountyhunter.view.fragment

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelFragment
import com.example.bountyhunter.extensions.dp2px
import com.example.bountyhunter.model.SettingUtil
import com.example.bountyhunter.view.adapter.GoodAdapter
import com.example.bountyhunter.view.viewmodel.MarketViewModel
import kotlinx.android.synthetic.main.fragment_market.*


class FragmentMarket : BaseViewModelFragment<MarketViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    val adapter = GoodAdapter {
        findNavController().navigate(R.id.action_global_fragmentGood, Bundle().apply {
            putSerializable("good", it)
        })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        SettingUtil.settingData?.let {
            viewModel.schoolId = it.schoolId
            tv_title.text = "${it.schoolName}闲置区"
        }

        if (viewModel.schoolId == -1) {
            findNavController().navigate(R.id.action_global_fragmentSchool)
        }

        tv_title.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentSchool)
        }

        btn_release_good.setOnClickListener {
            if (viewModel.schoolId != SettingUtil.settingData?.userSchoolId) {
                Toast.makeText(context, "检测到你在另一个社区，你只能发布商品到你自己的社区噢~", Toast.LENGTH_LONG).show()
            } else {
                findNavController().navigate(R.id.action_global_fragmentGoodEdit)
            }
        }

        drag_head_view.onRefreshAction = {
            refresh()
        }
        refresh()

        rv_good.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        rv_good.adapter = adapter
        rv_good.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = context?.dp2px(8f) ?: 0
                outRect.bottom = context?.dp2px(4f) ?: 0
                outRect.top = context?.dp2px(4f) ?: 0

            }
        })

        viewModel.goodList.observeNotNull {
            adapter.submitList(it)
            drag_head_view.finishRefresh()
        }
    }

    private fun refresh() {
        viewModel.getAllGood()
    }

}