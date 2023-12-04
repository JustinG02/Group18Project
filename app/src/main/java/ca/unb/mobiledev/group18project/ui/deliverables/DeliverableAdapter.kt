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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.ui.courses.CoursesViewModel
import ca.unb.mobiledev.group18project.ui.singlecourse.SingleCourseFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

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
        val deliverableTitle= currView!!.findViewById<TextView>(R.id.deliverable_title)
        val deliverableGrade = currView!!.findViewById<TextView>(R.id.deliverable_grade)
        val deliverableDate = currView!!.findViewById<TextView>(R.id.deliverable_date)
        val deliverableWeight = currView!!.findViewById<TextView>(R.id.deliverable_weight)
        //val deliverableGrade = currView!!.findViewById<TextView>(R.id.deliverable_grade)

        val deliverableMenu = currView!!.findViewById<ImageView>(R.id.image_menu)

        if(item!!.grade != null) {
            deliverableGrade.text = "Grade: "+item!!.grade.toString()+"%"
        }else{
            deliverableGrade.text = "No Grade"
        }

        deliverableDate.text = formatDate(item.dueDate) +" "+ formatTime(item.dueTime)
        deliverableWeight.text = "Weight: "+item.weight.toString() + "%"
        deliverableTitle.text = item.courseName + " - " + item!!.name
        //deliverableGrade.text= item.grade.toString()+ "%"

        currView.setOnClickListener {
            // Navigate to the SingleCourseFragment
            viewmodel.viewModelScope.launch {

                val singleCourseFragment = SingleCourseFragment()

                val item : Course = viewmodel.getCourse(item.courseID)

                // Pass the clicked course to the fragment using a Bundle
                val bundle = Bundle()
                bundle.putSerializable("course", item)
                singleCourseFragment.arguments = bundle

                // Use findNavController to navigate to SingleCourseFragment with the bundle
                fragment.findNavController().navigate(R.id.action_navigation_deliverables_to_navigation_single_course, bundle)
            }
        }

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

    fun formatDate(dueDate: String?): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        if(dueDate.isNullOrEmpty()){
            return ""
        }
        val date = inputDateFormat.parse(dueDate)
        val formattedDate = outputDateFormat.format(date)
        return "$formattedDate"
    }

    fun formatTime(dueTime: String?) : String{
        val inputTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        if(dueTime.isNullOrEmpty()){
            return ""
        }
        val time = inputTimeFormat.parse(dueTime)
        val formattedTime = outputTimeFormat.format(time)
        return "$formattedTime"

    }
}
