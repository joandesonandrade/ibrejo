package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;

/**
 * Created by JoHN on 11/05/2018.
 */

public class listaeventoadapter extends RecyclerView.Adapter<listaeventoadapter.myHolder> {

    private List<empresa> list = new ArrayList<>();
    private Context mContext;

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

    public listaeventoadapter(List<empresa> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public myHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.solid)
                .error(R.drawable.solid);

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.row_evento,parent,false);
        final listaeventoadapter.myHolder viewHolder = new listaeventoadapter.myHolder(view);

       /* viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"clicado",Toast.LENGTH_LONG).show();
            }
        }); */

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myHolder holder, final int position) {

        String subTitulo = list.get(position).getNome();
        if(subTitulo.length() > 17){
            subTitulo = subTitulo.substring(0,17);
            subTitulo +="...";
        }else{
            subTitulo = subTitulo;
        }

        holder.titulo.setText(subTitulo);

        String subDescricao = removeHtml(list.get(position).getDescricao());
        if(subDescricao.length() > 50){
            subDescricao = subDescricao.substring(0,50);
            subDescricao +="...";
        }else{
            subDescricao = subDescricao;
        }

        holder.descricao.setText(subDescricao);

        Glide.with(mContext)
                .load(list.get(position).getImagem())
                .apply(options)
                .into(holder.perfil);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nome = list.get(position).getNome();
                descricao = list.get(position).getDescricao();
                telefone = list.get(position).getTelefone();
                email = list.get(position).getEmail();
                facebook = list.get(position).getFacebook();
                instagram = list.get(position).getInstagram();
                endereco = list.get(position).getEndereco();
                imagem = list.get(position).getImagem();
                lat = list.get(position).getLat();
                lng = list.get(position).getLng();
                plano = list.get(position).getPlano();
                id = md5(nome);


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
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
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

    class myHolder extends RecyclerView.ViewHolder{

        private RelativeLayout relativeLayout;
        private ImageView perfil;
        private TextView titulo;
        private TextView descricao;

        public myHolder(View itemView) {
            super(itemView);

            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.box_evento);
            perfil = (ImageView)itemView.findViewById(R.id.evento_profile);
            titulo = (TextView)itemView.findViewById(R.id.evento_title);
            descricao = (TextView)itemView.findViewById(R.id.evento_descricao);

        }
    }

}
