package ca.unb.mobiledev.group18project.ui.singlecourse

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ca.unb.mobiledev.group18project.MainActivity
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.databinding.FragmentSingleCourseBinding
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.ui.courses.CoursesViewModel
import ca.unb.mobiledev.group18project.ui.deliverables.DeliverableAdapter
import ca.unb.mobiledev.group18project.ui.deliverables.DeliverablesViewModel
import java.util.Calendar


class SingleCourseFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSingleCourseBinding? = null

    private lateinit var mDeliverablesViewModel: DeliverablesViewModel
    private lateinit var mCourseViewModel: CoursesViewModel
    private lateinit var mListView: ListView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var actionBar : ActionBar

    private lateinit var thisCourse : Course

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDeliverablesViewModel = ViewModelProvider(this).get(DeliverablesViewModel::class.java)
        mCourseViewModel = ViewModelProvider(this).get(CoursesViewModel::class.java)

        _binding = FragmentSingleCourseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getSerializable("course")?.let { course ->
            if (course is Course) { // Replace 'Course' with the actual type of your course object
                thisCourse = course
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.title = thisCourse.name

        mDeliverablesViewModel.getAllDeliverablesOfACourse(thisCourse.courseID).observe(viewLifecycleOwner) {
            SearchCourseDeliverables(thisCourse.courseID)
        }

        mListView = root.findViewById(R.id.deliverable_list)
        val addButton = root.findViewById<Button>(R.id.add_deliverables)

        addButton.setOnClickListener(this)

        return root
    }

    override fun onResume() {
        super.onResume()
        // Hide the action bar
        (activity as? MainActivity)?.hideBottomNav()
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showBottomNav()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_deliverables -> {
                BuildDialog("ADD DELIVERABLE", null, true)
            }
        }
    }

    fun BuildDialog(title: String, deliverable: Deliverable?, new: Boolean) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_add_deliverable, null)
        val spinnerCourse = dialogView.findViewById<Spinner>(R.id.spinnerCourses)
        val editDeliverableName = dialogView.findViewById<EditText>(R.id.editTextDeliverableName)
        val editDeliverableWeight = dialogView.findViewById<EditText>(R.id.editTextDeliverableWeight)
        val dueDateButton = dialogView.findViewById<Button>(R.id.dueDate)
        val dueTimeButton = dialogView.findViewById<Button>(R.id.dueTime)
        val infoText = dialogView.findViewById<EditText>(R.id.infoTextView)

        var selectedDueDate = ""
        var selectedDueTime = ""

        dueDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Note: Month is 0-based, so add 1 for display
                selectedDueDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
                dueDateButton.text = selectedDueDate
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))

            datePickerDialog.show()
        }

        dueTimeButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
                selectedDueTime = String.format("%02d:%02d", hourOfDay, minute)
                dueTimeButton.text = selectedDueTime
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)

            timePickerDialog.show()
        }

        val modifiedCourseList = mutableListOf<Course>()

        modifiedCourseList.add(thisCourse)

        val courseNames = modifiedCourseList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courseNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourse.adapter = adapter

        spinnerCourse.isEnabled = false

        if(!new){
            // Set existing values
            editDeliverableName.setText(deliverable?.name)
            editDeliverableWeight.setText(deliverable?.weight.toString())
            selectedDueDate = deliverable?.dueDate.toString() // Format: "YYYY-MM-DD"
            dueDateButton.text = selectedDueDate
            selectedDueTime = deliverable?.dueTime.toString() // Format: "YYYY-MM-DD"
            dueTimeButton.text = selectedDueTime
            infoText.setText(deliverable?.info.toString())
        }
        builder.setView(dialogView)
            .setTitle(title)
            .setPositiveButton("Submit") { _, _ ->

                val cName = modifiedCourseList.get(spinnerCourse.selectedItemId.toInt()).name
                val courseID = modifiedCourseList.get(spinnerCourse.selectedItemId.toInt()).courseID
                val dName = editDeliverableName.text.toString()
                val dWeight = editDeliverableWeight.text.toString().toInt()
                val info = infoText.text.toString()

                if (cName == "Select a Course" || courseID == -1 || dName == "" || dWeight == null || selectedDueDate == "" || selectedDueTime == "") {
                    Toast.makeText(binding.root.context, "Data entered is incomplete/incorrect format. Data has not been saved.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (new) {
                    Toast.makeText(binding.root.context, "New Data Entry", Toast.LENGTH_SHORT).show()
                    mDeliverablesViewModel.insert(dName, cName, courseID, selectedDueDate, selectedDueTime, dWeight, info)
                } else {
                    Toast.makeText(binding.root.context, "Updated Data Entry", Toast.LENGTH_SHORT).show()
                    deliverable?.name = dName
                    deliverable?.courseName = cName
                    deliverable?.courseID  = courseID
                    deliverable?.info = info
                    deliverable?.dueDate = selectedDueDate
                    deliverable?.dueTime = selectedDueTime
                    deliverable?.weight = dWeight
                    mDeliverablesViewModel.update(deliverable!!)
                }
            }
            .setNegativeButton("Cancel", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun SearchCourseDeliverables(courseID: Int) {
        mDeliverablesViewModel.getAllDeliverablesOfACourse(courseID).observe(viewLifecycleOwner) { deliverables ->
            val adapter = SingleCourseAdapter(requireContext(), deliverables, mDeliverablesViewModel, this)
            mListView.adapter = adapter
        }
    }

}