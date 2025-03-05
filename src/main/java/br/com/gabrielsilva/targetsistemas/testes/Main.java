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
        // Executa todos os testes sequencialmente
        teste01();
        teste02();
        teste03();
        teste04();
        teste05();
    }

    public static void teste01() {
        // Teste de soma dos números de 1 até o índice 13
        System.out.println("------------------Teste 01-------------------------");
        int INDICE = 13;
        int SOMA = 0;
        int K = 0;

        // Calcula a soma de 1 até 13
        while (K < INDICE) {
            K = K + 1;
            SOMA = SOMA + K;
        }

        // Exibe o resultado
        System.out.println("Soma total: " + SOMA);
        System.out.println("----------------------------------------------------");
    }

    public static void teste02() {
        // Teste para verificar se um número aleatório pertence à sequência de Fibonacci
        System.out.println("------------------Teste 02-------------------------");
        
        // Gerando um número aleatório entre 0 e 30
        int numero = new Random().nextInt(31);

        System.out.println("Número gerado: " + numero);

        // Verifica se o número gerado pertence à sequência de Fibonacci
        if (pertenceAFibonacci(numero)) {
            System.out.println(numero + " pertence à sequência de Fibonacci.");
        } else {
            System.out.println(numero + " não pertence à sequência de Fibonacci.");
        }
        System.out.println("----------------------------------------------------");
    }

    public static void teste03() {
        // Teste para calcular o menor, maior e média de faturamento, além dos dias acima da média
        System.out.println("------------------Teste 03-------------------------");
        
        // Lê os dados de faturamento do arquivo JSON
        List<FaturamentoDia> faturamentos = readRevenueFromJson("src/main/resources/faturamento.json");

        // Calcula o menor e maior faturamento
        double lowestRevenue = getLowestRevenue(faturamentos);
        double highestRevenue = getHighestRevenue(faturamentos);
        
        // Calcula a média de faturamento
        double averageRevenue = getAverageRevenue(faturamentos);
        
        // Conta os dias com faturamento acima da média
        int daysAboveAverage = countDaysAboveAverage(faturamentos, averageRevenue);

        // Exibe os resultados
        System.out.println("Menor faturamento: R$" + currencyFormat.format(lowestRevenue));
        System.out.println("Maior faturamento: R$" + currencyFormat.format(highestRevenue));
        System.out.println("Média de faturamento: R$" + currencyFormat.format(averageRevenue));
        System.out.println("Dias acima da média: " + daysAboveAverage);
        System.out.println("----------------------------------------------------");
    }

    public static void teste04() {
        // Teste para calcular o percentual de faturamento por estado
        System.out.println("------------------Teste 04-------------------------");
        
        // Faturamento de cada estado
        double spRevenue = 67836.43;
        double rjRevenue = 36678.66;
        double mgRevenue = 29229.88;
        double esRevenue = 27165.48;
        double otherRevenue = 19849.53;

        // Calcula o faturamento total
        double totalRevenue = spRevenue + rjRevenue + mgRevenue + esRevenue + otherRevenue;

        // Exibe o faturamento total
        System.out.println("Faturamento total: R$ " + currencyFormat.format(totalRevenue));

        // Formatação de percentual
        DecimalFormat percentageFormat = new DecimalFormat("0.0");

        // Exibe os percentuais de faturamento por estado
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
        // Teste para inverter um nome
        System.out.println("------------------Teste 05-------------------------");
        
        String nome = "Gabriel";
        System.out.println("Nome: " + nome);
        System.out.println("Nome invertido: " + reverseString(nome));
        System.out.println("----------------------------------------------------");
    }

    public static boolean pertenceAFibonacci(int numero) {
        // Função para verificar se o número pertence à sequência de Fibonacci
        int primeiro = 0, segundo = 1;

        // Verifica os dois primeiros números da sequência
        if (numero == primeiro || numero == segundo) {
            return true;
        }

        // Calcula os próximos números da sequência até encontrar o número ou ultrapassá-lo
        while (segundo < numero) {
            int proximoNumero = primeiro + segundo;
            primeiro = segundo;
            segundo = proximoNumero;
        }

        // Retorna se o número foi encontrado na sequência
        return segundo == numero;
    }

    public static List<FaturamentoDia> readRevenueFromJson(String filePath) {
        // Função para ler dados de faturamento de um arquivo JSON
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
        // Função para calcular o menor faturamento do mês
        double lowest = Double.MAX_VALUE;
        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > 0 && faturamento.getValor() < lowest) {
                lowest = faturamento.getValor();
            }
        }
        return lowest;
    }

    public static double getHighestRevenue(List<FaturamentoDia> faturamentos) {
        // Função para calcular o maior faturamento do mês
        double highest = Double.MIN_VALUE;
        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > highest) {
                highest = faturamento.getValor();
            }
        }
        return highest;
    }

    public static double getAverageRevenue(List<FaturamentoDia> faturamentos) {
        // Função para calcular a média de faturamento do mês, ignorando dias sem faturamento
        double sum = 0;
        int daysWithRevenue = 0;

        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > 0) {
                sum += faturamento.getValor();
                daysWithRevenue++;
            }
        }

        // Calcula a média, evitando divisão por zero
        return daysWithRevenue > 0 ? sum / daysWithRevenue : 0;
    }

    public static int countDaysAboveAverage(List<FaturamentoDia> faturamentos, double average) {
        // Função para contar quantos dias o faturamento foi acima da média
        int days = 0;

        for (FaturamentoDia faturamento : faturamentos) {
            if (faturamento.getValor() > average) {
                days++;
            }
        }
        return days;
    }

    public static double calculatePercentage(double revenue, double totalRevenue) {
        // Função para calcular o percentual de faturamento em relação ao total
        return (revenue / totalRevenue) * 100;
    }

    public static String reverseString(String str) {
        // Função para inverter uma string
        String inverted = "";
        for (int i = str.length() - 1; i >= 0; i--) {
            inverted += str.charAt(i);
        }
        return inverted;
    }
}