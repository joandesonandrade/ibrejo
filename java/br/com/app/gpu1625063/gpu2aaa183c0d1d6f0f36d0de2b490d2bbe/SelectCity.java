package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.R;

public class SelectCity extends AppCompatActivity {

    private ImageView bt_solanea,bt_bananeiras,bt_guarabira,ppp;
    private String file_city = "city";
    private String file_fb   = "facebook";
    private CallbackManager callbackManager;
    boolean isLoggedIn;
    private TextView EnterWithFacebook;
    private LoginButton bt_facebook_login;
    private ImageView fb_img_profile;

    /**/
    ScrollView vLay;
    LinearLayout bLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        getSupportActionBar().hide();

        printKey();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        isLoggedIn = accessToken != null && !accessToken.isExpired();

        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");

        ppp            = (ImageView)findViewById(R.id.ppppp);
        bt_facebook_login = (LoginButton) findViewById(R.id.fb_login);
        EnterWithFacebook = (TextView)findViewById(R.id.txt_enter_withFacebook);
        fb_img_profile = (ImageView)findViewById(R.id.fb_profile);

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);

        if(isLoggedIn) {

            RequestOptions options;
            options = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.loading_img);

            String iimage       = "";
            String nome_user_fb = "";
            if (FBpref.getString("fb_name","")!=""){
                nome_user_fb = FBpref.getString("fb_name","");
            }
            if (FBpref.getString("fb_image","")!=""){
                iimage = FBpref.getString("fb_image","");
            }

            fb_img_profile = (ImageView)findViewById(R.id.fb_profile);
            fb_img_profile.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(iimage)
                    .apply(options)
                    .into(fb_img_profile);


            EnterWithFacebook.setText("Olá, "+nome_user_fb+" Bem-Vindo!");
            EnterWithFacebook.setVisibility(View.VISIBLE);
            bt_facebook_login.setVisibility(View.GONE);
            ppp.setVisibility(View.GONE);
        }

        bt_facebook_login.setReadPermissions(permissions);
        callbackManager = CallbackManager.Factory.create();
        bt_facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code

                bt_facebook_login.setVisibility(View.GONE);
                ppp.setVisibility(View.GONE);
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

        //selected_title = (ImageView)findViewById(R.id.selected_title);
        bt_solanea    = (ImageView) findViewById(R.id.bt_solanea);
        bt_bananeiras = (ImageView)findViewById(R.id.bt_bananeiras);
        bt_guarabira  = (ImageView)findViewById(R.id.bt_guarabira);
        //bt_nao = (Button)findViewById(R.id.bt_city_nao);
        //bt_sim = (Button)findViewById(R.id.bt_city_sim);
        vLay = (ScrollView)findViewById(R.id.city_none);
        bLay = (LinearLayout)findViewById(R.id.city_selected);

        SharedPreferences pref = getSharedPreferences(file_city,MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        /*if(pref.getString("nome","") != "" || pref.getString("nome",null) != null){
            selected_title.setText("você está em "+pref.getString("nome","")+"?");
            bLay.setVisibility(View.VISIBLE);
            vLay.setVisibility(View.GONE);
        }*/
/*
        bt_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSceneMap();
            }
        });

        bt_nao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLay.setVisibility(View.GONE);
                vLay.setVisibility(View.VISIBLE);
                selected_title.setText("Onde você está ?");
            }
        });
*/
        bt_solanea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("nome","Solânea");
                editor.apply();
                LoadSceneMap();
            }
        });

        bt_bananeiras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("nome","Bananeiras");
                editor.apply();
                LoadSceneMap();
            }
        });

        bt_guarabira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("nome","Guarabira");
                editor.apply();
                LoadSceneMap();
            }
        });


    }


    private void initFacebook(JSONObject object,LoginResult loginResult) {

        String nome_user = null;
        String url_img_user = null;
        String id_user = null;

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
            fb_img_profile = (ImageView)findViewById(R.id.fb_profile);
            fb_img_profile.setVisibility(View.VISIBLE);

            RequestOptions options;
            options = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.loading_img);
            Glide.with(getApplicationContext())
                    .load(url_img_user)
                    .apply(options)
                    .into(fb_img_profile);

        }

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);
        SharedPreferences.Editor FBeditor = FBpref.edit();

        FBeditor.putString("fb_token",String.valueOf(loginResult.getAccessToken()));
        FBeditor.putString("fb_id",String.valueOf(id_user));
        FBeditor.putString("fb_name",String.valueOf(nome_user));
        FBeditor.putString("fb_image",String.valueOf(url_img_user));
        FBeditor.apply();

        EnterWithFacebook.setText("Olá, "+nome_user+" Bem-Vindo!");
        EnterWithFacebook.setVisibility(View.VISIBLE);

        RegistreUser();
    }

    private void RegistreUser() {
        String url = "http://app.informabrejo.com.br/users.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("dblog_Users",response.toString());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode,resultCode,data);

    }

    private void LoadSceneMap(){
        Intent i = new Intent(this,actmain.class);
        startActivity(i);
        finish();
    }

    private void printKey(){
        try {
           PackageInfo packageInfo = getPackageManager().getPackageInfo("br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe", PackageManager.GET_SIGNATURES);
            for(Signature signature:packageInfo.signatures){
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.d("dblog_keyhash", Base64.encodeToString(md.digest(),Base64.DEFAULT));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
