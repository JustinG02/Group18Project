package ca.unb.mobiledev.group18project.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date

@Entity(tableName = "deliverables_table",
    foreignKeys = [ForeignKey(
    entity = Course::class,
    childColumns = ["courseID"],
    parentColumns = ["courseID"])])
class Deliverable {
    @PrimaryKey(autoGenerate = true)
    var delivID : Int = 0

    var courseID : Int = 0

    var name: String? = ""

//    var dueDate: Date? = null

//    var dueTime: Time? = null

    var completed: Boolean = false

    var weight: Int? = null

    var info: String = ""

}