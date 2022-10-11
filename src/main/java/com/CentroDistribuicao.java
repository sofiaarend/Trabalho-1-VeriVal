package com;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CentroDistribuicao {

    public static final int MAX_ADITIVO = 500;
    public static final int MAX_ALCOOL = 2500;
    public static final int MAX_GASOLINA = 10000;
    private int TanqueAditivo = 0;
    private int TanqueAlcool1 = 0;
    private int TanqueAlcool2 = 0;
    private int TanqueGasolina = 0;
    private SITUACAO Situacao = SITUACAO.NORMAL;

    public CentroDistribuicao(int tAditivo, int tGasolina, int tAlcool1, int tAlcool2) {
        if (tAlcool1 != tAlcool2 || tAditivo > MAX_ADITIVO || tGasolina > MAX_GASOLINA || (tAlcool1 + tAlcool2) > MAX_ALCOOL) {
            throw new IllegalArgumentException();
        }

        if (tAditivo == 0 || tGasolina == 0 || tAlcool1 == 0 || tAlcool2 == 0) {
            throw new IllegalArgumentException();
        }

        TanqueAditivo = tAditivo;
        TanqueAlcool1 = tAlcool1;
        TanqueAlcool2 = tAlcool2;
        TanqueGasolina = tGasolina;

        defineSituacao();
    }

    public void defineSituacao() {
        SITUACAO situacaoAlcool = SITUACAO.NORMAL;
        SITUACAO situacaoAditivo = SITUACAO.NORMAL;
        SITUACAO situacaoGasolina = SITUACAO.NORMAL;

        // Valida Alcool
        if ((TanqueAlcool1 + TanqueAlcool2) < MAX_ALCOOL / 2) {
            situacaoAlcool = (TanqueAlcool1 + TanqueAlcool2) < MAX_ALCOOL / 4 ? SITUACAO.EMERGENCIA : SITUACAO.SOBRAVISO;
        }
        // Valida Aditivo
        if (TanqueAditivo < MAX_ADITIVO / 2) {
            situacaoAditivo = TanqueAditivo < MAX_ADITIVO / 4 ? SITUACAO.EMERGENCIA : SITUACAO.SOBRAVISO;
        }
        // Valida Gasolina
        if (TanqueGasolina < MAX_GASOLINA / 2) {
            situacaoGasolina = TanqueGasolina < MAX_GASOLINA / 4 ? SITUACAO.EMERGENCIA : SITUACAO.SOBRAVISO;
        }

        List<SITUACAO> sitList = Arrays.asList(situacaoAlcool,
                situacaoAditivo,
                situacaoGasolina);

        Situacao = sitList.stream()
                .max(Comparator.comparing(Enum::ordinal))
                .orElse(SITUACAO.NORMAL);
    }

    public SITUACAO getSituacao() {
        return Situacao;
    }

    public int gettGasolina() {
        return TanqueGasolina;
    }

    public int gettAditivo() {
        return TanqueAditivo;
    }

    public int gettAlcool1() {
        return TanqueAlcool1;
    }

    public int gettAlcool2() {
        return TanqueAlcool2;
    }

    public int recebeAditivo(int qtdade) { 
        if(qtdade <= 0) {
            return -1;
        }

        if(qtdade > MAX_ADITIVO - gettAditivo()) {
            int aux = qtdade - (MAX_ADITIVO - gettAditivo());
            TanqueAditivo += aux;
            return aux;
        } else {
            TanqueAditivo += qtdade;
            return qtdade;
        }
    }

    public int recebeGasolina(int qtdade) { 
        if(qtdade <= 0) {
            return -1;
        }

        if(qtdade > MAX_GASOLINA - gettGasolina()) {
            int aux = qtdade - (MAX_GASOLINA - gettGasolina());
            TanqueGasolina += aux;
            return aux;
        } else {
            TanqueGasolina += qtdade;
            return qtdade;
        }
    }

    public int recebeAlcool(int qtdade) { 
        if(qtdade <= 0) {
            return -1;
        }

        if(qtdade > MAX_ALCOOL - (gettAlcool1() + gettAlcool2())) {
            int aux = qtdade - (MAX_ALCOOL - (gettAlcool1() + gettAlcool2()));
            TanqueAlcool1 += aux/2;
            TanqueAlcool2 += aux/2;
            return aux;
        } else {
            TanqueAlcool1 += qtdade/2;
            TanqueAlcool2 += qtdade/2; 
            return qtdade;
        }
    }

    public int[] encomendaCombustivel(int qtdade, TIPOPOSTO tipoPosto) {

        if(qtdade < 0) {
            return new int[-7];
        }

        if(tipoPosto == TIPOPOSTO.COMUM && Situacao == SITUACAO.EMERGENCIA) {
            return new int [-14];
        }

        if(gettAditivo() <= 0 || gettAlcool1() <= 0 || gettAlcool2() <= 0 || gettGasolina() <=0) {
            return new int [-21];
        }

        if(Situacao == SITUACAO.SOBRAVISO && tipoPosto == TIPOPOSTO.COMUM) {
            int metade = qtdade/2;

            int qtdAditivo = (int)(metade*(5/100.0f));
            int qtdGasolina = (int)(metade*(70/100.0f));
            int qtdAlcool = (int)(metade*(25/100.0f));

            int metadeAlcool = qtdAlcool/2;

            if(gettAditivo() < qtdAditivo || gettAlcool1() < metadeAlcool || gettAlcool2() < metadeAlcool || gettGasolina() < qtdGasolina) {
                return new int [-21];
            } else {
                TanqueAditivo -= qtdAditivo;
                TanqueGasolina -= qtdGasolina;
                TanqueAlcool1 -= metadeAlcool;
                TanqueAlcool2 -= metadeAlcool;
                defineSituacao();
            }
        } else if(Situacao == SITUACAO.EMERGENCIA && tipoPosto == TIPOPOSTO.ESTRATEGICO) {
            int metade = qtdade/2;

            int qtdAditivo = (int)(metade*(5/100.0f));
            int qtdGasolina = (int)(metade*(70/100.0f));
            int qtdAlcool = (int)(metade*(25/100.0f));

            int metadeAlcool = qtdAlcool/2;

            if(gettAditivo() < qtdAditivo || gettAlcool1() < metadeAlcool || gettAlcool2() < metadeAlcool || gettGasolina() < qtdGasolina) {
                return new int [-21];
            } else {
                TanqueAditivo -= qtdAditivo;
                TanqueGasolina -= qtdGasolina;
                TanqueAlcool1 -= metadeAlcool;
                TanqueAlcool2 -= metadeAlcool;
                defineSituacao();
            }
        } else {
            int qtdAditivo = (int)(qtdade*(5/100.0f));
            int qtdGasolina = (int)(qtdade*(70/100.0f));
            int qtdAlcool = (int)(qtdade*(25/100.0f));

            int metadeAlcool = qtdAlcool/2;

            if(gettAditivo() < qtdAditivo || gettAlcool1() < metadeAlcool || gettAlcool2() < metadeAlcool || gettGasolina() < qtdGasolina) {
                return new int [-21];
            } else {
                TanqueAditivo -= qtdAditivo;
                TanqueGasolina -= qtdGasolina;
                TanqueAlcool1 -= metadeAlcool;
                TanqueAlcool2 -= metadeAlcool;
                defineSituacao();
            }
        }

        return new int[]{gettAditivo(), gettGasolina(), gettAlcool1(), gettAlcool2()};
    }

    public enum SITUACAO {NORMAL, SOBRAVISO, EMERGENCIA}

    public enum TIPOPOSTO {COMUM, ESTRATEGICO}
}
