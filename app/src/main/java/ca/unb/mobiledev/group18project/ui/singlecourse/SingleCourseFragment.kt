package ca.unb.mobiledev.group18project.ui.singlecourse

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ca.unb.mobiledev.group18project.databinding.FragmentSingleCourseBinding


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

        actionBar = (activity as AppCompatActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)

        return root
    }

    override fun onPause() {
        super.onPause()
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                findNavController().navigateUp()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}