package construtora.model.entity;

public class Obra {
    private int id;
    private String endereco;
    private String tipoObra;
    private String status;
    private Cliente cliente;
    
    // Constructor
    public Obra(int id, String endereco, String tipoObra, String status, Cliente cliente) {
        this.setId(id);
        this.setEndereco(endereco);
        this.setTipoObra(tipoObra);
        this.setStatus(status);
        this.setCliente(cliente);
    }

    @Override
    public String toString() {
        return "Obra{" + "id=" + id + ", endereco=" + endereco + ", tipoObra=" + tipoObra + ", status=" + status + '}';
    }
    
    
    
    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoObra() {
        return tipoObra;
    }

    public void setTipoObra(String tipoObra) {
        this.tipoObra = tipoObra;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
}
