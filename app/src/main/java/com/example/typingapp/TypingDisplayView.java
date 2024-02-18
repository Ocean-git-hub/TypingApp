package com.example.typingapp;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

class TypingDisplayView {
    private TextView sentenceTextView, howReadingTextView, nowHowReadingTextView, finishedHowReadingTextView,
            clearSentenceTextView, clearCharacterTextView;

    TypingDisplayView(Activity activity) {
        sentenceTextView = (TextView) activity.findViewById(R.id.sentenceTextView);
        howReadingTextView = (TextView) activity.findViewById(R.id.howReadingTextView);
        nowHowReadingTextView = (TextView) activity.findViewById(R.id.nowHowReadingTextView);
        finishedHowReadingTextView = (TextView) activity.findViewById(R.id.finishedHowReadingTextView);
        clearSentenceTextView = (TextView) activity.findViewById(R.id.clearSentenceTextView);
        clearCharacterTextView = (TextView) activity.findViewById(R.id.clearCharacterTextView);
        sentenceTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        howReadingTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        nowHowReadingTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    void setSentenceTextView(String s) {
        sentenceTextView.setText(s);
    }

    void setHowReadingTextView(String s) {
        howReadingTextView.setText(s);
    }

    void setNowHowReadingTextView(String s) {
        nowHowReadingTextView.setText(s);
    }

    void setFinishedHowReadingTextView(String s) {
        finishedHowReadingTextView.setText(s);
    }

    void setClearCharacterTextView(String s) {
        clearCharacterTextView.setText(s);
    }

    void setClearSentenceTextView(String s) {
        clearSentenceTextView.setText(s);
    }
}
