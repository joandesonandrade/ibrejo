package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class tutorial extends AppCompatActivity {


    private LinearLayout tela;
    private Button pular;
    private int contador = 0;
    private int[] imagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        getSupportActionBar().hide();

        imagens=new int[]{
                R.mipmap.tutorial_a,
                R.mipmap.tutorial_b,
                R.mipmap.tutorial_c,
                R.mipmap.tutorial_d,
                R.mipmap.tutorial_e,
                R.mipmap.tutorial_f
        };

        tela = (LinearLayout)findViewById(R.id.ppk_tela);
        pular = (Button)findViewById(R.id.pular);
        tela.setBackgroundResource(imagens[contador]);
        pular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizado();
            }
        });
        tela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador < (imagens.length-1)){
                    contador++;
                    tela.setBackgroundResource(imagens[contador]);
                }else{
                    finalizado();
                }
            }
        });

    }

    public void finalizado(){
        Intent i = new Intent(getApplicationContext(),SelectCity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(i);
        finish();
    }
}
