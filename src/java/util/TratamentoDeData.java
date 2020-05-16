/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author Nelson_Amaral
 */
public class TratamentoDeData {

    //Busaca data atual 
    public Date buscaDataAtual() {
        Date data = new Date();
//        SimpleDateFormat formatador = new SimpleDateFormat(getData());
//        formatador.format(data);
        return data;
    }

    //Busaca Hora atual 
    public Date buscaHoraAtual() {
        Date data = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat(getHora());
        formatador.format(data);
        return data;
    }

    //soma dias na data
    public Date somarDiasData(int dias, Date data) {
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(data);
        calendario.add(Calendar.DAY_OF_MONTH, dias);

        return calendario.getTime();
    }

    public int diferencaEntreData(Date nascimento) {
        Calendar dataAtual = Calendar.getInstance();
        Calendar dataNascimento = Calendar.getInstance();

        dataAtual.setTime(buscaDataAtual());
        dataNascimento.setTime(nascimento);

        int day = dataAtual.get(Calendar.DAY_OF_YEAR) - dataNascimento.get(Calendar.DAY_OF_YEAR);

        return day;
    }

    public int comverterTudoParaDias(int dia, int mes, int ano) {
        return dia + (mes * 30) + (ano * 365);
    }

    public int somadia_mes_ano(int dia, int mes, int ano) {
        return dia + mes + (ano * 365);
    }

    public String getData() {
        return "dd/MM/YYYY";
    }

    public String getHora() {
        return "H:mm:ss";
    }

    public int retornaTamanhoDoMesReferente(int mes) {

        switch (mes) {
            case 1:
                return 31;
            case 2:
                return 28;
            case 3:
                return 30;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 31;
            case 7:
                return 30;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
            default:
                break;
        }
        return 0;
    }
    
       //Busaca data atual 
    public Date buscarDataRetroativa(int qt) {
        Date data = new Date();
        Calendar c = Calendar.getInstance(); 
        c.setTime(data);
        c.add(Calendar.MONTH, - qt);

        
        return c.getTime();
    }
}
