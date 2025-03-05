package br.com.gabrielsilva.targetsistemas.testes;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

public class Main {

    private static DecimalFormat currencyFormat = new DecimalFormat("###,###,###.##");

    public static void main(String[] args) {
        teste01();
        teste02();
        teste03();
        teste04();
        teste05();
    }

    public static void teste01() {
        System.out.println("------------------Teste 01-------------------------");
        int INDICE = 13;
        int SOMA = 0;
        int K = 0;

        while (K < INDICE) {
            K = K + 1;
            SOMA = SOMA + K;
        }

        System.out.println("Soma total: " + SOMA);
        System.out.println("----------------------------------------------------");
    }

    public static void teste02() {
        System.out.println("------------------Teste 02-------------------------");
        // Gerando um número aleatório ;)
        int numero = new Random().nextInt(31);

        System.out.println("Número gerado: " + numero);

        if (pertenceAFibonacci(numero)) {
            System.out.println(numero + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println(numero + " não pertence à sequência de Fibonacci.");
        }
        System.out.println("----------------------------------------------------");
    }

    public static void teste03() {
        System.out.println("------------------Teste 03-------------------------");
        
        List<FaturamentoDia> faturamentos = readRevenueFromJson("src/main/resources/faturamento.json");

        double lowestRevenue = getLowestRevenue(faturamentos);
        double highestRevenue = getHighestRevenue(faturamentos);
        double averageRevenue = getAverageRevenue(faturamentos);
        int daysAboveAverage = countDaysAboveAverage(faturamentos, averageRevenue);

        System.out.println("Menor faturamento: R$" + currencyFormat.format(lowestRevenue));
        System.out.println("Maior faturamento: R$" + currencyFormat.format(highestRevenue));
        System.out.println("Média de faturamento: R$" + currencyFormat.format(averageRevenue));
        System.out.println("Dias acima da média: " + daysAboveAverage);
        System.out.println("----------------------------------------------------");
    }

    public static void teste04() {
        System.out.println("------------------Teste 04-------------------------");
        double spRevenue = 67836.43;
        double rjRevenue = 36678.66;
        double mgRevenue = 29229.88;
        double esRevenue = 27165.48;
        double otherRevenue = 19849.53;

        double totalRevenue = spRevenue + rjRevenue + mgRevenue + esRevenue + otherRevenue;

        System.out.println("Faturamento total: R$ " + currencyFormat.format(totalRevenue));

        DecimalFormat percentageFormat = new DecimalFormat("0.0");

        System.out.println(
                "Percentual de SP: " + percentageFormat.format(calculatePercentage(spRevenue, totalRevenue)) + "%");
        System.out.println(
                "Percentual de RJ: " + percentageFormat.format(calculatePercentage(rjRevenue, totalRevenue)) + "%");
        System.out.println(
                "Percentual de MG: " + percentageFormat.format(calculatePercentage(mgRevenue, totalRevenue)) + "%");
        System.out.println(
                "Percentual de ES: " + percentageFormat.format(calculatePercentage(esRevenue, totalRevenue)) + "%");
        System.out.println("Percentual de Outros: "
                + percentageFormat.format(calculatePercentage(otherRevenue, totalRevenue)) + "%");
        System.out.println("----------------------------------------------------");
    }

    public static void teste05() {
        System.out.println("------------------Teste 05-------------------------");
        String nome = "Gabriel";
        System.out.println("Nome: " + nome);
        System.out.println("Nome invertido: " + reverseString(nome));
        System.out.println("----------------------------------------------------");
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

    public static List<FaturamentoDia> readRevenueFromJson(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            // Lê o JSON e retorna uma lista de objetos FaturamentoDia
            return List.of(gson.fromJson(reader, FaturamentoDia[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double getLowestRevenue(List<FaturamentoDia> faturamentos) {
        double lowest = Double.MAX_VALUE;
        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > 0 && faturamento.getValor() < lowest) {
                lowest = faturamento.getValor();
            }
        }
        return lowest;
    }

    public static double getHighestRevenue(List<FaturamentoDia> faturamentos) {
        double highest = Double.MIN_VALUE;
        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > highest) {
                highest = faturamento.getValor();
            }
        }
        return highest;
    }

    public static double getAverageRevenue(List<FaturamentoDia> faturamentos) {
        double sum = 0;
        int daysWithRevenue = 0;

        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > 0) {
                sum += faturamento.getValor();
                daysWithRevenue++;
            }
        }

        return daysWithRevenue > 0 ? sum / daysWithRevenue : 0;
    }

    public static int countDaysAboveAverage(List<FaturamentoDia> faturamentos, double average) {
        int days = 0;

        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > average) {
                days++;
            }
        }
        return days;
    }

    public static double calculatePercentage(double revenue, double totalRevenue) {
        return (revenue / totalRevenue) * 100;
    }

    public static String reverseString(String str) {
        String inverted = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            inverted += str.charAt(i);
        }
        return inverted;
    }
}