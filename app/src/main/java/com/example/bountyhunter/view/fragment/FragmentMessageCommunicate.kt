package com.example.bountyhunter.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseFragment
import com.example.bountyhunter.network.*
import com.example.bountyhunter.view.adapter.MessageItemAdapter
import com.example.bountyhunter.widgets.ClipboardController
import com.example.bountyhunter.widgets.OptionalDialog
import com.example.bountyhunter.widgets.OptionalPopWindow
import com.example.moneycounter4.widgets.ProgressDialogW
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_message_communicate.*
import java.util.concurrent.TimeUnit

class FragmentMessageCommunicate : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_message_communicate, container, false)
    }


    var friendId = ""
    var friendName = MutableLiveData("")


    val adapter = MessageItemAdapter({ view, privateMessageItem ->
        val optionPopWindow = OptionalPopWindow.Builder().with(requireContext())
            .addOptionAndCallback("复制") {
                ClipboardController.copyText(requireContext(), privateMessageItem.messageContent)
            }
            .addOptionAndCallback("删除") {
                OptionalDialog.show(requireContext(), "是否删除？仅自己可见", {}) {
                    deleteMessage(privateMessageItem.id)
                    refresh()
                }
            }
        optionPopWindow.show(view, OptionalPopWindow.AlignMode.CENTER, 0)
    }, {
        findNavController().navigate(R.id.action_global_fragmentIndividual, Bundle().apply {
            putBoolean("is_mine", false)
            putString("user_id", it.userId)
        })
    })

    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        friendId = arguments?.getString("friend_id", "") ?: ""
        friendName.value = arguments?.getString("friend_name", "") ?: ""

        iv_more.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentIndividual, Bundle().apply {
                putBoolean("is_mine", false)
                putString("user_id", friendId)
            })
        }


        rv_message.layoutManager = LinearLayoutManager(requireContext())
        rv_message.adapter = adapter


        drag_head_view.onRefreshAction = {
            refresh()
        }
        refresh()


        // 发帖

        btn_send.setOnClickListener {
            if (et_reply.text.toString().isNotBlank()) {
                sendMessage(et_reply.text.toString())
            }

        }

        val c = Observable.interval(5, TimeUnit.SECONDS)
            .setSchedulers()
            .safeSubscribeBy {

                if (!rv_message.canScrollVertically(1)) {
                    refresh()
                }
            }

        friendName.observe(viewLifecycleOwner) {
            tv_title.text = "与${it}的对话"
        }

    }

    private fun sendMessage(content: String) {
        ProgressDialogW.show(requireContext(), "请稍后", "正在回复", false)
        val o = ApiGenerator.getApiService(Api::class.java).submitPrivateMessage(friendId, content)
            .setSchedulers()
            .checkError()
            .doFinally {
                ProgressDialogW.hide()
            }
            .safeSubscribeBy {

                activity?.runOnUiThread {
                    et_reply.setText("")
                    refresh()
                }


            }
    }

    private fun deleteMessage(id: Int) {
        ProgressDialogW.show(requireContext(), "请稍后", "正在删除", false)
        val o = ApiGenerator.getApiService(Api::class.java).deletePrivateMessage(id)
            .setSchedulers()
            .checkError()
            .doFinally {
                ProgressDialogW.hide()
            }
            .safeSubscribeBy {
                refresh()
            }
    }

    @SuppressLint("SetTextI18n")
    private fun refresh() {
        val o = ApiGenerator.getApiService(Api::class.java).getPrivateMessage(friendId)
            .setSchedulers()
            .checkApiError()
            .doFinally {
                drag_head_view?.finishRefresh()
            }.safeSubscribeBy {

                activity?.apply {
                    runOnUiThread {
                        rv_message.postDelayed({
                            runOnUiThread {
                                (rv_message.layoutManager as LinearLayoutManager).scrollToPosition(
                                    it.size - 1
                                )
                            }
                        }, 100)
                        if (it.isNotEmpty() && friendName.value.isNullOrBlank()) {

                            it[0].friendUser?.nickname?.let { nickname ->
                                friendName.value = nickname
                            }
                        }
                    }
                }

                adapter.submitList(it.sortedBy { item ->
                    item.id
                })

            }


        val p = ApiGenerator.getApiService(Api::class.java).clearCountPrivateMessageFriend(friendId)
            .setSchedulers()
            .checkError()
            .doFinally {
            }
            .safeSubscribeBy {
            }
    }


}