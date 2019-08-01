package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.R;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.act_informations;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database.DBHelper;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database.DBHelper;

/**
 * Created by JoHN on 08/05/2018.
 */

public class listaAdapter extends RecyclerView.Adapter<listaAdapter.myHolder> {

    private Context mContext;
    private List<empresa> lst = new ArrayList<>();
    private RequestOptions options;

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

    private DBHelper dbHelper;

    public listaAdapter(Context mContext, List<empresa> lst) {
        this.mContext = mContext;
        this.lst = lst;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        options = new RequestOptions()
                .circleCrop()
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.loading_img);

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.row_lista,parent,false);
        final listaAdapter.myHolder viewholder = new listaAdapter.myHolder(view);

        viewholder.coo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oneClickj(viewholder.list_name_id.getText().toString());
            }
        });

        return viewholder;
    }

    public void oneClickj(String nome){
        List<empresa> lst = new ArrayList<>();
        dbHelper = new DBHelper(mContext);
        lst = dbHelper.getData(md5(nome));
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
        id = md5(nome);
        plano = lst.get(0).getPlano();


        Intent informations = new Intent(mContext,act_informations.class);
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
        informations.putExtra("id",id);
        informations.putExtra("plano",plano);
        informations.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(informations);
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
    public void onBindViewHolder(myHolder holder, int position) {

        String subNome = lst.get(position).getNome();
        holder.list_name_id.setText(subNome);
        if(subNome.length() > 25){
            subNome = subNome.substring(0,25);
            subNome +="...";
        }else{
            subNome = subNome;
        }

        holder.lista_nome.setText(subNome);

        String subDescricao = removeHtml(lst.get(position).getDescricao());
        if(subDescricao.length() > 50){
            subDescricao = subDescricao.substring(0,100);
            subDescricao +="...";
        }else{
            subDescricao = subDescricao;
        }

        holder.lista_descricao.setText(subDescricao);

        Glide.with(mContext)
                .load(lst.get(position).getImagem())
                .apply(options)
                .into(holder.lista_img);

        switch (lst.get(position).getPlano()){
            case "bronze":
                holder.macaxeira.setBackgroundResource(R.mipmap.plano_bronze);
                break;
            case "prata":
                holder.macaxeira.setBackgroundResource(R.mipmap.plano_prata);
                break;
            case "ouro":
                holder.macaxeira.setBackgroundResource(R.mipmap.plano_ouro);
                break;
        }
    }

    private String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }

    class myHolder extends RecyclerView.ViewHolder{

        private ImageView lista_img;
        private TextView lista_nome,lista_descricao;
        private ConstraintLayout coo;
        private ImageView macaxeira;
        private TextView list_name_id;

        public myHolder(View itemView) {
            super(itemView);

            lista_img = (ImageView)itemView.findViewById(R.id.lista_img);
            lista_nome = (TextView)itemView.findViewById(R.id.lista_nome);
            lista_descricao = (TextView)itemView.findViewById(R.id.lista_descricao);
            coo = (ConstraintLayout)itemView.findViewById(R.id.lista_container);
            macaxeira = (ImageView)itemView.findViewById(R.id.macaxeira);
            list_name_id = (TextView)itemView.findViewById(R.id.list_name_id);

        }
    }
}
