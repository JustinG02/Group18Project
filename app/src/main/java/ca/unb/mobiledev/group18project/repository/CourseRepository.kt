package ca.unb.mobiledev.group18project.repository

import android.app.Application
import ca.unb.mobiledev.group18project.daos.CourseDao
import ca.unb.mobiledev.group18project.db.AppDatabase.Companion.getDatabase
import ca.unb.mobiledev.group18project.entities.Courses

class CourseRepository(application: Application) {
    private val courseDao: CourseDao? = getDatabase(application).courseDao()

    fun insertRecord(name: String?, ch: Int) {
        val course = Courses()
        course.name = name
        course.ch = ch
        courseDao!!.insertItem(course)
    }
}