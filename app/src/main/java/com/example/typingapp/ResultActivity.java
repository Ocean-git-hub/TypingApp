package com.example.typingapp;

import android.content.Intent;
import android.icu.math.BigDecimal;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setResultView();
        Button mainActivityButton = (Button) findViewById(R.id.mainActivityButton);
        mainActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setResultView() {
        Intent intent = getIntent();
        float correctAnswerRate = calculateCorrectAnswerRate(intent.getIntExtra("clearType", 0), intent.getIntExtra("missType", 0));

        ResultDisplayView resultDisplayView = new ResultDisplayView(this);
        resultDisplayView.setClearSentenceTextView(String.valueOf("入力した文:" + intent.getIntExtra("clearSentence", 0)) + "文");
        resultDisplayView.setClearTypeTextView("入力した文字:" + String.valueOf(intent.getIntExtra("clearType", 0)) + "文字");
        resultDisplayView.setCorrectAnswerRateTextView("正解率:" + String.valueOf(correctAnswerRate) + "%");
        resultDisplayView.setMissTypeTextView("間違えた回数:" + String.valueOf(intent.getIntExtra("missType", 0)) + "回");
        resultDisplayView.setScoreTextView("スコア:" + String.valueOf(calculateScore(intent.getIntExtra("clearType", 0), intent.getFloatExtra("endTime", 0), correctAnswerRate)) + "点");
        resultDisplayView.setTypeTimeTextView("経過時間:" + String.valueOf(intent.getFloatExtra("endTime", 0)) + "秒");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private float calculateCorrectAnswerRate(int clearType, int missType) {
        BigDecimal correctType = new BigDecimal(clearType * 100);
        BigDecimal allType = new BigDecimal(clearType + missType);
        BigDecimal result = correctType.divide(allType, 2, BigDecimal.ROUND_HALF_UP);
        return result.floatValue();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private int calculateScore(int clearType, float endTime, float correctAnswerRate) {
        BigDecimal allType = new BigDecimal(clearType * 60);
        BigDecimal minuteTime = new BigDecimal(endTime);
        BigDecimal WPM = allType.divide(minuteTime, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal correctAnswerRate1 = new BigDecimal(correctAnswerRate / 100);
        BigDecimal originScore = WPM.multiply(correctAnswerRate1);
        BigDecimal score = originScore.setScale(0, BigDecimal.ROUND_HALF_UP);
        return score.intValue();
    }
}
