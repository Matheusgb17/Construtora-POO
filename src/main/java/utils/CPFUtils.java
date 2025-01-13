package utils;

import java.util.InputMismatchException;

public class CPFUtils {

    public static boolean validarCPF(String cpf) {
        // verificações iniciais para evitar calculos desnecessários
        if (cpf.length() != 11
                || cpf.equals("00000000000") || cpf.equals("11111111111")
                || cpf.equals("22222222222") || cpf.equals("33333333333")
                || cpf.equals("44444444444") || cpf.equals("55555555555")
                || cpf.equals("66666666666") || cpf.equals("77777777777")
                || cpf.equals("88888888888") || cpf.equals("99999999999")) {
            System.out.println("Formato invalido! ");
            return false;
        }

        char carac;
        int soma, i, digito_verific_1, digito_verific_2, num, dig10, dig11;

        soma = 0;
        dig10 = (int) cpf.charAt(9) - '0';
        dig11 = (int) cpf.charAt(10) - '0';

        for (i = 0; i < 9; i++) {
            carac = cpf.charAt(i);
            num = (int) carac - '0';

            soma = soma + (num * (10 - i));
        }

        if (((soma * 10) % 11) % 10 != dig10) {
            System.out.println("Digito Verificador 1 invalido! ");
            return false;
        }

        soma = 0;

        for (i = 0; i < 10; i++) {
            carac = cpf.charAt(i);
            num = (int) carac - '0';

            soma = soma + (num * (11 - i));
        }

        if (((soma * 10) % 11) % 10 != dig11) {
            System.out.println("Digito Verificador 2 invalido! ");
            return false;
        }
        return true;
    }
}
