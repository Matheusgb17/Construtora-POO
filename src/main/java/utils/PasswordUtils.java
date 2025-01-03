/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matheus
 */
public class PasswordUtils {

    /**
     * Classe para criptografia de senhas
     *
     * @param senha String que deseja criptografar
     * @return String criptografada
     */
    public static String criptografarSenha(String senha) {
        try {
            // Obtém uma instância do algoritmo de hash SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = md.digest(senha.getBytes()); // Calcula o hash em bytes

            // StringBuilder para construir a string hexadecimal do hash
            StringBuilder sb = new StringBuilder();

            for (byte b : messageDigest) {
                // Converte cada byte em hexadecimal e adiciona ao StringBuilder
                sb.append(String.format("%02X", 0xFF & b));
            }

            // System.out.print(sb.toString());
            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            // Erro caso o algoritmo SHA-256 não esteja disponível
            Logger.getLogger(PasswordUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; // caso inesperado
    }
}
