package de.hochschulestralsund.quizapp.Api;

import java.util.List;

import de.hochschulestralsund.quizapp.Entities.Question;

// Interface to work with response of Questions Service
// Has to implemented and set as parameter when calling Methods of QuestionService
public interface QuestionResponseCallback {
   void onQuestionResponse(List<Question> questionList);

}
