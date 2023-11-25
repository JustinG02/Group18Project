package ca.unb.mobiledev.group18project.ui.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ca.unb.mobiledev.group18project.MainActivity
import ca.unb.mobiledev.group18project.databinding.FragmentCoursesHistoryBinding

class CoursesHistoryFragment : CoursesFragment() {

    private var _binding: FragmentCoursesHistoryBinding? = null

    private lateinit var mCourseViewModel: CoursesViewModel
    private lateinit var mListView: ListView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mCourseViewModel = ViewModelProvider(this).get(CoursesViewModel::class.java)

        _binding = FragmentCoursesHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mListView = binding.coursesHistory

        mCourseViewModel.getAllCompleteCourses().observe(viewLifecycleOwner) {
            SearchCompleteCourses()
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

    fun SearchCompleteCourses() {
        mCourseViewModel.getAllCompleteCourses().observe(viewLifecycleOwner) { courses ->
            val adapter = CoursesAdapter(requireContext(), courses, mCourseViewModel, this)
            mListView.adapter = adapter
        }
    }
}