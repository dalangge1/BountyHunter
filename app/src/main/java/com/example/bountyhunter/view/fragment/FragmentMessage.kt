package com.example.bountyhunter.view.fragment

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelFragment
import com.example.bountyhunter.extensions.dp2px
import com.example.bountyhunter.view.adapter.MessageAdapter
import com.example.bountyhunter.view.viewmodel.MessageViewModel
import com.example.bountyhunter.widgets.OptionalDialog
import com.example.bountyhunter.widgets.OptionalPopWindow
import kotlinx.android.synthetic.main.fragment_message.*


class FragmentMessage : BaseViewModelFragment<MessageViewModel>() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    val adapter = MessageAdapter({
        findNavController().navigate(R.id.action_global_fragmentMessageCommunicate, Bundle().apply {
            putString("friend_id", it.friendId)
        })
    }, { view, p ->
        val optionPopWindow = OptionalPopWindow.Builder().with(requireContext())
            .addOptionAndCallback("删除") {
                OptionalDialog.show(requireContext(), "是否删除与他的全部聊天记录？仅自己可见", {}) {
                    viewModel.deletePrivateMessageFriend(p.friendId)
                    refresh()
                }
            }
        optionPopWindow.show(view, OptionalPopWindow.AlignMode.CENTER, 0)
    })

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_message.layoutManager = LinearLayoutManager(context)
        rv_message.adapter = adapter
        rv_message.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.bottom = context?.dp2px(4f) ?: 0
                outRect.top = context?.dp2px(4f) ?: 0
                outRect.right = context?.dp2px(8f) ?: 0
                outRect.left = context?.dp2px(8f) ?: 0
            }
        })
        drag_head_view.onRefreshAction = {
            refresh()
        }
        refresh()

        viewModel.messageList.observeNotNull {
            adapter.submitList(it)
            drag_head_view.finishRefresh()
            viewModel.countPrivateMessage()
        }

        viewModel.deleteStatus.observeNotNull {
            if (it) {
                drag_head_view.refresh()
            }
        }

        viewModel.privateMessageCount.observeNotNull {
            tv_title.text = "我的消息(未读:${it})"
        }

    }

    private fun refresh() {
        viewModel.getPrivateMessageList()
    }


}