package com.example.project2_team18


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
import com.google.android.gms.maps.GoogleMap
import com.synnapps.carouselview.ImageListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider

import com.synnapps.carouselview.CarouselView
import kotlinx.android.synthetic.main.fragment_photos.*
import kotlinx.android.synthetic.main.nav_header_main.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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
    private lateinit var photoButton: Button
    private lateinit var currentPhotoPath: String
    private lateinit var bitmaps : List<Bitmap>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        val carouselView = (view as ViewGroup).getChildAt(0)

        photoButton = view.findViewById(R.id.takePhotoButton)

        photoButton.setOnClickListener {
            dispatchTakePictureIntent()
        }

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

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(this.context?.packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    Log.e("FILE IO EXCEPTION", "An error occurred while creating the File")
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this.context!!,
                        "com.example.project2_team18.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = this.context!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            //TODO Reload the images

        }
    }


}
