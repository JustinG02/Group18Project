package ca.unb.mobiledev.group18project.ui.deliverables

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.group18project.R
import ca.unb.mobiledev.group18project.databinding.FragmentCoursesBinding
import ca.unb.mobiledev.group18project.databinding.FragmentDeliverablesBinding
import ca.unb.mobiledev.group18project.entities.Course
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.ui.courses.CoursesViewModel

class DeliverablesFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDeliverablesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var mDeliverablesViewModel: DeliverablesViewModel
    private lateinit var mListView: ListView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mDeliverablesViewModel = ViewModelProvider(this).get(DeliverablesViewModel::class.java)

        _binding = FragmentDeliverablesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mListView = root.findViewById(R.id.deliverables_view)
        val addButton = root.findViewById<Button>(R.id.add_deliverables)

        addButton.setOnClickListener(this)

        mDeliverablesViewModel.allCourses.observe(viewLifecycleOwner) {

        }
        return root
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
        val editTextDeliverableCourse = dialogView.findViewById<EditText>(R.id.editTextCourseName)
        val editDeliverableName = dialogView.findViewById<EditText>(R.id.editTextDeliverableName)
        val editDeliverableDate  = dialogView.findViewById<EditText>(R.id.editTextDeliverableDate)
        val editDeliverableWorth = dialogView.findViewById<EditText>(R.id.editTextDeliverableWorth)

        builder.setView(dialogView)
            .setTitle(title)
            .setPositiveButton("Submit") { _, _ ->
                val cName = editTextDeliverableCourse.text.toString()
                val dName = editDeliverableName.text.toString()
                val dDate = editDeliverableDate.text.toString()
                val dWorth = editDeliverableWorth.text.toString()


                if (cName == "" || dName.toIntOrNull() == null) {
                    Toast.makeText(binding.root.context, "Data entered is incomplete/incorrect format. Data has not been saved.", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (new) {
                    Toast.makeText(binding.root.context, "New Data Entry", Toast.LENGTH_SHORT).show()
                    mDeliverablesViewModel.insert(dName,cName.toInt())
                } else {
                    Toast.makeText(binding.root.context, "Updated Data Entry", Toast.LENGTH_SHORT).show()
                    deliverable?.name = dName
                    deliverable?.courseID  = cName.toInt()
                    mDeliverablesViewModel.update(deliverable!!)
                }
            }
            .setNegativeButton("Cancel", null)

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}