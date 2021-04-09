package com.example.bountyhunter.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelFragment
import com.example.bountyhunter.model.Config
import com.example.bountyhunter.view.viewmodel.IndividualViewModel
import kotlinx.android.synthetic.main.fragment_individual.*


class FragmentIndividual : BaseViewModelFragment<IndividualViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_individual, container, false)
    }

    var userId = ""
    var userName = ""

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val isMine = arguments?.getBoolean("is_mine")
        userId = arguments?.getString("user_id") ?: ""

        isMine?.let {
            if (!isMine) {
                layoutCondition.visibility = View.GONE
                imageViewSetting.visibility = View.GONE
            } else {
                textViewTag.text = "我的主题"
            }
        }

        if (Config.userId != userId) {
            btn_message.visibility = View.VISIBLE
        }

        btn_message.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_fragmentMessageCommunicate,
                Bundle().apply {
                    putString("friend_id", userId)
                    putString("friend_name", userName)
                })
        }

        val listener = SwipeRefreshLayout.OnRefreshListener {


            if (isMine == true) {
                viewModel.getUser(Config.userId)

            } else {
                viewModel.getUser(userId)
            }

        }

        viewModel.user.observeNotNull { user ->
            textViewUsrName.text = user.nickname
            textViewUserId.text = user.userId
            userName = user.nickname
            if (user.text.isBlank()) {
                tv_text.text = "TA还没有写个性签名~"
            } else {
                tv_text.text = user.text
            }

            when (user.sex) {
                "男" -> imageViewSex.setImageResource(R.drawable.ic_man)
                "女" -> imageViewSex.setImageResource(R.drawable.ic_woman)
            }
            context?.let { Glide.with(it).load(user.avatarUrl).into(imageViewUsrPic) }
            swipeRefreshLayout.isRefreshing = false

        }

        swipeRefreshLayout.setOnRefreshListener(listener)
        listener.onRefresh()
        swipeRefreshLayout.isRefreshing = true

        imageViewSetting.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentSetting)
        }

    }


}