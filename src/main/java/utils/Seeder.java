package utils;

import construtora.model.dao.*;
import construtora.model.entity.*;
import java.util.*;

public class Seeder {
    
    private Random random;
    
    private String[] papeis = { "administrador", "cliente", "construtor", "engenheiro", "funcionario" };
    
    public Seeder () {
        this.random = new Random();
    }
    
    public void run () {
        String papel;
        
        /* Gerando o usuário administrador padrão, se necessário. */
        this.gerarUsuariosPadrao();
        
        /* Gerando dados aleatórios no banco. */
        for (int i = 0; i < 100; i++) {
            papel = this.escolherPapelAleatorio();
        
            switch (papel) {
                case "administrador":
                    this.gerarAdministrador(papel);
                break;
                
                case "cliente":
                    this.gerarCliente(papel);
                break;
                
                case "construtor":
                    this.gerarConstrutor(papel);
                break;
                
                case "engenheiro":
                    this.gerarEngenheiro(papel);
                break;
                
                case "funcionario":
                    this.gerarFuncionario(papel);
                break;
            }
        }
    }
    
    private String escolherPapelAleatorio () {
        return this.papeis[random.nextInt(papeis.length)];
    }
    
    private String escolherStatusAleatorio () {
        String[] status = { "Ativo", "Inativo" };
        
        return status[random.nextInt(status.length)];
    }
    
    private String escolherServicoAleatorio () {
        String[] servicos = {
            "Construção de casas", "Reformas residenciais", "Pintura", "Elétrica", "Hidráulica",
            "Serralheria", "Alvenaria", "Impermeabilização", "Instalação de gás", "Pavimentação",
            "Decoração de interiores", "Paisagismo", "Projetos arquitetônicos", "Manutenção predial", "Construção de muros"
        };
        
        return servicos[random.nextInt(servicos.length)];
    }
    
    private String escolherNomeAleatorio () {
        String[] nomes = {
            "João", "Pedro", "Lucas", "Carlos", "Rafael", "Felipe", "Gabriel", "Eduardo", "Thiago", "Mateus",
            "Fernando", "Roberto", "André", "Bruno", "Leandro", "Marcos", "Vitor", "Diego", "Ricardo", "José",
            "Maria", "Ana", "Juliana", "Carla", "Fernanda", "Patrícia", "Camila", "Jéssica", "Luciana", "Beatriz",
            "Mariana", "Renata", "Raquel", "Isabela", "Amanda", "Larissa", "Gabriela", "Vanessa", "Tatiane", "Roberta"
        };
        
        return nomes[random.nextInt(nomes.length)] + " " + nomes[random.nextInt(nomes.length)];
    }
    
    private String gerarCpfAleatorio () {
        String cpf = "";
        for (int i = 0; i < 9; i++) {
            cpf += random.nextInt(10); 
        }
        cpf += this.gerarDigitosVerificadores(cpf);
        return cpf;
    }
    
    private String gerarDigitosVerificadores (String cpfBase) {
        int[] pesos1 = { 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] pesos2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
        
        // Calculando o primeiro dígito verificador
        int soma1 = 0;
        for (int i = 0; i < 9; i++) {
            soma1 += (cpfBase.charAt(i) - '0') * pesos1[i];
        }
        int digito1 = (soma1 % 11) < 2 ? 0 : 11 - (soma1 % 11);
        
        // Calculando o segundo dígito verificador
        int soma2 = 0;
        for (int i = 0; i < 9; i++) {
            soma2 += (cpfBase.charAt(i) - '0') * pesos2[i];
        }
        int digito2 = (soma2 % 11) < 2 ? 0 : 11 - (soma2 % 11);
        
        return String.format("%d%d", digito1, digito2);
    }
    
    private String gerarTelefoneAleatorio () {
        String telefone = "";
        for (int i = 0; i < 11; i++) {
            telefone += random.nextInt(10);
        }
        return telefone;
    }
    
