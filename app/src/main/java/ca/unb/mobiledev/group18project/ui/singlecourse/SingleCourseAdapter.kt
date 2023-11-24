package ca.unb.mobiledev.group18project.ui.singlecourse

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.ui.courses.CoursesFragment
import ca.unb.mobiledev.group18project.ui.courses.CoursesViewModel

class SingleCourseAdapter(context: Context, items: List<Course>, private val viewmodel: CoursesViewModel, private val fragment: CoursesFragment) : ArrayAdapter<Course>(
    context, 0, items) {

    private lateinit var actionBar : ActionBar

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.deliverablelist_layout, parent, false)
        }
        return currView!!
    }
}