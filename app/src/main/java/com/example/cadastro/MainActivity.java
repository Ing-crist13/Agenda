package com.example.cadastro;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btCadastrar, btExcluir, btConsultar, btExibir;
    EditText edNome, edCelular, edEmail;
    TextView txtId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btCadastrar = findViewById(R.id.btCadastrar);
        btExcluir = findViewById(R.id.btExcluir);
        btConsultar = findViewById(R.id.btConsultar);
        btExibir = findViewById(R.id.btExibir);

        edNome = findViewById(R.id.edNome);
        edCelular = findViewById(R.id.edCelular);
        edEmail = findViewById(R.id.edEmail);

        txtId = findViewById(R.id.txtId);


        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCampos()) {
                    Contato c;
                    c = new Contato();
                    c.setNome(edNome.getText().toString());
                    c.setCelular(edCelular.getText().toString());
                    c.setEmail(edEmail.getText().toString());

                    ContatoDAO dao;
                    dao = new ContatoDAO(MainActivity.this);
                    dao.salvarContato(c);
                    Toast.makeText(MainActivity.this, "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    limparCampos();
                }else
                    Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
        });

        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContatoDAO dao;
                dao = new ContatoDAO(MainActivity.this);
                dao.excluirContato(Integer.parseInt(txtId.getText().toString()));
                Toast.makeText(MainActivity.this, " Contato Excluido com sucesso", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        });

        btConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contato c;
                c = new Contato();
                ContatoDAO dao;
                dao = new ContatoDAO(MainActivity.this);
                c = dao.consultarPorNome(edNome.getText().toString());
                if (c != null) {
                    txtId.setText(String.valueOf(c.getId()));
                    edNome.setText(c.getNome());
                    edCelular.setText(c.getCelular());
                    edEmail.setText(c.getEmail());

                } else {
                    Toast.makeText(MainActivity.this, "Contato não cadastrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btExibir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                exibirProxima();

            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    private void limparCampos() {
        txtId.setText("");
        edNome.setText("");
        edCelular.setText("");
        edEmail.setText("");
    }

    private void exibirProxima() {
        Intent telaPrincipal = new Intent(MainActivity.this, Exibir.class);
        startActivity(telaPrincipal);
        finish();
    }

    private boolean validarCampos() {
        edNome = (EditText) findViewById(R.id.edNome);
        edCelular = (EditText) findViewById(R.id.edCelular);
        edEmail = (EditText) findViewById(R.id.edEmail);
        return !TextUtils.isEmpty(edNome.getText().toString().trim()) && !TextUtils.isEmpty(edCelular.getText().toString().trim())
                && !TextUtils.isEmpty(edEmail.getText().toString().trim()) ;
    }
}
