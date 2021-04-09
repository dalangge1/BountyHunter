package com.example.bountyhunter.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseFragment
import com.example.bountyhunter.bean.beannew.Good
import com.example.bountyhunter.model.Config
import com.example.bountyhunter.network.*
import com.example.bountyhunter.widgets.OptionalDialog
import kotlinx.android.synthetic.main.fragment_good.*


class FragmentGood : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_good, container, false)
    }

    var good: Good? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        good = arguments?.getSerializable("good") as Good?
        bindView()
        tv_user_nickname2.setOnClickListener {
            findNavController().navigate(R.id.action_global_fragmentIndividual, Bundle().apply {
                putBoolean("is_mine", false)
                putString("user_id", good?.submitUser?.userId ?: "")
            })
        }
        btn_good_message.setOnClickListener {
            findNavController().navigate(
                R.id.action_global_fragmentMessageCommunicate,
                Bundle().apply {
                    putString("friend_id", good?.submitUser?.userId ?: "")
                    putString("friend_name", good?.submitUser?.nickname ?: "")
                })
        }
        btn_good_delete.setOnClickListener {
            OptionalDialog.show(requireContext(), "是否删除？", {}) {
                deleteGood()
            }
        }

        btn_get_good.setOnClickListener {
            OptionalDialog.show(requireContext(), "是否确定拍下？", {}) {
                getGood()
            }
        }

    }

    private fun bindView() {
        good?.let {
            Glide.with(this).load(it.picUrl).into(iv_good)
            Glide.with(this).load(it.submitUser?.avatarUrl).into(iv_user_avatar2)
            tv_good_name2.text = it.goodName
            if (it.getUserId.isBlank()) {
                tv_is_get2.visibility = View.GONE
            } else {
                tv_is_get2.visibility = View.VISIBLE
            }
            tv_user_nickname2.text = it.submitUser?.nickname
            tv_price2.text = it.price.toString()
            tv_good_describe.text = it.statement

            // 控制按钮的显示隐藏
            if (it.submitUser?.userId ?: "" == Config.userId) {
                // 如果发布者是自己
                btn_good_delete.visibility = View.VISIBLE
                btn_good_message.visibility = View.GONE
            } else {
                // 如果发布者是自己
                btn_good_delete.visibility = View.GONE
                btn_good_message.visibility = View.VISIBLE
            }
            if (it.submitUser?.userId ?: "" != Config.userId && it.getUserId.isBlank()) {
                btn_get_good.visibility = View.VISIBLE
            } else {
                btn_get_good.visibility = View.GONE
            }
        }
    }

    private fun deleteGood() {
        good?.let {
            ApiGenerator.getApiService(Api::class.java).deleteGood(it.goodId)
                .setSchedulers()
                .checkError()
                .doOnError {
                    Toast.makeText(context, "删除失败！", Toast.LENGTH_SHORT).show()
                }
                .safeSubscribeBy {
                    activity?.runOnUiThread {
                        findNavController().navigate(R.id.action_fragmentGood_pop)
                    }

                }
        }

    }


    private fun getGood() {
        good?.let {
            Log.e("sandyzhang", "gid: ${it.goodId}")
            ApiGenerator.getApiService(Api::class.java).getGood(it.goodId)
                .setSchedulers()
                .checkError()
                .doOnError {
                    Toast.makeText(context, "拍下失败！请检查他和你是否在一个学校，或者商品已经没啦", Toast.LENGTH_SHORT)
                        .show()
                }
                .safeSubscribeBy {
                    activity?.runOnUiThread {
                        Toast.makeText(context, "成功拍下，请留意卖家跟您的交流", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_fragmentGood_pop)
                    }

                }
        }

    }

}