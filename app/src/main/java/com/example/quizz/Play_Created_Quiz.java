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

public class Play_Created_Quiz extends AppCompatActivity implements View.OnClickListener {
    TextView question;
    private Button ans1, ans2, ans3, ans4, submit;
    int total_ques;
    int current_ques_index=0;
    int score=0;
    String selected_ans="";
    //--------------------------------Handling intent for quiz_is_ready activity
    public static final String KEY1="array1D";
    public static final String KEY2="rows";
    public static final String KEY3="cols";
    private String[] Questions2;
//    private String[] CorrectAns;
    private String[] CorrectAns;
    private String[][] Options2;


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
        question=findViewById(R.id.Questionview);
        ans1=findViewById(R.id.ans1btn);
        ans2=findViewById(R.id.ans2btn);
        ans3=findViewById(R.id.ans3btn);
        ans4=findViewById(R.id.ans4btn);
        submit=findViewById(R.id.submitbtn);
        submit.setBackgroundColor(Color.GREEN);

        ans1.setOnClickListener(this);
        ans2.setOnClickListener(this);
        ans3.setOnClickListener(this);
        ans4.setOnClickListener(this);
        submit.setOnClickListener(this);

        Intent intent = getIntent();
        total_ques= intent.getIntExtra("Total_q",0);
        Questions2=intent.getStringArrayExtra("Questions");
        CorrectAns=intent.getStringArrayExtra("CorrectAns");
//
//        //------------------------------for Options array i.e 2D
        int rows2= intent.getIntExtra("rows",0);
        int cols2=intent.getIntExtra("cols",0);
        String[] array1D = intent.getStringArrayExtra("array1D");
//        //----------------------------------------------------2d array of this activity
        Options2 = new String[rows2][cols2];
//        //--------------------------------------------------Converting 1D array to 2D
        if(array1D != null){
            for (int i = 0; i < rows2; i++) {
                System.arraycopy(array1D, i * cols2, Options2[i], 0, cols2);
            }
        }
//
        if (intent != null && checkIntentKeys(intent)) {
            // Do a task if intent has been passed with all required keys
            SetQuestion();
        } else {
            // Handle the case where intent is missing or keys are not present
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
            String value=CorrectAns[current_ques_index];
            if(selected_ans.equals(value)){
                score++;
                Log.d("MyCheck", " present score: "+score);
            }
            current_ques_index++;
            if (current_ques_index < total_ques){
                SetQuestion();
//                Log.d("MyCheck", "setquestion() called current_ques_index: "+current_ques_index);
                Log.d("MyCheck", "setquestion() called score: "+score);
            }
            else {
                Log.d("MyCheck", " Last score: "+score);
                Intent intent = new Intent(Play_Created_Quiz.this, score_activity.class);
                intent.putExtra("SCORE",score);
                startActivity(intent);
            }

        }
        else{
            //answer has been clicked
            selected_ans= clikedButton.getText().toString();
            clikedButton.setBackgroundColor(Color.MAGENTA);
        }

    }
    private boolean checkIntentKeys(Intent intent) {
        return intent.hasExtra(KEY1) && intent.hasExtra(KEY2) && intent.hasExtra(KEY3);
    }

    private void SetQuestion() {
        if (Questions2 != null && Options2 != null && current_ques_index < Questions2.length) {
            question.setText(Questions2[current_ques_index]);
            ans1.setText(Options2[current_ques_index][0]);
            ans2.setText(Options2[current_ques_index][1]);
            ans3.setText(Options2[current_ques_index][2]);
            ans4.setText(Options2[current_ques_index][3]);
        }
    }

    private void handleMissingIntent() {
        question.setText("Something is wrong handleMissingIntent \n has been called");
    }
}