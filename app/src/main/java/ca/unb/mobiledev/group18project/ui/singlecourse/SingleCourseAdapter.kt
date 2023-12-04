package ca.unb.mobiledev.group18project.ui.singlecourse

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
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
import java.text.SimpleDateFormat
import java.util.Locale

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
        val deliverableGrade = currView!!.findViewById<TextView>(R.id.deliverable_grade)

        val deliverableMenu = currView!!.findViewById<ImageView>(R.id.image_menu)


        if(item!!.grade != null) {
            deliverableGrade.text = "Grade: "+item!!.grade.toString()+"%"
        }else{
            deliverableGrade.text = "No Grade"
        }

        deliverableName.text = item!!.name
        deliverableDate.text = formatDate(item.dueDate) +" "+ formatTime(item.dueTime)
        deliverableWeight.text = "Weight: "+item.weight.toString() + "%"

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

        return currView!!
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