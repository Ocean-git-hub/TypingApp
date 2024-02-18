package com.example.typingapp;

import android.app.Activity;
import android.widget.TextView;

class ResultDisplayView {
    private TextView clearTypeTextView, missTypeTextView, clearSentenceTextView, typeTimeTextView,
            correctAnswerRateTextView, scoreTextView;

    ResultDisplayView(Activity activity) {
        clearSentenceTextView = (TextView) activity.findViewById(R.id.clearSentenceTextView);
        clearTypeTextView = (TextView) activity.findViewById(R.id.clearTypeTextView);
        missTypeTextView = (TextView) activity.findViewById(R.id.missTypeTextView);
        typeTimeTextView = (TextView) activity.findViewById(R.id.typeTimeTextView);
        scoreTextView = (TextView) activity.findViewById(R.id.scoreTextView);
        correctAnswerRateTextView = (TextView) activity.findViewById(R.id.correctAnswerRateTextView);
    }

    void setClearSentenceTextView(String s) {
        clearSentenceTextView.setText(s);
    }

    void setClearTypeTextView(String s) {
        clearTypeTextView.setText(s);
    }

    void setMissTypeTextView(String s) {
        missTypeTextView.setText(s);
    }

    void setTypeTimeTextView(String s) {
        typeTimeTextView.setText(s);
    }

    void setCorrectAnswerRateTextView(String s) {
        correctAnswerRateTextView.setText(s);
    }

    void setScoreTextView(String s) {
        scoreTextView.setText(s);
    }
}
