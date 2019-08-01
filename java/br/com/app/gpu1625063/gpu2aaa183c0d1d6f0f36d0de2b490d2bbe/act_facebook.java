package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class act_facebook extends AppCompatActivity {

    private ImageView profile_img;
    private TextView profile_nome,entrar_instrucao;
    private LoginButton fb;
    private Button entrar_finish;

    private String file_fb   = "facebook";
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_facebook);

        getSupportActionBar().setTitle("Entrar com o facebook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        entrar_instrucao = (TextView)findViewById(R.id.entrar_instrucao);
        profile_img  = (ImageView)findViewById(R.id.entrar_img);
        profile_nome = (TextView)findViewById(R.id.entrar_nome);
        fb           = (LoginButton)findViewById(R.id.entrar_fb);
        entrar_finish = (Button)findViewById(R.id.entrar_finish);

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);

        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");

        fb.setReadPermissions(permissions);
        callbackManager = CallbackManager.Factory.create();
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code

                fb.setVisibility(View.GONE);
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        initFacebook(object,loginResult);
                        Log.d("dblog_obj",String.valueOf(object));
                    }
                });

                Bundle params = new Bundle();
                params.putString("fields","id,name");
                graphRequest.setParameters(params);
                graphRequest.executeAsync();

                //Toast.makeText(getApplicationContext(),String.valueOf(loginResult.getRecentlyGrantedPermissions()),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(),"você cancelou o login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(),"ocorreu um problema na autenticação",Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initFacebook(JSONObject object,LoginResult loginResult) {

        String nome_user = null;
        String url_img_user = null;
        String id_user = null;

        entrar_instrucao.setVisibility(View.GONE);
        entrar_finish.setVisibility(View.VISIBLE);

        if (object == null || object.length() == 0){
            Toast.makeText(getApplicationContext(), "não foi possível conectar com o facebook", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            id_user = object.getString("id");
            nome_user = object.getString("name");
            url_img_user = "https://graph.facebook.com/"+id_user+"/picture?width=150&height=150";
            //Log.d("dblog_email",object.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(url_img_user!=null){
            RequestOptions options;
            options = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.loading_img);
            Glide.with(getApplicationContext())
                    .load(url_img_user)
                    .apply(options)
                    .into(profile_img);

        }

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);
        SharedPreferences.Editor FBeditor = FBpref.edit();

        FBeditor.putString("fb_token",String.valueOf(loginResult.getAccessToken()));
        FBeditor.putString("fb_id",String.valueOf(id_user));
        FBeditor.putString("fb_name",String.valueOf(nome_user));
        FBeditor.putString("fb_image",String.valueOf(url_img_user));
        FBeditor.apply();

        profile_nome.setText("Olá, "+nome_user+" Bem-Vindo!");
        //EnterWithFacebook.setTextSize(20);

        RegistreUser();

        entrar_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void RegistreUser() {
        String url = "http://app.informabrejo.com.br/users.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){

                SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);

                Map<String,String> params = new HashMap<String, String>();
                params.put("usuario",FBpref.getString("fb_name",""));
                params.put("id_user",FBpref.getString("fb_id",""));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:

                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
}
