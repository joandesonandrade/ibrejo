package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model.empresa;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "map.db";
    public static final String TABLE_NAME = "empresas";
    public static final String COLUMN_nome = "nome";
    public static final String COLUMN_descricao = "descricao";
    public static final String COLUMN_telefone = "telefone";
    public static final String COLUMN_email = "email";
    public static final String COLUMN_facebook = "facebook";
    public static final String COLUMN_instagram = "instagram";
    public static final String COLUMN_endereco = "endereco";
    public static final String COLUMN_imagem = "imagem";
    public static final String COLUMN_lat = "lat";
    public static final String COLUMN_lng = "lng";
    public static final String COLUMN_id = "id";
    public static final String COLUMN_prefix = "prefix";
    public static final String COLUMN_id_category = "id_category";
    public static final String COLUMN_plano = "plano";
    private HashMap hp;
    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table empresas " +
                        "(id text,ai integer primary key, nome text,descricao text,telefone text,email text,facebook text,instagram text,endereco text,imagem text,lat text,lng text,prefix text, id_category text, plano text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS empresas");
        onCreate(db);
    }

    public boolean insertEmpresa (String nome, String descricao, String telefone, String email,String facebook,String instagram,String endereco,String imagem, String lat, String lng, String id,String prefix, String id_category, String plano) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", nome);
        contentValues.put("descricao", descricao);
        contentValues.put("telefone", telefone);
        contentValues.put("email", email);
        contentValues.put("facebook", facebook);
        contentValues.put("instagram", instagram);
        contentValues.put("endereco", endereco);
        contentValues.put("imagem", imagem);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        contentValues.put("id", id);
        contentValues.put("prefix",prefix);
        contentValues.put("id_category",id_category);
        contentValues.put("plano",plano);
        db.insert("empresas", null, contentValues);
        return true;
    }

    public List<empresa> search(String query){
        List<empresa> lst = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from empresas where nome like '%"+query+"%'",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                empresa info = new empresa();
                info.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_nome)));
                info.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_descricao)));
                info.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_telefone)));
                info.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_email)));
                info.setFacebook(cursor.getString(cursor.getColumnIndex(COLUMN_facebook)));
                info.setInstagram(cursor.getString(cursor.getColumnIndex(COLUMN_instagram)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setImagem(cursor.getString(cursor.getColumnIndex(COLUMN_imagem)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lat))));
                info.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lng))));
                info.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_prefix)));
                info.setId_category(cursor.getString(cursor.getColumnIndex(COLUMN_id_category)));
                info.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_plano)));

                lst.add(info);

                cursor.moveToNext();

            }
        }

        return lst;
    }


    public List<empresa> getData(String id) {

        List<empresa> lst = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from empresas where id='"+id+"'", null );

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                empresa info = new empresa();
                info.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_nome)));
                info.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_descricao)));
                info.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_telefone)));
                info.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_email)));
                info.setFacebook(cursor.getString(cursor.getColumnIndex(COLUMN_facebook)));
                info.setInstagram(cursor.getString(cursor.getColumnIndex(COLUMN_instagram)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setImagem(cursor.getString(cursor.getColumnIndex(COLUMN_imagem)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lat))));
                info.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lng))));
                info.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_prefix)));
                info.setId_category(cursor.getString(cursor.getColumnIndex(COLUMN_id_category)));
                info.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_plano)));

                lst.add(info);

                cursor.moveToNext();

            }
        }
        return lst;
    }

    public List<empresa> getDataCategory(String id) {

        List<empresa> lst = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from empresas where id_category='"+id+"'", null );


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                empresa info = new empresa();
                info.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_nome)));
                info.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_descricao)));
                info.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_telefone)));
                info.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_email)));
                info.setFacebook(cursor.getString(cursor.getColumnIndex(COLUMN_facebook)));
                info.setInstagram(cursor.getString(cursor.getColumnIndex(COLUMN_instagram)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setImagem(cursor.getString(cursor.getColumnIndex(COLUMN_imagem)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lat))));
                info.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lng))));
                info.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_prefix)));
                info.setId_category(cursor.getString(cursor.getColumnIndex(COLUMN_id_category)));
                info.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_plano)));

               // Log.d("dblog",info.getNome());

                lst.add(info);

                cursor.moveToNext();

            }
        }
        return lst;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateEmpresa (String nome, String descricao, String telefone, String email,String facebook,String instagram,String endereco,String imagem, String lat, String lng, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", nome);
        contentValues.put("descricao", descricao);
        contentValues.put("telefone", telefone);
        contentValues.put("email", email);
        contentValues.put("facebook", facebook);
        contentValues.put("instagram", instagram);
        contentValues.put("endereco", endereco);
        contentValues.put("imagem", imagem);
        contentValues.put("lat", lat);
        contentValues.put("lng", lng);
        contentValues.put("id", id);
        db.update("empresas", contentValues, "id = ? ", new String[] { id } );
        return true;
    }

    public Integer deleteEmpresa (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("empresas",
                "id = ? ",
                new String[] { id });
    }

    public Boolean deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);

        return true;
    }

    public List<empresa> getAllEmpresas() {
        List<empresa> lst = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from empresas",null);
        cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                empresa info = new empresa();
                info.setNome(cursor.getString(cursor.getColumnIndex(COLUMN_nome)));
                info.setDescricao(cursor.getString(cursor.getColumnIndex(COLUMN_descricao)));
                info.setTelefone(cursor.getString(cursor.getColumnIndex(COLUMN_telefone)));
                info.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_email)));
                info.setFacebook(cursor.getString(cursor.getColumnIndex(COLUMN_facebook)));
                info.setInstagram(cursor.getString(cursor.getColumnIndex(COLUMN_instagram)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setImagem(cursor.getString(cursor.getColumnIndex(COLUMN_imagem)));
                info.setEndereco(cursor.getString(cursor.getColumnIndex(COLUMN_endereco)));
                info.setLat(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lat))));
                info.setLng(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_lng))));
                info.setPrefix(cursor.getString(cursor.getColumnIndex(COLUMN_prefix)));
                info.setId_category(cursor.getString(cursor.getColumnIndex(COLUMN_id_category)));
                info.setPlano(cursor.getString(cursor.getColumnIndex(COLUMN_plano)));

                lst.add(info);

                cursor.moveToNext();

            }
        }
        return lst;
    }
}
