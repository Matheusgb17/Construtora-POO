package construtora.controller;

import construtora.model.dao.ClienteDAO;
import construtora.model.dao.ConstrutorDAO;
import construtora.model.dao.EngenheiroDAO;
import construtora.model.dao.FuncionarioDAO;
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
    private FuncionarioService funcionarioService = new FuncionarioService();
    
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
        
        switch (opcao) {
            case 1 -> { 
                System.out.println("=== CADASTRANDO NOVO CLIENTE ===");
                
                Cliente cliente = new Cliente();
                administradorService.cadastrarCliente(cliente);
            }
            case 2 -> {
                System.out.println("=== EDITAR CLIENTE ===");
                System.out.println("Informe o CPF do cliente que deseja editar: ");
                String cpf = scanner.nextLine();
                Cliente cliente = clienteService.recuperarCliente(cpf);
                
                System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

                System.out.print("Nome [" + cliente.getNome() + "]: ");
                String entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) cliente.setNome(entrada);

                System.out.print("CPF [" + cliente.getCpf()+ "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) cliente.setCpf(entrada);

                System.out.print("Telefone [" + cliente.getTelefone() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) cliente.setTelefone(entrada);
                
                System.out.print("Senha [********]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) cliente.setSenha(PasswordUtils.criptografarSenha(entrada));
                
                clienteService.atualizarCliente(cliente);
                
                System.out.println("Cliente atualizado com sucesso!");
            }
            case 3 -> {
                System.out.println("=== DELETAR CLIENTE ===");
                
                System.out.println("Informe o CPF do cliente que deseja deletar: ");
                String cpf = scanner.nextLine();
                
                ClienteDAO cd = new ClienteDAO();
                cd.delete(cpf);
                cd.close();
                
                System.out.print("Cliente deleado com sucesso!");
            }
            case 4 -> {
                System.out.println("=== BUSCAR CLIENTE POR CPF ===");
                
                System.out.println("Insira o CPF do cliente: ");
                String cpf = scanner.nextLine();
                
                Cliente cliente = clienteService.recuperarCliente(cpf);
                
                if (cliente == null) {
                    System.out.println("Cliente não existe!");
                }
                else {
                    System.out.println("Cliente #" + cliente.getId());
                    System.out.println("Nome: " + cliente.getNome());
                    System.out.println("Cpf: " + cliente.getCpf());
                    System.out.println("Telefone: " + cliente.getTelefone() + "\n");
                }
            }
            case 5 -> {
                System.out.println("=== TODOS OS CLIENTES ===");
                
                List<Cliente> clientes = clienteService.listarTodosClientes();
                
                for (Cliente c : clientes){
                    System.out.println("Cliente #" + c.getId());
                    System.out.println("Nome: " + c.getNome());
                    System.out.println("Cpf: " + c.getCpf());
                    System.out.println("Telefone: "+ c.getTelefone() + "\n");
                }
            }
        }   
    }
    
    public void executarAcao3Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        switch(opcao){
            case 1 -> {
                System.out.println("=== CADASTRANDO NOVO CONSTRUTOR ===");
                Construtor construtor = new Construtor();
                administradorService.cadastrarConstrutor(construtor);
            }
            case 2 -> {
                System.out.println("=== EDITAR CONSTRUTOR ===");
                System.out.println("Informe o CPF do construtor que deseja editar: ");
                String cpf = scanner.nextLine();
                Construtor construtor = construtorService.recuperarConstrutor(cpf);
                
                System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

                System.out.print("Nome [" + construtor.getNome() + "]: ");
                String entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) construtor.setNome(entrada);

                System.out.print("CPF [" + construtor.getCpf()+ "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) construtor.setCpf(entrada);

                System.out.print("Telefone [" + construtor.getTelefone() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) construtor.setTelefone(entrada);
                
                System.out.println("Tipo de serviço [" + construtor.getTipoServico() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) construtor.setTipoServico(entrada);
                
                System.out.print("Senha [********]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) construtor.setSenha(PasswordUtils.criptografarSenha(entrada));
                
                construtorService.atualizarConstrutor(construtor);
                
                System.out.println("Construtor atualizado com sucesso!");
            }
            case 3 -> {
                System.out.println("=== DELETAR CONSTRUTOR ====");
                System.out.println("Insira o CPF do construtor que deseja deletar: ");
                String cpf = scanner.nextLine();
                
                ConstrutorDAO cd = new ConstrutorDAO();
                cd.delete(cpf);
                cd.close();
            }
            case 4 -> {
                System.out.println("=== BUSCAR CONSTRUTOR POR CPF ===");
                
                System.out.println("Insira o CPF do construtor: ");
                String cpf = scanner.nextLine();
                
                Construtor construtor = construtorService.recuperarConstrutor(cpf);
                
                if (construtor == null) {
                    System.out.println("Construtor não existe!");
                }
                else {
                    System.out.println("Construtor #" + construtor.getId());
                    System.out.println("Nome: " + construtor.getNome());
                    System.out.println("Cpf: " + construtor.getCpf());
                    System.out.println("Telefone: " + construtor.getTelefone());
                    System.out.println("Tipo de serviço: " + construtor.getTipoServico() + "\n");
                }
            }
            case 5 -> {
                System.out.println("=== TODOS OS CONSTRUTORES ===");
                List<Construtor> construtores = construtorService.listarTodosConstrutores();
                
                for (Construtor c : construtores){
                    System.out.println("Construtor #" + c.getId());
                    System.out.println("Nome: " + c.getNome());
                    System.out.println("Cpf: " + c.getCpf());
                    System.out.println("Telefone: "+ c.getTelefone());
                    System.out.println("Tipo de serviço: " + c.getTipoServico() + "\n");
                }
            }
        }
    }
    
    public void executarAcao4Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        switch(opcao){
            
            case 1 -> {
                System.out.println("=== CADASTRAR FUNCIONARIO ===");
                Funcionario funcionario = new Funcionario();
                
                System.out.println("Insira o CPF do construtor responsável pelo funcionário a ser cadastrado: ");
                String cpf = scanner.nextLine();
               
                Construtor construtor = construtorService.recuperarConstrutor(cpf);
                
                administradorService.cadastrarFuncionario(funcionario, construtor);
            }
            case 2 -> {
                System.out.println("=== EDITAR FUNCIONARIO ===");
                System.out.println("Informe o CPF do funcionario que deseja editar: ");
                String cpf = scanner.nextLine();
                Funcionario funcionario = funcionarioService.recuperarFuncionario(cpf);
                
                System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

                System.out.print("Nome [" + funcionario.getNome() + "]: ");
                String entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) funcionario.setNome(entrada);
                
                System.out.print("CPF [" + funcionario.getCpf()+ "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) funcionario.setCpf(entrada);
                
                System.out.println("Cargo [" + funcionario.getCargo()+ "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) funcionario.setCargo(entrada);
                
                System.out.println("CPF do Construtor [" + funcionario.getConstrutor().getNome()+ "]:");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()){
                    Construtor construtor = construtorService.recuperarConstrutor(entrada);
                    funcionario.setConstrutor(construtor);
                }

                System.out.print("Telefone [" + funcionario.getTelefone() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) funcionario.setTelefone(entrada);
                
                System.out.print("Senha [********]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) funcionario.setSenha(PasswordUtils.criptografarSenha(entrada));
                
                funcionarioService.atualizarFuncionario(funcionario);
                
                System.out.println("Funcionário atualizado com sucesso!");
            }
            case 3 -> {
                System.out.println("=== DELETAR FUNCIONÁRIO ===");
                System.out.println("Insira o CPF do funcionário que deseja deletar: ");
                String cpf = scanner.nextLine();
                
                FuncionarioDAO fd = new FuncionarioDAO();
                fd.delete(cpf);
                fd.close();
            }
            case 4 -> {
                System.out.println("=== BUSCAR FUNCIONÁRIO POR CPF ===");
                
                System.out.println("Insira o CPF do funcionário: ");
                String cpf = scanner.nextLine();
                
                Funcionario funcionario = funcionarioService.recuperarFuncionario(cpf);
                
                if (funcionario == null) {
                    System.out.println("Funcionário não existe!");
                }
                else {
                    System.out.println("Funcionário #" + funcionario.getId());
                    System.out.println("Nome: " + funcionario.getNome());
                    System.out.println("Cpf: " + funcionario.getCpf());
                    System.out.println("Cargo: " + funcionario.getCargo());
                    System.out.println("Construtor: " + funcionario.getConstrutor().getNome());
                    System.out.println("Telefone: " + funcionario.getTelefone() + "\n");
                }
            }
            case 5 -> {
                System.out.println("=== TODOS OS FUNCIONÁRIOS ===");
                List<Funcionario> funcionarios = funcionarioService.listarTodosFuncionarios();
                
                for(Funcionario f : funcionarios){
                    System.out.println("Funcionário #" + f.getId());
                    System.out.println("Nome: " + f.getNome());
                    System.out.println("Cpf: " + f.getCpf());
                    System.out.println("Cargo: " + f.getCargo());
                    System.out.println("Construtor: " + f.getConstrutor().getNome());
                    System.out.println("Telefone: " + f.getTelefone() + "\n");
                }
            }
        }
        
        
            
        
    }
    
    public void executarAcao5Administrador (int opcao, Administrador administrador) {
        if (this.scanner.hasNextLine()) this.scanner.nextLine();
        
        switch(opcao){
            case 1 ->{
                System.out.println("=== CADASTRAR ENGENHEIRO ===");
                Engenheiro engenheiro = new Engenheiro();
                administradorService.cadastrarEngenheiro(engenheiro);
            }
            case 2 ->{
                System.out.println("=== EDITAR ENGENHEIRO ===");
                System.out.println("Informe o CPF do construtor que deseja editar: ");
                String cpf = scanner.nextLine();
                Engenheiro engenheiro = engenheiroService.recuperarEngenheiro(cpf);
                
                System.out.println("Atualize os dados. para manter o valor atual, apenas aperte Enter.");

                System.out.print("Nome [" + engenheiro.getNome() + "]: ");
                String entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) engenheiro.setNome(entrada);
                
                System.out.println("CREA [" + engenheiro.getCrea() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) engenheiro.setCrea(entrada);

                System.out.print("CPF [" + engenheiro.getCpf()+ "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) engenheiro.setCpf(entrada);

                System.out.print("Telefone [" + engenheiro.getTelefone() + "]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) engenheiro.setTelefone(entrada);
                
                System.out.print("Senha [********]: ");
                entrada = this.scanner.nextLine();
                if (!entrada.isEmpty()) engenheiro.setSenha(PasswordUtils.criptografarSenha(entrada));
                
                engenheiroService.atualizarEngenheiro(engenheiro);
                
                System.out.println("Engenheiro atualizado com sucesso!");
            }
            case 3 -> {
                System.out.println("=== DELETAR ENGENHEIRO ====");
                System.out.println("Insira o CPF do engenheiro que deseja deletar: ");
                String cpf = scanner.nextLine();
                
                EngenheiroDAO ed = new EngenheiroDAO();
                ed.delete(cpf);
                ed.close();
            }
            case 4 -> {
                System.out.println("=== BUSCAR ENGENHEIRO POR CPF ===");
                
                System.out.println("Insira o CPF do engenheiro: ");
                String cpf = scanner.nextLine();
                
                Engenheiro engenheiro = engenheiroService.recuperarEngenheiro(cpf);
                
                if (engenheiro == null) {
                    System.out.println("Engenheiro não existe!");
                }
                else {
                    System.out.println("Engenheiro #" + engenheiro.getId());
                    System.out.println("CREA: " + engenheiro.getCrea());
                    System.out.println("Nome: " + engenheiro.getNome());
                    System.out.println("Cpf: " + engenheiro.getCpf());
                    System.out.println("Telefone: " + engenheiro.getTelefone() + "\n");
                }
            }
            case 5 -> {
                System.out.println("=== TODOS OS ENGENHEIROS ===");
                
                List<Engenheiro> engenheiros = engenheiroService.listarTodosEngenheiros();
                
                for(Engenheiro e : engenheiros){
                    System.out.println("Engenheiro #" + e.getId());
                    System.out.println("CREA: " + e.getCrea());
                    System.out.println("Nome: " + e.getNome());
                    System.out.println("Cpf: " + e.getCpf());
                    System.out.println("Telefone: " + e.getTelefone());
                }
                
            }
        }
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
                    System.out.println("Cliente: " + obra.getCliente().getNome() + "\n");
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
                
                if (contratos.isEmpty()) {
                    System.out.println("Não existem contratos finalizados no momento!\n");
                }
                else {
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
            
            case 3 -> { // Listar contratos em andamento
                List<Contrato> contratos = new ArrayList<>();
                
                contratos = contratoService.getContratosEmAndamento();
                
                System.out.println("=== BUSCANDO CONTRATOS EM ANDAMENTO ===");
                
                if (contratos.isEmpty()) {
                    System.out.println("Não existem contratos em andamento no momento!\n");
                }
                else {
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
            
            case 4 -> { // Listar contratos futuros
                List<Contrato> contratos = new ArrayList<>();
                
                contratos = contratoService.getContratosFuturos();
                
                System.out.println("=== BUSCANDO CONTRATOS FUTUROS ===");
                
                if (contratos.isEmpty()) {
                    System.out.println("Não existem contratos futuros no momento!\n");
                }
                else {
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
    
    public Administrador buscarAdministrador (String cpf) {
        return administradorService.recuperarAdministrador(cpf);
    }
}
