package construtora.model.service;

import construtora.model.dao.RecebimentoDAO;
import construtora.model.entity.Administrador;
import construtora.model.entity.Transferencia;
import construtora.model.dao.TransferenciaDAO;
import construtora.model.entity.Cliente;
import construtora.model.entity.Recebimento;
import java.time.LocalDate;

public class AdministradorService {
    
     /**
     * Função pra realizar pagamentos
     * @param administrador Administrador que registrará o pagamento.
     * @param cliente Cliente que efetuará o pagamento.
     * @param valor Valor do pagamento.
     */
    public void registrarRecebimento(Administrador administrador, Cliente cliente, float valor) {

        //Agora instanciamos e criamos um novo pagamento
        RecebimentoDAO recebimentoDAO = new RecebimentoDAO();

        Recebimento recebimento = new Recebimento(cliente, 0, valor, LocalDate.now(), administrador);

        recebimentoDAO.create(recebimento);

        System.out.println("Recebimento de R$ " + valor + " registrado com sucesso do cliente: " + cliente.getNome());
    }

}
