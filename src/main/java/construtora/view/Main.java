package construtora.view;

import java.util.*;

import com.mysql.cj.log.Log;
import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;
import utils.*;


public class Main {
    
    public static void main(String[] args) {
        /* Adiciona dados padrão ao banco de dados. */
        Seeder seeder = new Seeder();
        seeder.run();
        
        Login.login();
    }
}
