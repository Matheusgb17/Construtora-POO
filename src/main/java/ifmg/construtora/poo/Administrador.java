    package ifmg.construtora.poo;

public class Administrador extends Usuario {
    private String cargo;

    // Constructor

    public Administrador(String cargo, int id, String nome, String cpf, String telefone, String senha, String papel) {
        super(id, nome, cpf, telefone, senha, papel);
        this.setCargo(cargo);
    }
    

    // Getters e setters
    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
