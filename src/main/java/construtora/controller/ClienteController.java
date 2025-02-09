package construtora.controller;

import construtora.model.dao.ObraDAO;

import construtora.model.entity.Cliente;
import construtora.model.entity.Obra;

import construtora.model.service.ClienteService;
import construtora.model.service.ObraService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteController {
    
    ClienteService cs = new ClienteService();
    ObraService os = new ObraService();
    Scanner sc = new Scanner(System.in);
    ObraDAO od = new ObraDAO();
    
    public void executarAcaoCliente(int opcao, Cliente clienteLogado){
        
        switch(opcao){
            case 1:
                System.out.println("=== SOLICITANDO NOVA OBRA ===");
                
                System.out.println("Infome o endereço da obra: ");
                String endereco = this.sc.nextLine();
                System.out.println("Informe o tipo da obra: ");
                String tipo = this.sc.nextLine();
                
                cs.solicitarObra(clienteLogado, endereco, tipo);
                break;
            case 2: 
                System.out.println("=== OBRAS DO "+clienteLogado.getNome()+" ===");
                
                List<Obra> obras = new ArrayList<>();
                obras = cs.visualizarObrasCliente(clienteLogado.getCpf());
                
                for (Obra o : obras){
                    System.out.println("=== OBRA #" + o.getId()+ " ===");
                    System.out.println("Endereço: R$ " + o.getEndereco());
                    System.out.println("Tipo da obra: " + o.getTipoObra());
                    System.out.println("Status: "+ o.getStatus());
                    System.out.println("");
                }
                break;
            case 3: 
                System.out.println("=== EDITAR DADOS ===");
                
                System.out.println("Insira o id da obra que deseja editar: ");
                int id = this.sc.nextInt();
                Obra obra = os.retornarObraPorID(id);
                
                System.out.println("=== ATUALIZANDO DADOS DA OBRA #" + id + " ===");
                
                System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

                System.out.print("Endereço [" + obra.getEndereco() + "]: ");
                String entrada = this.sc.nextLine();
                if (!entrada.isEmpty()) obra.setEndereco(entrada);

                System.out.print("Tipo da obra [" + obra.getTipoObra()+ "]: ");
                entrada = this.sc.nextLine();
                if (!entrada.isEmpty()) obra.setTipoObra(entrada);

                os.atualizarObra(obra);
                
                break;
            case 0:
                System.out.println("Saindo...");
                System.exit(0);
                break;
        }
    }

}
