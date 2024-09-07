package com.example.flagapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class StartScreenActivity extends AppCompatActivity {

    private EditText etPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        etPlayerName = findViewById(R.id.et_player_name);
        Button btnStartGame = findViewById(R.id.btn_start_game);

        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = etPlayerName.getText().toString();
                Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
                intent.putExtra("PLAYER_NAME", playerName);
                startActivity(intent);
                finish(); // Finaliza a atividade atual para que o usuário não possa voltar para ela.
            }
        });
    }
}