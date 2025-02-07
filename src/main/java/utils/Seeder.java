package utils;

import construtora.model.dao.*;
import construtora.model.entity.*;
import java.util.*;

public class Seeder {
    
    private Random random;
    private AdministradorDAO ad = new AdministradorDAO();
    private ClienteDAO cd = new ClienteDAO();
    private ConstrutorDAO cond = new ConstrutorDAO();
    private EngenheiroDAO ed = new EngenheiroDAO();
    private FuncionarioDAO fd = new FuncionarioDAO();
    
    private String[] papeis = { "administrador", "cliente", "construtor", "engenheiro", "funcionario" };
    
    public Seeder () {
        this.random = new Random();
    }
    
    public void run () {
        String papel;
        
        /* Gerando o usuário administrador padrão, se necessário. */
        gerarAdministradorPadrao();
        
        /* Gerando dados aleatórios no banco. */
        for (int i = 0; i < random.nextInt(100) + 1; i++) {
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
        Administrador adm = new Administrador(
                "adm", 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(), 
                papel
        );
        
        this.ad.create(adm);
    }
    
    private void gerarCliente (String papel) {
        Cliente cli = new Cliente(
                this.escolherStatusAleatorio(),
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(), 
                papel
        );
        
        this.cd.create(cli);
    }
    
    private void gerarConstrutor (String papel) {
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
    }
    
    private void gerarEngenheiro (String papel) {
        Engenheiro eng = new Engenheiro(
                this.gerarRegistroCREAAleatorio(), 
                0, 
                this.escolherNomeAleatorio(), 
                this.gerarCpfAleatorio(), 
                this.gerarTelefoneAleatorio(), 
                this.gerarSenhaAleatoria(),
                papel
        );
        
        this.ed.create(eng);
    }
    
    private void gerarFuncionario (String papel) {
        List<Construtor> construtores = this.cond.findAll();
        
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
        
        this.fd.create(func);
    }
    
    public void gerarAdministradorPadrao () {
        if (this.ad.find("99999999999") == null) {
            Administrador padrao = new Administrador(
                    "Cargo padrão", 
                    0, 
                    "Default", 
                    "99999999999", 
                    "00000000000", 
                    "123456789", 
                    ad.getTableName()
            );
            
            this.ad.create(padrao);
        }
    }
}
