package ca.unb.mobiledev.group18project.ui.courses

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ca.unb.mobiledev.group18project.daos.CourseDao
import ca.unb.mobiledev.group18project.entities.Courses
import ca.unb.mobiledev.group18project.repository.CourseRepository

class CoursesViewModel(application: Application) : AndroidViewModel(application){

    private val courseRepository: CourseRepository = CourseRepository(application)

    val allCourses: LiveData<List<Courses>> = courseRepository.getAllRecords()

    // Insert a new record
    fun insert(name: String?, num: Int) {
        courseRepository.insertRecord(name, num)
    }

    fun deleteByName(name: String) {
        courseRepository.deleteRecordByName(name)
    }

    fun delete(course: Courses) {
        courseRepository.deleteRecord(course)
    }

    fun update(course: Courses) {
        courseRepository.updateRecord(course)
    }

    fun getAllIncompleteCourses(): List<Courses>? {
        return courseRepository.getAllIncompleteRecords()
    }

    fun getAllCompleteCourses(): List<Courses>? {
        return courseRepository.getAllCompletedRecords()
    }
}