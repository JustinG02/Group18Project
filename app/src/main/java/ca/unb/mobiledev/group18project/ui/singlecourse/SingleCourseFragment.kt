package ca.unb.mobiledev.group18project.ui.singlecourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ca.unb.mobiledev.group18project.MainActivity
import ca.unb.mobiledev.group18project.databinding.FragmentSingleCourseBinding
import ca.unb.mobiledev.group18project.entities.Course


class SingleCourseFragment : Fragment() {

    private var _binding: FragmentSingleCourseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var actionBar : ActionBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSingleCourseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        arguments?.getSerializable("course")?.let { course ->
            if (course is Course) { // Replace 'Course' with the actual type of your course object
                (activity as? AppCompatActivity)?.supportActionBar?.title = course.name
            }
        }

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

}