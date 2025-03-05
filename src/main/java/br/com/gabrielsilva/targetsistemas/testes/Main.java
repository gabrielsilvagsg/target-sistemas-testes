package br.com.gabrielsilva.targetsistemas.testes;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        teste01();
        teste02();
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

    public static void teste02() {
        // Gerando um número aleatório ;)
        int numero = new Random().nextInt(31);
    
        System.out.println("Número gerado: " + numero);
    
        if (pertenceAFibonacci(numero)) {
            System.out.println(numero + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println(numero + " não pertence à sequência de Fibonacci.");
        }
    }

    public static boolean pertenceAFibonacci(int numero) {
        int primeiro = 0, segundo = 1;

        if (numero == primeiro || numero == segundo) {
            return true;
        }

        while (segundo < numero) {
            int proximoNumero = primeiro + segundo;
            primeiro = segundo;
            segundo = proximoNumero;
        }

        return segundo == numero;
    }
}