package construtora.model.entity;

public class Engenheiro extends Usuario {
    private String crea;
    
    // Constructor

    public Engenheiro(String crea, int id, String nome, String cpf, String telefone, String senha, String papel) {
        super(id, nome, cpf, telefone, senha, papel);
        this.setCrea(crea);
    }
    
    
    // Getters e setters
    public String getCrea() {
        return crea;
    }

    public void setCrea(String crea) {
        this.crea = crea;
    }

    public void setTipoServico(String nextLine) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
