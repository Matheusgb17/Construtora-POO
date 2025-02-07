package construtora.model.entity;

import static utils.PasswordUtils.criptografarSenha;

public class Usuario {
    // Atributos
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String senha;
    private String papel;

    // Constructor
    public Usuario(int id, String nome, String cpf, String telefone, String senha, String papel) {
        this.setId(id);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setTelefone(telefone);
        this.setSenha(senha);
        this.setPapel(papel);
    }
    public Usuario() {

    }



    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", telefone=" + telefone + ", senha=" + senha + ", papel=" + papel + '}';
    }
}
