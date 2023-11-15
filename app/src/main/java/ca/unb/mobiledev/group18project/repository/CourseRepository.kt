package ca.unb.mobiledev.group18project.repository

import android.app.Application
import androidx.lifecycle.LiveData
import ca.unb.mobiledev.group18project.daos.CourseDao
import ca.unb.mobiledev.group18project.db.AppDatabase
import ca.unb.mobiledev.group18project.db.AppDatabase.Companion.getDatabase
import ca.unb.mobiledev.group18project.entities.Courses
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class CourseRepository(application: Application) {
    private val courseDao: CourseDao? = getDatabase(application).courseDao()

    fun insertRecord(name: String?, ch: Int) {
        val course = Courses()
        course.name = name
        course.ch = ch
        AppDatabase.databaseWriterExecutor.execute { courseDao!!.insertCourse(course) }
    }

    fun getAllRecords(): List<Courses> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            courseDao!!.listAllCourses()
        })
        return searchResultFuture.get()
    }

    fun getAllCompletedRecords(): List<Courses> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            courseDao!!.listAllCompletedCourses()
        })
        return searchResultFuture.get()
    }

    fun getAllIncompleteRecords(): List<Courses> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            courseDao!!.listAllIncompleteCourses()
        })
        return searchResultFuture.get()
    }
}