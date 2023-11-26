package ca.unb.mobiledev.group18project.ui.singlecourse

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.ui.courses.CoursesFragment
import ca.unb.mobiledev.group18project.ui.courses.CoursesViewModel
import ca.unb.mobiledev.group18project.ui.deliverables.DeliverableAdapter
import ca.unb.mobiledev.group18project.ui.deliverables.DeliverablesViewModel

class SingleCourseAdapter(context: Context, items: List<Deliverable>, private val viewmodel: DeliverablesViewModel, private val fragment: SingleCourseFragment) : ArrayAdapter<Deliverable>(
    context, 0, items) {

    private lateinit var actionBar : ActionBar

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.coursedeliverablelist_layout, parent, false)
        }

        val deliverableName = currView!!.findViewById<TextView>(R.id.deliverable_name)
        val deliverableDate = currView!!.findViewById<TextView>(R.id.deliverable_date)
        val deliverableWeight = currView!!.findViewById<TextView>(R.id.deliverable_weight)


        val DeliverableMenu = currView!!.findViewById<ImageView>(R.id.image_menu)

        deliverableName.text = item!!.name
        deliverableDate.text = item.dueDate +" "+ item.dueTime
        deliverableWeight.text = item.weight.toString() + "%"

        return currView!!
    }
}