package ifmg.construtora.poo;

import java.time.LocalDate;

public class Transferencia {
    private int id;
    private float valor;
    private LocalDate data;
    private Administrador administrador;
    
    // Constructor

    public Transferencia(int id, float valor, LocalDate data) {
        this.setId(id);
        this.setValor(valor);
        this.setData(data);
        this.setAdministrador(administrador);
    }
    
    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
