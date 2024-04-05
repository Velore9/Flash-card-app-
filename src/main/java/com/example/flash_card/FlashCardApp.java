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

    private List<FlashCard> flashcards; // create a list based on the values of the class
    private int currentCardIndex; // identifier for which flash card to show
    private Label questionLabel;
    private Label answerLabel;

    @Override
    public void start(Stage primaryStage) {
        flashcards = new ArrayList<>(); // an instance of the list based
        currentCardIndex = -1; // since 0 would mean there are 1 current instance of question but since there are none it has to be less than 0 to start with

        // Create UI elements
        Button addButton = new Button("Add Flashcard");
        Button flipButton = new Button("Flip");
        Button nextButton = new Button("Next");
        Button prevButton = new Button("Previous");
        questionLabel = new Label();
        answerLabel = new Label();

        // set event on what each button does
        addButton.setOnAction(e -> addFlashcard());
        flipButton.setOnAction(e -> flipFlashcard());
        nextButton.setOnAction(e -> showNextFlashcard());
        prevButton.setOnAction(e -> showPreviousFlashcard());

        // add buttons to the menu
        HBox controlBox = new HBox(10, addButton, flipButton, prevButton, nextButton);
        controlBox.setAlignment(Pos.CENTER);

        // set the labels on a vertical box
        VBox cardBox = new VBox(20, questionLabel, answerLabel);
        cardBox.setAlignment(Pos.CENTER);

        // borders with doesnt take up whole screen
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

        // asically console readline of c#
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(question -> { // make the result value equal to question

            TextInputDialog answerDialog = new TextInputDialog();
            answerDialog.setHeaderText("Enter Flashcard Information");
            answerDialog.setContentText("Answer:");

            Optional<String> answerResult = answerDialog.showAndWait();

            answerResult.ifPresent(answer -> { // make answerResult value equal to the answer
                FlashCard flashcard = new FlashCard(question, answer); // for each instance of the class flashcard
                flashcards.add(flashcard); // add that new instance of class to the list

                // takes your view on the newly created flashcard regardless of where you currently are
                currentCardIndex = flashcards.size() - 1;
                showCurrentFlashcard();
            });
        });
    }

    private void flipFlashcard() {
        if (currentCardIndex >= 0 && currentCardIndex < flashcards.size()) {
            FlashCard currentFlashcard = flashcards.get(currentCardIndex);

            if (questionLabel.isVisible()) { // if the question is visible or not. If it is then make it invisible and change it to the answer
                questionLabel.setVisible(false);
                answerLabel.setText(currentFlashcard.getAnswer());
                answerLabel.setVisible(true);

            } else { // if not visibble change it to visible
                questionLabel.setVisible(true);
                answerLabel.setVisible(false);
                answerLabel.setText("");
            }
        }
    }

    private void showNextFlashcard() {

        if (currentCardIndex < flashcards.size() - 1) { // if currentcard is not greater than amount of flashcards then go next or else it wont do anything
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
        FlashCard currentFlashcard = flashcards.get(currentCardIndex); // get the current question with the identifier of the card index
        questionLabel.setText(currentFlashcard.getQuestion());
        answerLabel.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

