package construtora.model.service;

import construtora.model.entity.Obra;
import construtora.model.dao.ObraDAO;
import java.util.List;
import java.util.ArrayList;

public class ObraService {
    public Obra retornarObraPorID (int id) {
        ObraDAO od = new ObraDAO();
        Obra obra = od.find(id);
        od.close();
        return obra;
    }
    
    public List<Obra> getObrasNaoAprovadas () {
        ObraDAO od = new ObraDAO();
        
        List<Obra> obras = new ArrayList<>();
        
        obras = od.findNotApproved();
        
        od.close();
        return obras;
    }
    
    public List<Obra> getObrasAprovadas () {
        ObraDAO od = new ObraDAO();
        
        List<Obra> obras = new ArrayList<>();
        
        obras = od.findApproved();
        
        od.close();
        return obras;
    }
    
    public List<Obra> getObrasSobRevisao () {
        ObraDAO od = new ObraDAO();
        
        List<Obra> obras = new ArrayList<>();
        
        obras = od.findUnderReview();
        
        od.close();
        return obras;
    }
    
    public void atualizarObra (Obra obra) {
        ObraDAO obraDAO = new ObraDAO();
        obraDAO.update(obra);
        obraDAO.close();
    }
}
