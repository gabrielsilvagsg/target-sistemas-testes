package br.com.gabrielsilva.targetsistemas.testes;

public class Main {

    public static void main(String[] args) {
        teste01();
    }

    public static void teste01() {
        int INDICE = 13;
        int SOMA = 0;
        int K = 0;

        while (K < INDICE) {
            K = K + 1;
            SOMA = SOMA + K;
        }

        System.out.println("Soma total: " + SOMA);
    }
}