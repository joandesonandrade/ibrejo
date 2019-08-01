package br.com.app.gpu1625063.gpu2aaa183c0d1d6f0f36d0de2b490d2bbe.model;

/**
 * Created by JoHN on 29/04/2018.
 */

public class informations {
    private String telefone;
    private String endereco;
    private String descricao;
    private String email;
    private String facebook;
    private String instagram;
    private  String nome;
    private String id;
    private String plano;

    public informations(){

    }


    public informations(String telefone, String endereco, String descricao, String email, String facebook, String instagram,String nome,String id, String plano) {
        this.telefone = telefone;
        this.endereco = endereco;
        this.descricao = descricao;
        this.email = email;
        this.facebook = facebook;
        this.instagram = instagram;
        this.nome = nome;
        this.id = id;
        this.plano = plano;
    }


    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getEmail() {
        return email;
    }

    public String getFacebook() {
        return facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
