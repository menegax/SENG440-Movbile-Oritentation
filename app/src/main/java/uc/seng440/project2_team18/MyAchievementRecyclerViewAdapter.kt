package uc.seng440.project2_team18

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_achievement.view.*
import uc.seng440.project2_team18.AchievementsFragment.OnListFragmentInteractionListener
import uc.seng440.project2_team18.Models.Achievement.Achievement

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the specified
 * [OnListFragmentInteractionListener]
 */
class MyAchievementRecyclerViewAdapter(

    private var mValues: List<Achievement>,
    private val mListener: OnListFragmentInteractionListener?

) : RecyclerView.Adapter<MyAchievementRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    lateinit var context: Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Achievement
            mListener?.OnListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.fragment_achievement, parent, false)
        return ViewHolder(view)
    }

    private var selectedIndex: Int = RecyclerView.NO_POSITION

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitle.text = item.title
        val status: String = when {
            item.status == "Gold" -> context.resources.getString(R.string.Gold)
            item.status == "Silver" -> context.resources.getString(R.string.Silver)
            else -> context.resources.getString(R.string.Bronze)
        }
        holder.mStatus.text = status
        val description: String = when (item.title) {
            "Recreation Center" -> context.resources.getString(R.string.recreation_center)
            "Frother!" -> context.resources.getString(R.string.frother)
            "Central Library" -> context.resources.getString(R.string.central_library)
            "EPS Library" -> context.resources.getString(R.string.eps_library)
            "Macmillan Brown Library" -> context.resources.getString(R.string.macmillan_brown_library)
            "The Book Shop" -> context.resources.getString(R.string.the_book_shop)
            "Meremere Building" -> context.resources.getString(R.string.meremere_building)
            "Erskine" -> context.resources.getString(R.string.erskine)
            "Engineering Core" -> context.resources.getString(R.string.engineering_core)
            "The True Engineer!" -> context.resources.getString(R.string.the_true_engineer)
            "The True Breather!" -> context.resources.getString(R.string.the_true_breather)
            "The Shilling Club" -> context.resources.getString(R.string.the_shilling_club)
            else -> ""
        }
        holder.mDescription.text = description
        holder.isActive = selectedIndex == position

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
        if (item.achieved) {
            spinImage(holder.mImageView)
            when (item.status) {
                "Silver" -> holder.mImageView.setColorFilter(Color.parseColor("#C0C0C0"))
                "Gold" -> holder.mImageView.setColorFilter(Color.parseColor("#CFB53B"))
                else -> holder.mImageView.setColorFilter(Color.parseColor("#CD7F32"))
            }
        }
    }

    fun spinImage(imageToAnimate: ImageView) {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )

        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.duration = 1300
        rotateAnimation.repeatCount = Animation.INFINITE

        imageToAnimate.startAnimation(rotateAnimation)

    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.achievementLogo
        val mStatus: TextView = mView.achievementStatus
        val mDescription: TextView = mView.achievementDescription
        val mTitle: TextView = mView.achievementTitle

        var isActive: Boolean = false
            set(value) {
                field = value
                itemView.setBackgroundColor(if(value) Color.LTGRAY else Color.TRANSPARENT)
            }
        override fun toString(): String {
            return super.toString() + " '" + mTitle.text + "'"
        }





    }
}