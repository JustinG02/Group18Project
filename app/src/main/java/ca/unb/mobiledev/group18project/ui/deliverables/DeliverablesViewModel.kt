package ca.unb.mobiledev.group18project.ui.deliverables

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ca.unb.mobiledev.group18project.entities.Deliverable
import ca.unb.mobiledev.group18project.repository.DeliverableRepository
import java.util.Date

class DeliverablesViewModel(application: Application) : AndroidViewModel(application){

    private val _text = MutableLiveData<String>().apply {
        value = "This is the Deliverables Section"
    }
    val text: LiveData<String> = _text

    private val deliverableRepository: DeliverableRepository = DeliverableRepository(application)

    val allCourses: LiveData<List<Deliverable>> = deliverableRepository.getAllRecords()

    // Insert a new record
    fun insert(name: String?, courseID: Int) { //, dueDate: Date, weight: Int, info: String
        deliverableRepository.insertRecord(name, courseID) //, dueDate, weight, info
    }

    fun delete(deliverable: Deliverable) {
        deliverableRepository.deleteRecord(deliverable)
    }

    fun update(deliverable: Deliverable) {
        deliverableRepository.updateRecord(deliverable)
    }

    fun getAllIncompleteCourses(): LiveData<List<Deliverable>>? {
        return deliverableRepository.getAllIncompleteRecords()
    }

    fun getAllDeliverablesOfACourse(courseID: Int): LiveData<List<Deliverable>>? {
        return deliverableRepository.getAllDeliverablesOfACourse(courseID)
    }
}