package com.example.bountyhunter.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelFragment
import com.example.bountyhunter.bean.beannew.ReplyInfo
import com.example.bountyhunter.model.SettingUtil
import com.example.bountyhunter.view.adapter.TalkRecyclerViewAdapter
import com.example.bountyhunter.view.viewmodel.CommunityViewModel
import kotlinx.android.synthetic.main.fragment_community.*


class FragmentCommunity : BaseViewModelFragment<CommunityViewModel>() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_community, container, false)
    }

    lateinit var adapter: TalkRecyclerViewAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        SettingUtil.settingData?.let {
            viewModel.schoolId = it.schoolId
            tvTitle.text = "${it.schoolName}社区"
        }

        if (viewModel.schoolId == -1) {
            findNavController().navigate(R.id.action_global_fragmentSchool)
        }

        tvTitle.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentSchool)
        }


        recyclerViewTalkList.layoutManager = LinearLayoutManager(context)
        if (recyclerViewTalkList.itemDecorationCount == 0) {
            recyclerViewTalkList.addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }


        // 设置点击事件
        adapter = TalkRecyclerViewAdapter(requireContext()) { dynamic, v ->
            FragmentTalkDetail.viewModel = viewModel
            Navigation.findNavController(v)
                .navigate(R.id.action_global_fragmentTalkDetail, Bundle().apply {
                    putInt("dynamic_id", dynamic.dynamicId)
                })
        }


        recyclerViewTalkList.adapter = adapter


        val listener = {
            viewModel.refreshDynamic()
        }

        drag_head_view.onRefreshAction = listener
        listener.invoke()

        sendDynamic.setOnClickListener {
            viewModel.replyInfo.value = ReplyInfo("", "", -1, -1)
            FragmentTalkEdit.viewModel = viewModel
            val navController = findNavController()
            navController.navigate(R.id.action_global_fragmentTalkEdit)
        }




        viewModel.dynamicList.observeNotNull {
            adapter.setList(it)
            drag_head_view?.finishRefresh()
        }


    }


}