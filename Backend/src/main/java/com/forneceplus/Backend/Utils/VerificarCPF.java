package com.forneceplus.Backend.Utils;



public class VerificarCPF {

    public static boolean verificarCPF(String cpf) {
        // Remove não digitos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Checa se o CPF tem 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Checa se todos os dígitos são iguais
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calcula o primeiro dígito verificador
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstCheckDigit = 11 - (sum % 11);
        if (firstCheckDigit > 9) {
            firstCheckDigit = 0;
        }

        // Calcula o segundo dígito verificador
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondCheckDigit = 11 - (sum % 11);
        if (secondCheckDigit > 9) {
            secondCheckDigit = 0;
        }

        // Verifica se os dígitos calculados são iguais aos dígitos do CPF
        return Character.getNumericValue(cpf.charAt(9)) == firstCheckDigit && Character.getNumericValue(cpf.charAt(10)) == secondCheckDigit;
    }

}
