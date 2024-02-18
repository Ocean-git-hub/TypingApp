package com.example.typingapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.DatabaseUtils;

import java.util.ArrayList;
import java.util.Collections;

class Quest {
    QuestionSentence[] getQuestionSentences(Activity activity, int sentenceNumber, int howReadingCharacterNumber, String genre) {
        OperateDatabase operateDatabase = new OperateDatabase(activity);
        long allSentenceNumber = DatabaseUtils.queryNumEntries(operateDatabase.getDatabase(), "sentence");

        if (genre != null) {
            if (genre.isEmpty()) {
                genre = null;
            }
        }

        if (allSentenceNumber != 0) {
            if (genre == null) {
                if (allSentenceNumber < sentenceNumber || sentenceNumber == 0) {
                    sentenceNumber = (int) allSentenceNumber;
                }
                QuestionSentence[] questionSentences = new QuestionSentence[sentenceNumber];
                if (howReadingCharacterNumber == 0) {
                    ArrayList allSentenceList = getShuffleList(operateDatabase.getSentence());

                    for (int i = 0; i < sentenceNumber; i++)
                        questionSentences[i] = new QuestionSentence();
                    for (int i = 0; i < sentenceNumber; i++)
                        questionSentences[i].setQuestionSentence((QuestionSentence) allSentenceList.get(i));
                    return questionSentences;
                } else if (howReadingCharacterNumber != 0) {
                    ArrayList CNSentenceList = getShuffleList(operateDatabase.getSentence(howReadingCharacterNumber));
                    if (CNSentenceList.size() != 0) {
                        if (CNSentenceList.size() < sentenceNumber) {
                            sentenceNumber = CNSentenceList.size();
                        }
                        for (int i = 0; i < sentenceNumber; i++)
                            questionSentences[i] = new QuestionSentence();
                        for (int i = 0; i < sentenceNumber; i++)
                            questionSentences[i].setQuestionSentence((QuestionSentence) CNSentenceList.get(i));
                        return questionSentences;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                ArrayList genreAllSentenceList = getShuffleList(operateDatabase.getSentence(genre));

                if (allSentenceNumber < sentenceNumber || sentenceNumber == 0) {
                    sentenceNumber = (int) allSentenceNumber;
                }
                if (genreAllSentenceList.size() < sentenceNumber) {
                    sentenceNumber = genreAllSentenceList.size();
                }
                QuestionSentence[] questionSentences = new QuestionSentence[sentenceNumber];
                if (howReadingCharacterNumber == 0) {
                    for (int i = 0; i < sentenceNumber; i++)
                        questionSentences[i] = new QuestionSentence();
                    for (int i = 0; i < sentenceNumber; i++)
                        questionSentences[i].setQuestionSentence((QuestionSentence) genreAllSentenceList.get(i));
                    return questionSentences;
                } else if (howReadingCharacterNumber != 0) {
                    ArrayList GCNSentenceList = getShuffleList(operateDatabase.getSentence(genre, howReadingCharacterNumber));
                    if (GCNSentenceList.size() != 0) {
                        for (int i = 0; i < sentenceNumber; i++)
                            questionSentences[i] = new QuestionSentence();
                        for (int i = 0; i < sentenceNumber; i++)
                            questionSentences[i].setQuestionSentence((QuestionSentence) GCNSentenceList.get(i));
                        return questionSentences;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    private ArrayList getShuffleList(Cursor cursor) {
        ArrayList<QuestionSentence> usersSentence = new ArrayList<>();
        boolean mov = cursor.moveToFirst();
        while (mov) {
            QuestionSentence questionSentence = new QuestionSentence();
            questionSentence.setQuestionSentence(cursor.getString(0), cursor.getString(1));
            usersSentence.add(questionSentence);
            mov = cursor.moveToNext();
        }
        cursor.close();
        Collections.shuffle(usersSentence);
        return usersSentence;
    }
}
