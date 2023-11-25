package ca.unb.mobiledev.group18project.ui.courseshistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ca.unb.mobiledev.group18project.MainActivity
import ca.unb.mobiledev.group18project.databinding.FragmentCoursesHistoryBinding

class CoursesHistoryFragment : Fragment() {

    private var _binding: FragmentCoursesHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCoursesHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

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