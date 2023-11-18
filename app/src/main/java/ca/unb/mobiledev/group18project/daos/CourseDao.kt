package ca.unb.mobiledev.group18project.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.unb.mobiledev.group18project.entities.Course

@Dao
interface CourseDao {

    @Query("SELECT * from courses_table ORDER BY courseID ASC")
    fun listAllCourses(): LiveData<List<Course>>

    @Query("SELECT * from courses_table WHERE completed = 'false' ORDER BY name ASC ")
    fun listAllIncompleteCourses(): LiveData<List<Course>>

    @Query("SELECT * from courses_table WHERE completed = 'true' ORDER BY endDate ASC")
    fun listAllCompletedCourses(): LiveData<List<Course>>

    //No need for this?
    //@Query("SELECT * from courses_table WHERE name = :name ORDER BY name ASC")
    //fun findCourseByName(name: String): List<Courses>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCourse(course: Course)

    //No need for this? also seems like you can have duplicate name so this function proves hazardous
    //@Query("DELETE FROM courses_table WHERE name = :courseName")
    //fun deleteCourseByName(courseName: String)

    @Delete
    fun deleteCourse(course: Course)

    @Update
    fun updateCourse(course: Course)
}