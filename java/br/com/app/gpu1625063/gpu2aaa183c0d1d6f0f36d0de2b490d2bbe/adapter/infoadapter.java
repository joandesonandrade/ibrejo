package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.act_facebook;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.informations;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.R;

/**
 * Created by JoHN on 29/04/2018.
 */

public class infoadapter extends RecyclerView.Adapter<infoadapter.myHolder> {

    private Context mContext;
    private List<informations> list;
    private Activity act;

    private String url_api = "http://app.informabrejo.com.br/img.php";
    private ImageView galeria1,galeria2,galeria3,galeria4,galeria5;
    private LinearLayout comment_client;
    private Button avaliar;
    private Button comment_login;
    private RequestOptions options;

    private String file_fb   = "facebook";

    private int zpontos;

    /*avaliacao*/
    ImageView z_profile;
    Button    z_button;

    private View Superview;

    private EditText editavaliar;

    private List<String> urls = new ArrayList<>();

    private ImageView grade1,grade2,grade3,grade4,grade5;
    private TextView tAvaliacao;

    public  infoadapter(){

    }

    public infoadapter(Context mContext,Activity act, List<informations> list) {
        this.mContext = mContext;
        this.list = list;
        this.act = act;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.row_empresa,parent,false);

        final myHolder viewholder = new myHolder(view);

        String iip = "Tel: "+list.get(0).getTelefone()+"\n E-mail: "+list.get(0).getEmail();

        switch (list.get(0).getPlano()){
            case "bronze":
                    viewholder.Chris.setVisibility(View.VISIBLE);
                    viewholder.antChris.setVisibility(View.GONE);
                    viewholder.Chris.setText(iip);
                break;
            case "prata":
                /*viewholder.Chris.setVisibility(View.VISIBLE);
                viewholder.antChris.setVisibility(View.GONE);
                viewholder.Chris.setText(iip);*/
                break;
            case "ouro":
                /*viewholder.Chris.setVisibility(View.VISIBLE);
                    viewholder.antChris.setVisibility(View.GONE);
                    viewholder.Chris.setText(iip);*/
                break;
        }

