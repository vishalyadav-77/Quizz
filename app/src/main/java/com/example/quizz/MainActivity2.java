package com.example.quizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
     TextView question;
     private Button ans1, ans2, ans3, ans4, submit;
     int total_ques=ques_ans.questions.length;
     int current_ques_index=0;
     int score=0;
     String selected_ans="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        question=findViewById(R.id.Question);
        ans1=findViewById(R.id.ans1);
        ans2=findViewById(R.id.ans2);
        ans3=findViewById(R.id.ans3);
        ans4=findViewById(R.id.ans4);
        submit=findViewById(R.id.submit);
        submit.setBackgroundColor(Color.GREEN);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);

        load_ques();
    }
//--------------------------------------------------------CHOICES HANDLING HERE
    @Override
    public void onClick(View v) {
        ans1.setBackgroundColor(Color.WHITE);
        ans2.setBackgroundColor(Color.WHITE);
        ans3.setBackgroundColor(Color.WHITE);
        ans4.setBackgroundColor(Color.WHITE);

        Button clikedButton = (Button) v;
        if(clikedButton.getId()== R.id.submit){
            if(selected_ans==ques_ans.correctans[current_ques_index]){
                score++;
            }
            current_ques_index++;
            if (current_ques_index < total_ques){
                load_ques();
            }
            else {
                Intent intent = new Intent(MainActivity2.this, score_activity.class);
                intent.putExtra("SCORE",score);
                intent.putExtra("Total_Question",total_ques);
                startActivity(intent);
            }

        }
        else{
            //answer has been clicked
            selected_ans= clikedButton.getText().toString();
            clikedButton.setBackgroundColor(Color.MAGENTA);
        }

    }
    void load_ques(){
        question.setText(ques_ans.questions[current_ques_index]);
        ans1.setText(ques_ans.answers[current_ques_index][0]);
        ans2.setText(ques_ans.answers[current_ques_index][1]);
        ans3.setText(ques_ans.answers[current_ques_index][2]);
        ans4.setText(ques_ans.answers[current_ques_index][3]);
    }

}