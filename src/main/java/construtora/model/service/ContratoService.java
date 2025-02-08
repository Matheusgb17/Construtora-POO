package construtora.model.service;

import construtora.model.entity.Contrato;
import construtora.model.dao.ContratoDAO;
import java.util.List;
import java.util.ArrayList;

public class ContratoService {
    public List<Contrato> getContratosFinalizados () {
        List<Contrato> contratos = new ArrayList<>();
        
        ContratoDAO cd = new ContratoDAO();
        
        contratos = cd.getContratosFinalizados();
        
        cd.close();
        
        return contratos;
    }
    
    public List<Contrato> getContratosEmAndamento () {
        List<Contrato> contratos = new ArrayList<>();
        
        ContratoDAO cd = new ContratoDAO();
        
        contratos = cd.getContratosEmAndamento();
        
        cd.close();
        
        return contratos;
    }
    
    public List<Contrato> getContratosFuturos () {
        List<Contrato> contratos = new ArrayList<>();
        
        ContratoDAO cd = new ContratoDAO();
        
        contratos = cd.getContratosFuturos();
        
        cd.close();
        
        return contratos;
    }
}
