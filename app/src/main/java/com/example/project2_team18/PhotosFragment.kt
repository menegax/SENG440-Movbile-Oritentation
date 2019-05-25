package com.example.project2_team18


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.synnapps.carouselview.ImageListener
import android.widget.ImageView

import com.synnapps.carouselview.CarouselView




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PhotosFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private lateinit var carouselView: CarouselView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_photos, container, false)
        val carouselView = (view as ViewGroup).getChildAt(0)

        //Set the adapter
        if (carouselView is CarouselView) {
            with(view) {
                carouselView.pageCount = sampleImages.size
                carouselView.setImageListener(imageListener)
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    //https://developer.android.com/training/camera/photobasics
    //How to take and save photos
    var sampleImages =
        intArrayOf(R.drawable.ic_add_circle
            , R.drawable.ic_map
            , R.drawable.ic_add,
            R.drawable.ic_chase_logo,
            R.drawable.ic_logout)

    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView) {
            imageView.setImageResource(sampleImages[position])
        }
    }


}
