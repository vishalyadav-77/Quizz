package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class quiz_is_ready extends AppCompatActivity {
    TextView quizReady;
    Button play, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quiz_is_ready);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        quizReady=findViewById(R.id.textView3);
        play=findViewById(R.id.play);
        share=findViewById(R.id.share);
        //-------------------------------------------getting intents
        Intent intent= getIntent();
        int Total_Q= intent.getIntExtra("Total_q",0);
        String[] Questions=intent.getStringArrayExtra("Questions");
        int rows= intent.getIntExtra("rows",0);
        int cols=intent.getIntExtra("cols",0);
        String[] array1D = intent.getStringArrayExtra("array1D");
        String[] CorrectAnswers=intent.getStringArrayExtra("CorrectAns");

//        quizReady.setText(String.valueOf(CorrectAnswers[0]));
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent arr_intent= new Intent(quiz_is_ready.this, Play_Created_Quiz.class);
                arr_intent.putExtra("array1D",array1D);
                arr_intent.putExtra("rows",rows);
                arr_intent.putExtra("cols",cols);
                arr_intent.putExtra("Questions",Questions);
                arr_intent.putExtra("Total_q",Total_Q);
                arr_intent.putExtra("CorrectAns",CorrectAnswers);
                startActivity(arr_intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(quiz_is_ready.this, "Share nahi chalta abhi", Toast.LENGTH_SHORT).show();
            }
        });
    }
}