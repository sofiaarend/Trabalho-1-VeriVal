package com;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.CentroDistribuicao.TIPOPOSTO;


public class CentroDistribuicaoTest {

  @ParameterizedTest
  @CsvSource({
    "250,5000,625,625,NORMAL",
    "499,9999,1249,1249,NORMAL",
    "249,10000,1250,1250,SOBRAVISO",
    "500,4999,1250,1250,SOBRAVISO",
    "124,10000,1250,1250,EMERGENCIA",
    "500,2499,1250,1250,EMERGENCIA",
  })
  public void shouldConstructWithRightSituacao(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String situacao) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    Assertions.assertEquals(situacao, centroDistribuicao.getSituacao().toString());
  }

  @ParameterizedTest
  @CsvSource({
      "500,10000,624,1250",
      "500,10000,1250,624",
      "500,15000,1250,1250",
      "1500,10000,1250,1250",
      "500,10000,1300,1300",
      "-5,10000,1250,1250",
      "500,-10,1250,1250",
      "500,10000,-12,-12",
      "0,10000,1250,1250", // add pós code coverage
      "500,0,1250,1250", // add pós code coverage
      "500,10000,0,0", // add pós code coverage
  })
  public void shouldThrowIllegalArgumentException(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2) {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new CentroDistribuicao(Integer.parseInt(tAditivo), Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1),
          Integer.parseInt(tAlcool2));
    });
  }

  @ParameterizedTest
  @CsvSource({
      "300,5000,625,625,100,100", // guarda o total recebido
      "400,5000,625,625,300,100", // guarda o que cabe
      "400,5000,625,625,-10,-1", // retorna erro
      "400,5000,625,625,0,-1" // retorna erro
  })
  public void shouldRecebeAditivoReturnTheRightValue(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd, String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAditivo(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "300,8000,625,625,1000,1000",
      "400,9000,625,625,3000,1000",
      "400,8000,625,625,-10,-1",
      "400,8000,625,625,0,-1"
  })
  public void shouldRecebeGasolinaReturnTheRightValue(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeGasolina(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,9000,900,900,400,400",
      "400,9000,1100,1100,400,300",
      "400,8000,1100,1100,-10,-1",
      "400,8000,1100,1100,0,-1"
  })
  public void shouldRecebeAlcoolReturnTheRightValue(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAlcool(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  // Quantidade for invalida
  @ParameterizedTest
  @CsvSource({
      "500,10000,1250,1250,0",
      "500,10000,1250,1250,-10",
  })
  public void shouldReturnQtdInvalidaOnEncomendaCombustivel(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {-7, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // Posto comum
  @ParameterizedTest
  @CsvSource({
      "124,10000,1250,1250,500", // Aditivo emergencia
      "500,2499,1250,1250,500", // Gasolina emergencia
      "500,10000,312,312,500", // Alcool emergencia
  })
  public void shouldReturnTipoPostoInsuficienteOnEncomendaCombustivel(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {-14, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // Posto comum
  @ParameterizedTest
  @CsvSource({
    "500,10000,1250,1250,20000", // Situacao normal
    "249,10000,1250,1250,10000", // Aditivo sobraviso
    "500,4999,1250,1250,14286", // Gasolina sobraviso
    "500,10000,624,624,10000", // Alcool sobraviso
  })
  public void shouldReturnInsuficienteOnEncomendaCombustivelSobraviso(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {-21, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // Posto estrategico
  @ParameterizedTest
  @CsvSource({
    "124,10000,1250,1250,5000", // Aditivo emergencia
    "500,2499,1250,1250,7144", // Gasolina emergencia
    "500,10000,312,312,5000", // Alcool emergencia
})
  public void shouldReturnInsuficienteOnEncomendaCombustivelEmergencia(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.ESTRATEGICO);
    int[] expectedResult = {-21, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // Posto comum
  @ParameterizedTest
  @CsvSource({
    "500,10000,1250,1250,8000,100,4400,250,250", // Situacao normal
    "250,5000,625,625,2000,150,3600,375,375", // Situacao normal
    "249,10000,1250,1250,8000,49,7200,750,750", // Situacao sobraviso
  })
  public void shouldReturnRestoCombustivelPostoComum(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd, String expected1, String expected2, String expected3, String expected4) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {
        Integer.parseInt(expected1),
        Integer.parseInt(expected2),
        Integer.parseInt(expected3),
        Integer.parseInt(expected4)
    };
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // Posto estrategico
  @ParameterizedTest
  @CsvSource({
    "500,10000,1250,1250,8000,100,4400,250,250", // Situacao normal
    "249,10000,1250,1250,4000,49,7200,750,750", // Situacao sobraviso
    "124,10000,1250,1250,4960,0,8264,940,940", // Situacao emergencia
  })
  public void shouldReturnRestoCombustivelPostoEstrategico(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd, String expected1, String expected2, String expected3, String expected4) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.ESTRATEGICO);
    int[] expectedResult = {
        Integer.parseInt(expected1),
        Integer.parseInt(expected2),
        Integer.parseInt(expected3),
        Integer.parseInt(expected4)
    };
    Assertions.assertArrayEquals(expectedResult, result);
  }
}
