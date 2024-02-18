package com.example.typingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class AddUsersSentenceActivity extends AppCompatActivity {
    EditText sentenceEditText, howReadingEditText, selectedGenreEditText;
    String selectedGenre;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users_sentence);

        findAllView();
        buttonAction();
        setSpinner();
    }


    @Override
    protected void onResume() {
        super.onResume();
        selectedGenre = null;
        setSpinner();
    }

    private void setSpinner() {
        OperateDatabase operateDatabase = new OperateDatabase(this);
        Cursor cursor = operateDatabase.getSentence();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> spinnerItem = new ArrayList<>();

        if (selectedGenre == null) {
            spinnerItem.add("指定なし");
        } else if (selectedGenre.equals("")) {
            spinnerItem.add("指定なし");
        } else {
            spinnerItem.add(selectedGenre);
            spinnerItem.add("指定なし");
        }

        boolean mov = cursor.moveToFirst();
        while (mov) {
            arrayList.add(cursor.getString(3));
            mov = cursor.moveToNext();
        }
        cursor.close();

        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) != null) {
                boolean tmp = true;
                for (int j = 0; j < spinnerItem.size(); j++) {
                    if (spinnerItem.get(j).equals(arrayList.get(i))) {
                        tmp = false;
                    }
                }
                if (tmp) {
                    spinnerItem.add(arrayList.get(i));
                }
            }
        }

        Spinner genreSpinner = (Spinner) findViewById(R.id.addGenreSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genreSpinner.setAdapter(adapter);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (spinner.getSelectedItem().equals("指定なし")) {
                    selectedGenre = null;
                } else {
                    selectedGenre = (String) spinner.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addSentence(String sentence, String howReading, String genre) {
        Pattern pattern = Pattern.compile("^[a-z-]+$");

        if (!sentence.equals("") && !howReading.equals("")) {
            errorView(6);
            if (pattern.matcher(howReading).find()) {
                errorView(4);
                OperateDatabase operateDatabase = new OperateDatabase(this);
                if (!operateDatabase.searchSentence(sentence)) {
                    if (operateDatabase.addSentence(sentence, howReading, genre) == -1) {
                        makeToast("追加失敗");
                    } else {
                        makeToast("追加成功");
                        sentenceEditText.getEditableText().clear();
                        howReadingEditText.getEditableText().clear();
                        selectedGenreEditText.setText("");
                        selectedGenreEditText.getEditableText().clear();
                        setSpinner();
                    }
                } else {
                    makeToast("登録済みです");
                }
            } else {
                errorView(3);
            }
        } else if (sentence.equals("")) {
            errorView(1);
            if (pattern.matcher(howReading).find()) {
                errorView(4);
            }
        } else {
            errorView(2);
            errorView(5);
        }
    }

    private void errorView(int errorId) {
        TextView errorHowReading = (TextView) findViewById(R.id.ErrorHowReadingTextView);
        TextView errorSentence = (TextView) findViewById(R.id.ErrorSentenceTextView);
        switch (errorId) {
            case 1:
                errorSentence.setText("空白は追加できません");
                break;
            case 2:
                errorHowReading.setText("空白は追加できません");
                break;
            case 3:
                errorHowReading.setText("追加できない文字が含まれています");
                break;
            case 4:
                errorHowReading.setText("");
                break;
            case 5:
                errorSentence.setText("");
                break;
            case 6:
                if (errorHowReading.getText().equals("空白は追加できません")) {
                    errorHowReading.setText("");
                }
                if (errorSentence.getText().equals("空白は追加できません")) {
                    errorSentence.setText("");
                }
        }
    }

    private void buttonAction() {
        Button addButton = (Button) findViewById(R.id.addSentenceButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGenre == null) {
                    selectedGenre = selectedGenreEditText.getText().toString();
                } else if (selectedGenreEditText.getText() != null) {
                    if (!selectedGenreEditText.getText().toString().equals("")){
                        selectedGenre = selectedGenreEditText.getText().toString();
                    }
                }

                addSentence(sentenceEditText.getText().toString(), howReadingEditText.getText().toString(),
                        selectedGenre);
            }
        });
        Button watchDatabaseButton = (Button) findViewById(R.id.watchDatabaseButton);
        watchDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddUsersSentenceActivity.this, WatchDatabaseActivity.class));
            }
        });
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void findAllView() {
        sentenceEditText = (EditText) findViewById(R.id.addSentenceEditText);
        howReadingEditText = (EditText) findViewById(R.id.addHowReadingEditText);
        selectedGenreEditText = (EditText) findViewById(R.id.genreEditText);
    }
}

