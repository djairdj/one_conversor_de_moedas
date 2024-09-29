package enums;


import api.Exchange;

import java.util.Locale;

public enum MoedaTipo {
  REAL(EnumCoins.BRL, new Locale("pt", "BR")),
  DOLLAR(EnumCoins.USD, new Locale("en", "US")),
  EURO(EnumCoins.EUR, new Locale(Locale.ITALIAN.getCountry(), "IT")),
  LIBRAS_ESTERLINAS(EnumCoins.GBP, new Locale(Locale.UK.getLanguage(), Locale.UK.getCountry())),
  BITCOIN(EnumCoins.BTC, new Locale(Locale.ROOT.getLanguage())),
  ARS(EnumCoins.ARS, new Locale("es", "AR")),

  ;

  private double cambio;
  private Locale local;
  public EnumCoins sigla;

  private MoedaTipo(EnumCoins sigla, Locale local) {
    this.sigla = sigla;
    this.cambio = Exchange.getRate(sigla, EnumCoins.BRL);
    this.local = local;
  }

  public double cambio() {
    return this.cambio;
  }

  public void setCambio(double cambio) {
    this.cambio = Math.abs(cambio);
  }

  public Locale local() {
    return this.local;
  }
}
