package com.example.quizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

public class Play_Created_Quiz extends AppCompatActivity implements View.OnClickListener {
    TextView question;
    private Button ans1, ans2, ans3, ans4, submit;
    int total_ques;
    int current_ques_index=0;
    int score=0;
    String selected_ans="";
    List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.play_created_quiz_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        question = findViewById(R.id.Questionview);
        ans1 = findViewById(R.id.ans1btn);
        ans2 = findViewById(R.id.ans2btn);
        ans3 = findViewById(R.id.ans3btn);
        ans4 = findViewById(R.id.ans4btn);
        submit = findViewById(R.id.submitbtn);
        submit.setBackgroundColor(Color.GREEN);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);

        Intent intent = getIntent();
        Quiz quiz = (Quiz) intent.getSerializableExtra("quiz_obj");
        Log.d("findbtn","getting intent quiz: "+quiz);
        if (quiz != null && quiz.questions != null && !quiz.questions.isEmpty()) {
            total_ques = quiz.questions.size();
            questionList = quiz.questions;
            SetQuestion(); // start the game
        } else {
            handleMissingIntent();
        }
    }

    @Override
    public void onClick(View v) {
        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);

        Button clikedButton = (Button) v;
        if(clikedButton.getId()== R.id.submitbtn){
            if (selected_ans.equals(questionList.get(current_ques_index).options.get(questionList.get(current_ques_index).correctIndex))) {
                score++;
            }
            current_ques_index++;
            if (current_ques_index < total_ques){
                SetQuestion();
                Log.d("MyCheck", "setquestion() called score: "+score);
            }

            else {
                Log.d("MyCheck", " Last score: "+score);
                Intent intent2 = new Intent(Play_Created_Quiz.this, score_activity.class);
                intent2.putExtra("SCORE",score);
                startActivity(intent2);
            }

        }
        else{
            //answer has been clicked
            selected_ans= clikedButton.getText().toString();
            clikedButton.setBackgroundColor(Color.MAGENTA);
        }

    }

    private void SetQuestion() {
            Question q = questionList.get(current_ques_index);
            question.setText(q.question);
            ans1.setText(q.options.get(0));
            ans2.setText(q.options.get(1));
            ans3.setText(q.options.get(2));
            ans4.setText(q.options.get(3));
    }

    private void handleMissingIntent() {
        question.setText("Something is wrong handleMissingIntent \n has been called");
    }
}