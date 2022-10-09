package src.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import src.main.CentroDistribuicao;
import src.main.CentroDistribuicao.TIPOPOSTO;

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
      "500,10000,312,1250",
      "500,10000,1250,312",
      "500,10000,1250,2000",
      "500,15000,1250,312",
      "1500,10000,1250,312",
      "-5,10000,1250,312",
      "500,-10,1250,312",
      "500,10000,-12,312",
      "500,10000,1250,-12"
  })
  public void shouldThrowIllegalArgumentException(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2) {
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      new CentroDistribuicao(Integer.parseInt(tAditivo), Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1),
          Integer.parseInt(tAlcool2));
    });
  }

  @ParameterizedTest
  @CsvSource({
      "300,5000,625,625,100,100",
  })
  public void shouldStoreTotalAditivo(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd, String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAditivo(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,5000,625,625,200,100",
  })
  public void shouldStoreHalfAditivo(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAditivo(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,5000,625,625,-10,-1",
      "400,5000,625,625,0,-1"
  })
  public void shouldReturnErrorOnRecebeAditivo(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd, String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAditivo(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "300,8000,625,625,1000,1000",
  })
  public void shouldStoreTotalGasolina(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeGasolina(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,9000,625,625,2000,1000",
  })
  public void shouldStoreHalfGasolina(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeGasolina(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,8000,625,625,-10,-1",
      "400,8000,625,625,0,-1"
  })
  public void shouldReturnErrorOnRecebeGasolina(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeGasolina(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,9000,900,900,400,400",
  })
  public void shouldStoreTotalAlcool(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAlcool(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,9000,1100,1100,400,300",
  })
  public void shouldStoreLessAlcool(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAlcool(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  @ParameterizedTest
  @CsvSource({
      "400,8000,1100,1100,-10,-1",
      "400,8000,1100,1100,0,-1"
  })
  public void shouldReturnErrorOnRecebeAlcool(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2, String qtd,
      String expected) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));
    int result = centroDistribuicao.recebeAlcool(Integer.parseInt(qtd));
    Assertions.assertEquals(Integer.parseInt(expected), result);
  }

  // -7 se for tipo posto invalido 
  @ParameterizedTest
  @CsvSource({
      "500,10000,1250,1250,200",
  })
  public void shouldReturnPostoInvalidoOnEncomendaCombustivel(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.INVALIDO);
    int[] expectedResult = {-7, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // -7 se quantidade for invalida
  @ParameterizedTest
  @CsvSource({
      "500,10000,1250,1250,0",
  })
  public void shouldReturnQtdInvalidaOnEncomendaCombustivel(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {-7, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // -14 situacao emergencia e posto comum
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

  // -21 combustivel não suficiente
  // situacao sobraviso posto comum
  @ParameterizedTest
  @CsvSource({
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

  // situacao emergencia posto estrategico
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

  // situacao normal 
  @ParameterizedTest
  @CsvSource({
      "500,10000,1250,1250,20000",
  })
  public void shouldReturnInsuficienteOnEncomendaCombustivelNormal(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
      String qtd) {
    CentroDistribuicao centroDistribuicao = new CentroDistribuicao(Integer.parseInt(tAditivo),
        Integer.parseInt(tGasolina), Integer.parseInt(tAlcool1), Integer.parseInt(tAlcool2));

    int[] result = centroDistribuicao.encomendaCombustivel(Integer.parseInt(qtd), TIPOPOSTO.COMUM);
    int[] expectedResult = {-21, 0, 0, 0};
    Assertions.assertArrayEquals(expectedResult, result);
  }

  // retorna o que restou de combustivel 
  // situacao normal
  @ParameterizedTest
  @CsvSource({
      "500,10000,1250,1250,8000,100,4400,250,250",
      "250,5000,625,625,2000,150,3600,375,375",
  })
  public void shouldReturnRestoCombustivelSituacaoNormal(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
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

  // situacao sobraviso - comum
  @ParameterizedTest
  @CsvSource({
    "249,10000,1250,1250,8000,49,7200,750,750", // Aditivo sobraviso -- add resto
    // "500,4999,1250,1250,14286", // Gasolina sobraviso
    // "500,10000,624,624,10000", // Alcool sobraviso
  })
  public void shouldReturnRestoCombustivelSituacaoSobraviso(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
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

  // teste sobreviso - estrategico
  @ParameterizedTest
  @CsvSource({
    "249,10000,1250,1250,4000,49,7200,750,750", // Aditivo sobraviso -- add resto
    // "500,4999,1250,1250,14286", // Gasolina sobraviso
    // "500,10000,624,624,10000", // Alcool sobraviso
  })
  public void shouldReturnRestoCombustivelSituacaoSobravisoEstrategico(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
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

  // situacao emergencia - estrategico
  @ParameterizedTest
  @CsvSource({
      "124,10000,1250,1250,4960,0,8264,940,940", // Aditivo emergencia
      // "500,2499,1250,1250,7144", // Gasolina emergencia
      // "500,10000,312,312,5000", // Alcool emergencia
  })
  public void shouldReturnRestoCombustivelSituacaoEmergencia(String tAditivo, String tGasolina, String tAlcool1, String tAlcool2,
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
