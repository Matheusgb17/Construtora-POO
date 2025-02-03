package construtora.model.service;

import construtora.model.dao.*;
import construtora.model.entity.*;

import construtora.model.dao.ClienteDAO;
import construtora.model.entity.Cliente;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AdministradorService {

    Scanner scanner = new Scanner(System.in);
    ClienteDAO clienteDAO = new ClienteDAO();
    ConstrutorDAO construtorDAO = new ConstrutorDAO();
    FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    EngenheiroDAO engenheiroDAO = new EngenheiroDAO();

    public void cadastrarCliente(Cliente cliente) {
        //Informando os dados do cliente
        System.out.println("Digite o nome do cliente: ");
        cliente.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do cliente: ");
        cliente.setCpf(scanner.nextLine());
        if (utils.CPFUtils.validarCPF(cliente.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarCliente(cliente);
        }
        System.out.println("Digite o telefone do cliente: ");
        cliente.setTelefone(scanner.nextLine());
        if (utils.TelefoneUtils.validarTelefone(cliente.getTelefone()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarCliente(cliente);
        }
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = cliente.getTelefone().substring(cliente.getTelefone().length() - 4);

        //setando senha criptografada
        cliente.setSenha(senha);

        cliente.setStatus("ativo");

        int retornoId = clienteDAO.create(cliente);
        if (retornoId > 0) {
            cliente.setId(retornoId);
        }

    }

    public void cadastrarFuncionario(Funcionario funcionario, Construtor construtor) {
        funcionario.setConstrutor(construtor);

        //Informando os dados do funcionario
        System.out.println("Digite o nome do funcionario: ");
        funcionario.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do funcionario: ");
        funcionario.setCpf(scanner.nextLine());
        if (utils.CPFUtils.validarCPF(funcionario.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarFuncionario(funcionario, construtor);
        }
        System.out.println("Digite o telefone do funcionario: ");
        funcionario.setTelefone(scanner.nextLine());
        if (utils.TelefoneUtils.validarTelefone(funcionario.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarFuncionario(funcionario, construtor);
        }
        System.out.println("Digite o cargo do funcionario: ");
        funcionario.setCargo(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do funcionario, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = funcionario.getTelefone().substring(funcionario.getTelefone().length() - 4);

        //setando senha criptografada
        funcionario.setSenha(senha);

        int retornoId = funcionarioDAO.create(funcionario);
        if (retornoId > 0) {
            funcionario.setId(retornoId);
        }
    }

    public void cadastrarConstrutor(Construtor construtor) {

        //Informando os dados do cliente
        scanner.nextLine();
        System.out.println("Digite o nome do construtor: ");
        construtor.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do construtor: ");
        construtor.setCpf(scanner.nextLine());
        if (utils.CPFUtils.validarCPF(construtor.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarConstrutor(construtor);
        }
        System.out.println("Digite o telefone do construtor: ");
        construtor.setTelefone(scanner.nextLine());
        if (utils.TelefoneUtils.validarTelefone(construtor.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarConstrutor(construtor);
        }
        System.out.println("Digite o tipo de serviço do construtor: ");
        construtor.setTipoServico(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = construtor.getTelefone().substring(construtor.getTelefone().length() - 4);

        //setando senha criptografada
        construtor.setSenha(senha);

        int retornoId = construtorDAO.create(construtor);
        if (retornoId > 0) {
            construtor.setId(retornoId);
        }

    }

    public void cadastrarEngenheiro(Engenheiro engenheiro) {
        //Informando os dados 
        scanner.nextLine();
        System.out.println("Digite o nome do engenheiro: ");
        engenheiro.setNome(scanner.nextLine());
        System.out.println("Digite o cpf do engenheiro: ");
        engenheiro.setCpf(scanner.nextLine());
        if (utils.CPFUtils.validarCPF(engenheiro.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarEngenheiro(engenheiro);
        }
        System.out.println("Digite o telefone do engenheiro: ");
        engenheiro.setTelefone(scanner.nextLine());
        if (utils.TelefoneUtils.validarTelefone(engenheiro.getCpf()) == false) {
            System.out.println("Insira os dados novamente!\n");
            cadastrarEngenheiro(engenheiro);
        }
        System.out.println("Digite o tipo de serviço do engenheiro: ");
        engenheiro.setTipoServico(scanner.nextLine());
        /*A senha de primeiro acesso será os 4 ultimos dígitos do telefone do cliente, a ideia é que após o primeiro acesso
        o usuário altere a senha.*/
        String senha = engenheiro.getTelefone().substring(engenheiro.getTelefone().length() - 4);

        //setando senha criptografada
        engenheiro.setSenha(senha);

        int retornoId = engenheiroDAO.create(engenheiro);
        if (retornoId > 0) {
            engenheiro.setId(retornoId);
        }
    }

    public void registrarRecebimento(Administrador administrador, Cliente cliente, float valor) {

        //Agora instanciamos e criamos um novo pagamento
        RecebimentoDAO recebimentoDAO = new RecebimentoDAO();

        Recebimento recebimento = new Recebimento(cliente, 0, valor, LocalDate.now(), administrador);

        recebimentoDAO.create(recebimento);

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
            return;
        }

        // Exibe os detalhes atuais da obra
        System.out.println("Detalhes da Obra:");
        System.out.println("ID: " + obra.getId());
        System.out.println("Endereço: " + obra.getEndereco());
        System.out.println("Tipo de obra: " + obra.getTipoObra());
        System.out.println("Status atual: " + obra.getStatus());
        System.out.println("Cliente: " + obra.getCliente());
        System.out.println("Deseja aceitar esta obra? (s/n)");
        String resposta = scanner.nextLine();

        if (resposta.equalsIgnoreCase("s")) {
            // Atualiza o atributo "status" para "Aprovada"
            obra.setStatus("Aprovada");

            // Persiste a alteração no banco de dados 
            obraDAO.update(obra);
            System.out.println("Obra aprovada com sucesso!");
        } else {
            System.out.println("Operação cancelada. A obra não foi aceita.");
            obra.setStatus("Não aprovada");
            obraDAO.update(obra);
        }
    }

    public static void gerarContrato(Obra obra) {
        // Define o nome do arquivo de contrato
        String nomeArquivo = "Contrato_Obra_" + obra.getId() + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("          CONTRATO DE PRESTAÇÃO DE SERVIÇO         \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("ID da Obra: " + obra.getId() + "\n");
            writer.write("Endereço: " + obra.getEndereco() + "\n");
            writer.write("Tipo de Obra: " + obra.getTipoObra() + "\n");
            writer.write("Status: " + obra.getStatus() + "\n");
            writer.write("Cliente: " + obra.getCliente().getId() + " - " + obra.getCliente().getNome() + "\n\n");
            writer.write("Pelo presente instrumento, a empresa se compromete a realizar a obra nos termos estabelecidos...\n");
            writer.write("\n-------------------------------------------\n");
            writer.write("Assinatura do Cliente: ___________________\n");
            writer.write("Assinatura da Empresa: ___________________\n");

            System.out.println("Contrato gerado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o contrato: " + e.getMessage());
        }
    }

    public static void gerarPagamento(Construtor construtor, double valor, String descricao) {
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
        String nomeArquivo = "Pagamento_Construtor_" + construtor.getId() + ".txt";

        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("===========================================\n");
            writer.write("            RECIBO DE PAGAMENTO           \n");
            writer.write("===========================================\n\n");
            writer.write("Data: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n");
            writer.write("ID do Construtor: " + construtor.getId() + "\n");
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
    }

}
