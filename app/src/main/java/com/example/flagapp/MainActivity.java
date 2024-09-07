package com.example.flagapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.flagapp.FinalScreenActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button bAnswer1, bAnswer2, bAnswer3, bAnswer4;
    ImageView iv_flag;
    List<Country> list;
    Random r;
    int turn = 1;
    int score = 0; // Variável para rastrear acertos
    final int totalRounds = 15; // Número total de rodadas
    String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        r = new Random();

        // Recebe o nome do jogador da Intent
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        if (playerName == null || playerName.isEmpty()) {
            playerName = "Jogador"; // Nome padrão caso o nome não seja fornecido
        }

        // Adiciona um log para verificar o valor de playerName
        Log.d("MainActivity", "Nome do jogador: " + playerName);

        iv_flag = findViewById(R.id.iv_flag);
        bAnswer1 = findViewById(R.id.bAnswer1);
        bAnswer2 = findViewById(R.id.bAnswer2);
        bAnswer3 = findViewById(R.id.bAnswer3);
        bAnswer4 = findViewById(R.id.bAnswer4);

        list = new ArrayList<>();

        for (int i = 0; i < new Database().answer.length; i++) {
            list.add(new Country(new Database().answer[i], new Database().flags[i]));
        }

        Collections.shuffle(list);
        newQuestion(turn);

        View.OnClickListener answerClickListener = v -> {
            Button clickedButton = (Button) v;
            boolean isCorrect = clickedButton.getText().toString().equalsIgnoreCase(list.get(turn - 1).getName());

            if (isCorrect) {
                clickedButton.setBackgroundColor(Color.parseColor("#198C19")); // Cor verde para resposta correta
                score++; // Incrementa a pontuação em caso de acerto
            } else {
                clickedButton.setBackgroundColor(Color.parseColor("#CC0000")); // Cor vermelha para resposta errada
                highlightCorrectAnswer(); // Destaca a resposta correta
            }

            // Aguarda um momento antes de mudar para a próxima pergunta ou mostrar a tela final
            clickedButton.postDelayed(() -> {
                if (turn < totalRounds) {
                    turn++;
                    newQuestion(turn);
                } else {
                    // Finaliza o quiz e vai para a tela final
                    showFinalScreen();
                }
            }, 1500); // 1.5 segundos de delay para dar tempo de visualizar a resposta
        };

        bAnswer1.setOnClickListener(answerClickListener);
        bAnswer2.setOnClickListener(answerClickListener);
        bAnswer3.setOnClickListener(answerClickListener);
        bAnswer4.setOnClickListener(answerClickListener);
    }

    private void resetButtonColors() {
        // Reseta a cor dos botões para a cor branca
        bAnswer1.setBackgroundColor(Color.parseColor("#FFFFFF")); // Cor branca
        bAnswer2.setBackgroundColor(Color.parseColor("#FFFFFF")); // Cor branca
        bAnswer3.setBackgroundColor(Color.parseColor("#FFFFFF")); // Cor branca
        bAnswer4.setBackgroundColor(Color.parseColor("#FFFFFF")); // Cor branca
    }

    private void newQuestion(int number) {
        resetButtonColors(); // Reseta as cores quando uma nova pergunta é carregada

        iv_flag.setImageResource(list.get(number - 1).getImage());

        int correctAnswerIndex = r.nextInt(4);
        List<Button> buttons = new ArrayList<>();
        buttons.add(bAnswer1);
        buttons.add(bAnswer2);
        buttons.add(bAnswer3);
        buttons.add(bAnswer4);

        // Define o texto para o botão correto
        buttons.get(correctAnswerIndex).setText(list.get(number - 1).getName());
        buttons.get(correctAnswerIndex).setTag("correct"); // Armazena a tag da resposta correta

        for (int i = 0; i < buttons.size(); i++) {
            if (i != correctAnswerIndex) {
                int randomIndex;
                do {
                    randomIndex = r.nextInt(list.size());
                } while (randomIndex == number - 1 || isDuplicate(randomIndex, buttons));

                buttons.get(i).setText(list.get(randomIndex).getName());
                buttons.get(i).setTag(null); // Certifique-se de que as outras respostas não estejam marcadas como corretas
            }
        }
    }


    private void highlightCorrectAnswer() {
        // Destaca o botão com a resposta correta
        if ("correct".equals(bAnswer1.getTag())) {
            bAnswer1.setBackgroundColor(Color.parseColor("#198C19")); // Cor verde
        } else if ("correct".equals(bAnswer2.getTag())) {
            bAnswer2.setBackgroundColor(Color.parseColor("#198C19")); // Cor verde
        } else if ("correct".equals(bAnswer3.getTag())) {
            bAnswer3.setBackgroundColor(Color.parseColor("#198C19")); // Cor verde
        } else if ("correct".equals(bAnswer4.getTag())) {
            bAnswer4.setBackgroundColor(Color.parseColor("#198C19")); // Cor verde
        }
    }

    private boolean isDuplicate(int index, List<Button> buttons) {
        for (Button button : buttons) {
            if (button.getText().toString().equalsIgnoreCase(list.get(index).getName())) {
                return true;
            }
        }
        return false;
    }

    private void showFinalScreen() {
        // Finaliza o quiz e vai para a tela final
        Intent finalScreenIntent = new Intent(MainActivity.this, FinalScreenActivity.class);
        finalScreenIntent.putExtra("PLAYER_NAME", playerName); // Envia o nome do jogador
        finalScreenIntent.putExtra("SCORE", score); // Envia a pontuação final

        // Adiciona logs para verificar os valores passados para a FinalScreenActivity
        Log.d("MainActivity", "Finalizando jogo com nome: " + playerName);
        Log.d("MainActivity", "Pontuação final: " + score);

        startActivity(finalScreenIntent);
        finish();
    }
}
