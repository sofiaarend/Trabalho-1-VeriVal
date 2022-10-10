package com;
public class CentroDistribuicao {
  // Angelo, Maria Luisa e Sofia 

  public enum SITUACAO {
    NORMAL,
    SOBRAVISO,
    EMERGENCIA,
  }

  public enum TIPOPOSTO {
    COMUM,
    ESTRATEGICO,
    INVALIDO
  }

  public static final int MAX_ADITIVO = 500;
  public static final int MAX_ALCOOL = 2500;
  public static final int MAX_GASOLINA = 10000;
  public int tGasolina;
  public int tAditivo;
  public int tAlcool1;
  public int tAlcool2;
  public SITUACAO situacao;

  public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
    if (tAditivo > MAX_ADITIVO) 
      throw new IllegalArgumentException("INVALIDO"); 
    else if (tAditivo < 0) 
      throw new IllegalArgumentException("INVALIDO"); 
    else 
      this.tAditivo = tAditivo;

    if (tGasolina > MAX_GASOLINA) 
      throw new IllegalArgumentException("INVALIDO"); 
    else if (tGasolina < 0) 
      throw new IllegalArgumentException("INVALIDO"); 
    else 
      this.tGasolina = tGasolina;

    if (tAlcool1 + tAlcool2 > MAX_ALCOOL) 
      throw new IllegalArgumentException("INVALIDO"); 
    else if (tAlcool1 < 0 || tAlcool2 < 0) 
      throw new IllegalArgumentException("INVALIDO"); 
    else if (tAlcool1 != tAlcool2) 
      throw new IllegalArgumentException("INVALIDO"); 
    else {
      this.tAlcool1 = tAlcool1;
      this.tAlcool2 = tAlcool2;
    }

    this.defineSituacao();
  }

  public void defineSituacao() {
    if (
      this.getAditivo() < 0.25 * MAX_ADITIVO ||
      this.getGasolina() < 0.25 * MAX_GASOLINA ||
      this.getAlcool1() < 0.25 * MAX_ALCOOL / 2 ||
      this.getAlcool2() < 0.25 * MAX_ALCOOL / 2
    ) {
      this.situacao = SITUACAO.EMERGENCIA;
    } else if (
      this.getAditivo() < 0.5 * MAX_ADITIVO ||
      this.getGasolina() < 0.5 * MAX_GASOLINA ||
      this.getAlcool1() < 0.5 * MAX_ALCOOL / 2 ||
      this.getAlcool2() < 0.5 * MAX_ALCOOL / 2
    ) {
      this.situacao = SITUACAO.SOBRAVISO;
    } else {
      this.situacao = SITUACAO.NORMAL;
    }
  }

  public SITUACAO getSituacao() {
    return this.situacao;
  }

  public int getGasolina() {
    return this.tGasolina;
  }

  public int getAditivo() {
    return this.tAditivo;
  }

  public int getAlcool1() {
    return this.tAlcool1;
  }

  public int getAlcool2() {
    return this.tAlcool2;
  }

  public int recebeAditivo(int qtdade) {
    if (qtdade <= 0) {
      return -1;
    }

    int livre = MAX_ADITIVO - this.tAditivo;
    int armazenou = 0;
    if (qtdade <= livre) {
      this.tAditivo += qtdade;
      armazenou = qtdade;
    } else {
      this.tAditivo = MAX_ADITIVO;
      armazenou = livre;
    }
    this.defineSituacao();
    return armazenou;
  }

  public int recebeGasolina(int qtdade) {
    if (qtdade <= 0) {
      return -1;
    }

    int livre = MAX_GASOLINA - this.tGasolina;
    int armazenou = 0;
    if (qtdade <= livre) {
      this.tGasolina += qtdade;
      armazenou = qtdade;
    } else {
      this.tGasolina = MAX_GASOLINA;
      armazenou = livre;
    }
    this.defineSituacao();
    return armazenou;
  }

  public int recebeAlcool(int qtdade) {
    if (qtdade <= 0) {
      return -1;
    }

    int livre = MAX_ALCOOL - (this.tAlcool1 + this.tAlcool2);
    int armazenou = 0;
    if (qtdade <= livre) {
      this.tAlcool1 += qtdade/2;
      this.tAlcool2 += qtdade/2;
      armazenou = qtdade;
    } else {
      this.tAlcool1 = MAX_ALCOOL/2;
      this.tAlcool2 = MAX_ALCOOL/2;
      armazenou = livre;
    }
    this.defineSituacao();
    return armazenou;
  }

  public int[] encomendaCombustivel(int qtd, TIPOPOSTO tipoPosto) {
    if (
      qtd <= 0 ||
      (tipoPosto != TIPOPOSTO.COMUM && tipoPosto != TIPOPOSTO.ESTRATEGICO)
    ) {
      return new int[] { -7, 0, 0, 0 };
    }
    if (this.situacao == SITUACAO.EMERGENCIA && tipoPosto == TIPOPOSTO.COMUM) {
      return new int[] { -14, 0, 0, 0 };
    }
    if (this.situacao == SITUACAO.SOBRAVISO && tipoPosto == TIPOPOSTO.COMUM) {
      return calculaCombustivel((int) (qtd * 0.5));
    }
    if (
      this.situacao == SITUACAO.EMERGENCIA && tipoPosto == TIPOPOSTO.ESTRATEGICO
    ) {
      return calculaCombustivel((int) (qtd * 0.5));
    }
    return calculaCombustivel(qtd);
  }

  private int[] calculaCombustivel(int qtd) {
    double qtdAditivo = qtd * 0.05;
    double qtdGasolina = qtd * 0.7;
    double qtdAlcool = qtd * 0.25;

    if (
      (int) qtdAditivo > this.tAditivo ||
      (int) qtdGasolina > this.tGasolina ||
      (int) qtdAlcool > (this.tAlcool1 + this.tAlcool2)
    ) {
      return new int[] { -21, 0, 0, 0 };
    } else {
      this.tAditivo = this.tAditivo - (int) qtdAditivo;
      this.tGasolina = this.tGasolina - (int) qtdGasolina;

      double metadeAlcool = qtdAlcool / 2.0;
      this.tAlcool1 = this.tAlcool1 - (int) metadeAlcool;
      this.tAlcool2 = this.tAlcool2 - (int) metadeAlcool;

      this.defineSituacao();
      
      return new int[] {
        this.tAditivo,
        this.tGasolina,
        this.tAlcool1,
        this.tAlcool2,
      };
    }
  }
}
