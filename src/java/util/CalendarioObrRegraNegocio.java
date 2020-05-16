/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.CalendarioObrigatorioDAO;
import dao.IntervaloVacinacaoDAO;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import model.CalendarioObrigatorio;
import model.IntervaloVacinacao;
import model.Usuario;

/**
 *
 * @author Victor_Aguiar
 */
public class CalendarioObrRegraNegocio {

    //CRIAR METHODU
    public long CalcularNascimentoEmDiasVida(Date DataNascimento, String dataatual) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        Date d1 = df.parse(df.format(DataNascimento));
        Date d2 = df.parse(dataatual);
        long dt = (d2.getTime() - d1.getTime()) + 3600000; // 1 hora para compensar horário de verão

        long diasdevida = dt / 86400000L;

        return diasdevida;
    }

    public CalendarioObrigatorio ConsultaAFaixaDiasDeVidaMesAtual(Long diasvida, int diaatual, int mesatual, CalendarioObrigatorio calendarioobrigatorio) throws ParseException {

        TratamentoDeData tratamentodata = new TratamentoDeData();

        int diasmes = tratamentodata.retornaTamanhoDoMesReferente(mesatual);

        //Calculando faixa para pesquisa no banco por dias
        int subtracao = diasmes - diaatual;

        int diasdevidanofinaldomes = subtracao + Integer.valueOf(diasvida.toString());

        int diasdevidanoiniciodomes = Integer.valueOf(diasvida.toString()) - diaatual;

        calendarioobrigatorio.setDiasvidafinalmes(diasdevidanofinaldomes);
        calendarioobrigatorio.setDiasvidainiciomes(diasdevidanoiniciodomes);

        System.out.println(diasdevidanoiniciodomes);
        System.out.println(diasdevidanofinaldomes);

        return calendarioobrigatorio;
    }

    //Utilizado para o method da servelt cadastrar calendario obrigatorio
    public void intervalopersonalizado(IntervaloVacinacao intervaloVacinacao, String[] intervalos, int DoseInicialdiasDeVida) throws ServletException, IOException {

        MesAnosEmDiasCadastroCalendarioObrigatorio anosMesParaDias = new MesAnosEmDiasCadastroCalendarioObrigatorio();
        IntervaloVacinacaoDAO intervaloVacinacaoDAO = new IntervaloVacinacaoDAO();

        //Contador para exibir a sequencia das doses a ser cadastrada
        int i = 1;

        //Variavel utilizada para somar os valores do intervalo personalizado
        int cont = DoseInicialdiasDeVida;

        if (intervalos != null) {

            intervaloVacinacao.setDose(i);

            intervaloVacinacao.setDias(DoseInicialdiasDeVida);

            intervaloVacinacaoDAO.cadastraIntervaloVacinacao(intervaloVacinacao);

            for (String s : intervalos) {
                //auto increment variavel i
                i++;

                intervaloVacinacao.setDose(i);

                //Operacao de somatoria dos intervalos personalizado de cada dose
                cont = cont + anosMesParaDias.meseanosParaDias(String.valueOf(s));

                intervaloVacinacao.setDias(cont);

                intervaloVacinacaoDAO.cadastraIntervaloVacinacao(intervaloVacinacao);

            }
        }
    }

    //Utilizado para o method da servelt cadastrar calendario obrigatorio
    public void intervalofixo(IntervaloVacinacao intervaloVacinacao, int DoseInicialdiasDeVida, int intervalofixo) throws ServletException, IOException {
        IntervaloVacinacaoDAO intervaloVacinacaoDAO = new IntervaloVacinacaoDAO();

        //Contador para exibir a sequencia das doses a ser cadastrada
        int i = 0;

        int doses = intervaloVacinacao.getDose();

        int cont = DoseInicialdiasDeVida;

        while (i < doses) {

            //auto increment variavel i
            i++;

            intervaloVacinacao.setDose(i);

            if (i == 1) {
                intervaloVacinacao.setDias(DoseInicialdiasDeVida);
            } else {
                cont = cont + intervalofixo;
                intervaloVacinacao.setDias(cont);
            }

            intervaloVacinacaoDAO.cadastraIntervaloVacinacao(intervaloVacinacao);

        }
    }

}
