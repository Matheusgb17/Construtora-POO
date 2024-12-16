package ifmg.construtora.poo;

import java.time.LocalDate;

public class Recebimento extends Transferencia {
    /* Descreve o pagamento de um cliente à construtora, ou seja, o recebimento
    de um pagamento de um cliente por parte da construtora. */
    
    private Cliente cliente;

    public Recebimento(Cliente cliente, int id, float valor, LocalDate data, Administrador administrador) {
        super(id, valor, data, administrador);
        this.setCliente(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
