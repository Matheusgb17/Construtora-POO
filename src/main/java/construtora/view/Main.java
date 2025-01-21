package construtora.view;

import java.util.*;
import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;
import utils.*;
import static utils.CPFUtils.validarCPF;
import static utils.TelefoneUtils.formatarTelefone;
import static utils.TelefoneUtils.validarTelefone;

public class Main {
    
    public static void main(String[] args) {
        //EngenheiroDAO ed = new EngenheiroDAO();

        //Engenheiro e1 = new Engenheiro("abc", 0, "Gabrielle", "12345678910", "1", "1", "Engenheiro");
        //Engenheiro e2 = new Engenheiro("cba", 0, "Nicolas", "09876543211", "2", "2", "Engenheiro");
        //Engenheiro e3 = new Engenheiro("bac", 0, "Miguel", "99999999999", "3", "3", "Engenheiro");
        //ed.create(e3);
        System.out.println(validarCPF("16388703658") ? "Válido" : "Inválido");

        String telefone1 = "11966406068", telefone2 = "966406068", telefone3 = "66406068", telefone4 = "833910911", telefone5 = "1231";

        System.out.println("=> --------------- <=");
        System.out.println(validarTelefone(telefone1) ? "Válido" : "Inválido");
        System.out.println(validarTelefone(telefone2) ? "Válido" : "Inválido");
        System.out.println(validarTelefone(telefone3) ? "Válido" : "Inválido");
        System.out.println(validarTelefone(telefone4) ? "Válido" : "Inválido");
        System.out.println(validarTelefone(telefone5) ? "Válido" : "Inválido");

        System.out.println(formatarTelefone(telefone1));
        System.out.println(formatarTelefone(telefone2));
        System.out.println(formatarTelefone(telefone3));
        System.out.println(formatarTelefone(telefone4));
        System.out.println(formatarTelefone(telefone5));

        //ed.delete(2);
    }
}
