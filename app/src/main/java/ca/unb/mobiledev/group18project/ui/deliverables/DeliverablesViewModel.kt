package ca.unb.mobiledev.group18project.ui.deliverables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DeliverablesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is the Deliverables Section"
    }
    val text: LiveData<String> = _text
}