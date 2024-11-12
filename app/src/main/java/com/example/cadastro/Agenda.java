package com.example.cadastro;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    ListView lvDados;
    ContatoDAO c;
    ArrayList<String> dataList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_agenda);

        c = new ContatoDAO(Agenda.this);
        lvDados = findViewById(R.id.lvDados);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvDados.setAdapter(adapter);

        showData1();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showData1() {
        Cursor cursor = c.exibirDados();
        while (cursor.moveToNext()) {
            dataList.add(cursor.getString(1));
            dataList.add(cursor.getString(2));
            dataList.add(cursor.getString(3));
            // Pegue o segundo campo (name)
        }
        adapter.notifyDataSetChanged();
    }
}