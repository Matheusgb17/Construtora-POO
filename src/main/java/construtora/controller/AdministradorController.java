package construtora.controller;

import construtora.model.entity.*;
import construtora.model.service.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.MenuUtils;
import utils.PasswordUtils;


public class AdministradorController {
    private Scanner scanner = new Scanner(System.in);
    private AdministradorService administradorService = new AdministradorService();
    private ConstrutorService construtorService = new ConstrutorService();
    private ClienteService clienteService = new ClienteService();
    private EngenheiroService engenheiroService = new EngenheiroService();
    private ObraService obraService = new ObraService();
    private ContratoService contratoService = new ContratoService();
    
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
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 8);
                    this.executarAcao6Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 8);
            }
            
            case 7 -> { // Adicionar contrato
                do {
                    MenuUtils.administradorSubMenu7();
                    opcaoSecundaria = MenuUtils.selecionarOpcao(1, 5);
                    this.executarAcao7Administrador(opcaoSecundaria, administrador);
                } while (opcaoSecundaria != 5); 
            }
            
            case 8 -> { // Adicionar pagamento
                this.executarAcao8Administrador(administrador);
            }
            
            case 9 -> { // Adicionar recebimento
                this.executarAcao9Administrador(administrador);
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
        
        administradorService.atualizarAdministrador(administrador);
    }
    
    public void executarAcao2Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
    }
    
    public void executarAcao3Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
    }
    
    public void executarAcao4Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
    }
    
    public void executarAcao5Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
    }
    
    public void executarAcao6Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        switch (opcao) {
            case 1 -> { // Cadastrar obra pelo cliente
                System.out.println("=== CADASTRANDO NOVA OBRA ===");
                
                System.out.println("Digite o CPF do cliente solicitante: ");
                String cpf = this.scanner.nextLine();
                
                Cliente cliente = clienteService.recuperarCliente(cpf);
                
                if (cliente == null) return;
                
                System.out.println("Digite o endereço pretendido para a obra: ");
                String endereco = this.scanner.nextLine();
                
                System.out.println("Qual é o tipo da obra? ");
                String tipoObra = this.scanner.nextLine();
                
                clienteService.solicitarObra(cliente, endereco, tipoObra);
            }
            
            case 2 -> { // Aprovar/Reprovar uma obra
                administradorService.aceitarObra();
            }
            
            case 3 -> { // Buscar obra por ID
                System.out.println("=== BUSCANDO OBRA ===");
                
                System.out.print("Digite o ID da obra que procura: ");
                int id = this.scanner.nextInt();
                
                Obra obra = obraService.retornarObraPorID(id);
                
                if (obra == null) System.out.println("Obra com ID " + id + " não encontrada.");
                else {
                    System.out.println("== DETALHES DA OBRA " + id + " ==");
                    System.out.println("Endereço: " + obra.getEndereco());
                    System.out.println("Tipo de obra: " + obra.getTipoObra());
                    System.out.println("Status atual: " + obra.getStatus());
                    System.out.println("Cliente: " + obra.getCliente());
                }
            }
            
            case 4 -> { // Buscar obras por cliente
                List<Obra> obras = new ArrayList<>();
                
                System.out.println("=== BUSCANDO OBRAS DE CLIENTE ===");
                
                System.out.print("Digite o CPF do cliente: ");
                String cpf = this.scanner.nextLine();
                
                Cliente cliente = clienteService.recuperarCliente(cpf);
                if (cliente == null) System.out.println("Cliente não existe");
                else {
                    obras = clienteService.visualizarObrasCliente(cpf);

                    if (obras.isEmpty())
                        System.out.println("Nenhuma obra encontrada para esse cliente.");
                    else {
                        for (Obra o : obras) {
                            System.out.println("== DETALHES DA OBRA " + o.getId() + " ==");
                            System.out.println("Endereço: " + o.getEndereco());
                            System.out.println("Tipo de obra: " + o.getTipoObra());
                            System.out.println("Status atual: " + o.getStatus());
                            System.out.println("Cliente: " + o.getCliente().getNome() + "\n");
                        }
                    }
                }
            }
            
            case 5 -> { // Obras não aprovadas
                List<Obra> obras = new ArrayList<>();
                obras = obraService.getObrasNaoAprovadas();
                
                if (obras.isEmpty())
                    System.out.println("Nenhuma obra encontrada para esse status.");
                else {
                    for (Obra o : obras) {
                        System.out.println("== DETALHES DA OBRA " + o.getId() + " ==");
                        System.out.println("Endereço: " + o.getEndereco());
                        System.out.println("Tipo de obra: " + o.getTipoObra());
                        System.out.println("Status atual: " + o.getStatus());
                        System.out.println("Cliente: " + o.getCliente().getNome() + "\n");
                    }
                }
            }
            
            case 6 -> { // Obras aprovadas
                List<Obra> obras = new ArrayList<>();
                obras = obraService.getObrasAprovadas();
                
                if (obras.isEmpty())
                    System.out.println("Nenhuma obra encontrada para esse status.");
                else {
                    for (Obra o : obras) {
                        System.out.println("== DETALHES DA OBRA " + o.getId() + " ==");
                        System.out.println("Endereço: " + o.getEndereco());
                        System.out.println("Tipo de obra: " + o.getTipoObra());
                        System.out.println("Status atual: " + o.getStatus());
                        System.out.println("Cliente: " + o.getCliente().getNome() + "\n");
                    }
                }
            }
            
            case 7 -> { // Obras em revisão
                List<Obra> obras = new ArrayList<>();
                obras = obraService.getObrasSobRevisao();
                
                if (obras.isEmpty())
                    System.out.println("Nenhuma obra encontrada para esse status.");
                else {
                    for (Obra o : obras) {
                        System.out.println("== DETALHES DA OBRA " + o.getId() + " ==");
                        System.out.println("Endereço: " + o.getEndereco());
                        System.out.println("Tipo de obra: " + o.getTipoObra());
                        System.out.println("Status atual: " + o.getStatus());
                        System.out.println("Cliente: " + o.getCliente().getNome() + "\n");
                    }
                }
            }
        }
    }
    
    public void executarAcao7Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        switch (opcao) {
            case 1 -> { // Cadastrar contrato
                // Achando o engenheiro
                System.out.print("Digite o CPF do engenheiro responsável: ");
                String cpf = scanner.nextLine();
                
                Engenheiro eng = engenheiroService.recuperarEngenheiro(cpf);
                if (eng == null) {
                    System.out.println("Engenheiro não encontrado!");
                    return;
                }
                
                // Achando o construtor
                System.out.print("Digite o CPF do construtor responsável: ");
                cpf = scanner.nextLine();
                
                Construtor con = construtorService.recuperarConstrutor(cpf);
                if (con == null) {
                    System.out.println("Construtor não contrado");
                    return;
                }
                
                // Achando a obra
                System.out.print("Digite o ID da obra: ");
                int id = scanner.nextInt();
                
                Obra obra = obraService.retornarObraPorID(id);
                if (obra == null) {
                    System.out.println("Obra não contrada!");
                    return;
                }
                
                // Colocando o valor do contrato
                System.out.print("Digite o valor do contrato: ");
                float valor = scanner.nextFloat();
                if (valor <= 0) {
                    System.out.println("O valor deve ser maior que 0.");
                    return;
                }
                
                scanner.nextLine(); // Limpando o buffer
                
                // Datas de início e fim
                LocalDate dataInicio = this.lerData(0);
                LocalDate dataFim = this.lerData(1);
                
                // Gerando o contrato
                Contrato contrato = new Contrato(0, dataInicio, dataFim, valor, eng, con, obra);
                administradorService.cadastrarContratoDeServico(contrato);
            }
            
            case 2 -> { // Listar contratos finalizados
                List<Contrato> contratos = new ArrayList<>();
                
                contratos = contratoService.getContratosFinalizados();
                
                System.out.println("=== BUSCANDO CONTRATOS FINALIZADOS ===");
                
                for (Contrato c : contratos) {
                    System.out.println("== DETALHES DO CONTRATO " + c.getId() + " ==");
                    System.out.println("Valor: " + c.getValor());
                    System.out.println("Data de início: " + c.getDataInicio());
                    System.out.println("Data de fim: " + c.getDataFim());
                    System.out.println("Engenheiro: " + c.getEngenheiro().getNome());
                    System.out.println("Construtor: " + c.getConstrutor().getNome());
                    System.out.println("Obra: #" + c.getObra().getId() + "\n");
                }
            }
            
            case 3 -> { // Listar contratos em andamento
                List<Contrato> contratos = new ArrayList<>();
                
                contratos = contratoService.getContratosEmAndamento();
                
                System.out.println("=== BUSCANDO CONTRATOS EM ANDAMENTO ===");
                
                for (Contrato c : contratos) {
                    System.out.println("== DETALHES DO CONTRATO " + c.getId() + " ==");
                    System.out.println("Valor: " + c.getValor());
                    System.out.println("Data de início: " + c.getDataInicio());
                    System.out.println("Data de fim: " + c.getDataFim());
                    System.out.println("Engenheiro: " + c.getEngenheiro().getNome());
                    System.out.println("Construtor: " + c.getConstrutor().getNome());
                    System.out.println("Obra: #" + c.getObra().getId() + "\n");
                }
            }
            
            case 4 -> { // Listar contratos futuros
                List<Contrato> contratos = new ArrayList<>();
                
                contratos = contratoService.getContratosFuturos();
                
                System.out.println("=== BUSCANDO CONTRATOS FUTUROS ===");
                
                for (Contrato c : contratos) {
                    System.out.println("== DETALHES DO CONTRATO " + c.getId() + " ==");
                    System.out.println("Valor: " + c.getValor());
                    System.out.println("Data de início: " + c.getDataInicio());
                    System.out.println("Data de fim: " + c.getDataFim());
                    System.out.println("Engenheiro: " + c.getEngenheiro().getNome());
                    System.out.println("Construtor: " + c.getConstrutor().getNome());
                    System.out.println("Obra: #" + c.getObra().getId() + "\n");
                }
            }
        }
    }
    
    public void executarAcao8Administrador (Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        System.out.println("=== CRIANDO NOVO PAGAMENTO ===");
        
        System.out.print("CPF do construtor: ");
        String cpf = this.scanner.nextLine();
        Construtor c = construtorService.recuperarConstrutor(cpf);
        
        System.out.print("Valor do pagamento: ");
        float valor = this.scanner.nextFloat();
        
        System.out.print("Descrição do pagamento: ");
        this.scanner.nextLine();
        String desc = this.scanner.nextLine();
        
        administradorService.gerarPagamento(administrador, c, valor, desc);
    }
    
    public void executarAcao9Administrador (Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        System.out.println("=== CRIANDO NOVO RECEBIMENTO ===");
        
        System.out.print("CPF do cliente: ");
        String cpf = this.scanner.nextLine();
        Cliente c = clienteService.recuperarCliente(cpf);
        
        System.out.print("Valor do pagamento do cliente: ");
        float valor = this.scanner.nextFloat();
        
        System.out.print("Descrição do pagamento: ");
        this.scanner.nextLine();
        String desc = this.scanner.nextLine();
        
        administradorService.gerarRecebimento(administrador, c, valor, desc);
    }
    
    public LocalDate lerData (int inicioOuFim) {
        System.out.print("Digite a data de " + (inicioOuFim == 0 ? "início" : "fim") + " (yyyy-MM-dd): ");
        String input = this.scanner.nextLine();

        // Definição do formatter para ler o formato dd/MM/yyyy
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Converte a string para LocalDate
        LocalDate data = LocalDate.parse(input, inputFormatter);
        
        return data;
    }
}
