package com.example.flash_card;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlashCardApp extends Application {

    private List<FlashCard> flashcards;
    private int currentCardIndex;
    private Label questionLabel;
    private Label answerLabel;

    @Override
    public void start(Stage primaryStage) {
        flashcards = new ArrayList<>();
        currentCardIndex = -1;

        // Create UI elements
        Button addButton = new Button("Add Flashcard");
        Button flipButton = new Button("Flip");
        Button nextButton = new Button("Next");
        Button prevButton = new Button("Previous");
        questionLabel = new Label();
        answerLabel = new Label();

        addButton.setOnAction(e -> addFlashcard());
        flipButton.setOnAction(e -> flipFlashcard());
        nextButton.setOnAction(e -> showNextFlashcard());
        prevButton.setOnAction(e -> showPreviousFlashcard());

        HBox controlBox = new HBox(10, addButton, flipButton, prevButton, nextButton);
        controlBox.setAlignment(Pos.CENTER);

        VBox cardBox = new VBox(20, questionLabel, answerLabel);
        cardBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(cardBox);
        root.setBottom(controlBox);
        BorderPane.setMargin(controlBox, new Insets(20));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Flash Card App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addFlashcard() {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setHeaderText("Enter Flashcard Information");
        dialog.setContentText("Question:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(question -> {

            TextInputDialog answerDialog = new TextInputDialog();
            answerDialog.setHeaderText("Enter Flashcard Information");
            answerDialog.setContentText("Answer:");

            Optional<String> answerResult = answerDialog.showAndWait();

            answerResult.ifPresent(answer -> {
                FlashCard flashcard = new FlashCard(question, answer);
                flashcards.add(flashcard);
                currentCardIndex = flashcards.size() - 1;
                showCurrentFlashcard();
            });
        });
    }

    private void flipFlashcard() {
        if (currentCardIndex >= 0 && currentCardIndex < flashcards.size()) {
            FlashCard currentFlashcard = flashcards.get(currentCardIndex);

            if (questionLabel.getText().equals(currentFlashcard.getQuestion())) {

                questionLabel.setText(currentFlashcard.getAnswer());
                answerLabel.setText("");

            } else {

                questionLabel.setText(currentFlashcard.getQuestion());
                answerLabel.setText(currentFlashcard.getAnswer());
            }
        }
    }

    private void showNextFlashcard() {

        if (currentCardIndex < flashcards.size() - 1) {
            currentCardIndex++;
            showCurrentFlashcard();
        }
    }

    private void showPreviousFlashcard() {
        if (currentCardIndex > 0) {
            currentCardIndex--;
            showCurrentFlashcard();
        }
    }

    private void showCurrentFlashcard() {
        FlashCard currentFlashcard = flashcards.get(currentCardIndex);
        questionLabel.setText(currentFlashcard.getQuestion());
        answerLabel.setText(""); // if answer is revealed then it will stay on the question page
    }

    public static void main(String[] args) {
        launch(args);
    }
}

