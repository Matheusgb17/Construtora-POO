package utils;

import java.util.Scanner;

public class MenuUtils {
    private static Scanner scanner = new Scanner(System.in);
    
    public static void construtorMainMenu () {
        System.out.println("===== CONSTRUTOR =====");
        System.out.println("1 - Verificar contratos");
        System.out.println("0 - Sair");
    }
    
    public static void funcionarioMainMenu () {
        System.out.println("===== FUNCIONÁRIO =====");
        System.out.println("1 - Verificar contratos");
        System.out.println("0 - Sair");
    }
    
    public static void engenheiroMainMenu () {
        System.out.println("===== ENGENHEIRO =====");
        System.out.println("1 - Verificar contratos");
        System.out.println("0 - Sair");
    }
    
    public static void clienteMainMenu () {
        System.out.println("===== CLIENTE =====");
        System.out.println("1 - Solicitar obra");
        System.out.println("2 - Visualizar obras");
        System.out.println("3 - Editar dados");
        System.out.println("0 - Sair");
    }
    
    public static void administradorMainMenu () {
        System.out.println("===== ADMINISTRADOR =====");
        System.out.println("1 - Editar dados");
        System.out.println("2 - Gerenciar clientes");
        System.out.println("3 - Gerenciar construtores");
        System.out.println("4 - Gerenciar funcionários");
        System.out.println("5 - Gerenciar engenheiros");
        System.out.println("6 - Gerenciar obras");
        System.out.println("7 - Adicionar contrato");
        System.out.println("8 - Adicionar pagamento");
        System.out.println("9 - Adicionar recebimento");
        System.out.println("0 - Sair");
    }
    
    public static void administradorSubMenu2 () {
        System.out.println("===== GERENCIAR CLIENTES =====");
        System.out.println("1 - Cadastrar cliente");
        System.out.println("2 - Editar cliente");
        System.out.println("3 - Deletar cliente");
        System.out.println("4 - Buscar cliente por CPF");
        System.out.println("5 - Listar todos os clientes");
        System.out.println("6 - Voltar");
    }
    
    public static void administradorSubMenu3 () {
        System.out.println("===== GERENCIAR CONSTRUTORES =====");
        System.out.println("1 - Cadastrar construtor");
        System.out.println("2 - Editar construtor");
        System.out.println("3 - Deletar construtor");
        System.out.println("4 - Buscar construtor por CPF");
        System.out.println("5 - Listar todos os construtores");
        System.out.println("6 - Voltar");
    }
    
    public static void administradorSubMenu4 () {
        System.out.println("===== GERENCIAR FUNCIONÁRIOS =====");
        System.out.println("1 - Cadastrar funcionário");
        System.out.println("2 - Editar funcionário");
        System.out.println("3 - Deletar funcionário");
        System.out.println("4 - Buscar funcionário por CPF");
        System.out.println("5 - Listar todos os funcionários");
        System.out.println("6 - Voltar");
    }
    
    public static void administradorSubMenu5 () {
        System.out.println("===== GERENCIAR ENGENHEIROS =====");
        System.out.println("1 - Cadastrar engenheiro");
        System.out.println("2 - Editar engenheiro");
        System.out.println("3 - Deletar engenheiro");
        System.out.println("4 - Buscar engenheiro por CPF");
        System.out.println("5 - Listar todos os engenheiros");
        System.out.println("6 - Voltar");
    }
    
    public static void administradorSubMenu6 () {
        System.out.println("===== GERENCIAR OBRAS =====");
        System.out.println("1 - Cadastrar obra");
        System.out.println("2 - Aprovar/Reprovar obra");
        System.out.println("3 - Buscar obra por ID");
        System.out.println("4 - Buscar obras por cliente");
        System.out.println("5 - Listar obras não aprovadas");
        System.out.println("6 - Listas obras aprovadas");
        System.out.println("7 - Listar obras sob revisão");
        System.out.println("8 - Voltar");
    }
    
    public static void administradorSubMenu7 () {
        System.out.println("===== GERENCIAR CONTRATOS =====");
        System.out.println("1 - Cadastrar contrato");
        System.out.println("2 - Listar contratos finalizados");
        System.out.println("3 - Listar contratos em andamento");
        System.out.println("4 - Listar contratos futuros");
        System.out.println("5 - Voltar");
    }
    
    /*public static void administradorSubMenu8 () {
        System.out.println("===== GERENCIAR PAGAMENTOS =====");
        System.out.println("1 - Cadastrar pagamento");
        System.out.println("2 - Editar pagamento");
        System.out.println("3 - Listar pagamentos");
        System.out.println("4 - Voltar");
    }
    
    public static void administradorSubMenu9 () {
        System.out.println("===== GERENCIAR RECEBIMENTOS =====");
        System.out.println("1 - Cadastrar recebimento");
        System.out.println("2 - Editar recebimento");
        System.out.println("3 - Listar recebimento");
        System.out.println("4 - Voltar");
    } */
    
    public static int selecionarOpcao (int primeiraOpcao, int ultimaOpcao) {
        int opcao;
        
        do {
            System.out.print("Digite: ");
            opcao = MenuUtils.scanner.nextInt();
            
            if (opcao < primeiraOpcao || opcao > ultimaOpcao) {
                System.out.println("===== OPÇÃO INVÁLIDA =====");
            }
        }
        while(opcao < primeiraOpcao || opcao > ultimaOpcao);
        
        return opcao;
    }
}