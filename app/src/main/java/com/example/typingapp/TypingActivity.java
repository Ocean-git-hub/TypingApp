package com.example.typingapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

public class TypingActivity extends AppCompatActivity implements TextWatcher {
    private EditText inputEditText;
    private WhetherCorrectSentence whetherCorrectSentence;
    private TypingSE typingSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);
        findAllView();
        inputEditText.addTextChangedListener(this);

        Quest quest = new Quest();
        typingSE = new TypingSE(this);
        typingSE.setSEOfIncorrectAnswer();
        Intent intent = getIntent();
        whetherCorrectSentence = new WhetherCorrectSentence(quest.getQuestionSentences(this,
                intent.getIntExtra("sentenceNumber", 0),
                intent.getIntExtra("characterNumber", 0),
                intent.getStringExtra("genre")), this, typingSE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        typingSE.releaseSoundPool();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void afterTextChanged(Editable s) {
        String s1 = s.toString();
        if (!s1.equals("")) {
            char character = s1.charAt(0);
            if (whetherCorrectSentence.judgeSentence(character)) {
                inputEditText.getEditableText().clear();
            } else {
                Toast.makeText(this, "データベースに単語が登録されていません", Toast.LENGTH_SHORT).show();
                inputEditText.getEditableText().clear();
            }
        }
    }

    private void findAllView() {
        inputEditText = (EditText) findViewById(R.id.inputEditText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

}
