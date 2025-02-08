package construtora.controller;

import construtora.model.entity.Construtor;
import construtora.model.entity.Contrato;
import construtora.model.service.ConstrutorService;
import java.util.ArrayList;
import java.util.List;

public class ConstrutorController {
    private ConstrutorService cons = new ConstrutorService();
    private List<Contrato> contratos = new ArrayList<Contrato>();
    
    public void executarAcaoConstrutor (int opcao, Construtor construtor) {
        switch (opcao) {
            case 1 -> {
                this.contratos = cons.getContratosPorConstrutor(construtor);
                
                for (Contrato c : contratos) {
                    System.out.println("=== CONTRATO #" + c.getId() + " ===");
                    System.out.println("Valor: R$ " + c.getValor());
                    System.out.println("Engenheiro responsável: " + c.getEngenheiro().getNome() + " | CPF: " + c.getEngenheiro().getCpf());
                    System.out.println("Construtor responsável: " + c.getConstrutor().getNome() + " | CPF: " + c.getConstrutor().getCpf());
                    System.out.println("Obra: #" + c.getObra().getId());
                    System.out.println("Data de início do contrato: " + c.getDataInicio());
                    System.out.println("Data de finalização do contrato: " + c.getDataFim());
                    System.out.println("");
                }
            }
            
            case 0 -> {
                System.out.println("Saindo!");
                System.exit(0);
            }
        }
    }
}
