package com.example.flagapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FinalScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        // Recupera os dados passados pela Intent
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("PLAYER_NAME");
        int score = intent.getIntExtra("SCORE", 0);

        // Verifica se os dados foram recebidos corretamente
        Log.d("FinalScreenActivity", "Nome do jogador recebido: " + playerName);
        Log.d("FinalScreenActivity", "Pontuação recebida: " + score);

        // Configura a mensagem personalizada
        TextView tvPlayerName = findViewById(R.id.tvPlayerName);
        tvPlayerName.setText("Parabéns, " + playerName + "!");

        // Configura o resultado
        TextView tvScore = findViewById(R.id.tvScore);
        tvScore.setText("Você acertou " + score + " de 15 bandeiras!");

        // Botão para voltar ao menu
        Button btnMainMenu = findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mainMenuIntent = new Intent(FinalScreenActivity.this, StartScreenActivity.class);
                startActivity(mainMenuIntent);
                finish();
            }
        });

        // Botão para jogar novamente
        Button btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent playAgainIntent = new Intent(FinalScreenActivity.this, MainActivity.class);
                startActivity(playAgainIntent);
                finish();
            }
        });
    }
}
