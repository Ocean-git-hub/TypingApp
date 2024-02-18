package com.example.typingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

class WhetherCorrectSentence {
    private int whereReading;
    private int whereSentence;
    private int howReadingLength;
    private int questionSentenceLength;
    private int missType;
    private int clearType;
    private int clearSentence;
    private float endTime;
    private char nowHowReading;
    private String howReading;
    private String sentence;
    private String finishedHowReading;
    private TypingDisplayView typingDisplayView;
    private TypingTimer typingTimer;
    private Activity activity;
    private TypingSE typingSE;
    private SharedPreferences sharedPreferences;
    private QuestionSentence[] questionSentences;


    WhetherCorrectSentence(QuestionSentence[] questionSentences, Activity activity, TypingSE typingSE) {
        this.questionSentences = questionSentences;

        if (questionSentences != null) {
            if (questionSentences.length == 0) {
                questionSentences = null;
            }
        }

        if (questionSentences != null) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

            this.typingSE = typingSE;
            typingTimer = new TypingTimer();
            typingDisplayView = new TypingDisplayView(activity);
            this.activity = activity;
            nowHowReading = questionSentences[whereSentence].getHowReading().charAt(whereReading);
            howReadingLength = questionSentences[whereSentence].getHowReading().length();
            howReading = questionSentences[whereSentence].getHowReading();
            sentence = questionSentences[whereSentence].getSentence();
            questionSentenceLength = questionSentences.length;
            finishedHowReading = "";
            displayView();
        }
    }

    private void displayView() {
        typingDisplayView.setSentenceTextView(sentence);
        typingDisplayView.setHowReadingTextView(howReading);
        typingDisplayView.setNowHowReadingTextView("次は" + String.valueOf(nowHowReading));
        typingDisplayView.setClearSentenceTextView(String.valueOf(clearSentence) + "回正解");
        typingDisplayView.setClearCharacterTextView(String.valueOf(clearType) + "文字正解");
        typingDisplayView.setFinishedHowReadingTextView(finishedHowReading);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    boolean judgeSentence(char s) {
        if (questionSentences != null) {
            if (clearType == 0 && missType == 0) {
                typingTimer.start();
            }
            if (nowHowReading == s) {
                clearType++;
                whereReading++;
                finishedHowReading = finishedHowReading + s;
                displayView();
                if (howReadingLength == whereReading) {
                    whereSentence++;
                    whereReading = 0;
                    finishedHowReading = "";
                    clearSentence++;
                    displayView();
                    if (questionSentenceLength == whereSentence) {
                        endTime = typingTimer.end();
                        finishAction();
                    } else {
                        howReadingLength = questionSentences[whereSentence].getHowReading().length();
                        nowHowReading = questionSentences[whereSentence].getHowReading().charAt(whereReading);
                        sentence = questionSentences[whereSentence].getSentence();
                        howReading = questionSentences[whereSentence].getHowReading();
                        displayView();
                    }
                } else {
                    nowHowReading = questionSentences[whereSentence].getHowReading().charAt(whereReading);
                    displayView();
                }
            } else {
                missType++;
                setIncorrectSE();
            }
            return true;
        } else {
            return false;
        }
    }

    private void finishAction() {
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra("missType", missType);
        intent.putExtra("clearType", clearType);
        intent.putExtra("endTime", endTime);
        intent.putExtra("clearSentence", clearSentence);
        activity.startActivity(intent);
    }

    private void setIncorrectSE() {
        if (sharedPreferences.getBoolean("SEOfIncorrectAnswer", false)) {
            typingSE.playSEOfIncorrectAnswer();
        }
    }
}
