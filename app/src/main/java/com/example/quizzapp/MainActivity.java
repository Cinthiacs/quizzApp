package com.example.quizzapp;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.AlertDialog;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView questoesTextView;
    TextView todas_questoesTextView;
    Button respA, respB, respC, respD, respE;
    Button btn_enviar;
    Button btn_limpar;
    Button btn_reiniciar;
    int pontos = 0;
    int questoes_totais = questionario.questao.length;
    int questao_indice = 0;
    String resposta_selecionada = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todas_questoesTextView = findViewById(R.id.todas_questoes);
        questoesTextView = findViewById(R.id.questoes);
        respA = findViewById(R.id.resp_a);
        respB = findViewById(R.id.resp_b);
        respC = findViewById(R.id.resp_c);
        respD = findViewById(R.id.resp_d);
        respE = findViewById(R.id.resp_e);
        btn_enviar = findViewById(R.id.btn_enviar);
        btn_limpar = findViewById(R.id.btn_limpar);
        btn_reiniciar = findViewById(R.id.btn_reiniciar);

        respA.setOnClickListener(this);
        respB.setOnClickListener(this);
        respC.setOnClickListener(this);
        respD.setOnClickListener(this);
        respE.setOnClickListener(this);
        btn_enviar.setOnClickListener(this);
        btn_limpar.setOnClickListener(this);
        btn_reiniciar.setOnClickListener(v -> reiniciarQuiz());

        todas_questoesTextView.setText("Número de Questões: " + questoes_totais);
        carrega_nova_questao();
    }

    private void carrega_nova_questao() {
        if (questao_indice == questoes_totais) {
            final_quiz();
            return;
        }
        questoesTextView.setText(questionario.questao[questao_indice]);
        respA.setText(questionario.escolhas[questao_indice][0]);
        respB.setText(questionario.escolhas[questao_indice][1]);
        respC.setText(questionario.escolhas[questao_indice][2]);
        respD.setText(questionario.escolhas[questao_indice][3]);
        respE.setText(questionario.escolhas[questao_indice][4]);

        resposta_selecionada = "";
    }

    private void final_quiz() {
        String status;
        if (pontos >= questoes_totais * 0.6) {
            status = "Passou no teste";
        } else {
            status = "Tente de Novo";
        }
        new AlertDialog.Builder(this)
                .setTitle(status)
                .setMessage("Acertou: " + pontos + " de " + questoes_totais)
                .setPositiveButton("Tente de Novo", (dialog, i) -> reiniciarQuiz())
                .setCancelable(false)
                .show();
    }

    private void reiniciarQuiz() {
        pontos = 0;
        questao_indice = 0;
        carrega_nova_questao();
    }

    @Override
    public void onClick(View view) {
        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.btn_enviar) {
            if (!resposta_selecionada.isEmpty()) {
                boolean acertou = resposta_selecionada.equals(questionario.respostas[questao_indice]);

                String mensagem = acertou ? "Acertou!" : "Errou!";
                int corBotao = acertou ? Color.GREEN : Color.RED;
                clickedButton.setBackgroundColor(corBotao);

                new AlertDialog.Builder(this)
                        .setTitle(acertou ? "Resposta Correta" : "Resposta Incorreta")
                        .setMessage(mensagem)
                        .setPositiveButton("Próxima", (dialog, which) -> {
                            questao_indice++;
                            carrega_nova_questao();
                            limpa_selecao();
                        })
                        .setCancelable(false)
                        .show();
            }
        } else if (clickedButton.getId() == R.id.btn_limpar) {
            resposta_selecionada = "";
            limpa_selecao();
        } else {
            resposta_selecionada = clickedButton.getText().toString();
            limpa_selecao();
            clickedButton.setBackgroundColor(Color.GREEN);
        }
    }

    private void limpa_selecao() {
        respA.setBackgroundColor(Color.GRAY);
        respB.setBackgroundColor(Color.GRAY);
        respC.setBackgroundColor(Color.GRAY);
        respD.setBackgroundColor(Color.GRAY);
        respE.setBackgroundColor(Color.GRAY);
    }
}