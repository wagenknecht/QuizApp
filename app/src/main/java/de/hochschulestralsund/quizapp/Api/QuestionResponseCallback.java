package de.hochschulestralsund.quizapp.Api;

import java.util.List;

import de.hochschulestralsund.quizapp.Entities.Question;

public interface QuestionResponseCallback {
    public void onQuestionResponse(List<Question> questionList);

}
