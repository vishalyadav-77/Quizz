package com.example.quizz;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class score_activity extends AppCompatActivity {
    TextView result;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        result=findViewById(R.id.result);
        mediaPlayer= MediaPlayer.create(this,R.raw.win);
        mediaPlayer.start();
        Intent intent = getIntent();
        int score2 = intent.getIntExtra("SCORE",0);
        int totalQ = intent.getIntExtra("Total_Question",0);
        String r= String.valueOf(score2);
        String t= String.valueOf(totalQ);
        result.setText(r+"/"+t);
    }
    @Override
    public void onBackPressed() {//IF THE BACK_BUTTON IS PRESSED GO TO MAIN PAGE
        // Do nothing
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Release the MediaPlayer when the activity is destroyed
            mediaPlayer = null;
        }
    }

}