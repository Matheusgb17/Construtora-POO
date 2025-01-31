package construtora.view;

import java.util.*;

import com.mysql.cj.log.Log;
import construtora.model.service.*;
import construtora.model.entity.*;
import construtora.model.dao.*;
import construtora.controller.*;
import utils.*;

import static construtora.view.Login.login;
import static utils.CPFUtils.validarCPF;
import static utils.TelefoneUtils.formatarTelefone;
import static utils.TelefoneUtils.validarTelefone;

public class Main {
    
    public static void main(String[] args) {
        login();

    }
}
