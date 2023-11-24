package ca.unb.mobiledev.group18project.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.unb.mobiledev.group18project.entities.Deliverable

@Dao
interface DeliverableDao {
    @Query("SELECT * from deliverables_table ORDER BY delivID ASC")
    fun listAllDeliverables(): LiveData<List<Deliverable>>
    @Query("SELECT * from deliverables_table WHERE completed = 'false' ") //ORDER BY dueDate ASC 
    fun listAllIncompleteDeliverables(): LiveData<List<Deliverable>>

    @Query("SELECT * from deliverables_table WHERE courseID = :courseID ORDER BY name ASC ")
    fun listAllCourseDeliverables(courseID: Int): LiveData<List<Deliverable>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDeliverable(deliverable: Deliverable)

    @Delete
    fun deleteDeliverable(deliverable: Deliverable)

    @Update
    fun updateDeliverable(deliverable: Deliverable)

}