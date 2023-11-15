package ca.unb.mobiledev.group18project.ui.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ca.unb.mobiledev.group18project.entities.Courses
import ca.unb.mobiledev.group18project.repository.CourseRepository

class CoursesViewModel(application: Application) : AndroidViewModel(application){

    private val courseRepository: CourseRepository = CourseRepository(application)

    // Insert a new record
    fun insert(name: String?, num: Int) {
        courseRepository.insertRecord(name, num)
    }

    // Remove an existing record
    fun getAllCourses(): List<Courses>? {
        return courseRepository.getAllRecords()
    }

    fun getAllIncompleteCourses(): List<Courses>? {
        return courseRepository.getAllIncompleteRecords()
    }

    fun getAllCompleteCourses(): List<Courses>? {
        return courseRepository.getAllCompletedRecords()
    }
}