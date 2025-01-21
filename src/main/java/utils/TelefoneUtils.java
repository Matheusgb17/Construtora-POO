/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Matal
 */
public class TelefoneUtils {

    public static boolean validarTelefone(String tel) {
        return tel.matches("\\d{11}") || (tel.matches("\\d{9}") && tel.charAt(0) == '9') || tel.matches("\\d{8}");
    }

    public static String formatarTelefone(String tel) {
        if (tel.matches("\\d{11}")) {
            return String.format("(%s) %s-%s", tel.substring(0, 2), tel.substring(2, 7), tel.substring(7, 11));
        }
        if (tel.matches("\\d{9}") && tel.charAt(0) == '9') {
            return String.format("%s-%s", tel.substring(0, 5), tel.substring(5, 9));
        }
        if (tel.matches("\\d{8}")) {
            return String.format("%s-%s", tel.substring(0, 4), tel.substring(4, 8));
        }
        return null;
    }
}
