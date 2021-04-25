package com.example.bountyhunter.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bountyhunter.R
import com.example.bountyhunter.base.BaseViewModelFragment
import com.example.bountyhunter.network.FileUploadUtil
import com.example.bountyhunter.view.viewmodel.GoodViewModel
import com.example.bountyhunter.widgets.KeyboardController
import com.example.moneycounter4.widgets.ProgressDialogW
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_good_edit.*

class FragmentGoodEdit : BaseViewModelFragment<GoodViewModel>() {


    private var imgPath: String? = null
    private var upLoading = false
    private var imgUrl: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_good_edit, container, false)
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)





        progressBar.indeterminateDrawable.setColorFilter(
            resources.getColor(
                R.color.colorLightRed,
                null
            ), PorterDuff.Mode.SRC_IN
        )

        imageViewPic.setOnClickListener {

            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
                if (it) {
                    selectPic()
                }
            }

        }



        textViewSend.setOnClickListener {
            KeyboardController.hideInputKeyboard(requireContext(), it)

            if (upLoading) {
                Toast.makeText(requireContext(), "图片还没上传好鸭~", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (imgPath.isNullOrBlank()) {
                Toast.makeText(requireContext(), "请上传商品图片~~", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            activity?.let { it1 -> ProgressDialogW.show(it1, "请稍后", "正在上传~", false) }
            Log.e("sandyzhang", imgUrl!!)
            try {
                viewModel.releaseGood(
                    et_good_price.text.toString().toDouble(),
                    et_good_name.text.toString(),
                    et_good_describe.text.toString(),
                    imgUrl!!
                )
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "请输入正确的商品价格！", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.releaseGoodStatus.observeNotNull {
            ProgressDialogW.hide()
            if (it) {
                val navController = findNavController()
                while (navController.backStack.size >= 1) {
                    navController.popBackStack()
                }
                navController.navigate(R.id.action_global_fragmentMarket)
            }
        }


    }

    private fun selectPic() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, 11)
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            val selectedImage = data!!.data
            val filePathColumn =
                arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                selectedImage?.let {
                    requireContext().contentResolver.query(
                        it,
                        filePathColumn,
                        null,
                        null,
                        null
                    )
                }
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            imgPath = columnIndex?.let { cursor.getString(it) }
            cursor?.close()


            //imgPath?.let { LogW.d(it) }//===================================================
            progressBar.visibility = View.VISIBLE
            imageViewPic.visibility = View.GONE
            imageViewPic.setImageBitmap(null)

            upLoading = true

            // TODO imgPath
            if (imgPath.isNullOrEmpty()) {
                return
            }
            FileUploadUtil.uploadMultiFile(listOf(imgPath!!)) {
                activity?.runOnUiThread {
                    if (it.picUrls.isNotEmpty()) {
                        imgUrl = it.picUrls[0]
                        progressBar.visibility = View.GONE
                        imageViewPic.visibility = View.VISIBLE
                        Glide.with(this).load(it.picUrls[0]).into(imageViewPic)
                        upLoading = false
                    }
                }
            }


        }
    }


}