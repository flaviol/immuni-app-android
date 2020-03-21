package org.ascolto.onlus.geocrowd19.android.models.survey

import java.io.Serializable

//class QuestionAnswer(
//    val questionId: QuestionId,
//    val answers: List<Answer>
//)

sealed class Answer: Serializable

class SimpleAnswer(val index: AnswerIndex) : Answer()

class CompositeAnswer(val componentIndexes: List<AnswerIndex>) : Answer()