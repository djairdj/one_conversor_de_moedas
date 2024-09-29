package enums;

import java.util.Locale;

public enum EnumCoinToUse {
  REAL(EnumCoin.BRL, new Locale("pt", "BR")),
  DOLLAR(EnumCoin.USD, new Locale("en", "US")),
  EURO(EnumCoin.EUR, new Locale(Locale.ITALIAN.getCountry(), "IT")),
  LIBRAS_ESTERLINAS(EnumCoin.GBP, new Locale(Locale.UK.getLanguage(), Locale.UK.getCountry())),
  BITCOIN(EnumCoin.BTC, new Locale(Locale.ROOT.getLanguage())),
  //ARS(EnumCoins.ARS, new Locale("es", "AR")),
  ;

  public final Locale local;
  public final EnumCoin sigla;

  EnumCoinToUse(EnumCoin sigla, Locale local) {
    this.sigla = sigla;
    this.local = local;
  }

}
