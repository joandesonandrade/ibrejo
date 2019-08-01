package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
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

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;

public class splash extends AppCompatActivity {

    private ImageView bbbb;
    private boolean isNotify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();
        for (String key : extras.keySet()) {
            if(extras.getString(key) != null) {
                if(extras.getString("id") != null) {
                    String id = extras.getString("id");
                    Notify(id);
                    isNotify = true;
                    break;
                }
                if(extras.getString("news") != null) {
                    String id = extras.getString("news");
                    news(id);
                    isNotify = true;
                    break;
                }
            }
        }

        bbbb = (ImageView)findViewById(R.id.bbbg);

        /*fadein*/
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(2000);

        /*fadeout*/
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(3000);

        /*set animation*/
        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        this.bbbb.setAnimation(animation);

        Handler handle = new Handler();
        if(!isNotify) {
            handle.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalizadoSplash();
                }
            }, 4500);
        }
    }

    void finalizadoSplash(){

        String file = "joandeson_andrade";
        SharedPreferences pf = getSharedPreferences(file,MODE_PRIVATE);
        if(pf.getString("tutorial","") == "") {

            //criando key tutorial
            SharedPreferences.Editor edit = pf.edit();
            edit.putString("tutorial","oAmor_E_umaMerda");
            edit.apply();
            //

            Intent p = new Intent(this,tutorial.class);
            p.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(p);
            finish();

            return;
        }
        Intent i = new Intent(this,SelectCity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(i);
        finish();
    }


    private void news(String id) {

        Intent i = new Intent(this,NewsBrejo.class);
        i.putExtra("id",id);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();

    }

    private void Notify(final String id) {
        String url_event = "http://app.informabrejo.com.br/notify.php?id=" + URLEncoder.encode(id);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url_event, new Response.Listener<JSONArray>() {
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

                        List<empresa> lst = new ArrayList<>();
                        lst.add(info);

                        String nome = lst.get(0).getNome();
                        String descricao = lst.get(0).getDescricao();
                        String telefone = lst.get(0).getTelefone();
                        String email = lst.get(0).getEmail();
                        String facebook = lst.get(0).getFacebook();
                        String instagram = lst.get(0).getInstagram();
                        String endereco = lst.get(0).getEndereco();
                        String imagem = lst.get(0).getImagem();
                        Double lat = lst.get(0).getLat();
                        Double lng = lst.get(0).getLng();
                        String plano = lst.get(0).getPlano();


                        Intent informations = new Intent(getApplicationContext(),act_informations.class);
                        informations.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        informations.putExtra("nome",nome);
                        informations.putExtra("descricao",descricao);
                        informations.putExtra("telefone",telefone);
                        informations.putExtra("email",email);
                        informations.putExtra("facebook",facebook);
                        informations.putExtra("instagram",instagram);
                        informations.putExtra("endereco",endereco);
                        informations.putExtra("imagem",imagem);
                        informations.putExtra("lat",String.valueOf(lat));
                        informations.putExtra("lng",String.valueOf(lng));
                        informations.putExtra("plano",String.valueOf(plano));
                        informations.putExtra("id",id);
                        startActivity(informations);

                        finish();


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
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(arrayRequest);
    }
}