    private String gerarSenhaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&?<>";
        StringBuilder senha = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            senha.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return senha.toString();
    }
    
    private String gerarRegistroCREAAleatorio() {
        int numeroSequencial = random.nextInt(9000) + 1000; // Gera números de 1000 a 9999

        int codigoEstado = random.nextInt(99) + 1; // Gera números de 1 a 99
        String codigoEstadoStr = String.format("%02d", codigoEstado); // Formata para ter 2 dígitos

        return String.format("%04d/%s", numeroSequencial, codigoEstadoStr);
    }
    
    private String gerarCargoFuncionarioAleatorio () {
        String[] cargos = {
            "Engenheiro Civil", "Arquiteto", "Pedreiro", "Servente", "Mestre de Obras",
            "Eletricista", "Encanador", "Serralheiro", "Carpinteiro", "Pintor",
            "Gerente de Obras", "Auxiliar de Construção", "Topógrafo", "Técnico de Segurança do Trabalho", "Projetista"
        };
        
        return cargos[this.random.nextInt(cargos.length)];
    }
    
    private void gerarAdministrador (String papel) {
        AdministradorDAO ad = new AdministradorDAO();
        Administrador adm = new Administrador(
                "adm", 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(), 
                papel
        );
        
        ad.create(adm);
        ad.close();
    }
    
    private void gerarCliente (String papel) {
        ClienteDAO cd = new ClienteDAO();
        
        Cliente cli = new Cliente(
                this.escolherStatusAleatorio(),
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(), 
                papel
        );
        
        cd.create(cli);
        cd.close();
    }
    
    private void gerarConstrutor (String papel) {
        ConstrutorDAO cond = new ConstrutorDAO();
        
        Construtor cons = new Construtor(
                this.escolherServicoAleatorio(), 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(),
                papel
        );
        
        cond.create(cons);
        cond.close();
    }
    
    private void gerarEngenheiro (String papel) {
        EngenheiroDAO ed = new EngenheiroDAO();
        
        Engenheiro eng = new Engenheiro(
                this.gerarRegistroCREAAleatorio(), 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(),
                papel
        );
        
        ed.create(eng);
        ed.close();
    }
    
    private void gerarFuncionario (String papel) {
        ConstrutorDAO cond = new ConstrutorDAO();
        FuncionarioDAO fd = new FuncionarioDAO();
        
        List<Construtor> construtores = cond.findAll();
        
        if (construtores.isEmpty()) {
            return;
        }
        
        int index = this.random.nextInt(construtores.size());
        Construtor cons = construtores.get(index);
        
        Funcionario func = new Funcionario(
                this.gerarCargoFuncionarioAleatorio(), 
                cons, 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(),
                papel
        );
        
        fd.create(func);
        fd.close();
        
        cond.close();
    }
    
    public void gerarUsuariosPadrao () {
        AdministradorDAO ad = new AdministradorDAO();
        ClienteDAO cd = new ClienteDAO();
        ConstrutorDAO cond = new ConstrutorDAO();
        EngenheiroDAO ed = new EngenheiroDAO();
        FuncionarioDAO fd = new FuncionarioDAO();
        
        if (ad.find("99999999999") == null) {
            Administrador admPadrao = new Administrador(
                    "Cargo padrão", 
                    0, 
                    "Administrador Padrão", 
                    "99999999999", 
                    "00000000000", 
                    "123456789", 
                    ad.getTableName()
            );
            
            ad.create(admPadrao);
        }
        
        if (cd.find("88888888888") == null) {
            Cliente cliPadrao = new Cliente(
                    "Ativo", 
                    0, 
                    "Cliente Padrão",
                    "88888888888",
                    "11111111111",
                    "123456789",
                    cd.getTableName()
            );
            
            cd.create(cliPadrao);
        }
        
        if (cond.find("77777777777") == null) {
            Construtor consPadrao = new Construtor(
                    "Serviço padrão", 
                    0, 
                    "Construtor Padrão", 
                    "77777777777", 
                    "22222222222", 
                    "123456789", 
                    cond.getTableName()
            );
            
            cond.create(consPadrao);
        }
        
        if (ed.find("66666666666") == null) {
            Engenheiro engPadrao = new Engenheiro(
                    "9999/99", 
                    0, 
                    "Engenheiro Padrão", 
                    "66666666666", 
                    "33333333333", 
                    "123456789", 
                    ed.getTableName()
            );
            
            ed.create(engPadrao);
        }
        
        if (fd.find("55555555555") == null) {
            Funcionario funcPadrao = new Funcionario(
                    "Cargo Padrão", 
                    cond.find("77777777777"), 
                    0, 
                    "Funcionário Padrão", 
                    "55555555555",
                    "44444444444", 
                    "123456789", 
                    fd.getTableName()
            );
            
            fd.create(funcPadrao);
        }
        
        ad.close();
        cd.close();
        cond.close();
        ed.close();
        fd.close();
    }
}
