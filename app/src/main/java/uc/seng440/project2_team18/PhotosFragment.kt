package uc.seng440.project2_team18


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.synnapps.carouselview.ImageListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider

import com.synnapps.carouselview.CarouselView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PhotosFragment : Fragment() {

    private lateinit var carouselViewInstance: CarouselView
    private lateinit var bitmaps : List<Bitmap>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        val carouselView = (view as ViewGroup).getChildAt(0)


        //Get all of the photos for the user
        loadImages()

        //Set the adapter
        if (carouselView is CarouselView) {
            with(view) {
                carouselViewInstance = carouselView
                carouselView.pageCount = bitmaps.size
                carouselView.setImageListener(imageListener)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    fun loadImages() {
        val file = this.context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val files = file.listFiles()
        bitmaps = files.map {
                file -> BitmapFactory.decodeFile(file.path)
        }
    }

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            imageView.setImageBitmap(bitmaps[position])
        }


    }



}
