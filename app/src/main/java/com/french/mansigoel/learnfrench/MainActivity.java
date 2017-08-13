package com.french.mansigoel.learnfrench;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.french.mansigoel.learnfrench.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        TextView numbers = (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Opening numbers activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), NumbersActivity.class);
                startActivity(intent);
            }
        });

        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Opening family activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), FamilyActivity.class);
                startActivity(intent);
            }
        });

        TextView colors = (TextView) findViewById(R.id.colors);
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Opening colors activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), ColorsActivity.class);
                startActivity(intent);
            }
        });

        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Opening Phrases activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PhrasesActivity.class);
                startActivity(intent);
            }
        });

        TextView calendar = (TextView) findViewById(R.id.calendar);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Opening calendar activity", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), CalendarActivity.class);
                startActivity(intent);
            }
        });


    }

}

