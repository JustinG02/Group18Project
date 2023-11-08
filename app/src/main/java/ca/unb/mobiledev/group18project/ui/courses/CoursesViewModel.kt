package ca.unb.mobiledev.group18project.ui.courses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoursesViewModel : ViewModel(){
    private val _text = MutableLiveData<String>().apply {
        value = "This is the Courses Section"
    }
    val text: LiveData<String> = _text
}