        viewholder.bt_telefone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(0).getTelefone()==null) {
                    Toast.makeText(mContext,"Telefone está vazio",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent iCall = new Intent(Intent.ACTION_DIAL);
                iCall.setData(Uri.parse("tel:"+list.get(0).getTelefone()));
                iCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(iCall);
            }
        });
        viewholder.bt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(0).getEmail()==null) {
                    Toast.makeText(mContext,"E-mail está vazio",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent iMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",list.get(0).getEmail(), null));
                iMail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(iMail);

            }
        });

        viewholder.bt_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(0).getFacebook()==null) {
                    Toast.makeText(mContext,"Facebook está vazio",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent webfacebook = new Intent(Intent.ACTION_VIEW);
                webfacebook.setData(Uri.parse(list.get(0).getFacebook()));
                webfacebook.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webfacebook);
            }
        });

        viewholder.bt_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(0).getInstagram()==null) {
                    Toast.makeText(mContext,"Instagram está vazio",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent webinstagram = new Intent(Intent.ACTION_VIEW);
                webinstagram.setData(Uri.parse(list.get(0).getInstagram()));
                webinstagram.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(webinstagram);
            }
        });


        /**/
        //Log.d("dblog_size",String.valueOf(urls.size()));

        return viewholder;
    }

    void showDialog(String url){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(act);
        View mView = act.getLayoutInflater().inflate(R.layout.row_dialog_image,null);
        mBuilder.setView(mView);

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.solid)
                .error(R.drawable.solid);

        ImageView vii = (ImageView)mView.findViewById(R.id.ze_das_novinhas);
        ImageView bt_close = (ImageView)mView.findViewById(R.id.dig_img_close);

        Glide.with(mContext)
                .load(url)
                .apply(options)
                .into(vii);


        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void onBindViewHolder(myHolder holder, int position) {


        //holder.telefone.setText(list.get(position).getTelefone()+" | "+list.get(position).getEmail());
        //holder.endereco.setText(list.get(position).getEndereco());
        holder.descricao.setHtml(list.get(position).getDescricao(),
                new HtmlResImageGetter(holder.descricao));

 //

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class myHolder extends RecyclerView.ViewHolder{

        TextView telefone,endereco;
        HtmlTextView descricao;
        ImageView bt_telefone,bt_email,bt_facebook,bt_instagram;

        /**/
        LinearLayout antChris;
        TextView Chris;


        public myHolder(View itemView) {
            super(itemView);

            setSuperview(itemView);

            //telefone = (TextView)itemView.findViewById(R.id.tn_telefone);
            //endereco = (TextView)itemView.findViewById(R.id.tn_endereco);
            descricao = (HtmlTextView)itemView.findViewById(R.id.tn_descricao);
            bt_telefone = (ImageView)itemView.findViewById(R.id.bt_call);
            bt_email = (ImageView)itemView.findViewById(R.id.bt_email);
            bt_facebook = (ImageView)itemView.findViewById(R.id.bt_facebook);
            bt_instagram = (ImageView)itemView.findViewById(R.id.bt_instagram);

            /**/
            grade1 = (ImageView)itemView.findViewById(R.id.bt_grade_1);
            grade2 = (ImageView)itemView.findViewById(R.id.bt_grade_2);
            grade3 = (ImageView)itemView.findViewById(R.id.bt_grade_3);
            grade4 = (ImageView)itemView.findViewById(R.id.bt_grade_4);
            grade5 = (ImageView)itemView.findViewById(R.id.bt_grade_5);
            tAvaliacao = (TextView)itemView.findViewById(R.id.meta_avaliar);

            /**/
            antChris = (LinearLayout)itemView.findViewById(R.id.ant_chris);
            Chris    = (TextView)itemView.findViewById(R.id.chris);

            /**/
            galeria1 = (ImageView)itemView.findViewById(R.id.galeria_1);
            galeria2 = (ImageView)itemView.findViewById(R.id.galeria_2);
            galeria3 = (ImageView)itemView.findViewById(R.id.galeria_3);
            galeria4 = (ImageView)itemView.findViewById(R.id.galeria_4);
            galeria5 = (ImageView)itemView.findViewById(R.id.galeria_5);

            z_profile = (ImageView)itemView.findViewById(R.id.image_avaliar);
            z_button  = (Button)itemView.findViewById(R.id.avaliar);

            comment_client = (LinearLayout)itemView.findViewById(R.id.user_cliente);
            avaliar        = (Button)itemView.findViewById(R.id.avaliar);
            comment_login  = (Button)itemView.findViewById(R.id.comment_login);

            editavaliar    = (EditText)itemView.findViewById(R.id.comment_edit);

            CreateGallery();
            CreateCategory();
            GetAvaliacoes(itemView);

        }
    }

    private void GetAvaliacoes(final View v) {

        String url_comment_api = "http://app.informabrejo.com.br/fuzil.php";
        url_comment_api+="?id="+list.get(0).getId();

        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, url_comment_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });*/



        JsonArrayRequest request = new JsonArrayRequest(url_comment_api, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj = null;
                Double totalPontos = 0.0;
                int totaluser   = 0;

                totaluser = response.length();
                for (int i=0; i<response.length(); i++){
                    try {
                        obj = response.getJSONObject(i);

                        RelativeLayout empresaContainer = (RelativeLayout)v.findViewById(R.id.empresa_container);
                        LayoutInflater linflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LinearLayout fuzil = (LinearLayout)empresaContainer.findViewById(R.id.fuzil);
                        View customView = linflater.inflate(R.layout.row_comment, null);


                        RequestOptions CAoptions = new RequestOptions()
                                .circleCrop()
                                .placeholder(R.drawable.loading_img)
                                .error(R.drawable.loading_img);

                        TextView nome_user = customView.findViewById(R.id.comentario_nome);
                        TextView comentario = customView.findViewById(R.id.comentario_texto);
                        ImageView image_user = customView.findViewById(R.id.comentario_foto);

                        nome_user.setText(obj.getString("usuario"));
                        comentario.setText(obj.getString("texto"));

                        ImageView sm1,sm2,sm3,sm4,sm5;
                        sm1 = (ImageView)customView.findViewById(R.id.sm_g_1);
                        sm2 = (ImageView)customView.findViewById(R.id.sm_g_2);
                        sm3 = (ImageView)customView.findViewById(R.id.sm_g_3);
                        sm4 = (ImageView)customView.findViewById(R.id.sm_g_4);
                        sm5 = (ImageView)customView.findViewById(R.id.sm_g_5);

                        List<ImageView> sm = new ArrayList<>();
                        sm.add(sm1);
                        sm.add(sm2);
                        sm.add(sm3);
                        sm.add(sm4);
                        sm.add(sm5);

                        for (int ic=0;ic<Integer.parseInt(obj.getString("pontos"));ic++){
                            sm.get(ic).setVisibility(View.VISIBLE);
                        }

                        totalPontos +=Integer.parseInt(obj.getString("pontos"));

                        String url_perfil_img = "https://graph.facebook.com/"+obj.getString("id_user")+"/picture/?width=200&height=200";

                        Glide.with(mContext)
                                .load(url_perfil_img)
                                .apply(CAoptions)
                                .into(image_user);

                        fuzil.addView(customView);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                somaModeFuzil(totalPontos,totaluser);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("dblog_erro",String.valueOf(error.getMessage()));
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    private void somaModeFuzil(Double pontos,int all){
        Double total = pontos / all;
        int index = 0;

        if(pontos == 0 || pontos < 0 || pontos == null)
            total = 5.0;

        List<ImageView> grade = new ArrayList<>();
        grade.add(grade1);
        grade.add(grade2);
        grade.add(grade3);
        grade.add(grade4);
        grade.add(grade5);

        for (int ic = 0;ic<5;ic++){
            grade.get(ic).setImageDrawable(mContext.getResources().getDrawable(R.drawable.grade_pr_normal));
        }

        if(total >= 1 && total < 2){
            index = 1;
        }else if(total >= 2 && total < 3){
            index = 2;
        }else if(total >= 3 && total < 4){
            index = 3;
        }else if(total >= 4 && total < 5){
            index = 4;
        }else if(total >= 5){
            index = 5;
        }

        for (int ic = 0; ic < index; ic++){
            grade.get(ic).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_grade));
        }

        tAvaliacao.setText(String.valueOf(total).replace(".",",").substring(0,3));

    }

    private void CreateCategory(){

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        Boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if(!isLoggedIn) {

            comment_client.setVisibility(View.GONE);
            avaliar.setVisibility(View.GONE);
            comment_login.setVisibility(View.VISIBLE);

            comment_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /**/
                    comment_client.setVisibility(View.VISIBLE);
                    avaliar.setVisibility(View.VISIBLE);
                    comment_login.setVisibility(View.GONE);
                    /**/

                    Intent i = new Intent(mContext,act_facebook.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(i);
                }
            });

        }


        RequestOptions CAoptions = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.loading_img);

        String url_img = "";
        SharedPreferences FBpref = mContext.getSharedPreferences(file_fb,mContext.MODE_PRIVATE);
        if(FBpref.getString("fb_image","")!=""){
            url_img = FBpref.getString("fb_image","");
        }


        Glide.with(mContext)
                .load(url_img)
                .apply(CAoptions)
                .into(z_profile);

        avaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                SendComment();
            }
        });

    }

    private void SendComment(){
        if(editavaliar.length()>200){
            Toast.makeText(mContext,"seu texto ultrapassa o limite de 200 caracteres",Toast.LENGTH_LONG).show();
        }

        String comment = editavaliar.getText().toString();

        editavaliar.setText("");
        //Toast.makeText(mContext,"AÇÃO PARA COMENTAR NESSA EMPRESA",Toast.LENGTH_LONG).show();

        ShowDialogComment(comment);
    }

    private void ShowDialogComment(final String comment){
        AlertDialog.Builder DigComment = new AlertDialog.Builder(act);
        View view = act.getLayoutInflater().inflate(R.layout.dig_avaliar,null,false);

        TextView mcText = (TextView)view.findViewById(R.id.cm_texto);
        mcText.setText(comment);

        ImageView mcGrade1,mcGrade2,mcGrade3,mcGrade4,mcGrade5;

        mcGrade1 = (ImageView)view.findViewById(R.id.cm_bt_grade_1);
        mcGrade2 = (ImageView)view.findViewById(R.id.cm_bt_grade_2);
        mcGrade3 = (ImageView)view.findViewById(R.id.cm_bt_grade_3);
        mcGrade4 = (ImageView)view.findViewById(R.id.cm_bt_grade_4);
        mcGrade5 = (ImageView)view.findViewById(R.id.cm_bt_grade_5);

        final List<ImageView> mcgrade = new ArrayList<>();
        mcgrade.add(mcGrade1);
        mcgrade.add(mcGrade2);
        mcgrade.add(mcGrade3);
        mcgrade.add(mcGrade4);
        mcgrade.add(mcGrade5);

        int grafinal = 0;

        for(int ic=0;ic<mcgrade.size();ic++){
            final int zz = (ic+1);
            grafinal = zz;
            mcgrade.get(ic).setImageDrawable(mContext.getResources().getDrawable(R.drawable.grade_pr_big));
            mcgrade.get(ic).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    setZpontos(zz);

                    for (int z=0;z<mcgrade.size();z++){
                        mcgrade.get(z).setImageDrawable(mContext.getResources().getDrawable(R.drawable.grade_pr_big));
                    }

                    for (int z=0;z<zz;z++){
                        mcgrade.get(z).setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_grade_big));
                    }
                }
            });
        }

        DigComment.setView(view);
        final AlertDialog dig = DigComment.create();

        final Button btSend = (Button)view.findViewById(R.id.cm_bt);
        final String finalcomment = comment;
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("dblog_fuzil",String.valueOf(getZpontos()));

                if(getZpontos() == 0){
                    Toast.makeText(mContext,"Selecione as estrelas",Toast.LENGTH_LONG).show();
                }
                else
                {
                    dig.cancel();
                    mandandoFuzil(getZpontos(), finalcomment);
                }
            }
        });

        dig.show();
    }

    public int getZpontos() {
        return zpontos;
    }

    public void setZpontos(int zpontos) {
        this.zpontos = zpontos;
    }

    public View getSuperview() {
        return Superview;
    }

    public void setSuperview(View superview) {
        Superview = superview;
    }

    private void mandandoFuzil(final int pontos, final String comentario) {
        String url = "http://app.informabrejo.com.br/avaliar.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                RelativeLayout empresaContainer = (RelativeLayout)getSuperview().findViewById(R.id.empresa_container);
                LinearLayout fuzil = (LinearLayout)empresaContainer.findViewById(R.id.fuzil);
                fuzil.removeAllViews();

                GetAvaliacoes(getSuperview());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){

                SharedPreferences FBpref = mContext.getSharedPreferences(file_fb,mContext.MODE_PRIVATE);

                Map<String,String> params = new HashMap<String, String>();
                params.put("usuario",FBpref.getString("fb_name",""));
                params.put("id_user",FBpref.getString("fb_id",""));
                params.put("id_empresa",list.get(0).getId());
                params.put("texto",comentario);
                params.put("pontos",String.valueOf(pontos));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(request);
    }

    private void CreateGallery() {

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.solid)
                .error(R.drawable.solid);

        final List<ImageView> imgviewlist = new ArrayList<>();
        imgviewlist.add(galeria1);
        imgviewlist.add(galeria2);
        imgviewlist.add(galeria3);
        imgviewlist.add(galeria4);
        imgviewlist.add(galeria5);

       url_api+="?id="+list.get(0).getId();
        //Log.d("url",url_api);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url_api, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        if(jsonObject.getString("url") != "" || jsonObject.getString("url") != null) {

                            //imgviewlist.get(i).setVisibility(View.VISIBLE);

                            Glide.with(mContext)
                                    .load(jsonObject.getString("url"))
                                    .apply(options)
                                    .into(imgviewlist.get(i));

                        }

                        urls.add(jsonObject.getString("url"));

                        Log.d("dblog_size",String.valueOf(urls.size()));

                        final int size = urls.size();

                        if(urls.size() > 0) {
                            galeria1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(size >= 1) {
                                        showDialog(urls.get(0));
                                    }else{
                                        Toast.makeText(mContext,"Mídia vazia",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            galeria2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(size >= 2) {
                                        showDialog(urls.get(1));
                                    }else{
                                        Toast.makeText(mContext,"Mídia vazia",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            galeria3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(size >= 3) {
                                        showDialog(urls.get(2));
                                    }else{
                                        Toast.makeText(mContext,"Mídia vazia",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            galeria4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(size >= 4) {
                                        showDialog(urls.get(3));
                                    }else{
                                        Toast.makeText(mContext,"Mídia vazia",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            galeria5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(size >= 5) {
                                        showDialog(urls.get(4));
                                    }else{
                                        Toast.makeText(mContext,"Mídia vazia",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    } catch (JSONException e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(mContext, "você possui um problema de conexão", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.add(arrayRequest);

    }
}
