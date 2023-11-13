package com.cbfacademy.apiassessment.Flashcard;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public class JSONFileHandler {
    public static void writeJSONFile(List<Flashcard> flashcards, String filename) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filename)){
            gson.toJson(flashcards, writer);
            writer.flush();
        } catch (IOException e) {

        }
    }

    public static @NotNull
    @Unmodifiable List<Flashcard> readJSONFile(String filename) {
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        Gson gson = new GsonBuilder().create();

        try(Reader reader = new FileReader(filename)) {
            flashcards = gson.fromJson(reader, new TypeToken<List<Flashcard>>() {
            }.getType());
        } catch (IOException e) {

        }
    return flashcards;
    }


    public static List<Flashcard> getAllFlashcards(String filename) {
        return readJSONFile(filename);
    }


    public static void addFlashcard(Flashcard flashcard, String filename) {
        List<Flashcard> flashcards = getAllFlashcards(filename);
        flashcards.add(flashcard);
        writeJSONFile(flashcards,filename);
    }

    public static void removeFlashcard(UUID id, String filename) {
        List<Flashcard> flashcards = new ArrayList<>(getAllFlashcards(filename));
        // Find flashcards in list
        for (Flashcard flashcard:flashcards) {
            //Remove specified flashcard from list
            if (flashcard.getID()==id) {
                flashcards.remove(flashcard);
            }
        }

        //Overwrite json with updated list
        writeJSONFile(flashcards, "Flashcards.json");
    }

    public static void updateFlashcard(String filename, Flashcard updatedFlashcard) {
        List<Flashcard> flashcards = new ArrayList<>(getAllFlashcards(filename));
        // Find flashcards in list
        for (Flashcard flashcard:flashcards) {
            //Remove specified flashcard from list and add updated version
            if (flashcard.getID()==updatedFlashcard.getID()) {
                flashcards.remove(flashcard);
                flashcards.add(updatedFlashcard);
            }
        }
        //Overwrite json with updated list
        writeJSONFile(flashcards, "Flashcards.json");

    }
            
}
