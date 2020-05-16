/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Victor_Aguiar
 */
public class MesAnosEmDiasCadastroCalendarioObrigatorio {

    public int anoOuDias(String tempo) {
        if (tempo.charAt(0) == '0') {
            return anosParaDias(tempo);
        } else {
            return meseanosParaDias(tempo);
        }
    }

    public int meseanosParaDias(String mes) {
        switch (mes) {
            case "1":
                return 30;
            case "2":
                return 60;
            case "3":
                return 90;
            case "4":
                return 120;
            case "5":
                return 150;
            case "6":
                return 180;
            case "7":
                return 210;
            case "8":
                return 240;
            case "9":
                return 270;
            case "10":
                return 300;
            case "11":
                return 330;
            case "01":
                return 365;
            case "02":
                return 365 * 2;
            case "03":
                return 365 * 3;
            case "04":
                return 365 * 4;
            case "05":
                return 365 * 5;
            case "06":
                return 365 * 6;
            case "07":
                return 365 * 7;
            case "08":
                return 365 * 8;
            case "09":
                return 365 * 9;
            case "010":
                return 365 * 10;
            case "011":
                return 365 * 11;
            default:
                break;
        }
        return 0;
    }

    public int anoParaDias(int ano) {
        return 365 * ano;
    }

    public int mesParaDias(int mes) {
        return 30 * mes;
    }
    
    public int anosParaDias(String ano) {
        switch (ano) {
            case "01":
                return 365;
            case "02":
                return 365 * 2;
            case "03":
                return 365 * 3;
            case "04":
                return 365 * 4;
            case "05":
                return 365 * 5;
            case "06":
                return 365 * 6;
            case "07":
                return 365 * 7;
            case "08":
                return 365 * 8;
            case "09":
                return 365 * 9;
            case "10":
                return 365 * 10;
            case "11":
                return 365 * 11;
            default:
                break;
        }
        return 0;
    }
}
