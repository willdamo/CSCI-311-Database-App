package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText titleEdit;
    EditText writerEdit;
    Spinner spinner;
    Button addButton;
    Button getYearButton;
    Button getWriterButton;
    DatabaseControl control;
    TextView resultView;
    RecyclerView recyclerView;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        control = new DatabaseControl(this);
        control.open();
        if(control.getTitles().length>0) {
            adapter = new CustomAdapter(control.getTitles());
        }
        control.close();

        titleEdit = findViewById(R.id.titleEdit);
        writerEdit = findViewById(R.id.writerEdit);
        spinner = findViewById(R.id.spinner);
        addButton = findViewById(R.id.addButton);
        getYearButton = findViewById(R.id.getYearButton);
        getWriterButton = findViewById(R.id.getWriterButton);
        resultView = findViewById(R.id.resultView);
        recyclerView = findViewById(R.id.rView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString();
                String year = ((TextView) spinner.getSelectedView()).getText().toString();
                String writer = writerEdit.getText().toString();
                control.open();
                boolean itWorked = control.insert(title, year, writer);
                control.close();
                if (itWorked)
                    Toast.makeText(getApplicationContext(), "Added "+title+" "+year+" "+writer, Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "FAILED "+title+" "+year+" "+writer, Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.years, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getYearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                resultView.setText(control.getYear(titleEdit.getText().toString()));
                control.close();
            }
        });
        getWriterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control.open();
                resultView.setText((control.getWriter(titleEdit.getText().toString())));
            }
        });
    }
}





