package ifmg.construtora.poo;

public class Funcionario extends Usuario {
    private String cargo;
    private Construtor construtor;
    
    // Constructor
    public Funcionario(String cargo, Construtor construtor, int id, String nome, String cpf, String telefone, String senha, String papel) {
        super(id, nome, cpf, telefone, senha, papel);
        this.setCargo(cargo);
        this.setConstrutor(construtor);
    }
    
    // Getters e setters
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Construtor getConstrutor() {
        return construtor;
    }

    public void setConstrutor(Construtor construtor) {
        this.construtor = construtor;
    }
}
