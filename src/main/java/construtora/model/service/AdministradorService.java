package construtora.model.service;

import construtora.model.dao.*;
import construtora.model.entity.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdministradorService {

    Scanner scanner = new Scanner(System.in);
    
    public void cadastrarCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();

        // Solicita o nome do cliente
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());

        // Captura e valida o CPF do cliente utilizando loop
        String cpf;
        do {
            System.out.println("Digite o CPF do cliente: ");
            cpf = scanner.nextLine();
            if (!utils.CPFUtils.validarCPF(cpf)) {
                System.out.println("CPF inválido. Insira os dados novamente!");
            }
        } while (!utils.CPFUtils.validarCPF(cpf));
        cliente.setCpf(cpf);

        // Captura e valida o telefone do cliente utilizando loop
        String telefone;
        do {
            System.out.println("Digite o telefone do cliente: ");
            telefone = scanner.nextLine();
            if (!utils.TelefoneUtils.validarTelefone(telefone)) {
                System.out.println("Telefone inválido. Insira os dados novamente!");
            }
        } while (!utils.TelefoneUtils.validarTelefone(telefone));
        cliente.setTelefone(telefone);

        // Solicita a senha ao usuário
        System.out.println("Digite a senha de acesso: ");
        String senha = scanner.nextLine();
        cliente.setSenha(senha);

        // Define o status inicial do cliente
        cliente.setStatus("ativo");

        // Cria o cliente no banco de dados
        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar cliente.");
        }

        // Fecha os recursos do DAO
        clienteDAO.close();
    }

    public void cadastrarFuncionario(Funcionario funcionario, Construtor construtor) {
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        funcionario.setConstrutor(construtor);

        // Captura e valida o nome
        System.out.println("Digite o nome do funcionario: ");
        funcionario.setNome(scanner.nextLine());

        // Loop para validação do CPF
        String cpf;
        do {
            System.out.println("Digite o CPF do funcionario: ");
            cpf = scanner.nextLine();
            if (!utils.CPFUtils.validarCPF(cpf)) {
                System.out.println("CPF inválido. Insira os dados novamente!");
            }
        } while (!utils.CPFUtils.validarCPF(cpf));
        funcionario.setCpf(cpf);

        // Loop para validação do telefone
        String telefone;
        do {
            System.out.println("Digite o telefone do funcionario: ");
            telefone = scanner.nextLine();
            if (!utils.TelefoneUtils.validarTelefone(telefone)) {
                System.out.println("Telefone inválido. Insira os dados novamente!");
            }
        } while (!utils.TelefoneUtils.validarTelefone(telefone));
        funcionario.setTelefone(telefone);

        System.out.println("Digite o cargo do funcionario: ");
        funcionario.setCargo(scanner.nextLine());

        // Solicita a senha ao usuário
        System.out.println("Digite a senha de acesso: ");
        String senha = scanner.nextLine();
        funcionario.setSenha(senha);

        int retornoId = funcionarioDAO.create(funcionario);
        if (retornoId > 0) {
            funcionario.setId(retornoId);
            System.out.println("Funcionário cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar funcionário.");
        }

        funcionarioDAO.close();
    }

    public void cadastrarConstrutor(Construtor construtor) {
        ConstrutorDAO construtorDAO = new ConstrutorDAO();

        // Se houver algum newline pendente no scanner, consome-o.
        scanner.nextLine();

        // Captura do nome do construtor
        System.out.println("Digite o nome do construtor: ");
        construtor.setNome(scanner.nextLine());

        // Captura e validação do CPF
        String cpf;
        do {
            System.out.println("Digite o CPF do construtor: ");
            cpf = scanner.nextLine();
            if (!utils.CPFUtils.validarCPF(cpf)) {
                System.out.println("CPF inválido. Insira os dados novamente!");
            }
        } while (!utils.CPFUtils.validarCPF(cpf));
        construtor.setCpf(cpf);

        // Captura e validação do telefone
        String telefone;
        do {
            System.out.println("Digite o telefone do construtor: ");
            telefone = scanner.nextLine();
            if (!utils.TelefoneUtils.validarTelefone(telefone)) {
                System.out.println("Telefone inválido. Insira os dados novamente!");
            }
        } while (!utils.TelefoneUtils.validarTelefone(telefone));
        construtor.setTelefone(telefone);

        // Captura do tipo de serviço
        System.out.println("Digite o tipo de serviço do construtor: ");
        construtor.setTipoServico(scanner.nextLine());

        // Solicita a senha ao usuário
        System.out.println("Digite a senha de acesso: ");
        String senha = scanner.nextLine();
        construtor.setSenha(senha);

        // Cria o construtor no banco de dados
        int retornoId = construtorDAO.create(construtor);
        if (retornoId > 0) {
            construtor.setId(retornoId);
            System.out.println("Construtor cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar construtor.");
        }

        // Fecha os recursos do DAO (por exemplo, a conexão com o banco)
        construtorDAO.close();
    }

    public void cadastrarEngenheiro(Engenheiro engenheiro) {
        EngenheiroDAO engenheiroDAO = new EngenheiroDAO();

        // Se houver algum newline pendente, consome-o.
        scanner.nextLine();

        // Captura do nome do engenheiro
        System.out.println("Digite o nome do engenheiro: ");
        engenheiro.setNome(scanner.nextLine());

        // Captura e validação do CPF
        String cpf;
        do {
            System.out.println("Digite o CPF do engenheiro: ");
            cpf = scanner.nextLine();
            if (!utils.CPFUtils.validarCPF(cpf)) {
                System.out.println("CPF inválido. Insira os dados novamente!");
            }
        } while (!utils.CPFUtils.validarCPF(cpf));
        engenheiro.setCpf(cpf);

        // Captura e validação do telefone
        String telefone;
        do {
            System.out.println("Digite o telefone do engenheiro: ");
            telefone = scanner.nextLine();
            if (!utils.TelefoneUtils.validarTelefone(telefone)) {
                System.out.println("Telefone inválido. Insira os dados novamente!");
            }
        } while (!utils.TelefoneUtils.validarTelefone(telefone));
        engenheiro.setTelefone(telefone);

        // Captura do tipo de serviço
        System.out.println("Digite o tipo de serviço do engenheiro: ");
        engenheiro.setTipoServico(scanner.nextLine());

        // Solicita a senha ao usuário
        System.out.println("Digite a senha de acesso: ");
        String senha = scanner.nextLine();
        engenheiro.setSenha(senha);

        // Cria o engenheiro no banco de dados
        int retornoId = engenheiroDAO.create(engenheiro);
        if (retornoId > 0) {
            engenheiro.setId(retornoId);
            System.out.println("Engenheiro cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar engenheiro.");
        }

        // Fecha os recursos do DAO
        engenheiroDAO.close();
    }

    public void gerarRecebimento(Administrador administrador, Cliente cliente, float valor, String descricao) {
        // Validação de entrada
        if (cliente == null) {
            System.err.println("Erro: Cliente inválido. O pagamento não pode ser gerado.");
            return;
        }

        if (valor <= 0) {
            System.err.println("Erro: O valor do pagamento deve ser maior que zero.");
            return;
        }

        // Define o nome do arquivo de pagamento
        String nomeArquivo = "recibos/recebimentos/Recebimento_Cliente_" + cliente.getCpf() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("            RECIBO DE RECEBIMENTO           \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("CPF do Construtor: " + cliente.getCpf()+ "\n");
            writer.write("Nome do Construtor: " + cliente.getNome() + "\n");
            writer.write("Descrição: " + descricao + "\n");
            writer.write("Valor: R$ " + String.format("%.2f", valor) + "\n\n");

            writer.write("A construtora confirma o recebimento do cliente acima pelo serviço prestado.\n");
            writer.write("\n-------------------------------------------\n");
            writer.write("Assinatura do Cliente: ___________________\n\n");
            writer.write("Assinatura da Construtora: ___________________\n");

            System.out.println("Recebimento gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o recebimento: " + e.getMessage());
        }
        

        //Agora instanciamos e criamos um novo pagamento
        RecebimentoDAO recebimentoDAO = new RecebimentoDAO();
        Recebimento recebimento = new Recebimento(cliente, 0, valor, LocalDate.now(), administrador);
        recebimentoDAO.create(recebimento);
        recebimentoDAO.close();

        System.out.println("Recebimento de R$ " + valor + " registrado com sucesso do cliente: " + cliente.getNome());
    }

    /**
     * Função para aceitar uma obra. Essa função altera o atributo "status" da
     * obra para "aceita".
     */
    public void aceitarObra() {
        ObraDAO obraDAO = new ObraDAO();

        // Solicita o ID da obra que deverá ser aceita
        System.out.println("Digite o ID da obra que deseja aceitar: ");
        int idObra = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do Scanner

        // Recupera a obra com base no ID informado
        Obra obra = obraDAO.find(idObra);
        if (obra == null) {
            System.out.println("Obra não encontrada com o ID informado.");
            obraDAO.close();
        }

        // Exibe os detalhes atuais da obra
        System.out.println("Detalhes da Obra:");
        System.out.println("ID: " + obra.getId());
        System.out.println("Endereço: " + obra.getEndereco());
        System.out.println("Tipo de obra: " + obra.getTipoObra());
        System.out.println("Status atual: " + obra.getStatus());
        System.out.println("Cliente: " + obra.getCliente().getNome());
        System.out.println("Deseja aceitar esta obra? (s/n)");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            // Atualiza o atributo "status" para "Aprovada"
            obra.setStatus(obraDAO.getApproved());

            // Persiste a alteração no banco de dados 
            obraDAO.update(obra);
            System.out.println("Obra aprovada com sucesso!");
            
            obra = obraDAO.find(idObra); // Pega os dados atualizados
            this.gerarContratoDeObra(obra); // Gera o contrato da obra em si
        } else {
            System.out.println("Operação cancelada. A obra não foi aceita.");
            obra.setStatus(obraDAO.getNotApproved());
            obraDAO.update(obra);
        }
        
        obraDAO.close();
    }

    public void gerarContratoDeObra(Obra obra) {
        // Define o nome do arquivo de contrato
        String nomeArquivo = "recibos/contratos/Contrato_Obra_" + obra.getId() + "_" + LocalDateTime.now() +".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("      CONTRATO DE PRESTAÇÃO DE OBRAS     \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("ID da Obra: " + obra.getId() + "\n");
            writer.write("Endereço: " + obra.getEndereco() + "\n");
            writer.write("Tipo de Obra: " + obra.getTipoObra() + "\n");
            writer.write("Status: " + obra.getStatus() + "\n");
            writer.write("Cliente: " + obra.getCliente().getCpf() + " - " + obra.getCliente().getNome() + "\n\n");
            writer.write("Pelo presente instrumento, a empresa se compromete a realizar a obra nos termos estabelecidos.\n");
            writer.write("\n-------------------------------------------\n");
            writer.write("Assinatura do Cliente: ___________________\n\n");
            writer.write("Assinatura da Empresa: ___________________\n");

            System.out.println("Contrato gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o contrato: " + e.getMessage());
        }
    }
    
    public void cadastrarContratoDeServico (Contrato contrato) {
        ContratoDAO contd = new ContratoDAO();
        
        contd.create(contrato);
        
        contd.close();
        
        this.gerarContratoDeServico(contrato);
    }
    
    public void gerarContratoDeServico (Contrato contrato) {
        // Define o nome do arquivo de pagamento
        String nomeArquivo = "recibos/contratos/Contrato_Obra_" + contrato.getObra().getId() + "_Engenheiro_" + contrato.getEngenheiro().getCpf() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("      CONTRATO DE PRESTAÇÃO DE SERVIÇOS     \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("ID da Obra: " + contrato.getObra().getId() + "\n");
            writer.write("Engenheiro: " + contrato.getEngenheiro().getNome() + "\n");
            writer.write("Construtor: " + contrato.getConstrutor().getNome() + "\n");
            writer.write("Valor: " + contrato.getValor() + "\n");
            writer.write("Data de início: " + contrato.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("Data de fim: " + contrato.getDataFim().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n");
            writer.write("Pelo presente instrumento, a empresa se compromete a realizar a obra nos termos estabelecidos.\n");
            writer.write("\n-------------------------------------------\n");
            writer.write("Assinatura do Cliente: ___________________\n\n");
            writer.write("Assinatura da Empresa: ___________________\n");

            System.out.println("Contrato gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o pagamento: " + e.getMessage());
        }
    }

    public void gerarPagamento(Administrador administrador, Construtor construtor, float valor, String descricao) {
        // Validação de entrada
        if (construtor == null) {
            System.err.println("Erro: Construtor inválido. O pagamento não pode ser gerado.");
            return;
        }

        if (valor <= 0) {
            System.err.println("Erro: O valor do pagamento deve ser maior que zero.");
            return;
        }

        // Define o nome do arquivo de pagamento
        String nomeArquivo = "recibos/pagamentos/Pagamento_Construtor_" + construtor.getCpf() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss")) + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("            RECIBO DE PAGAMENTO           \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("CPF do Construtor: " + construtor.getCpf()+ "\n");
            writer.write("Nome do Construtor: " + construtor.getNome() + "\n");
            writer.write("Descrição: " + descricao + "\n");
            writer.write("Valor: R$ " + String.format("%.2f", valor) + "\n\n");

            writer.write("A construtora confirma o pagamento ao profissional acima pelo serviço prestado.\n");
            writer.write("\n-------------------------------------------\n");
            writer.write("Assinatura do Construtor: ___________________\n");
            writer.write("Assinatura da Construtora: ___________________\n");

            System.out.println("Pagamento gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o pagamento: " + e.getMessage());
        }
        
        // Colocar pagamento no banco de dados.
        PagamentoDAO pagamentoDAO = new PagamentoDAO();
        Pagamento pagamento = new Pagamento(construtor, 0, valor, LocalDate.now(), administrador);
        pagamentoDAO.create(pagamento);
        pagamentoDAO.close();
    }
    
    public Administrador recuperarAdministrador (String cpf) {
        AdministradorDAO administradorDAO = new AdministradorDAO();
        Administrador adm = administradorDAO.find(cpf);
        administradorDAO.close();
        return adm;
    }
    
    public void atualizarAdministrador (Administrador administrador) {
        AdministradorDAO administradorDAO = new AdministradorDAO();
        administradorDAO.update(administrador);
        administradorDAO.close();
    }

}
