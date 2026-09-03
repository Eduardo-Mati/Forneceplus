package com.forneceplus.Backend.Utils;



public class VerificarCPF {

    public static boolean verificarCPF(String cpf) {
        return cpf.matches("[0-9]{11}");
    }

}
