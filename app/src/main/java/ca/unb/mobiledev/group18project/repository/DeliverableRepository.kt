package ca.unb.mobiledev.group18project.repository

import android.app.Application
import androidx.lifecycle.LiveData
import ca.unb.mobiledev.group18project.daos.DeliverableDao
import ca.unb.mobiledev.group18project.db.AppDatabase
import ca.unb.mobiledev.group18project.db.AppDatabase.Companion.getDatabase
import ca.unb.mobiledev.group18project.entities.Deliverable
import java.util.Date
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class DeliverableRepository(application: Application) {
    private val deliverableDao: DeliverableDao? = getDatabase(application).deliverableDao()

    fun insertRecord(name: String?, courseID: Int, dueDate: Date, weight: Int, info: String) {
        val deliverable = Deliverable()
        deliverable.name = name
        deliverable.courseID = courseID
        deliverable.dueDate = dueDate
        deliverable.weight = weight
        deliverable.completed = false
        deliverable.info = info
        AppDatabase.databaseWriterExecutor.execute { deliverableDao!!.insertDeliverable(deliverable) }
    }

    fun updateRecord(deliverable: Deliverable) {
        AppDatabase.databaseWriterExecutor.execute { deliverableDao!!.updateDeliverable(deliverable) }
    }

    fun deleteRecord(deliverable: Deliverable) {
        AppDatabase.databaseWriterExecutor.execute { deliverableDao!!.deleteDeliverable(deliverable) }
    }


    fun getAllRecords(): LiveData<List<Deliverable>> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            deliverableDao!!.listAllDeliverables()
        })
        return searchResultFuture.get()
    }

    fun getAllIncompleteRecords(): LiveData<List<Deliverable>> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            deliverableDao!!.listAllIncompleteDeliverables()
        })
        return searchResultFuture.get()
    }

    fun getAllDeliverablesOfACourse(courseID: Int): LiveData<List<Deliverable>> {
        val searchResultFuture = Executors.newSingleThreadExecutor().submit(Callable {
            deliverableDao!!.listAllCourseDeliverables(courseID)
        })
        return searchResultFuture.get()
    }
}