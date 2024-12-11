package ifmg.construtora.poo;

public class Engenheiro extends Usuario {
    private String crea;
    
    // Constructor
    public Engenheiro(String crea, int id, String nome, String cpf, String telefone, String senha) {
        super(id, nome, cpf, telefone, senha);
        this.setCrea(crea);
    }
    
    // Getters e setters
    public String getCrea() {
        return crea;
    }

    public void setCrea(String crea) {
        this.crea = crea;
    }
    
}
