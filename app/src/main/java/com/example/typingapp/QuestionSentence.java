package com.example.typingapp;

class QuestionSentence {
    private String sentence;
    private String howReading;

    void setQuestionSentence(String sentence, String howReading) {
        this.sentence = sentence;
        this.howReading = howReading;
    }

    void setQuestionSentence(QuestionSentence questionSentence) {
        sentence = questionSentence.getSentence();
        howReading = questionSentence.getHowReading();
    }

    String getSentence() {
        return sentence;
    }

    String getHowReading() {
        return howReading;
    }
}
