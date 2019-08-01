package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database.DBHelper;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;


public class actmain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    public static FloatingActionButton fab;
    private mapFragment mapfragment;

    public static mapFragment mapfrag = null;

    private String id_ca_md5 = "";

    private String file_fb   = "facebook";

    private ImageView user_image;
    private ImageView info_profile;
    private TextView user_nome;
    private LoginButton fb_login;
    private CallbackManager callbackManager;

    private FloatingSearchView searchView;
    private boolean buscador = false;
    private actmain act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actmain);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        act = this;
        searchView = (FloatingSearchView)findViewById(R.id.search);
        searchView.setVisibility(View.GONE);

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                DBHelper db = new DBHelper(act);
                List<empresa> emp = db.search(newQuery);
                if(emp.size() > 0){
                    LatLng latLng = new LatLng(emp.get(0).getLat(), emp.get(0).getLng());
                    mapfrag.mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mapfrag.mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    for (int i = 0;i<mapfragment.Supermaker.size();i++){
                        if(
                                latLng.longitude == mapfragment.Supermaker.get(i).getPosition().longitude
                                &&
                                        latLng.latitude == mapfragment.Supermaker.get(i).getPosition().latitude
                                ) {
                                        mapfragment.Supermaker.get(i).showInfoWindow();
                                        break;
                                }
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"nada foi encontrado",Toast.LENGTH_SHORT).show();

                    for (int i = 0;i<mapfragment.Supermaker.size();i++){
                        mapfragment.Supermaker.get(i).hideInfoWindow();
                    }

                }
            }
        });


        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_event) {

                    Intent i_evento = new Intent(act,act_evento.class);
                    i_evento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i_evento);

                    return;

                } else if (id == R.id.nav_classifild) {

                    Intent i_classificado = new Intent(act,act_classificado.class);
                    i_classificado.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i_classificado);

                    return;

                } else if (id == R.id.nav_info) {

                    StartProfileAbout();

                    return;

                }else if (id == R.id.news){

                    Intent i_news = new Intent(act,NewsBrejo.class);
                    i_news.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i_news);

                    return;

                }

                id_ca_md5 = md5(item.getTitle().toString());
                //Log.d("dblog",id_ca_md5);

                if(id_ca_md5.length() == 0)
                    return;

                Intent i = new Intent(act,lista.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("nome",item.getTitle().toString());
                i.putExtra("id",id_ca_md5);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        user_image = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.user_image);
        user_nome   = (TextView)navigationView.getHeaderView(0).findViewById(R.id.user_nome);
        fb_login = (LoginButton)navigationView.getHeaderView(0).findViewById(R.id.user_login);

        info_profile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_informabrejo);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);
        if(isLoggedIn) {

            info_profile.setVisibility(View.GONE);
            user_image.setVisibility(View.VISIBLE);

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


            Glide.with(getApplicationContext())
                    .load(iimage)
                    .apply(options)
                    .into(user_image);


            user_nome.setText(nome_user_fb);
            user_nome.setTextSize(15);
            fb_login.setVisibility(View.GONE);
        }

        mapfragment = new mapFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.container,mapfragment,"maps");
        ft.commitNowAllowingStateLoss();

        mapfrag = mapfragment;


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mapFragment mmap = new mapFragment();
            mmap.getPositionGPS(getApplicationContext(),actmain.this,mapfragment);
            fab.setVisibility(View.INVISIBLE);
            }
        });


        /*login facebook*/
        List<String> permissions = new ArrayList<>();
        permissions.add("email");
        permissions.add("public_profile");

        fb_login.setReadPermissions(permissions);
        callbackManager = CallbackManager.Factory.create();
        fb_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code

                fb_login.setVisibility(View.GONE);
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

    private void initFacebook(JSONObject object, LoginResult loginResult) {

        String nome_user = null;
        String url_img_user = null;
        String id_user = null;

        if (object == null || object.length() == 0) {
            Toast.makeText(getApplicationContext(), "não foi possível conectar com o facebook", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            id_user = object.getString("id");
            nome_user = object.getString("name");
            url_img_user = "https://graph.facebook.com/" + id_user + "/picture?width=150&height=150";
            //Log.d("dblog_email",object.getString("email"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user_nome.setText(nome_user);
        user_nome.setTextSize(15);

        user_image.setVisibility(View.VISIBLE);

        if (url_img_user != null) {

            RequestOptions options;
            options = new RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_img)
                    .error(R.drawable.loading_img);
            Glide.with(getApplicationContext())
                    .load(url_img_user)
                    .apply(options)
                    .into(user_image);

        }

        SharedPreferences FBpref = getSharedPreferences(file_fb,MODE_PRIVATE);
        SharedPreferences.Editor FBeditor = FBpref.edit();

        FBeditor.putString("fb_token",String.valueOf(loginResult.getAccessToken()));
        FBeditor.putString("fb_id",String.valueOf(id_user));
        FBeditor.putString("fb_name",String.valueOf(nome_user));
        FBeditor.putString("fb_image",String.valueOf(url_img_user));
        FBeditor.apply();

        fb_login.setVisibility(View.GONE);
        info_profile.setVisibility(View.GONE);

        RegistreUser();

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!buscador) {
                super.onBackPressed();
            }
        }

        if(buscador && !drawer.isDrawerOpen(GravityCompat.START)){
            searchView.setVisibility(View.GONE);
            buscador = !buscador;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actmain, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:{
                if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mapFragment mmap = new mapFragment();
                    mmap.getPositionGPS(getApplicationContext(),actmain.this,mapfragment);
                }else{

                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            /* Toast.makeText(this,"open settings",Toast.LENGTH_LONG).show(); */

            Intent i = new Intent(this,SelectCity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

            return true;
        }

        if(id == R.id.buscar){
            if(!buscador) {
                searchView.setVisibility(View.VISIBLE);
            }else{
                searchView.setVisibility(View.GONE);
            }
            buscador = !buscador;
        }

        if(id  == R.id.act_shared){

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "baixe agora mesmo o Informa Brejo https://play.google.com/store/apps/details?id=br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_event) {

            Intent i_evento = new Intent(this,act_evento.class);
            i_evento.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(i_evento);

            return true;
        } else if (id == R.id.nav_classifild) {

            Intent i_classificado = new Intent(this,act_classificado.class);
            i_classificado.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(i_classificado);

            return true;
        } else if (id == R.id.nav_info) {

            StartProfileAbout();

            return true;
        }else if (id == R.id.news){

            Intent i_news = new Intent(this,NewsBrejo.class);
            i_news.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(i_news);

            return true;

        }

        id_ca_md5 = md5(item.getTitle().toString());
        //Log.d("dblog",id_ca_md5);

        if(id_ca_md5.length() == 0)
            return true;

        Intent i = new Intent(this,lista.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("nome",item.getTitle().toString());
        i.putExtra("id",id_ca_md5);
        startActivity(i);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void StartProfileAbout(){

         String nome;
         String descricao;
         String telefone;
         String email;
         String facebook;
         String instagram;
         String endereco;
         String imagem;
         Double lat;
         Double lng;
         String id;
         String prefix;
         String plano;
         String id_category;
         DBHelper dbHelper;

        List<empresa> lst = new ArrayList<>();
        dbHelper = new DBHelper(this);
        lst = dbHelper.getData(md5("Informa Brejo"));
        //lst = dbHelper.getAllEmpresas();

        //Log.d("dblog",String.valueOf(lst));

        if(lst == null || lst.size() == 0)
            return;

        nome = lst.get(0).getNome();
        descricao = lst.get(0).getDescricao();
        telefone = lst.get(0).getTelefone();
        email = lst.get(0).getEmail();
        facebook = lst.get(0).getFacebook();
        instagram = lst.get(0).getInstagram();
        endereco = lst.get(0).getEndereco();
        imagem = lst.get(0).getImagem();
        lat = lst.get(0).getLat();
        lng = lst.get(0).getLng();
        plano = lst.get(0).getPlano();
        id = md5(nome);


        Intent informations = new Intent(this,act_informations.class);
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
    }

    public String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
