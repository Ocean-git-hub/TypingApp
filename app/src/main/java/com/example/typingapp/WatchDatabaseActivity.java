package com.example.typingapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class WatchDatabaseActivity extends AppCompatActivity {
    private TextView selectedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_databese);
        setRegisteredSentences();
    }

    @SuppressLint("SetTextI18n")
    private void setRegisteredSentences() {
        OperateDatabase operateDatabase = new OperateDatabase(this);
        if (DatabaseUtils.queryNumEntries(operateDatabase.getDatabase(), "sentence") == 0) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            setContentView(linearLayout);

            TextView textView = new TextView(this);
            textView.setText("Nothing to show");
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            linearLayout.addView(textView,
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        } else {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            ScrollView scrollView = new ScrollView(this);
            scrollView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            setContentView(scrollView);

            Cursor cursor = operateDatabase.showDatabase();
            boolean mov = cursor.moveToFirst();
            while (mov) {
                TextView textView = new TextView(this);
                registerForContextMenu(textView);
                textView.setText(String.format("%s:%s", cursor.getString(0), cursor.getString(1)));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                linearLayout.addView(textView);
                mov = cursor.moveToNext();
            }
            cursor.close();
            operateDatabase.closeDatabase();
            scrollView.addView(linearLayout);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        TextView textView = (TextView) v;
        String selectedSentence = textView.getText().toString();

        OperateDatabase operateDatabase = new OperateDatabase(this);
        String selectedGenre = operateDatabase.getGenre(deleteHowReading(selectedSentence));

        menu.setHeaderTitle(selectedSentence);
        menu.add(0, 1, 0, "削除");
        menu.add(0, 2, 0, "読み方変更");
        menu.add(0, 3, 0, "文変更");
        menu.add(0, 5, 0, "ジャンル変更(" + selectedGenre + ")");
        menu.add(0, 4, 0, "すべて削除");
        selectedView = textView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                OperateDatabase operateDatabase = new OperateDatabase(this);

                int id = operateDatabase.deleteSentence(deleteHowReading(selectedView.getText().toString()));

                if (id == 0 || id == -1) {
                    Toast.makeText(this, "削除失敗", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "削除成功", Toast.LENGTH_SHORT).show();
                    Intent intent = getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                }
                return true;
            case 2:
                final EditText editText = new EditText(this);
                new AlertDialog.Builder(this).setTitle("変更する値")
                        .setView(editText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!editText.getText().toString().equals("")) {
                                    Pattern pattern = Pattern.compile("^[a-z-]+$");
                                    if (pattern.matcher(editText.getText().toString()).find()) {
                                        OperateDatabase operateDatabase = new OperateDatabase(getApplicationContext());
                                        int rowId = operateDatabase.updateHowReading(editText.getText().toString(), deleteHowReading(selectedView.getText().toString()));
                                        if (rowId == 0 || rowId == -1) {
                                            makeToast("変更失敗");
                                        } else {
                                            makeToast("変更成功");
                                            selectedView.setText(deleteHowReading(selectedView.getText().toString()) + ":" + editText.getText().toString());
                                        }
                                    } else {
                                        makeToast("追加できない文字が含まれています");
                                    }
                                } else {
                                    makeToast("空白は追加できません");
                                }
                            }
                        })
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return true;
            case 3:
                final EditText editText1 = new EditText(this);
                new AlertDialog.Builder(this).setTitle("変更する値")
                        .setView(editText1)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OperateDatabase operateDatabase = new OperateDatabase(getApplicationContext());
                                int rowId = operateDatabase.updateSentence(editText1.getText().toString(), deleteHowReading(selectedView.getText().toString()));
                                if (rowId == 0 || rowId == -1) {
                                    makeToast("変更失敗");
                                } else {
                                    makeToast("変更成功");
                                    selectedView.setText(editText1.getText().toString() + ":" + deleteHowReading(selectedView.getText().toString()));
                                }
                            }
                        })
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return true;
            case 4:
                new AlertDialog.Builder(this).setTitle("本当に削除しますか？")
                        .setPositiveButton("はい", new DialogInterface.OnClickListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OperateDatabase operateDatabase = new OperateDatabase(getApplicationContext());

                                int id = operateDatabase.deleteAllSentence();

                                if (id == -1) {
                                    makeToast("削除失敗");
                                } else {
                                    makeToast("削除成功");

                                    LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                                    linearLayout.setGravity(Gravity.CENTER);
                                    setContentView(linearLayout);

                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setText("Nothing to show");
                                    textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                                    linearLayout.addView(textView,
                                            new LinearLayout.LayoutParams(
                                                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                                }
                            }
                        })
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return true;
            case 5:
                final EditText editText2 = new EditText(this);
                new AlertDialog.Builder(this).setTitle("変更する値")
                        .setView(editText2)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OperateDatabase operateDatabase = new OperateDatabase(getApplicationContext());
                                if (editText2.getText().toString().equals("")) {
                                    int rowId = operateDatabase.updateGenre(null, deleteHowReading(selectedView.getText().toString()));
                                    if (rowId == 0 || rowId == -1) {
                                        makeToast("変更失敗");
                                    } else {
                                        makeToast("変更成功");
                                    }
                                } else {
                                    int rowId = operateDatabase.updateGenre(editText2.getText().toString(), deleteHowReading(selectedView.getText().toString()));
                                    if (rowId == 0 || rowId == -1) {
                                        makeToast("変更失敗");
                                    } else {
                                        makeToast("変更成功");
                                    }
                                }
                            }
                        })
                        .setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private String deleteHowReading(String originS) {
        StringBuilder origin = new StringBuilder(originS);
        origin.delete(originS.indexOf(":"), originS.length());
        return origin.toString();
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
