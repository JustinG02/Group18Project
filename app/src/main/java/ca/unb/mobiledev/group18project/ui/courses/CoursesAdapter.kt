package ca.unb.mobiledev.group18project.ui.courses

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.entities.Courses


class CoursesAdapter(context: Context, items: List<Courses>, private val viewmodel: CoursesViewModel, private val fragment: CoursesFragment) : ArrayAdapter<Courses>(
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