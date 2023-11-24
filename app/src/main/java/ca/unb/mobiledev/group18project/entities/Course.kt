package ca.unb.mobiledev.group18project.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity(tableName = "courses_table")
class Course : Serializable {
    @PrimaryKey(autoGenerate = true)
    var courseID = 0

    var name: String? = null

    var ch : Int = 0

//    var startDate: Date? = null

//    var endDate: Date? = null

    var completed: Boolean = false

    var info: String = ""

}