package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class QuizId_play extends AppCompatActivity {
    Button findBtn, playBtn;
    EditText codeEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_id_play);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
       findBtn = findViewById(R.id.findQuizBtn);
       playBtn = findViewById(R.id.playBtn);
       codeEt = findViewById(R.id.codeET);

        playBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent= new Intent(QuizId_play.this, MainActivity2.class);
               startActivity(intent);
           }
       });

       findBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String quizId = codeEt.getText().toString().trim();
               if (quizId.isEmpty()) {
                   Toast.makeText(QuizId_play.this, "Enter a valid Quiz ID", Toast.LENGTH_SHORT).show();
                   return;
               }
               Log.d("findbtn", "quizId: " + quizId);
               FirebaseFirestore db = FirebaseFirestore.getInstance();
               Log.d("findbtn","db.collection will be calling");
               db.collection("mcq").document(quizId)
                       .get()
                       .addOnSuccessListener(doc -> {
                           Quiz quiz = doc.toObject(Quiz.class);
                           if (quiz != null) {
                               Intent intent = new Intent(QuizId_play.this, Play_Created_Quiz.class);
                               intent.putExtra("quiz_obj", quiz);
                               Log.d("findbtn","going to play quiz activity with, quiz: "+quiz);
                               startActivity(intent);
                           } else {
                               Toast.makeText(QuizId_play.this, "Quiz not found", Toast.LENGTH_SHORT).show();
                           }
                       })
                       .addOnFailureListener(e -> {
                           Log.e("findbtn", "Failed to fetch quiz", e);
                           Toast.makeText(QuizId_play.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                       });
           }
       });

    }
}