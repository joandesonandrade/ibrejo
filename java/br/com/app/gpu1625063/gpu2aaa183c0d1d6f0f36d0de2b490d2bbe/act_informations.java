package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.informations;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter.infoadapter;


public class act_informations extends AppCompatActivity {

    //**//
    private String nome;
    private String descricao;
    private String telefone;
    private String email;
    private String facebook;
    private String instagram;
    private String endereco;
    private String imagem;
    private String lat;
    private String lng;
    private String id;
    private String plano;

    private RequestOptions options;
    private RecyclerView rv;

    private String url_api = "http://app.informabrejo.com.br/img.php";

    private ImageView iih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_informations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");


        //receber as informações do putextras
        nome = getIntent().getExtras().getString("nome");
        telefone = getIntent().getExtras().getString("telefone");
        descricao = getIntent().getExtras().getString("descricao");
        facebook = getIntent().getExtras().getString("facebook");
        instagram = getIntent().getExtras().getString("instagram");
        imagem = getIntent().getExtras().getString("imagem");
        email = getIntent().getExtras().getString("email");
        endereco = getIntent().getExtras().getString("endereco");
        lat = getIntent().getExtras().getString("lat");
        lng = getIntent().getExtras().getString("lng");
        id = getIntent().getExtras().getString("id");
        plano = getIntent().getExtras().getString("plano");


        ImageView perfil = (ImageView) findViewById(R.id.perfil);
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.solid)
                .error(R.drawable.solid);

        Glide.with(getApplicationContext())
                .load(imagem)
                .apply(options)
                .into(perfil);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setTitleEnabled(true);
       // collapsingToolbarLayout.setTitle(nome);

        List<informations> lst = new ArrayList<>();

        informations info = new informations();
        info.setTelefone(telefone);
        info.setDescricao(descricao);
        info.setEndereco(endereco);
        info.setEmail(email);
        info.setFacebook(facebook);
        info.setInstagram(instagram);
        info.setNome(nome);
        info.setId(md5(nome));
        info.setPlano(plano);
        lst.add(info);

        infoadapter mInfo = new infoadapter(getApplicationContext(),this,lst);

        rv = (RecyclerView)findViewById(R.id.bvr);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv.setHasFixedSize(true);
        rv.setAdapter(mInfo);


        /*
        List<galeria> glist = new ArrayList<>();
        galeria g = new galeria();
        g.setUrl("https://joandesonandrade.com.br/informabrejo.png");
        glist.add(g);

        galeriaadapter mGaleria = new galeriaadapter(getApplicationContext(),glist);
        RecyclerView galeria = (RecyclerView)findViewById(R.id.galeria);
        galeria.setLayoutManager(new GridLayoutManager(getApplicationContext(),glist.size()));
        galeria.setHasFixedSize(true);
        galeria.setAdapter(mGaleria); */

        //alex2506

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPositionGPS();
                //Log.d("dblog",String.valueOf(actmain.mapfrag));
                //Log.d("dblog",String.valueOf(lat));
            }
        });


        iih = (ImageView)findViewById(R.id.planos);
        switch (plano){
            case "bronze":
                iih.setBackgroundResource(R.mipmap.plano_bronze);
                break;
            case "prata":
                iih.setBackgroundResource(R.mipmap.plano_prata);
                break;
            case "ouro":
                iih.setBackgroundResource(R.mipmap.plano_ouro);
                break;
        }

        iih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"empresa "+plano,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void getPositionGPS() {

        LocationManager locationManager = (LocationManager)
                getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET},
                    0);

            return;
        }

        LocationListener locationListener = new chuter(getApplicationContext(),actmain.mapfrag,Double.parseDouble(lat),Double.parseDouble(lng));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:{
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mapFragment mmap = new mapFragment();
                    getPositionGPS();
                }else{

                }
            }
        }
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

}
