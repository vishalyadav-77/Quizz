package com.example.quizz;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class quiz_ques extends AppCompatActivity {
    Button next;
    TextView T1;
    EditText Ques, A,B,C,D;
    private int Current_question_index=0;
    private String SelectedAns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.quiz_ques_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        next=findViewById(R.id.next);
        Ques=findViewById(R.id.ques);
        A=findViewById(R.id.T_ques);
        B=findViewById(R.id.optionB);
        C=findViewById(R.id.optionC);
        D=findViewById(R.id.optionD);
        T1=findViewById(R.id.viewQ);
        Button Abutton, Bbutton,Cbutton,Dbutton;
        Abutton=findViewById(R.id.Abutton);
        Bbutton=findViewById(R.id.Bbutton);
        Cbutton=findViewById(R.id.Cbutton);
        Dbutton=findViewById(R.id.Dbutton);

        //TOTAL QUESTIONS TAKEN FROM USER
        Intent intent= getIntent();
        int Total_Q= intent.getIntExtra("TotalQ",0);
        String Total_Q_text=String.valueOf(Total_Q);
        //TOTAL QUESTIONS TAKEN FROM USER
        //---------------------------------------------------ALL QUESTIONS & CHOICES HERE
        String[] Questions= new String[Total_Q];
        String[][] Options= new String[Total_Q][4];
        String[] CorrectAns= new String[Total_Q];
        //---------------------------------------------------------To save correct answer----------
        Abutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedAns=A.getText().toString();
                Abutton.setBackgroundColor(Color.MAGENTA);
            }
        });
        Bbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedAns=B.getText().toString();
                Bbutton.setBackgroundColor(Color.MAGENTA);
            }
        });
        Cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedAns=C.getText().toString();
                Cbutton.setBackgroundColor(Color.MAGENTA);
            }
        });
        Dbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedAns=D.getText().toString();
                Dbutton.setBackgroundColor(Color.MAGENTA);
            }
        });
//------------------------------------------------------------------To save correct answer----------
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Abutton.setBackgroundColor(Color.parseColor("#0091EA"));
                Bbutton.setBackgroundColor(Color.parseColor("#0091EA"));
                Cbutton.setBackgroundColor(Color.parseColor("#0091EA"));
                Dbutton.setBackgroundColor(Color.parseColor("#0091EA"));

                //Edittext to usable Strings must be use inside the button to work for everytime
                String Question= Ques.getText().toString();
                String ChoiceA = A.getText().toString();
                String ChoiceB = B.getText().toString();
                String ChoiceC = C.getText().toString();
                String ChoiceD = D.getText().toString();
                String selectedAnsLocal = SelectedAns;
                Log.d("QuizDebug", "Current_question_index: " + Current_question_index);
                Log.d("QuizDebug", "Total_Q: " + Total_Q);

                if (Current_question_index < Total_Q) {
                    Questions[Current_question_index] = Question;
                    CorrectAns[Current_question_index] = selectedAnsLocal;
                    Options[Current_question_index][0] = ChoiceA;
                    Options[Current_question_index][1] = ChoiceB;
                    Options[Current_question_index][2] = ChoiceC;
                    Options[Current_question_index][3] = ChoiceD;
                    Current_question_index++;

                    Log.d("QuizDebug", "Before: SelectedAns = " + SelectedAns);
                    Log.d("QuizDebug", "After increment: Current_question_index = " + Current_question_index);

                    if (Current_question_index == Total_Q) {
                        // Move to the next activity
                        int rows = Options.length;
                        int cols = Options[0].length;
                        String[] array1D = new String[rows * cols];
                        for (int i = 0; i < rows; i++) {
                            System.arraycopy(Options[i], 0, array1D, i * cols, cols);
                        }

                        Intent intent2 = new Intent(quiz_ques.this, quiz_is_ready.class);
                        intent2.putExtra("Questions", Questions);
                        intent2.putExtra("array1D", array1D);
                        intent2.putExtra("rows", rows);
                        intent2.putExtra("cols", cols);
                        intent2.putExtra("Total_q", Total_Q);
                        intent2.putExtra("CorrectAns",CorrectAns);
                        startActivity(intent2);
                    } else {
                        Ques.setText("");
                        A.setText("");
                        B.setText("");
                        C.setText("");
                        D.setText("");
                        Log.d("QuizDebug", "After: SelectedAns = " + SelectedAns);
                        SelectedAns = "";

                        if (Current_question_index == Total_Q - 1) {
                            next.setText("Submit");
                        }
                    }
                }
            }
        });

    }
}