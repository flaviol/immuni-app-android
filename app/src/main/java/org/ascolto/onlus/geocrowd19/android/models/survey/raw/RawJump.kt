package org.ascolto.onlus.geocrowd19.android.models.survey.raw

import org.ascolto.onlus.geocrowd19.android.models.survey.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RawJumpItem(
    @field:Json(name = "to") val destination: String,
    @field:Json(name = "condition") val condition: RawCondition
) {
    companion object {
        const val END_OF_SURVEY = "__end__"
    }

    fun jumpItem(): JumpItem {
        val destination =
            if (destination == END_OF_SURVEY) EndOfSurveyJumpDestination()
            else QuestionJumpDestination(destination)

        return JumpItem(
            destination = destination,
            condition = condition.condition()
        )
    }
}
