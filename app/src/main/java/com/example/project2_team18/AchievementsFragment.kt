package com.example.project2_team18


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


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
    var achievements : MutableList<Achievement> = ArrayList<Achievement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        achievements.add(0, Achievement("MVP", "Bronze"))
        achievements.add(1, Achievement("DPOY", "Bronze"))
        achievements.add(2, Achievement("ROY", "Bronze"))

        val view = inflater.inflate(R.layout.fragment_achievements_list, container, false)

        //Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
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
