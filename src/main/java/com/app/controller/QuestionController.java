package com.app.controller;

import com.app.entity.Question;
import com.app.model.QuestionModel;
import com.app.model.UserResponse;
import com.app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAllQuestions());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestions(@PathVariable String category) {
        return ResponseEntity.ok(questionService.findByCategory(category));
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        if(question == null) {
            return ResponseEntity.badRequest().body("Question cannot be null");
        }
        Question savedQuestion = questionService.addQuestion(question);
        if(savedQuestion == null) {
            return ResponseEntity.badRequest().body("Failed to add question");
        }
        return new ResponseEntity<>("New Question with Id: " + savedQuestion.getId() + " added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        if(question == null) {
            return ResponseEntity.badRequest().body("Question cannot be null");
        }
        Question updatedQuestion = questionService.addQuestion(question);
        if(updatedQuestion == null) {
            return ResponseEntity.badRequest().body("Failed to update question");
        }
        return new ResponseEntity<>("Question " + updatedQuestion.getId() + " updated successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Long id) {
        boolean deleted = questionService.deleteQuestion(id);
        if(!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Question " + id + " deleted successfully");
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Long>> generateQuestionsForQuiz(@RequestParam String category,
                                                               @RequestParam Long noOfQ) {
        List<Long> questionIds = questionService.generateQuestionsForQuiz(category, noOfQ);
        if(questionIds == null || questionIds.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(questionIds);
    }

    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionModel>> getQuestions(@RequestBody List<Long> questionIds) {
        return ResponseEntity.ok(questionService.getQuestions(questionIds));
    }

    @PostMapping("/checkScore")
    public ResponseEntity<Long> getScore(@RequestBody List<UserResponse> responses) {
        Long score = questionService.calculateScore(responses);
        return ResponseEntity.ok(score);
    }


}
