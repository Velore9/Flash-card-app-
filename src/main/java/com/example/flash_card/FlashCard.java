package com.example.flash_card;

public class FlashCard {
    private String question;
    private String answer;

    public FlashCard(String question, String answer) { // construct
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
