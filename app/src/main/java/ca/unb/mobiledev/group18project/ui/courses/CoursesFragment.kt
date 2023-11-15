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
import ca.unb.mobiledev.group18project.entities.Courses

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
        mCourseViewModel =
            ViewModelProvider(this).get(CoursesViewModel::class.java)

        _binding = FragmentCoursesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mListView = root.findViewById(R.id.courses_view)

        val addButton = root.findViewById<Button>(R.id.add_course)

        addButton.setOnClickListener(this)

        SearchIncompleteCourses()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_course -> {
                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                val inflater = requireActivity().layoutInflater
                val dialogView = inflater.inflate(R.layout.dialog_add_course, null)
                val editTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourseName)
                val chTextCourse = dialogView.findViewById<EditText>(R.id.editTextCourseCH)

                builder.setView(dialogView)
                    .setTitle("ADD COURSE")
                    .setPositiveButton("Submit") { _, _ ->
                        // Handle OK button click, you can access the entered text using editTextCourse.text.toString()
                        val name = editTextCourse.text.toString()
                        val ch = chTextCourse.text.toString()

                        if (ch.toIntOrNull() == null) {
                            Toast.makeText(binding.root.context, "Data entered is incomplete/incorrect format. Data has not been saved.", Toast.LENGTH_SHORT).show()
                            return@setPositiveButton
                        }

                        mCourseViewModel.insert(name, ch.toInt())
                        Toast.makeText(binding.root.context, "Data has been saved.", Toast.LENGTH_SHORT).show()
                        SearchIncompleteCourses()

                    }
                    .setNegativeButton("Cancel", null)

                val dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }

    fun SearchIncompleteCourses() {
        val results: List<Courses>? = mCourseViewModel.getAllIncompleteCourses()

        if (results!!.isEmpty()) {
            mListView.adapter = null
        } else {
            val adapter = CoursesAdapter(binding.root.context, results)
            mListView.adapter = adapter
        }
    }
}