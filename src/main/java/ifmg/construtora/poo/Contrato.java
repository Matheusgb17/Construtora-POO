package ifmg.construtora.poo;

import java.time.LocalDate;

public class Contrato {
    private int id;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private float valor;
    private Engenheiro engenheiro;
    private Construtor construtor;
    private Obra obra;
    
    // Constructor
    public Contrato(int id, LocalDate dataInicio, LocalDate dataFim, float valor, Engenheiro engenheiro, Construtor construtor, Obra obra) {
        this.setId(id);
        this.setDataInicio(dataInicio);
        this.setDataFim(dataFim);
        this.setValor(valor);
        this.setEngenheiro(engenheiro);
        this.setConstrutor(construtor);
        this.setObra(obra);
    }
    
    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Engenheiro getEngenheiro() {
        return engenheiro;
    }

    public void setEngenheiro(Engenheiro engenheiro) {
        this.engenheiro = engenheiro;
    }

    public Construtor getConstrutor() {
        return construtor;
    }

    public void setConstrutor(Construtor construtor) {
        this.construtor = construtor;
    }

    public Obra getObra() {
        return obra;
    }

    public void setObra(Obra obra) {
        this.obra = obra;
    }
}
