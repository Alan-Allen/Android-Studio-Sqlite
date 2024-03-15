package com.example.sqlite2;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private final String DBName = "userList.db";
    private String TableName = "userTable";
    private final int DBVersion = 1;
    DBHelper dbHelper;

    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> getNowArray = new ArrayList<>();

    Button insert, delete, update;
    EditText name, user, password;
    MyAdapter myAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this, DBName, null, DBVersion, TableName);
        dbHelper.chickTable();
        arrayList = dbHelper.searchAll();
        itemSetting();//連接所有元件
        recyclerViewSetting();//設置RecyclerView
        buttonFunction();//設置按鈕功能
        dbHelper.insert("Admin", "admin", "1234");

        SQLiteDatabase database = dbHelper.getWritableDatabase();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void buttonFunction() {//設置按鈕功能
        delete.setOnClickListener(v -> {
            clearAll();//清空目前所選以及所有editText
        });
        insert.setOnClickListener(v -> {
            dbHelper.insert(name.getText().toString()
                    ,user.getText().toString()
                    ,password.getText().toString());
            arrayList = dbHelper.searchAll();
            myAdapter.notifyDataSetChanged();
            clearAll();
        });
        update.setOnClickListener(v -> {
            dbHelper.modify(getNowArray.get(0).get("id")
                    ,name.getText().toString()
                    ,user.getText().toString()
                    ,password.getText().toString());
            arrayList = dbHelper.searchAll();
            myAdapter.notifyDataSetChanged();
            clearAll();

        });
    }

    private void clearAll() {//清空目前所選以及所有editText
        name.setText("");
        user.setText("");
        password.setText("");
        getNowArray.clear();
    }

    private void recyclerViewSetting() {//設置RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
    }

    private void itemSetting() {//連接所有元件
        insert = findViewById(R.id.Insert);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.updata);
        name = findViewById(R.id.Name);
        user = findViewById(R.id.User);
        password = findViewById(R.id.Password);
    }


    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {//設置Adapter

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.tvTitle.setText(arrayList.get(position).get("name"));

            holder.itemView.setOnClickListener((v) -> {
                getNowArray.clear();
                getNowArray = dbHelper.search(arrayList.get(position).get("id"));
                try {
                    name.setText(getNowArray.get(0).get("name"));
                    user.setText(getNowArray.get(0).get("user"));
                    password.setText(getNowArray.get(0).get("password"));
                } catch (Exception e) {
                    Log.d(TAG, "onBindViewHolder: " + e.getMessage());
                }

            });

        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}