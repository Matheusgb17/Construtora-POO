package construtora.controller;

import construtora.model.entity.*;
import construtora.model.service.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EngenheiroController {
    private EngenheiroService es = new EngenheiroService();
    private List<Contrato> contratos = new ArrayList<>();
    
    public void executarAcaoEngenheiro (int opcao, Engenheiro engenheiro) {
        switch (opcao) {
            case 1 -> {
                this.contratos = es.getContratosPorEngenheiro(engenheiro);
                
                if (contratos.isEmpty()) {
                    System.out.println("Nenhum contrato encontrado para esse engenheiro!");
                }
                else {
                    for (Contrato c : contratos) {
                        System.out.println("=== CONTRATO #" + c.getId() + " ===");
                        System.out.println("Valor: R$ " + c.getValor());
                        System.out.println("Engenheiro responsável: " + c.getEngenheiro().getNome() + " | CPF: " + c.getEngenheiro().getCpf());
                        System.out.println("Construtor responsável: " + c.getConstrutor().getNome() + " | CPF: " + c.getConstrutor().getCpf());
                        System.out.println("Obra: #" + c.getObra().getId());
                        System.out.println("Data de início do contrato: " + c.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        System.out.println("Data de finalização do contrato: " + c.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                        System.out.println("");
                    }
                }
            }
            case 0 -> {
                System.out.println("Saindo!");
                System.exit(0);
            }
        }
    }
}
