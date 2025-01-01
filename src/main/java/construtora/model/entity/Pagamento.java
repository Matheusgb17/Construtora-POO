package construtora.model.entity;

import java.time.LocalDate;

public class Pagamento extends Transferencia {
    /* Descreve o pagamento da construtora a um construtor. */
    
    private Construtor construtor;

    // Constructor
    public Pagamento(Construtor construtor, int id, float valor, LocalDate data, Administrador administrador) {
        super(id, valor, data, administrador);
        this.setConstrutor(construtor);
    }
    
    // Getters e setters
    public Construtor getConstrutor() {
        return construtor;
    }

    public void setConstrutor(Construtor construtor) {
        this.construtor = construtor;
    }
    
}
