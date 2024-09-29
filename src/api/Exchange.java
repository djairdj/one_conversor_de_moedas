package api;

import enums.EnumCoin;
import enums.EnumCoinCombinations;
import org.json.JSONException;
import org.json.JSONObject;

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
  }

  private static JSONObject jsonFromAPI() {
    StringBuilder urlMoedas = new StringBuilder();
    EnumCoinCombinations[] enumSiglasCoins = EnumCoinCombinations.values();
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

  public static double getRate(EnumCoin sourceEnumSiglasCoins, EnumCoin destinyEnumSiglasCoins) {
    return getRate(jsonObject, sourceEnumSiglasCoins, destinyEnumSiglasCoins);
  }

  public static double getRate(JSONObject jsonObject, EnumCoin sourceEnumSiglasCoins, EnumCoin destinyEnumSiglasCoins) {
    if(sourceEnumSiglasCoins == destinyEnumSiglasCoins) return 1;
    try {
      var content = jsonObject.getJSONObject(sourceEnumSiglasCoins.name() + destinyEnumSiglasCoins.name());
      return content.getDouble("bid");
    } catch(JSONException jsonException) {
      throw new RuntimeException("Taxa de câmbio de %s -> %s não encontrada.".formatted(
          sourceEnumSiglasCoins.name(), destinyEnumSiglasCoins.name()));
    }
  }
  
}
