package com.example.project2_team18

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_achievement.view.*
import com.example.project2_team18.AchievementsFragment.OnListFragmentInteractionListener

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the specified
 * [OnListFragmentInteractionListener]
 */
class MyAchievementRecyclerViewAdapter(

    var mValues: List<Achievement>,
    private val mListener: OnListFragmentInteractionListener?

) : androidx.recyclerview.widget.RecyclerView.Adapter<MyAchievementRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    lateinit var context: Context

    var database: AchievementDatabase? = null
//        set(value) {
//            field = value
//            value?.let {
//                LoadAchievementsTask(it, this).execute()
//            }
//        }

    init {
        LoadDatabaseTask(this).execute()
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

    private var selectedIndex: Int = androidx.recyclerview.widget.RecyclerView.NO_POSITION

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitle.text = item.title
        holder.mStatus.text = item.status
        holder.isActive = selectedIndex == position

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val mImageView: ImageView = mView.achievementLogo
        val mStatus: TextView = mView.achievementStatus
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

//    fun insert(achievement: Achievement) {
//        mValues.add(achievement)
//        selectedIndex = mValues.size - 1
//        notifyItemInserted(mValues.size - 1)
//    }
}