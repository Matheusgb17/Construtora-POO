package ifmg.construtora.poo;

public class Obra {
    private int id;
    private String endereco;
    private String tipoObra;
    
    // Constructor
    public Obra(int id, String endereco, String tipoObra) {
        this.setId(id);
        this.setEndereco(endereco);
        this.setTipoObra(tipoObra);
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
    
}
