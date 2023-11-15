package ca.unb.mobiledev.group18project.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.unb.mobiledev.group18project.entities.Courses

@Dao
interface CourseDao {

    @Query("SELECT * from courses_table ORDER BY id ASC")
    fun listAllCourses(): LiveData<List<Courses>>

    @Query("SELECT * from courses_table ORDER BY id ASC")
    fun listAllIncompleteCourses(): List<Courses>

    @Query("SELECT * from courses_table ORDER BY id ASC")
    fun listAllCompletedCourses(): List<Courses>

    @Query("SELECT * from courses_table WHERE name = :name ORDER BY id ASC")
    fun findCourseByName(name: String): List<Courses>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Courses)

    @Query("DELETE FROM courses_table WHERE name = :courseName")
    fun deleteCourseByName(courseName: String)

    @Delete
    fun deleteCourse(course: Courses)

    @Update
    fun updateCourse(course: Courses)
}