package ca.unb.mobiledev.group18project.ui.courses

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.LiveData
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.ui.singlecourse.SingleCourseFragment


class CoursesAdapter(context: Context, items: List<Course>, private val viewmodel: CoursesViewModel, private val fragment: CoursesFragment) : ArrayAdapter<Course>(
    context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val item = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.courselist_layout, parent, false)
        }

        // Lookup view for data population
        val courseName = currView!!.findViewById<TextView>(R.id.course_name)
        val courseCH = currView!!.findViewById<TextView>(R.id.course_ch)
        val courseMenu = currView!!.findViewById<ImageView>(R.id.image_menu)

        courseName.text = item!!.name
        courseCH.text = "${item.ch}ch"

        currView.setOnClickListener {
            // Navigate to the SingleCourseFragment
            val singleCourseFragment = SingleCourseFragment()

            // Pass the clicked course to the fragment using a Bundle
            val bundle = Bundle()
            bundle.putSerializable("course", item)
            singleCourseFragment.arguments = bundle

            // Perform the fragment transaction
            fragment.parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_activity_main, singleCourseFragment)
                .addToBackStack(null)
                .commit()
        }

        courseMenu.setOnClickListener {
            val popup = PopupMenu(context, courseMenu)
            popup.inflate(R.menu.courselist_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_option_edit -> {
                        fragment.BuildDialog("EDIT COURSE", item, false)
                        true
                    }
                    R.id.menu_option_delete -> {
                        viewmodel.delete(item)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }

        // Return the completed view to render on screen
        return currView
    }
}