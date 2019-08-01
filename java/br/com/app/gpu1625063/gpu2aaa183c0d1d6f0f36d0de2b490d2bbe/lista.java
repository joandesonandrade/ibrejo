package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter.listaAdapter;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database.DBHelper;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;

public class lista extends AppCompatActivity {


    private String id_ext;
    private String nome_ext;

    /**/
    private String nome;
    private String descricao;
    private String telefone;
    private String email;
    private String facebook;
    private String instagram;
    private String endereco;
    private String imagem;
    private Double lat;
    private Double lng;
    private String id;
    private String prefix;
    private String id_category;

    private LinearLayout nada_encontrado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        nome_ext = getIntent().getExtras().getString("nome");
        id_ext = getIntent().getExtras().getString("id");

        nada_encontrado = (LinearLayout)findViewById(R.id.nada_encontrado);
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_lista);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(nome_ext);

        List<empresa> eelist = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        eelist = dbHelper.getDataCategory(id_ext);

        if (eelist.size() > 0){
            rv.setVisibility(View.VISIBLE);
            nada_encontrado.setVisibility(View.GONE);
        }else{
            nada_encontrado.setVisibility(View.VISIBLE);
        }

        //listaAdapter listaA = new listaAdapter(getApplicationContext(),eelist);
        Log.d("dblog",String.valueOf(id_ext));
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(new listaAdapter(getApplicationContext(),eelist));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
