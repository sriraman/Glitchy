package me.bemind.glitchappcore

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable
import me.bemind.glitch.Effect

/**
 * Created by angelomoroni on 10/04/17.
 */

data class Response<out T, out V>(val activity: T, val image: V)

data class Image(val bitmap: Bitmap,val effect: Effect,var saved:Boolean) : Parcelable{
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Image> = object : Parcelable.Creator<Image> {
            override fun createFromParcel(source: Parcel): Image = Image(source)
            override fun newArray(size: Int): Array<Image?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readParcelable<Bitmap>(Bitmap::class.java.classLoader), Effect.values()[source.readInt()], 1.equals(source.readInt()))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeParcelable(bitmap, 0)
        dest?.writeInt(effect.ordinal)
        dest?.writeInt((if (saved) 1 else 0))
    }
}

data class ImageDescriptor ( val index:Int, val imageName:String,val effect: Effect,var saved: Boolean) : Parcelable{
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ImageDescriptor> = object : Parcelable.Creator<ImageDescriptor> {
            override fun createFromParcel(source: Parcel): ImageDescriptor = ImageDescriptor(source)
            override fun newArray(size: Int): Array<ImageDescriptor?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readString(), Effect.values()[source.readInt()], 1.equals(source.readInt()))

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(index)
        dest?.writeString(imageName)
        dest?.writeInt(effect.ordinal)
        dest?.writeInt((if (saved) 1 else 0))
    }
}

enum class State {
    BASE,EFFECT
}

abstract class EffectState(open val layout:Int) :Parcelable

data class GlitchEffectState(override val layout: Int) : EffectState(layout), Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<GlitchEffectState> = object : Parcelable.Creator<GlitchEffectState> {
            override fun createFromParcel(source: Parcel): GlitchEffectState = GlitchEffectState(source)
            override fun newArray(size: Int): Array<GlitchEffectState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(layout)
    }
}

data class AnaglyphEffectState(override val layout:Int,val progress:Int) : EffectState(layout), Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<AnaglyphEffectState> = object : Parcelable.Creator<AnaglyphEffectState> {
            override fun createFromParcel(source: Parcel): AnaglyphEffectState = AnaglyphEffectState(source)
            override fun newArray(size: Int): Array<AnaglyphEffectState?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(source.readInt(), source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(layout)
        dest?.writeInt(progress)
    }
}
