package construtora.model.entity;

public class Cliente extends Usuario {
    public String status;

    // Constructor

    public Cliente(String status, int id, String nome, String cpf, String telefone, String senha, String papel) {
        super(id, nome, cpf, telefone, senha, papel);
        this.setStatus(status);
    }
    
    
    // Getters e setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
