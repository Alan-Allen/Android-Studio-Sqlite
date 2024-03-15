package com.example.sqlite2;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private final String DBName = "userList.db";
    private String TableName = "userTalbe";
    private final int DBVersion = 1;
    DBHelper dbHelper;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button insert = findViewById(R.id.Insert);
        Button delete = findViewById(R.id.delete);
        Button update = findViewById(R.id.updata);
        EditText name = findViewById(R.id.Name);
        EditText user = findViewById(R.id.User);
        EditText password = findViewById(R.id.Password);

        dbHelper = new DBHelper(this, DBName, null, DBVersion, TableName);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
    }
}