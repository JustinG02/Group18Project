package ca.unb.mobiledev.group18project.repository

import android.app.Application
import ca.unb.mobiledev.group18project.daos.DeliverableDao
import ca.unb.mobiledev.group18project.db.AppDatabase.Companion.getDatabase

class DeliverableRepository(application: Application) {
    private val deliverableDao: DeliverableDao? = getDatabase(application).deliverableDao()

    fun insertRecord() {

    }
}