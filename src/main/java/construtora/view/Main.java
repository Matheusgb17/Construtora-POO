package construtora.view;

import java.util.*;
import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;
import utils.*;

public class Main {

    public static void main(String[] args) {
        EngenheiroDAO ed = new EngenheiroDAO();
        
        Engenheiro e1 = new Engenheiro("abc", 0, "Gabrielle", "12345678910", "1", "1", "Engenheiro");
        Engenheiro e2 = new Engenheiro("cba", 0, "Nicolas", "09876543211", "2", "2", "Engenheiro");
        Engenheiro e3 = new Engenheiro("bac", 0, "Miguel", "99999999999", "3", "3", "Engenheiro");
        
        //ed.create(e3);
        
        ed.delete(2);
    }
}
