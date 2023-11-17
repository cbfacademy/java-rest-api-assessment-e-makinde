package com.cbfacademy.apiassessment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cbfacademy.apiassessment.Flashcard.JSONFileHandler;
import com.cbfacademy.apiassessment.model.Flashcard;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcard")
public class FlashcardController {
    List<Flashcard> flashcards = new ArrayList<>();
    String JSONpath = "src/main/java/com/cbfacademy/apiassessment/Flashcard/userFlashcards/Flashcards.json";


    //Return all questions and answers that have been saved
	@GetMapping("/all")
    public List<Flashcard> getAllFlashcards(Model model) {
        flashcards = JSONFileHandler.readJSONFile(JSONpath);
        return flashcards;
    }

    //Return an individual question using the ID
    @GetMapping("/question/{id}")
    public String getQuestionByID(@PathVariable("id") String id, Model model) {

        flashcards = JSONFileHandler.readJSONFile(JSONpath);

        for (Flashcard flashcard:flashcards) {
            if (flashcard.getID().toString().equals(id)) {
                model.addAttribute("question", flashcard.getFlashcardQuestion());
                return flashcard.getFlashcardQuestion();
            }
        }
        return "No question found";
    
    }

    //Return an individual answer using the ID
    @GetMapping("/answer/{id}")
    public String getAnswerByID(@PathVariable("id") String id, Model model) {
        flashcards = JSONFileHandler.readJSONFile(JSONpath);

        for (Flashcard flashcard:flashcards) {
            if (flashcard.getID().toString().equals(id)) {
                model.addAttribute(flashcard.getFlashcardAnswer());
                return flashcard.getFlashcardAnswer();
            }
        }
        return "No Answer found";

    }

    //Get all questions with a certain difficulty
    @GetMapping("/questions/difficulty/{difficulty}")
    public List<Flashcard> getQuestionsByDifficulty(@PathVariable("difficulty") String difficulty) {
        flashcards = JSONFileHandler.readJSONFile(JSONpath);
        List<Flashcard> sameDifficultyFlashcards = new ArrayList<>();

        for (Flashcard flashcard:flashcards) {
            if (flashcard.getDifficulty().equals(difficulty)) {
                sameDifficultyFlashcards.add(flashcard);
            }
        }
        return sameDifficultyFlashcards;
    }

    //Get all questions within a certain topic
    @GetMapping("/questions/topic/{topic}")
    public List<Flashcard> getQuestionsByTopic(@PathVariable("topic") String topic) {
        flashcards = JSONFileHandler.readJSONFile(JSONpath);
        List<Flashcard> sameTopicFlashcards = new ArrayList<>();

        for (Flashcard flashcard:flashcards) {
            if (flashcard.getTopic().equals(topic)) {
                sameTopicFlashcards.add(flashcard);
            }
        }
        return sameTopicFlashcards;
    }

    @PostMapping(path="/new", produces="application/json")
    public void createFlashcard(@RequestBody Flashcard flashcard) {
        JSONFileHandler.addFlashcard(flashcard, JSONpath);
    }

    @PutMapping(path = "/update/{id}", produces = "application/json")
    public void updateFlashcard(@PathVariable("id") @RequestBody Flashcard flashcard) {
        JSONFileHandler.findFlashcardByID(flashcard.getID().toString(), JSONpath);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteFlashcardByID(@PathVariable("id") String id) {
        UUID flashcardToDeleteID = UUID.fromString(id);
        JSONFileHandler.removeFlashcard(flashcardToDeleteID, JSONpath);
    }

}