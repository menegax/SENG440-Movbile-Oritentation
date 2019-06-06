package uc.seng440.project2_team18


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uc.seng440.project2_team18.Models.Achievement.Achievement
import uc.seng440.project2_team18.Models.Achievement.AchievementRepository


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class AchievementsFragment : Fragment() {


    private var listener: OnListFragmentInteractionListener? = null
    var achievements : List<Achievement> = ArrayList<Achievement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val achievementRepository = AchievementRepository(this.context!!)
        achievements = achievementRepository.getAllAchievements()

        val view = inflater.inflate(R.layout.fragment_achievements_list, container, false)

        //Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context) as RecyclerView.LayoutManager?
                adapter = MyAchievementRecyclerViewAdapter(achievements, listener)
                val dividerItemDecoration = DividerItemDecoration(this.context, 1)
                this.addItemDecoration(dividerItemDecoration)
            }
        }
        return view
    }


    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information
     */
    interface OnListFragmentInteractionListener {
        fun OnListFragmentInteraction(item: Achievement?)
    }


}
