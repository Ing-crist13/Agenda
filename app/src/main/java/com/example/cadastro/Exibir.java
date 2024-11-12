
package com.example.cadastro;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Exibir extends AppCompatActivity {
    Button btVoltar;
    ContatoDAO c;
    ListView lvExibir;
    ArrayList<String> dataList = new ArrayList<>();
    ArrayList<String> idList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exibir);

        btVoltar = findViewById(R.id.btVoltar);

        c = new ContatoDAO(Exibir.this);
        lvExibir = findViewById(R.id.lvExibir);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvExibir.setAdapter(adapter);

        showData();

        btVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voltarTela();
            }
        });

        lvExibir.setOnItemClickListener((parent, view, position, id) -> {
            ContatoDAO contato = c;
            // Navegar para a tela de detalhes
            Intent intent = new Intent(this, Agenda.class);
            intent.putExtra("Contato", String.valueOf(contato));
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void showData() {
        Cursor cursor = c.exibirDados();
        if (cursor.getCount() == 0) {
            // Sem dados
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
            return;
        }
        while (cursor.moveToNext()) {
            dataList.add(cursor.getString(1));
            // Pegue o segundo campo (name)
        }
        adapter.notifyDataSetChanged();
    }
    private void voltarTela() {
        Intent telaPrincipal = new Intent(Exibir.this, MainActivity.class);
        startActivity(telaPrincipal);
        finish();
    }

    private void Proximatela() {
        Intent telaPrincipal = new Intent(Exibir.this, Agenda.class);
        startActivity(telaPrincipal);
        finish();
    }




}