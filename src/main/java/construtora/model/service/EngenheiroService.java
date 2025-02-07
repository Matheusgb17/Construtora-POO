package construtora.model.service;

import construtora.model.dao.EngenheiroDAO;
import construtora.model.dao.ContratoDAO;
import construtora.model.entity.Contrato;
import construtora.model.entity.Engenheiro;
import java.util.List;
import java.util.ArrayList;


public class EngenheiroService {

    private EngenheiroDAO ed = new EngenheiroDAO();
    private ContratoDAO cd = new ContratoDAO();
    
    public EngenheiroService() { 
    }
    
    /**
     * Função de filtrar os contratos de acordo com os engenheiros.
     * @param engenheiroId ID do engenheiro para filtragem
     * @return Lista com todos os contratos do engenheiro
     */
    public List<Contrato> getContratosPorEngenheiro (Engenheiro engenheiro) {
        List<Contrato> contratos = new ArrayList<>();
        
        contratos = cd.getTodosContratosPorEngenheiro(engenheiro);
        
        return contratos;
    }
    
    public Engenheiro recuperarEngenheiro (String cpf) {
        Engenheiro eng = ed.find(cpf);
        
        return eng;
    }
}
