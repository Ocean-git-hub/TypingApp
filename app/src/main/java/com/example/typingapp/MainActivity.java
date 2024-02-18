package com.example.typingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button startTypingButton, addSentenceButton, watchDatabaseButton;
    EditText inputTimesEditText;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllView();
        buttonAction();
        setSpinner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSpinner();

    }

    private void setSpinner() {
        OperateDatabase operateDatabase = new OperateDatabase(this);
        Cursor cursor = operateDatabase.getSentence();
        ArrayList<String> arrayList = new ArrayList<>();
        final ArrayList<String> spinnerItem = new ArrayList<>();
        spinnerItem.add("指定なし");

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

        Spinner genreSpinner = (Spinner) findViewById(R.id.genreSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genreSpinner.setAdapter(adapter);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner) parent;
                if (spinner.getSelectedItem().equals("指定なし")) {
                    selectedItem = null;
                } else {
                    selectedItem = (String) spinner.getSelectedItem();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findAllView() {
        startTypingButton = (Button) findViewById(R.id.startTypingButton);
        addSentenceButton = (Button) findViewById(R.id.addSentenceButton);
        watchDatabaseButton = (Button) findViewById(R.id.watchDatabaseButton);
        inputTimesEditText = (EditText) findViewById(R.id.inputEditText);
    }

    private void buttonAction() {
        startTypingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TypingActivity.class);
                intent.putExtra("whetherUsers", "users");
                intent.putExtra("sentenceNumber", getTimes());
                intent.putExtra("characterNumber", getCharacterNumber());
                intent.putExtra("genre", selectedItem);
                startActivity(intent);
            }
        });
        addSentenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddUsersSentenceActivity.class));
            }
        });
        watchDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WatchDatabaseActivity.class));
            }
        });
    }

    private int getTimes() {
        EditText inputTimesEditText = (EditText) findViewById(R.id.inputTimesEditText);
        if (inputTimesEditText.getText().toString().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(inputTimesEditText.getText().toString());
        }
    }

    private int getCharacterNumber() {
        EditText inputTimesEditText = (EditText) findViewById(R.id.characterNumberEditText);
        if (inputTimesEditText.getText().toString().equals("")) {
            return 0;
        } else {
            return Integer.parseInt(inputTimesEditText.getText().toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
