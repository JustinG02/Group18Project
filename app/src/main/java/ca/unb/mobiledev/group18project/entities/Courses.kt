package ca.unb.mobiledev.group18project.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses_table")
class Courses {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var name: String? = null
    var gpa = 0
    var ch = 0
    var isComplete : Boolean = false
}