package ca.unb.mobiledev.group18project.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.unb.mobiledev.group18project.entities.Courses

@Dao
interface CourseDao {

    @Query("SELECT * from courses_table ORDER BY id ASC")
    fun listAllCourses(): LiveData<List<Courses>>

    @Query("SELECT * from courses_table WHERE name = :name ORDER BY id ASC")
    fun findCourseByName(name: String): List<Courses>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(course: Courses)
}