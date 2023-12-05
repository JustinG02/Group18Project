package ca.unb.mobiledev.group18project

import ca.unb.mobiledev.group18project.entities.Deliverable

class GradeCalculations {

    //Percent complete: add all weight percentages for deliverables with grades entered(not null grades)
    //
    //Running grade: percent weight * grade entered  + running grade so far
    //
    //Current grade: running grade/ percent complete

    fun calculatePercentComplete(deliverables: List<Deliverable>): Int {
        return deliverables.filter { it.grade != null }
            .sumOf { it.weight!! }
    }

    fun calculateRunningGrade(deliverables: List<Deliverable>): Int {
        return deliverables.filter { it.grade != null }
            .sumOf { it.weight!! * it.grade!! }
    }

    fun calculateCurrentGrade(deliverables: List<Deliverable>): Int {
        val percentComplete = calculatePercentComplete(deliverables)
        return if (percentComplete > 0) {
            calculateRunningGrade(deliverables) / percentComplete
        } else {
            0  // or handle this case as needed
        }
    }


}