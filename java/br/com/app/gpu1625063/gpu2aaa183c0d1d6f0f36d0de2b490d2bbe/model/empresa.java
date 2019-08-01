package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model;

/**
 * Created by JoHN on 29/04/2018.
 */

public class empresa {
    private String nome;
    private String descricao;
    private String telefone;
    private String email;
    private String facebook;
    private String instagram;
    private String endereco;
    private String imagem;
    private double lat;
    private double lng;
    private String prefix;
    private String id_category;
    private String plano;

    public  empresa(){

    }

    public empresa(String nome, String descricao, String telefone, String email, String facebook, String instagram, String endereco, String imagem, double lat, double lng, String prefix, String id_category, String plano) {
        this.nome = nome;
        this.descricao = descricao;
        this.telefone = telefone;
        this.email = email;
        this.facebook = facebook;
        this.instagram = instagram;
        this.endereco = endereco;
        this.imagem = imagem;
        this.lat = lat;
        this.lng = lng;
        this.prefix = prefix;
        this.id_category = id_category;
        this.plano = plano;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
