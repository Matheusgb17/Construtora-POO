package construtora.model.service;

import construtora.model.entity.Contrato;
import java.util.List;
import java.util.ArrayList;

public class EngenheiroService {

    public EngenheiroService() {
    
    }
    
    /**
     * Função de filtrar os contratos de acordo com os engenheiros.
     * @param engenheiroId ID do engenheiro para filtragem
     * @return Lista com todos os contratos do engenheiro
     */
    public List<Contrato> getContratosPorEngenheiro (int engenheiroId) {
        List<Contrato> contratos = new ArrayList<>();
        
        return contratos;
    }
}
