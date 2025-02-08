package construtora.model.service;

import construtora.model.entity.Construtor;
import construtora.model.dao.ConstrutorDAO;
import construtora.model.dao.ContratoDAO;
import construtora.model.entity.Contrato;
import java.util.ArrayList;
import java.util.List;

public class ConstrutorService {
    
    public Construtor recuperarConstrutor (String cpf) {
        ConstrutorDAO cond = new ConstrutorDAO();
        Construtor con = cond.find(cpf);
        cond.close();
        
        return con;
    }
    
    public List<Contrato> getContratosPorConstrutor (Construtor construtor) {
        ContratoDAO cd = new ContratoDAO();
        List<Contrato> contratos = new ArrayList<>();
        
        contratos = cd.getTodosContratosPorConstrutor(construtor);
        cd.close();
        
        return contratos;
    }
}
