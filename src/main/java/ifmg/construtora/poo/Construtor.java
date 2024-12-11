package ifmg.construtora.poo;

public class Construtor extends Usuario {
    public String tipoServico;

    // Constructor

    public Construtor(String tipoServico, int id, String nome, String cpf, String telefone, String senha, String papel) {
        super(id, nome, cpf, telefone, senha, papel);
        this.setTipoServico(tipoServico);
    }
    

    // Getters e setters
    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
    
}
