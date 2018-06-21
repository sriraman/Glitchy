package me.bemind.glitchlibrary

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

/**
 * Created by angelomoroni on 09/04/17.
 */

abstract class GenericBottomSheet {
    protected var mBottomSheet :BottomSheetDialog? = null
    protected var context: Context? = null

    fun dismiss() {
        mBottomSheet?.dismiss()
    }

    abstract fun inflateView(view:View)
    abstract fun getLayoutResource():Int

    fun show(){
        mBottomSheet = BottomSheetDialog(context!!)

        val view = LayoutInflater.from(context)
                .inflate(getLayoutResource(),null, false)

        mBottomSheet?.setContentView(view)
        mBottomSheet?.setOnDismissListener { mBottomSheet = null }
        mBottomSheet?.show()

        inflateView(view)
    }
}

class PickPhotoBottomSheet private constructor() : GenericBottomSheet(){

    private var listener: OnPickPhotoListener? = null



    companion object Creator {
        fun getPickPhotoBottomSheet(context:Context,listener: OnPickPhotoListener) : PickPhotoBottomSheet{
            val pickPhotoBottmSheet = PickPhotoBottomSheet()

            pickPhotoBottmSheet.context = context
            pickPhotoBottmSheet.listener = listener

            return pickPhotoBottmSheet
        }
    }

    override fun getLayoutResource(): Int {
        return R.layout.pick_photo_bottom_sheet
    }

    override fun inflateView(view: View) {
        view.findViewById<TextView>(R.id.camera_text_view).setOnClickListener { listener?.openCamera() }
        view.findViewById<TextView>(R.id.gallery_text_view).setOnClickListener { listener?.openGallery() }
    }

    interface OnPickPhotoListener{
        fun openCamera()
        fun openGallery()
    }

}

class SaveImageBottomSheet private constructor() : GenericBottomSheet(){

    private var listener : OnSaveImageListener? = null


    companion object Creator {
        fun getSaveImageBottomSheet(context:Context,listener: SaveImageBottomSheet.OnSaveImageListener) : SaveImageBottomSheet{
            val saveImagebottomSheet = SaveImageBottomSheet()

            saveImagebottomSheet.context = context
            saveImagebottomSheet.listener = listener

            return saveImagebottomSheet
        }
    }

    override fun getLayoutResource() = R.layout.save_image_bottom_sheet

    override fun inflateView(view: View) {
        view.findViewById<TextView>(R.id.share_text_view).setOnClickListener { listener?.shareImage() }
        view.findViewById<TextView>(R.id.save_text_view).setOnClickListener { listener?.saveImage() }
    }



    interface OnSaveImageListener{
        fun saveImage()
        fun shareImage()
    }
}

class ShareAppBottomSheet private constructor() : GenericBottomSheet(){

    private var listener: OnShareDialogClick? = null

    companion object Creator {
        fun getShareAppDialogFragment(context: Context,onShareDialogClick: OnShareDialogClick) : ShareAppBottomSheet{
            val shareAppBS = ShareAppBottomSheet()
            shareAppBS.listener = onShareDialogClick
            shareAppBS.context = context

            return shareAppBS

        }
    }

    override fun inflateView(view: View) {
        view.findViewById<TextView>(R.id.share_text_view).setOnClickListener {
            dismiss()
            listener?.shareAppLink()
        }
        view.findViewById<TextView>(R.id.instagram_text_view).setOnClickListener {
            dismiss()
            listener?.instagram()
        }
        view.findViewById<TextView>(R.id.facebook_text_view).setOnClickListener {
            dismiss()
            listener?.facebook()
        }
        view.findViewById<TextView>(R.id.rate_text_view).setOnClickListener {
            dismiss()
            listener?.rateApp()
        }
    }

    override fun getLayoutResource() = R.layout.share_app_bottom_sheet

    interface OnShareDialogClick{
        fun rateApp()
        fun instagram()
        fun facebook()
        fun shareAppLink()
    }
}
