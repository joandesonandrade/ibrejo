package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter.listaAdapter;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter.listaeventoadapter;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;

public class act_evento extends AppCompatActivity {

    private RecyclerView rv;
    private String url_event = "http://app.informabrejo.com.br/event.php";
    private String file_city = "city";
    private String nome_city;
    private List<empresa> lstempresa = new ArrayList();

    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_evento);

        getSupportActionBar().setTitle("Eventos do Mês");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        jsonRequest();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:

                finish();

                return true;

                default:
                    return super.onOptionsItemSelected(item);

        }


    }

    private void jsonRequest() {
        final SharedPreferences pref = getSharedPreferences(file_city, Context.MODE_PRIVATE);
        if(pref.getString("nome",null) != null){
            nome_city = pref.getString("nome","");
        }
        url_event += "?city="+ URLEncoder.encode(nome_city);
        arrayRequest = new JsonArrayRequest(url_event, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        empresa info = new empresa();
                        info.setNome(jsonObject.getString("nome"));
                        info.setDescricao(jsonObject.getString("descricao"));
                        info.setTelefone(jsonObject.getString("telefone"));
                        info.setEmail(jsonObject.getString("email"));
                        info.setFacebook(jsonObject.getString("url_facebook"));
                        info.setInstagram(jsonObject.getString("url_instagram"));
                        info.setEndereco(jsonObject.getString("endereco"));
                        info.setImagem(jsonObject.getString("imagem"));
                        info.setEndereco(jsonObject.getString("endereco"));
                        info.setLat(jsonObject.getDouble("lat"));
                        info.setLng(jsonObject.getDouble("lng"));
                        info.setPrefix(jsonObject.getString("prefix"));
                        info.setId_category(jsonObject.getString("id_category"));
                        info.setPlano(jsonObject.getString("plano"));

                        lstempresa.add(info);

                        initEvent(lstempresa);

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "sem conexão a internet", Toast.LENGTH_LONG).show();
                error.printStackTrace();
                initError();
            }
        });


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);

    }

    void  initError(){
        TextView txtError = (TextView)findViewById(R.id.evento_erro);
        ProgressBar pbar = (ProgressBar)findViewById(R.id.progress_event);
        txtError.setVisibility(View.VISIBLE);
        pbar.setVisibility(View.GONE);
    }

    void initEvent(List<empresa> lst){
        ProgressBar pbar = (ProgressBar)findViewById(R.id.progress_event);
        pbar.setVisibility(View.GONE);
        rv = (RecyclerView)findViewById(R.id.rv_event);
        rv.setVisibility(View.VISIBLE);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setAdapter(new listaeventoadapter(lst,getApplicationContext()));
    }
}
