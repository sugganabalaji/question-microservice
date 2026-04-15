package com.app.service;

import com.app.entity.Question;
import com.app.model.QuestionModel;
import com.app.model.UserResponse;
import com.app.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> findAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> findByCategory(String category) {
        return questionRepository.findByCategoryIgnoreCase(category);
    }

    public Question addQuestion(Question question) {
        if(question == null) {
            throw new IllegalArgumentException("Question cannot be null");
        }
        try {
            return questionRepository.save(question);
        } catch (Exception e) {
            question.setId(null);
            return questionRepository.save(question);
        }
    }

    public boolean deleteQuestion(Long id) {
        boolean isExists = questionRepository.existsById(id);
        if(isExists) {
           questionRepository.deleteById(id);
        }
        return isExists;
    }

    public List<Long> generateQuestionsForQuiz(String category, Long noOfQ) {
        return questionRepository.findRandomQuestionsByCategory(category, noOfQ);
    }

    public List<QuestionModel> getQuestions(List<Long> questionIds) {
        List<Question> questions = questionRepository.findAllById(questionIds);
        return questions.stream()
                .map(question ->
                    new QuestionModel(question.getId(),
                            question.getQuestionTitle(),
                            question.getOption1(),
                            question.getOption2(),
                            question.getOption3(),
                            question.getOption4())
                ).toList();
    }

    public Long calculateScore(List<UserResponse> responses) {
        List<Long> questionIds = responses.stream()
                .map(UserResponse::getQuestionId)
                .toList();
        List<Question> questionsDB = questionRepository.findAllById(questionIds);
        Long score = 0L;
        for(UserResponse response : responses) {
            Question question = questionsDB.stream()
                    .filter(db -> db.getId().equals(response.getQuestionId()))
                    .findFirst().orElse(null);
            if(question != null && response.getUserSelected().equals(question.getRightAnswer())) {
                score++;
            }
        }
        return score;
    }

}
