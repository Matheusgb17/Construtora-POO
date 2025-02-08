package construtora.model.service;

import construtora.model.dao.ContratoDAO;
import construtora.model.dao.FuncionarioDAO;
import construtora.model.entity.Funcionario;
import construtora.model.entity.Contrato;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioService {
    
    
    
    public Funcionario recuperarFuncionario (String cpf) {
        FuncionarioDAO fd = new FuncionarioDAO();
        Funcionario func = fd.find(cpf);
        fd.close();
        return func;
    }
    
    public List<Contrato> getContratosPorFuncionario (Funcionario funcionario) {
        ContratoDAO cd = new ContratoDAO();
        List<Contrato> contratos = new ArrayList<>();
        contratos = cd.getTodosContratosPorFuncionario(funcionario);
        cd.close();
        return contratos;
    }
}
