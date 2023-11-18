package ca.unb.mobiledev.group18project.ui.courses

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.databinding.FragmentCoursesBinding
import ca.unb.mobiledev.group18project.entities.Course

class CoursesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCoursesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mCourseViewModel: CoursesViewModel
    private lateinit var mListView: ListView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mCourseViewModel = ViewModelProvider(this).get(CoursesViewModel::class.java)

        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mListView = root.findViewById(R.id.courses_view)

        val addButton = root.findViewById<Button>(R.id.add_course)

        addButton.setOnClickListener(this)

        mCourseViewModel.allCourses.observe(viewLifecycleOwner) {
            SearchIncompleteCourses()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_course -> {
                BuildDialog("ADD COURSE", null, true)
            }
        }
    }

    fun BuildDialog(title: String, course: Course?, new: Boolean) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_course, null)
        val editTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourseName)
        val chTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourseCH)

        builder.setView(dialogView)
            .setTitle(title)
            .setPositiveButton("Submit") { _, _ ->
                val name = editTextCourse.text.toString()
                val ch = chTextCourse.text.toString()

                if (name == "" || ch.toIntOrNull() == null) {
                    Toast.makeText(binding.root.context, "Data entered is incomplete/incorrect format. Data has not been saved.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (new) {
                    Toast.makeText(binding.root.context, "New Data Entry", Toast.LENGTH_SHORT).show()
                    mCourseViewModel.insert(name, ch.toInt())
                } else {
                    Toast.makeText(binding.root.context, "Updated Data Entry", Toast.LENGTH_SHORT).show()
                    course?.name = name
                    course?.ch = ch.toInt()
                    mCourseViewModel.update(course!!)
                }
            }
            .setNegativeButton("Cancel", null)

                val dialog: AlertDialog = builder.create()
                dialog.show()
    }

    fun SearchIncompleteCourses() {
        val results: List<Course>? = mCourseViewModel.getAllIncompleteCourses()

        if (results!!.isEmpty()) {
            mListView.adapter = null
        } else {
            val adapter = CoursesAdapter(binding.root.context, results, mCourseViewModel, this)
            mListView.adapter = adapter
        }
    }
}