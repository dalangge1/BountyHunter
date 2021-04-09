package com.example.bountyhunter.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.bountyhunter.R
import com.example.bountyhunter.model.Config
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

    private var currentPageId = R.id.action_global_fragmentMap


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.getBooleanExtra("login", false)) {
//            Thread{
//                Thread.sleep(1000L)
//                runOnUiThread {
//
//                }
//            }.start()
        }


        bottomViewList.setOnClickListener {
            clrBottomViewSelect()
            bottomViewList.setSelect(true)
            navTo(R.id.action_global_fragmentMarket, null)
        }

        bottomViewMine.setOnClickListener {
            clrBottomViewSelect()
            bottomViewMine.setSelect(true)
            val bundle = Bundle()
            bundle.putBoolean("is_mine", true)
            bundle.putString("user_id", Config.userId)
            navTo(R.id.action_global_fragmentIndividual, bundle)

        }
        bottomViewCommunity.setOnClickListener {
            clrBottomViewSelect()
            bottomViewCommunity.setSelect(true)
            navTo(R.id.action_global_fragmentCommunity, null)
        }

        bottomViewGraph.setOnClickListener {
            clrBottomViewSelect()
            bottomViewGraph.setSelect(true)
            navTo(R.id.action_global_fragmentMessage, null)
        }

        floatingMain.setOnClickListener {
            clrBottomViewSelect()
            navTo(R.id.action_global_fragmentMap,null)
        }

//        floatingCreate.setOnClickListener {
//            if(findNavController(R.id.fragment).backStack.size == 2){
//                findNavController(R.id.fragment).navigate(R.id.action_global_fragmentCounterEdit)
//            }
//        }


        //添加导航完成的listener,如果返回栈大于2说明不在首页平行的页面，则隐藏底部栏
        findNavController(R.id.fragment).addOnDestinationChangedListener { navController: NavController, navDestination: NavDestination, bundle: Bundle? ->
            if(navController.backStack.size > 2){//因为这个callback是导航完成后才触发的，栈中已经添加了目标fragment了，所以不是1而是2
                if(!bottomView.isHide){
                    bottomView.hide()//隐藏的动态效果
//                    floatingCreate.hide()
                }
            }else{
                if(bottomView.isHide){
                    bottomView.show()//进入的动态效果
//                    floatingCreate.show()
                }
            }
        }


    }


    @SuppressLint("RestrictedApi")
    private fun navTo(id : Int, bundle: Bundle?){
        val navController = findNavController(R.id.fragment)
        if(navController.backStack.size > 1){
            navController.popBackStack()
        }
        if (id != currentPageId){
            if (bundle != null) {
                navController.navigate(id, bundle)
            } else {
                navController.navigate(id)
            }
            currentPageId = id
        }


    }

    private fun clrBottomViewSelect(){
        bottomViewMine.setSelect(false)
        bottomViewCommunity.setSelect(false)
        bottomViewGraph.setSelect(false)
        bottomViewList.setSelect(false)
    }
}