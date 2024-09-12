package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Create_quiz extends AppCompatActivity {
     Button next;
     EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.create_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        next=findViewById(R.id.next);
        editText=findViewById(R.id.T_ques);
        String text= editText.getText().toString();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()){
                    Toast.makeText(Create_quiz.this, "Enter the value!", Toast.LENGTH_SHORT).show();
                }
                else{
                    String Total=editText.getText().toString();
                    int Total2=Integer.valueOf(Total);
                    Intent intent= new Intent(Create_quiz.this, quiz_ques.class);
                    intent.putExtra("TotalQ",Total2);
                    startActivity(intent);
                }

            }
        });

    }
}