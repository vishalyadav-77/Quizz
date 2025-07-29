package com.example.quizz;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class quiz_is_ready extends AppCompatActivity {
    TextView quizReady;
    Button play, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            FirebaseApp.initializeApp(this);
            Log.d("share_log", "Firebase initialized");
        } catch (Exception e) {
            Log.e("share_log", "Firebase init failed", e);
        }
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
        TextView quizCodeText = findViewById(R.id.quizCodeText);
        Button copyCodeBtn = findViewById(R.id.copyCodeBtn);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Question> questionList = new ArrayList<>();
                for (int i = 0; i < Total_Q; i++) {
                    List<String> opts = new ArrayList<>();
                    opts.add(array1D[i * cols]);
                    opts.add(array1D[i * cols + 1]);
                    opts.add(array1D[i * cols + 2]);
                    opts.add(array1D[i * cols + 3]);

                    int correctIndex = opts.indexOf(CorrectAnswers[i]);
                    questionList.add(new Question(Questions[i], opts, correctIndex));
                }

                Quiz quiz = new Quiz("Local Quiz", "Local User", questionList);

                Intent intent = new Intent(quiz_is_ready.this, Play_Created_Quiz.class);
                intent.putExtra("quiz_obj", quiz);
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an AlertDialog with two input fields
                AlertDialog.Builder builder = new AlertDialog.Builder(quiz_is_ready.this);
                builder.setTitle("Enter Quiz Info");

                // Layout with two EditTexts
                LinearLayout layout = new LinearLayout(quiz_is_ready.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(50, 40, 50, 10);

                final EditText inputTitle = new EditText(quiz_is_ready.this);
                inputTitle.setHint("Quiz Title");
                layout.addView(inputTitle);

                final EditText inputCreator = new EditText(quiz_is_ready.this);
                inputCreator.setHint("Created By");
                layout.addView(inputCreator);

                builder.setView(layout);

                builder.setPositiveButton("Upload & Share", (dialog, which) -> {
                    try {
                        String quizTitle = inputTitle.getText().toString().trim();
                        String creatorName = inputCreator.getText().toString().trim();

                        Log.d("share_log", "Quiz Title input: " + quizTitle);
                        Log.d("share_log", "Creator Name input: " + creatorName);
                        Log.d("share_log", "Total_Q: " + Total_Q);
                        Log.d("share_log", "Questions: " + (Questions != null ? Questions.length : "null"));
                        Log.d("share_log", "array1D: " + (array1D != null ? array1D.length : "null"));
                        Log.d("share_log", "CorrectAnswers: " + (CorrectAnswers != null ? CorrectAnswers.length : "null"));
                        Log.d("share_log", "rows: " + rows + ", cols: " + cols);

                        if (quizTitle.isEmpty() || creatorName.isEmpty()) {
                            Toast.makeText(quiz_is_ready.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                            Log.e("share_log", "One or more fields are empty");
                            return;
                        }

                        // === Firestore Logic ===
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        // Convert arrays into list of Question objects
                        List<Question> questionList = new ArrayList<>();
                        for (int i = 0; i < Total_Q; i++) {
                            try {
                                String ques = Questions[i];
                                List<String> opts = new ArrayList<>();
                                opts.add(array1D[i * cols]);
                                opts.add(array1D[i * cols + 1]);
                                opts.add(array1D[i * cols + 2]);
                                opts.add(array1D[i * cols + 3]);

                                int correctIndex = opts.indexOf(CorrectAnswers[i]);
                                Log.d("share_log", "Q" + i + ": " + ques + ", opts: " + opts + ", correct: " + CorrectAnswers[i] + ", correctIndex: " + correctIndex);
                                questionList.add(new Question(ques, opts, correctIndex));
                            } catch (Exception e) {
                                Log.e("share_log", "Exception while building Question object at i=" + i, e);
                            }
                        }
                        Log.d("share_log", "questionList size: " + questionList.size());
                        for (int i = 0; i < questionList.size(); i++) {
                            Question q = questionList.get(i);
                            Log.d("share_log", "Question[" + i + "]: " + q.question + ", options: " + q.options + ", correctIndex: " + q.correctIndex);
                        }
                        Log.d("share_log", "Title: " + quizTitle + ", Creator: " + creatorName);
                        Log.d("share_log","data converted now push starting..");

                        Quiz quiz = new Quiz(quizTitle, creatorName, questionList);
                        Log.d("share_log", "Quiz object: title=" + quiz.title + ", createdBy=" + quiz.createdBy + ", questions.size=" + (quiz.questions != null ? quiz.questions.size() : "null"));

                        db.collection("mcq")
                                .add(quiz)
                                .addOnSuccessListener(documentReference -> {
                                    String quizId = documentReference.getId();
                                    Log.d("share_log", "Upload success! Quiz ID: " + quizId);
                                    // Show quiz ID in TextView
                                    quizCodeText.setText("Quiz Code: " + quizId);
                                    quizCodeText.setVisibility(View.VISIBLE);
                                    copyCodeBtn.setVisibility(View.VISIBLE);

                                    // Copy button logic
                                    copyCodeBtn.setOnClickListener(copyView -> {
                                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("Quiz ID", quizId);
                                        clipboard.setPrimaryClip(clip);
                                        Toast.makeText(quiz_is_ready.this, "Quiz code copied!", Toast.LENGTH_SHORT).show();
                                    });

                                })
                                .addOnFailureListener(e -> {
                                    Log.e("share_log", "Upload failed: ", e);
                                    Toast.makeText(quiz_is_ready.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                });
                    } catch (Exception e) {
                        Log.e("share_log", "Exception in Upload & Share: ", e);
                        Toast.makeText(quiz_is_ready.this, "Something went wrong: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

                builder.show();

            }
        });


    }
    @Override
    public void onBackPressed() {
        Intent intentBack = new Intent(quiz_is_ready.this, MainActivity.class);
        startActivity(intentBack);
        finish(); // close current activity so it doesn't remain in the back stack
    }
}