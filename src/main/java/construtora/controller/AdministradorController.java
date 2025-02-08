package construtora.controller;

import construtora.model.entity.*;
import construtora.model.service.*;
import java.util.Scanner;
import utils.MenuUtils;
import utils.PasswordUtils;


public class AdministradorController {
    private Scanner scanner = new Scanner(System.in);
    private AdministradorService as = new AdministradorService();
    private ConstrutorService cs = new ConstrutorService();
    
    public void executarAcaoAdministrador (int opcao, Administrador administrador) {
        int opcaoSecundaria;
        
        switch (opcao) {
            case 1 -> { // Editar os próprios dados
                this.executarAcao1Administrador(administrador);
            }
            
            case 2 -> { // Gerenciar clientes
                do {
                    MenuUtils.administradorSubMenu2();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 6);
                    this.executarAcao2Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6);
            }
            
            case 3 -> { // Gerenciar construtores
                do {
                    MenuUtils.administradorSubMenu3();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 6);
                    this.executarAcao3Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6);
            }
            
            case 4 -> { // Gerenciar funcionários
                do {
                    MenuUtils.administradorSubMenu4();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 6);
                    this.executarAcao4Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6);
            }
            
            case 5 -> { // Gerenciar engenheiros
                do {
                    MenuUtils.administradorSubMenu5();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 6);
                    this.executarAcao5Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6);
            }
            
            case 6 -> { // Gerenciar obras
                do {
                    MenuUtils.administradorSubMenu6();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 4);
                    this.executarAcao6Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6);
            }
            
            case 7 -> { // Adicionar contrato
                /* do {
                    MenuUtils.administradorSubMenu7();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 7);
                    this.executarAcao7Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 6); */
            }
            
            case 8 -> { // Adicionar pagamento
                this.executarAcao8Administrador(administrador);
            }
            
            case 9 -> { // Adicionar recebimento
                
            }
            
            case 0 -> { // Sair
                System.out.println("Saindo!");
                System.exit(0);
            }
        }
    }
    
    public void executarAcao1Administrador (Administrador administrador) {
        System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

        System.out.print("Nome [" + administrador.getNome() + "]: ");
        String entrada = this.scanner.nextLine();
        if (!entrada.isEmpty()) administrador.setNome(entrada);
        
        System.out.print("CPF [" + administrador.getCpf()+ "]: ");
        entrada = this.scanner.nextLine();
        if (!entrada.isEmpty()) administrador.setCpf(entrada);
        
        System.out.print("Telefone [" + administrador.getTelefone() + "]: ");
        entrada = this.scanner.nextLine();
        if (!entrada.isEmpty()) administrador.setTelefone(entrada);
        
        System.out.print("Cargo [" + administrador.getCargo()+ "]: ");
        entrada = this.scanner.nextLine();
        if (!entrada.isEmpty()) administrador.setCargo(entrada);
        
        System.out.print("Senha [********]: ");
        entrada = this.scanner.nextLine();
        if (!entrada.isEmpty()) administrador.setSenha(PasswordUtils.criptografarSenha(entrada));
        
        as.atualizarAdministrador(administrador);
    }
    
    public void executarAcao2Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao3Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao4Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao5Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao6Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao7Administrador (int opcao, Administrador administrador) {
        
    }
    
    public void executarAcao8Administrador (Administrador administrador) {
        System.out.println("=== CRIANDO NOVO PAGAMENTO ===");
        
        System.out.print("CPF do construtor: ");
        String cpf = this.scanner.nextLine();
        Construtor c = cs.recuperarConstrutor(cpf);
        
        System.out.print("Valor do pagamento: ");
        float valor = this.scanner.nextFloat();
        
        System.out.print("Descrição do pagamento: ");
        this.scanner.nextLine();
        String desc = this.scanner.nextLine();
        
        as.gerarPagamento(administrador, c, valor, desc);
    }
    
    public void executarAcao9Administrador (int opcao, Administrador administrador) {
        
    }
}
