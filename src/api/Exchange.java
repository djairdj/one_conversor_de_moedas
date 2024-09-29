package api;

import enums.EnumCoins;
import enums.EnumCombinations;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class Exchange {
  public static final JSONObject jsonObject;
  private static final HttpClient client = HttpClient.newHttpClient();
  private static final String URL = "https://economia.awesomeapi.com.br/last/";

  static {
    jsonObject = jsonFromAPI();
//    jsonObject = jsonFromFile("src\\cofrinho\\api\\exchange.json");
  }

  /**
   * Função realiza a leitura de um texto já formatado em JSON num arquivo do diretório, após isso, retorna um JSONObject oriundo do texto citado.
   *
   * @param path Caminho do arquivo que contém o JSON já formatado.
   * @return JSONObject resultante do parse do texto do arquivo lido, ou um JSONObject vazio caso não seja possível a leitura
   */
  private static JSONObject jsonFromFile(String path) {
    StringBuilder sb = new StringBuilder();
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
      String linha;
      while((linha = reader.readLine()) != null) sb.append(linha);
    } catch(IOException e) {
      e.printStackTrace();
    }
    return new JSONObject(sb.toString());
  }

  private static JSONObject jsonFromAPI() {
    StringBuilder urlMoedas = new StringBuilder();
    EnumCombinations[] enumSiglasCoins = EnumCombinations.values();
    for(var sourceEnumSiglasCoins : enumSiglasCoins) urlMoedas.append(",%s".formatted(sourceEnumSiglasCoins));

    urlMoedas = new StringBuilder(URL + urlMoedas.substring(1));
    HttpRequest request = HttpRequest.newBuilder(URI.create(urlMoedas.toString())).GET().build();
    try {
      return new JSONObject(
          client.send(
              request, HttpResponse.BodyHandlers.ofString()
          ).body()
      );
    } catch(IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public static double getRate(EnumCoins sourceEnumSiglasCoins, EnumCoins destinyEnumSiglasCoins) {
    return getRate(jsonObject, sourceEnumSiglasCoins, destinyEnumSiglasCoins);
  }

  public static double getRate(JSONObject jsonObject, EnumCoins sourceEnumSiglasCoins, EnumCoins destinyEnumSiglasCoins) {
    if(sourceEnumSiglasCoins == destinyEnumSiglasCoins) return 1;
    try {
      var content = jsonObject.getJSONObject(sourceEnumSiglasCoins.name() + destinyEnumSiglasCoins.name());
      return content.getDouble("bid");
    } catch(JSONException jsonException) {
      throw new RuntimeException("Taxa de câmbio de %s -> %s não encontrada.".formatted(
          sourceEnumSiglasCoins.name(), destinyEnumSiglasCoins.name()));
    }
  }

  public static String getDataCreated(JSONObject jsonObject, EnumCoins sourceEnumSiglasCoins, EnumCoins destinyEnumSiglasCoins) {
    try {
      var content = jsonObject.getJSONObject(sourceEnumSiglasCoins.name() + destinyEnumSiglasCoins.name());
      return content.getString("create_date");
    } catch(JSONException jsonException) {
      throw new RuntimeException("Data de checagem do câmbio não encontrada");
    }
  }

  public static String getDataCreated(EnumCoins sourceEnumSiglasCoins, EnumCoins destinyEnumSiglasCoins) {
    return getDataCreated(jsonObject, sourceEnumSiglasCoins, destinyEnumSiglasCoins);
  }
}
