package construtora.model.service;

import construtora.model.dao.ClienteDAO;
import construtora.model.dao.ObraDAO;
import construtora.model.entity.Cliente;
import construtora.model.entity.Obra;
import java.util.List;

public class ClienteService {
    
    
     /**
     * Função pra solicitar obra.
     * @param cliente Cliente que ta solicitando a obra
     * @param endereco Endereço da obra
     * @param tipoObra Tipo da obra q foi solicitada
     * @throws IllegalArgumentException Se os dados fornecidos forem inválidos.
     */
    public void solicitarObra(Cliente cliente, String endereco, String tipoObra) {
        ObraDAO obraDAO = new ObraDAO();        

        //Verifica se o cliente é valido e se ele deu respostas válidas.
        if (cliente == null|| endereco == null || tipoObra == null || endereco.isEmpty()) {
            throw new IllegalArgumentException("Dados da obra ou cliente são inválidos.");
        }

        Obra novaObra = new Obra(0, endereco, tipoObra, obraDAO.getUnderReview(), cliente);

        obraDAO.create(novaObra);
        
        System.out.println("Obra solicitada com sucesso!");
        
        obraDAO.close();
    }
    
    /**
     * Função pra vizualizar todas as obras de um cliente.
     * @param cpf CPF do cliente cujas obras serão visualizadas.
     * @return Lista de obras do cliente.
     */
    public List<Obra> visualizarObrasCliente(String cpf) {
        ObraDAO obraDAO = new ObraDAO();

        //checa o cpf que foi passado
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("O CPF do cliente não pode ser nulo ou vazio.");
        }
        
        List<Obra> obras = obraDAO.findByClient(cpf);
        
        obraDAO.close();

        return obras;
    }
    
    public Cliente recuperarCliente (String cpf) {
        ClienteDAO clid = new ClienteDAO();
        Cliente cli = clid.find(cpf);
        clid.close();
        return cli;
    }
}
