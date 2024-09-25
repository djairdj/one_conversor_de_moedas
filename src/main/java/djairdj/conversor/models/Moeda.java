package djairdj.conversor.models;

public class Moeda {
  MoedaTipo tipo;
  double valor;

  public Moeda(double value, MoedaTipo tipoMoeda) {
    this.tipo = tipoMoeda;
    this.valor = value;
  }

  public String toString() {
    return "Moeda de " + this.tipo + ", valor: " + this.valor + " -> Câmbio: " + this.tipo.cambio();
  }
}
