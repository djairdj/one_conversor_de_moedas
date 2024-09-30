package view;

import api.Exchange;
import enums.EnumCoin;
import enums.EnumCoinToUse;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleIO {
  private static final Scanner scn = new Scanner(System.in);

  public static void start() {
    String welcome = """
        #############################################
              Bem vindo ao conversor de Moedas!
        #############################################
        """;
    System.out.println(welcome);
    try {
      showOptions();
      System.out.println("Grato pela atenção.");
    } catch(InterruptedException e) {
      System.err.println("Houve um problema no nosso programa, contate o administrador do software em https://www.linkedin.com/in/djairdj/");
      e.printStackTrace();
    }
  }

  private static void showOptions() throws InterruptedException {
    EnumCoinToUse baseCoin, conversionCoin;

    baseCoin = getBaseCoin();
    System.out.println();
    conversionCoin = getConversionCoin();
    System.out.println();
    double value = getValueFromUser(baseCoin);

    System.out.println("Moeda base escolhida: " + baseCoin);
    System.out.println("Moeda para conversão: " + conversionCoin);

    Double rate = getRateFromApi(baseCoin, conversionCoin);
    loading();
    if(rate != null) showResultConversion(value, baseCoin, conversionCoin, rate);
    else System.err.println("Não foi possível calcular a taxa de câmbio.");
    System.out.print("\nDeseja efetivar novo câmbio?\nDigite 1 para sim ou qualquer outra coisa para não: ");
    if(scn.nextLine().trim().equals("1")) showOptions();
  }

  private static EnumCoinToUse getBaseCoin() {
    showCoins();
    String messageQuestion = "\nInforme o número correspondente a moeda que desejas cambiar: ";
    String messageError = "Valor informado não condizente com o enunciado, corrija sua resposta.\n";
    EnumCoinToUse[] coins = EnumCoinToUse.values();
    int option = inputNumberValidate(1, coins.length, messageQuestion, messageError);
    return coins[option - 1];
  }

  private static EnumCoinToUse getConversionCoin() {
    showCoins();
    String messageQuestion = "\nInforme o número correspondente a moeda para a qual o valor será convertido: ";
    String messageError = "Valor informado não condizente com o enunciado, corrija sua resposta.\n";
    EnumCoinToUse[] coins = EnumCoinToUse.values();
    int option = inputNumberValidate(1, coins.length, messageQuestion, messageError);
    return coins[option - 1];
  }

  private static double getValueFromUser(EnumCoinToUse baseCoin) {
    String messageQuestion = String.format("Informe o valor em %s que desejas converter: ", baseCoin);
    String messageError = "Valor informado não é numérico, corrija sua resposta.\n";
    return inputNumberValidate(0d, Double.MAX_VALUE - 1, messageQuestion, messageError);
  }

  private static void showCoins() {
    System.out.println();
    EnumCoinToUse[] coins = EnumCoinToUse.values();
    StringBuilder result = new StringBuilder();
    for(int i = 0; i < coins.length; ) {
      result.append(String.format("%d - %s\n", ++i, coins[i - 1]));
    }
    System.out.print(result);
  }

  private static void loading() throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(50);
  }

  private static Double getRateFromApi(EnumCoinToUse coinBase, EnumCoinToUse coinTarget) {
    try {
      return Exchange.getRate(coinBase.sigla, coinTarget.sigla);
    } catch(Exception ex) {
      System.err.println("Houve um problema ao acessa a API: " + ex.getMessage());
    }
    return null;
  }

  private static void showResultConversion(double value, EnumCoinToUse base, EnumCoinToUse target, double rate) {
    NumberFormat moneyBase = NumberFormat.getCurrencyInstance(base.local);
    NumberFormat moneyConversion = NumberFormat.getCurrencyInstance(target.local);
    double newValue = value * rate;

    String messageBaseCoin =
        moneyBase.format(value),
        messageTargetCoin = moneyConversion.format(newValue),
        bitSymbol = "₿";

    DecimalFormat formatoBitcoin = new DecimalFormat("#,##0.##########");

    if(target.sigla == EnumCoin.BTC) {
      messageTargetCoin = String.format("%s %s", bitSymbol, formatoBitcoin.format(newValue));
    }
    if(base.sigla == EnumCoin.BTC) {
      messageBaseCoin = String.format("%s %s", bitSymbol, formatoBitcoin.format(value));
    }

    System.out.println("Efetivando o câmbio de " + EnumCoin.descriptionFromCombinations(base.sigla, target.sigla));
    System.out.printf("O valor %s na moeda %s dará ~= %s em %s\n", messageBaseCoin, base, messageTargetCoin, target);
  }

  private static int inputNumberValidate(int startWith, int endWith, String question, String messageError) {
    if(startWith > endWith) {
      int tmp = startWith;
      startWith = endWith;
      endWith = tmp;
    }
    int option;
    while(true) {
      try {
        System.out.print(question);
        option = Integer.parseInt(scn.nextLine());
        if(option >= startWith && option <= endWith) return option;
        else throw new IllegalArgumentException();
      } catch(Exception e) {
        System.err.print(messageError);
      }
    }
  }

  private static double inputNumberValidate(double startWith, double endWith, String question, String messageError) {
    if(startWith > endWith) {
      double tmp = startWith;
      startWith = endWith;
      endWith = tmp;
    }
    double option;
    while(true) {
      try {
        System.out.print(question);
        option = Double.parseDouble(scn.nextLine().replaceAll(",", "."));
        if(option >= startWith && option <= endWith) return option;
        else throw new IllegalArgumentException();
      } catch(Exception e) {
        System.err.print(messageError);
      }
    }
  }
}
