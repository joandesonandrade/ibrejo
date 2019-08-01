package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database.DBHelper;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.route;

public class mapFragment extends SupportMapFragment implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {

    public GoogleMap mMap;

    //**//
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
    private String plano;

    private int ca_index = 0;

    /**/
    private String url_api = "http://app.informabrejo.com.br/api.php";
    private String origens = "";
    private String destinations = "";
    private String url_api_maps = "";

    /*json*/
    private JsonArrayRequest arrayRequest;
    private RequestQueue requestQueue;
    private List<empresa> lstempresa = new ArrayList();
    List<route> mrr = new ArrayList<>();
    List<Polyline> polyline = new ArrayList<>();


    /**/
    // public static final String MY_PREFS_NAME = "map";
    private DBHelper dbHelper;

    /**/
    //private SharedPreferences pref;
    //private  String maps_file = "map";
    private String file_city = "city";
    private String nome_city;

    private int[] id_resource;

    public List<Marker> Supermaker = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);


        //*icones para o mapa <pinos>*//
        id_resource = new int[]{
                R.drawable.map_academia,
                R.drawable.map_agencia,
                R.drawable.map_artista,
                R.drawable.map_bar,
                R.drawable.map_bebidas,
                R.drawable.map_biju,
                R.drawable.map_bolo,
                R.drawable.map_buffet,
                R.drawable.map_calcados,
                R.drawable.map_carros,
                R.drawable.map_casa,
                R.drawable.map_cel,
                R.drawable.map_cerveja,
                R.drawable.map_classificado,
                R.drawable.map_clinica,
                R.drawable.map_comunicacao,
                R.drawable.map_contabilidade,
                R.drawable.map_cursos,
                R.drawable.map_doceria,
                R.drawable.map_engenho,
                R.drawable.map_esporte,
                R.drawable.map_estetica,
                R.drawable.map_evento,
                R.drawable.map_financeiras,
                R.drawable.map_fotos,
                R.drawable.map_gas,
                R.drawable.map_grafica,
                R.drawable.map_hotel,
                R.drawable.map_imobiliaria,
                R.drawable.map_juridico,
                R.drawable.map_laboratorio,
                R.drawable.map_lanches,
                R.drawable.map_lazer,
                R.drawable.map_loja_geral,
                R.drawable.map_optica,
                R.drawable.map_petshop,
                R.drawable.map_posto,
                R.drawable.map_publica,
                R.drawable.map_roupas,
                R.drawable.map_serigrafia,
                R.drawable.map_servicos,
                R.drawable.map_sorvete,
                R.drawable.map_supermercado,
                R.drawable.map_taxi,
                R.drawable.map_verdura,
                R.drawable.map_informabrejo
        };
    }

    private void jsonRequest(final GoogleMap mmap) {
        final SharedPreferences pref = getActivity().getSharedPreferences(file_city,Context.MODE_PRIVATE);
        if(pref.getString("nome",null) != null){
            nome_city = pref.getString("nome","");
        }
        url_api += "?city="+URLEncoder.encode(nome_city);
        arrayRequest = new JsonArrayRequest(url_api, new Response.Listener<JSONArray>() {
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
                        //**//
                        initMap(mmap, lstempresa, i);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }

                dbHelper = new DBHelper(getActivity());
                dbHelper.deleteAll();

                for (int i = 0; i < lstempresa.size(); i++) {

                    nome = lstempresa.get(i).getNome();
                    descricao = lstempresa.get(i).getDescricao();
                    telefone = lstempresa.get(i).getTelefone();
                    email = lstempresa.get(i).getEmail();
                    facebook = lstempresa.get(i).getFacebook();
                    instagram = lstempresa.get(i).getInstagram();
                    endereco = lstempresa.get(i).getEndereco();
                    imagem = lstempresa.get(i).getImagem();
                    lat = lstempresa.get(i).getLat();
                    lng = lstempresa.get(i).getLng();
                    id = md5(nome);
                    prefix = lstempresa.get(i).getPrefix();
                    id_category = lstempresa.get(i).getId_category();
                    plano = lstempresa.get(i).getPlano();


                    Boolean b = dbHelper.insertEmpresa(nome, descricao, telefone, email, facebook, instagram, endereco, imagem, lat.toString(), lng.toString(), id, prefix, id_category,plano);
          //          Log.d("insert",String.valueOf(b));
          //          Log.d("insert total", String.valueOf(dbHelper.numberOfRows()));
                    Log.d("dblog_geral",id_category);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "você possui um problema de conexão", Toast.LENGTH_LONG).show();
                offline(mmap);
                error.printStackTrace();
            }
        });


        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(arrayRequest);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void getPositionGPS(Context mcontext, Activity activity,mapFragment mapfragment) {

        LocationManager locationManager = (LocationManager)
                mcontext.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(mcontext,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mcontext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET},
                    0);

            return;
        }

        LocationListener locationListener = new MyLocationListener(mcontext,mapfragment);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(!isOnline()){
            Toast.makeText(getActivity(),"você está usando o modo offline",Toast.LENGTH_LONG).show();
            offline(googleMap);
        }else{
            jsonRequest(googleMap);
        }

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.raw_map));

            if (!success) {
               // Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            //Log.e(TAG, "Can't find style. Error: ", e);
        }



        /*if(pref.getString("lat",null) != null && pref.getString("lng",null) != null){
            initMapGps(googleMap,Double.parseDouble(pref.getString("lat","0")),Double.parseDouble(pref.getString("lng","0")));
        }*/
    }

    private void offline(GoogleMap googleMap) {
        List<empresa> lst = new ArrayList<>();

        dbHelper = new DBHelper(getActivity());
        lst = dbHelper.getAllEmpresas();

        for (int i=0;i<lst.size();i++){
            initMap(googleMap,lst,i);
        }

    }

    private void initMap(GoogleMap googleMap,List<empresa> info,int position) {
        mMap = googleMap;
        //mMap.setMinZoomPreference(15.0f);


        nome = info.get(position).getNome();
        descricao = info.get(position).getDescricao();
        telefone = info.get(position).getTelefone();
        email = info.get(position).getEmail();
        facebook = info.get(position).getFacebook();
        instagram = info.get(position).getInstagram();
        endereco = info.get(position).getEndereco();
        imagem = info.get(position).getImagem();
        lat = info.get(position).getLat();
        lng = info.get(position).getLng();
        id = md5(nome);
        prefix = info.get(position).getPrefix();

        switch (prefix){
            case "academia":
                ca_index = 0;
                break;
            case "agencia":
                ca_index = 1;
                break;
            case "artista":
                ca_index = 2;
                break;
            case "bar":
                ca_index = 3;
                break;
            case "bebidas":
                ca_index = 4;
                break;
            case "biju":
                ca_index = 5;
                break;
            case "bolos":
                ca_index = 6;
                break;
            case "buffet":
                ca_index = 7;
                break;
            case "calcados":
                ca_index = 8;
                break;
            case "carros":
                ca_index = 9;
                break;
            case "casa":
                ca_index = 10;
                break;
            case "cel":
                ca_index = 11;
                break;
            case "cerveja":
                ca_index = 12;
                break;
            case "classificado":
                ca_index = 13;
                break;
            case "clinica":
                ca_index = 14;
                break;
            case "comunicacao":
                ca_index = 15;
                break;
            case "contabilidade":
                ca_index = 16;
                break;
            case "cursos":
                ca_index = 17;
                break;
            case "docerias":
                ca_index = 18;
                break;
            case "engenho":
                ca_index = 19;
                break;
            case "esporte":
                ca_index = 20;
                break;
            case "estetica":
                ca_index = 21;
                break;
            case "evento":
                ca_index = 22;
                break;
            case "financeiras":
                ca_index = 23;
                break;
            case "fotos":
                ca_index = 24;
                break;
            case "gas":
                ca_index = 25;
                break;
            case "graficas":
                ca_index = 26;
                break;
            case "hotel":
                ca_index = 27;
                break;
            case "imobiliarias":
                ca_index = 28;
                break;
            case "juridico":
                ca_index = 29;
                break;
            case "laboratorio":
                ca_index = 30;
                break;
            case "lanches":
                ca_index = 31;
                break;
            case "lazer":
                ca_index = 32;
                break;
            case "lojas":
                ca_index = 33;
                break;
            case "otica":
                ca_index = 34;
                break;
            case "petshop":
                ca_index = 35;
                break;
            case "post":
                ca_index = 36;
                break;
            case "publica":
                ca_index = 37;
                break;
            case "roupas":
                ca_index = 38;
                break;
            case "serigrafia":
                ca_index = 39;
                break;
            case "servicos":
                ca_index = 40;
                break;
            case "sorvete":
                ca_index = 41;
                break;
            case "supermecados":
                ca_index = 42;
                break;
            case "taxi":
                ca_index = 43;
                break;
            case "verdura":
                ca_index = 44;
                break;
            case "sobrenos":
                ca_index = 45;
                break;
            default:
                ca_index = 0;
                break;
        }

        LatLng _map = new LatLng(lat, lng);
        Supermaker.add(mMap.addMarker(new MarkerOptions()
                .position(_map)
                .title(nome)
                .snippet(removeHtml(descricao))
                .icon(bitmapDescriptorFromVector(getActivity(),id_resource[ca_index]))));

        LatLng bananeiras = new LatLng(-6.754456, -35.633238);
        LatLng solanea    = new LatLng(-6.757966, -35.659171);
        LatLng guarabira  = new LatLng(-6.847667, -35.489049);

        /*if(nome_city == "Bananeiras"){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(bananeiras));
        }else if(nome_city == "Solânea"){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(solanea));
        }else if(nome_city == "Guarabira"){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(guarabira));
        }*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(_map));

        Log.d("dblog_ci",nome_city);

        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);


    }

    private String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
    }

    public Marker initMapGps(GoogleMap mmap, Double mlat,Double mlng){
        LatLng _map = new LatLng(mlat, mlng);
        Marker marker = mmap.addMarker(new MarkerOptions()
                .position(_map)
                .zIndex(1000)
                .title("minha posição")
                .snippet("essa é sua posição no momento.")
                .icon(bitmapDescriptorFromVector(getActivity(),R.drawable.ic_pino)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(_map));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //initRoute(mmap,_map,new LatLng(-6.7536008,-35.6879478));

        return marker;
    }

    public List<Polyline> initRoute(GoogleMap mmap, LatLng origem,LatLng destino){

        origens = String.valueOf(origem.latitude)+","+String.valueOf(origem.longitude);
        destinations = String.valueOf(destino.latitude)+","+String.valueOf(destino.longitude);
        origens = URLEncoder.encode(origens);
        destinations = URLEncoder.encode(destinations);

        url_api_maps = "https://maps.googleapis.com/maps/api/directions/json?origin="+origens+"&destination="+destinations+"&key=AIzaSyCWUY0817clRjFn6D9wIoKkb6qW9IszbPE";

        JsonObjectRequest obj = new JsonObjectRequest(url_api_maps, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray routers = response.getJSONArray("routes");

                    for(int i=0;i<routers.length();i++){

                        route rr = new route();
                        JSONObject route = routers.getJSONObject(i);
                        JSONArray legs = route.getJSONArray("legs");
                        JSONObject leg = legs.getJSONObject(0);
                        JSONObject overview_polyline = route.getJSONObject("overview_polyline");

                        //Log.d("dblog",overview_polyline.getString("points"));
                        rr.setPoints(decodePolyLine(overview_polyline.getString("points")));
                        mrr.add(rr);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dblog",error.toString());
            }
        });


        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(obj);

            /*polyline = mmap.addPolyline(new PolylineOptions().add(
                    origem,
                    destino
            ).width(5).color(Color.parseColor("#ac1c5a")));*/

            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(15)
                    .color(Color.parseColor("#ac1c5a"));


            for (int i=0;i<mrr.size();i++){
                for (int a = 0;a<mrr.get(i).getPoints().size();a++) {
                    polylineOptions.add(mrr.get(i).getPoints().get(a));
                    polyline.add(mmap.addPolyline(polylineOptions));
                }
            }

            mrr.clear();
            //Log.d("dblog",String.valueOf(mrr.size()));


        return polyline;

    }

    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
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


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onMapClick(LatLng latLng) {
        Toast.makeText(getContext(),latLng.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {



        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        List<empresa> lst = new ArrayList<>();
        dbHelper = new DBHelper(getActivity());
        lst = dbHelper.getData(md5(marker.getTitle()));
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


        Intent informations = new Intent(getActivity(),act_informations.class);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
