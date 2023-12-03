package ca.unb.mobiledev.group18project.ui.deliverables

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
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.ui.singlecourse.SingleCourseFragment

class DeliverableAdapter(context: Context, items: List<Deliverable>, private val viewmodel: DeliverablesViewModel, private val fragment: DeliverablesFragment) : ArrayAdapter<Deliverable>(
    context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        val item = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        var currView = convertView
        if (currView == null) {
            currView = LayoutInflater.from(context).inflate(R.layout.deliverablelist_layout, parent, false)
        }


        // Lookup view for data population
        val deliverableCourse= currView!!.findViewById<TextView>(R.id.deliverable_course)
        val deliverableName = currView!!.findViewById<TextView>(R.id.deliverable_name)
        val deliverableDate = currView!!.findViewById<TextView>(R.id.deliverable_date)
        val deliverableWeight = currView!!.findViewById<TextView>(R.id.deliverable_weight)
        //val deliverableGrade = currView!!.findViewById<TextView>(R.id.deliverable_grade)

        val deliverableMenu = currView!!.findViewById<ImageView>(R.id.image_menu)

        deliverableName.text = item!!.name
        deliverableDate.text = item.dueDate +" "+ item.dueTime
        deliverableWeight.text = item.weight.toString() + "%"
        deliverableCourse.text = item.courseName
        //deliverableGrade.text= item.grade.toString()+ "%"


        deliverableMenu.setOnClickListener {
            val popup = PopupMenu(context, deliverableMenu)
            popup.inflate(R.menu.courselist_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_option_edit -> {
                        fragment.BuildDialog("EDIT DELIVERABLE", item, false)
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
