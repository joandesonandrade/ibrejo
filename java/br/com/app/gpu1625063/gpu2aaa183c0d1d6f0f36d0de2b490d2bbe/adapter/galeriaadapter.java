package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.R;
import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.galeria;


/**
 * Created by JoHN on 02/05/2018.
 */

public class galeriaadapter extends RecyclerView.Adapter<galeriaadapter.myholder> {

    private Context mContext;
    private List<galeria> galeria;
    private RequestOptions options;

    public galeriaadapter(){

    }

    public galeriaadapter(Context mContext, List<br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.galeria> galeria) {
        this.mContext = mContext;
        this.galeria = galeria;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.row_image,parent,false);

        return new myholder(view);
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading_img)
                .error(R.drawable.loading_img);

        Glide.with(mContext)
                .load(galeria.get(position).getUrl())
                .apply(options)
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return galeria.size();
    }

    public class myholder extends RecyclerView.ViewHolder{

        ImageView image;


        public myholder(View itemView) {
            super(itemView);

            //image = (ImageView)itemView.findViewById(R.id.galeria);

        }
    }


}
