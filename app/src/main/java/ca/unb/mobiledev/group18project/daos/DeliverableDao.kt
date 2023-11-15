package ca.unb.mobiledev.group18project.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ca.unb.mobiledev.group18project.entities.Deliverables

@Dao
interface DeliverableDao {
//    @Query("SELECT * from deliverables_table ORDER BY delivID ASC")
//    fun listAllDeliverables(): List<Deliverables>
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insertDeliverable(deliverable: Deliverables)
}