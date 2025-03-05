package br.com.gabrielsilva.targetsistemas.testes;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.google.gson.Gson;

public class Main {

    public static void main(String[] args) {
        teste01();
        teste02();
        teste03();
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

    public static void teste03() {
        double[] revenues = readRevenueFromJson("src/main/resources/faturamento.json");

        double lowestRevenue = getLowestRevenue(revenues);
        double highestRevenue = getHighestRevenue(revenues);
        double averageRevenue = getAverageRevenue(revenues);
        int daysAboveAverage = countDaysAboveAverage(revenues, averageRevenue);

        System.out.println("Menor faturamento: " + lowestRevenue);
        System.out.println("Maior faturamento: " + highestRevenue);
        System.out.println("Média de faturamento: " + averageRevenue);
        System.out.println("Dias acima da média: " + daysAboveAverage);
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

     public static double[] readRevenueFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, double[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return new double[0];
        }
    }

    public static double getLowestRevenue(double[] revenues) {
        double lowest = Double.MAX_VALUE;
        for (double revenue : revenues) {
            if (revenue > 0 && revenue < lowest) {
                lowest = revenue;
            }
        }
        return lowest;
    }

    public static double getHighestRevenue(double[] revenues) {
        double highest = Double.MIN_VALUE;
        for (double revenue : revenues) {
            if (revenue > highest) {
                highest = revenue;
            }
        }
        return highest;
    }

    public static double getAverageRevenue(double[] revenues) {
        double sum = 0;
        int daysWithRevenue = 0;

        for (double revenue : revenues) {
            if (revenue > 0) {
                sum += revenue;
                daysWithRevenue++;
            }
        }

        return daysWithRevenue > 0 ? sum / daysWithRevenue : 0;
    }

    public static int countDaysAboveAverage(double[] revenues, double average) {
        int days = 0;

        for (double revenue : revenues) {
            if (revenue > average) {
                days++;
            }
        }
        return days;
    }
}