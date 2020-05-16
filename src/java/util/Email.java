/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.CampanhaDAO;
import dao.CidadeCampanhaDAO;
import dao.CidadeDAO;
import dao.EnderecoDAO;
import dao.PostoDAO;
import dao.UsuarioDAO;
import dao.VacinaDAO;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.Session;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import model.Campanha;
import model.Cidade;
import model.CorpoEmail;
import model.Endereco;
import model.Posto;
import model.Usuario;
import model.Vacina;

/**
 *
 * @author nelson_amaral
 */
public class Email {

    private Session criarSessionMail() {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", 465);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.port", 465);

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ice.pfc.umc@gmail.com", "Umc2019End1089");
            }
        });

        session.setDebug(true);

        return session;
    }

    public int envioSimples(String nomeRemetente, String assunto, String mensagem, String destinatario) throws UnsupportedEncodingException {
        try {
            String host = "smtp.gmail.com";
            String usuario = "ice.pfc.umc@gmail.com";
            String senha = "Umc2019End1089";
            String remetente = "ice.pfc.umc@gmail.com";
            boolean sessionDebug = true;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(remetente, nomeRemetente));
            msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            msg.setSubject(assunto);
            msg.setSentDate(new Date());
            msg.setContent(mensagem, "text/html;charset=UTF-8");

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, usuario, senha);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();

        } catch (MessagingException ex) {
            System.out.println(ex);
            return 0;
        } catch (UnsupportedEncodingException ex) {
            return 0;
        }
        return 1;
    }

    public void emailCampanha(CorpoEmail ce) {
        String url = "&zoom=15"
                   + "&size=600x300&maptype=terrain"
                   + "&style=feature:poi%7Celement:labels%7Cvisibility:off"
                   + "&style=feature:poi.medical%7Celement:labels%7Cvisibility:on"
                   + "&style=feature:transit%7Celement:labels%7Cvisibility:off"
                   + "&key=AIzaSyB-l3vu_08LgwDn83cwPtETpbC-TauHsfY";
        String cepOP = null;
        int cont = 0;
        for (String cep : ce.getCepPosto()) {
            url = url+"&markers=color:green%7Clabel:P%7C"+cep;
            System.out.println("url :"+url);
            if(cepOP == null){
                cepOP = cep;
            }
            cont ++;
            if(cont > 1){break;}
        }

        try {
            envioSimples(
                    "ICE", //Nome do Remetente(Quem envio)
                    "Campanha de vacinação.", //Assunto
                    //"<html><head></head><body><b>Mensagem em HTML!</b><br/><br/>Até logo,<br/>"+nome+"</body></html>",                   

                    "    <html> <head> </head> <body>"
                    + "    <h3>Informativo Campanha !</h3><br/> <b>Bom dia Sr(a) " + ce.getNomeUsuario() + "</b> <br/> "
                    + "     <p>Estamos enviando este e-mail para informar que a campanha: <a class='blue-text'>" + ce.getNomeCampanha() + "</a> está em andamento em sua região.</p>"
                    + "     <p>Para mais informações entrar em contato com o posto mais próximo.</p><br/>"
                    + "     <a href='https://www.google.com/maps/dir/?api=1&origin="+ce.getCepUsuario()+"&destination="+cepOP+"'>"
                    + "     <img src='https://maps.googleapis.com/maps/api/staticmap?center="+ce.getCepUsuario()+"&markers=color:blue%7Clabel:I%7C"+ce.getCepUsuario()+""+url+"'/></a>"
                    + "</body></html>",
                    ce.getEmailUsuario());

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public int finalizandoCampanha(Campanha campanha,Usuario usuario) {
        int retorno = 0;

        String url = "&zoom=15&size=600x300&maptype=terrain&style=feature:poi%7Celement:labels%7Cvisibility:off&style=feature:poi.medical%7Celement:labels%7Cvisibility:on&style=feature:transit%7Celement:labels%7Cvisibility:off&key=AIzaSyB-l3vu_08LgwDn83cwPtETpbC-TauHsfY'";

        url = url + "&markers=color:green%7Clabel:G%7C" + usuario.getEndereco();
        
        try {
            retorno = envioSimples(
                    "ICE", //Nome do Remetente(Quem envio)
                    "Encerramento da Campanha de Vacinação", //Assunto
                    //"<html><head></head><body><b>Mensagem em HTML!</b><br/><br/>Até logo,<br/>"+nome+"</body></html>",

                    "    <html> <head> </head> <body>"
                    + "    <h3>Informativo Campanha !</h3><br/> <b>Sr(a) " + usuario.getNome() + "</b> <br/> "
                    + "     <p>Estamos enviando este e-mail para informar que a campanha: <a class='blue-text'>" + campanha.getCampanha_nome() + "</a> encerrou na sua cidade.</p>"
                    + "     <p>Para mais informações entrar em contato com o posto mais próximo.</p><br/>"
                     + "     <a href='https://www.google.com/maps/dir/?api=1&origin=" + usuario.getEndereco() + "&destination=" + "OB DO CEP" + "'>"
                    + "     <img src='https://maps.googleapis.com/maps/api/staticmap?center=" + usuario.getEndereco().getCidade() + "," + usuario.getEndereco().getUf() + ",&markers=color:blue%7Clabel:S%7" + usuario.getEndereco() + "" + url + "/></a>"              
                            + "</body></html>",
                    usuario.getEmail());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public int emailPrimerioCadastro(Usuario usuario) {
        int retorno = 0;

        String url = "&zoom=15&size=600x300&maptype=terrain&style=feature:poi%7Celement:labels%7Cvisibility:off&style=feature:poi.medical%7Celement:labels%7Cvisibility:on&style=feature:transit%7Celement:labels%7Cvisibility:off&key=AIzaSyB-l3vu_08LgwDn83cwPtETpbC-TauHsfY'";

        try {
            retorno = envioSimples(
                    "ICE", //Nome do Remetente(Quem envio)
                    "Cadastro Concluido ICE.", //Assunto
                    //"<html><head></head><body><b>Mensagem em HTML!</b><br/><br/>Até logo,<br/>"+nome+"</body></html>",

                    "    <html> <head> </head> <body>"
                    + "    <h3>Bem Vindo !</h3><br/> <b>Sr(a) " + usuario.getNome() + "</b> <br/> "
                    + "     <p>Estamos enviando este e-mail para informar que seu cadastro no sistema ICE (Imunização contra epidemia) foi concluida</p>"
                    + "     <p>Para mais informações entrar em contato com o posto mais próximo.</p><br/>"
                    + "</body></html>",
                    usuario.getEmail());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public int emailCadastroDeDependente(Usuario usuario) {
        int retorno = 0;

        String url = "&zoom=15&size=600x300&maptype=terrain&style=feature:poi%7Celement:labels%7Cvisibility:off&style=feature:poi.medical%7Celement:labels%7Cvisibility:on&style=feature:transit%7Celement:labels%7Cvisibility:off&key=AIzaSyB-l3vu_08LgwDn83cwPtETpbC-TauHsfY'";

        try {
            retorno = envioSimples(
                    "ICE", //Nome do Remetente(Quem envio)
                    "Dependente Adicionado ICE.", //Assunto
                    //"<html><head></head><body><b>Mensagem em HTML!</b><br/><br/>Até logo,<br/>"+nome+"</body></html>",

                    "    <html> <head> </head> <body>"
                    + "    <h3>Bem Vindo !</h3><br/>"
                    + "     <p>Estamos enviando este e-mail para informar que o cadastro do dependente "+ usuario.getNome() +" no sistema ICE (Imunização contra epidemia) foi concluida</p>"
                    + "     <p>Agora todos os alertas de vacina referente ao seu dependente será comunicado através desde e-mail</p><br/>"
                    + "     <p>Para mais informações entrar em contato com o posto mais próximo.</p><br/>"
                    + "</body></html>",
                    usuario.getEmail());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public int emailDesligamentoDeDependente(Usuario usuario) {
        int retorno = 0;

        String url = "&zoom=15&size=600x300&maptype=terrain&style=feature:poi%7Celement:labels%7Cvisibility:off&style=feature:poi.medical%7Celement:labels%7Cvisibility:on&style=feature:transit%7Celement:labels%7Cvisibility:off&key=AIzaSyB-l3vu_08LgwDn83cwPtETpbC-TauHsfY'";

        try {
            retorno = envioSimples(
                    "ICE", //Nome do Remetente(Quem envio)
                    "Desvinculo do Dependente ICE.", //Assunto
                    //"<html><head></head><body><b>Mensagem em HTML!</b><br/><br/>Até logo,<br/>"+nome+"</body></html>",

                    "    <html> <head> </head> <body>"
                    + "    <h3>Bem Vindo !</h3><br/>"
                    + "     <p>Estamos enviando este e-mail para informar que o Desvinculo do dependente "+ usuario.getNome() +" do seu cadastro no sistema ICE (Imunização contra epidemia) foi concluida</p>"
                    + "     <p>Agora todos os alertas de vacina referente ao "+ usuario.getNome() +" será comunicado no e-mail adicionado no cadastro!</p><br/>"
                    + "     <p>Para mais informações entrar em contato com o posto mais próximo.</p><br/>"
                    + "</body></html>",
                    usuario.getEmail());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ArrayList<Usuario> CampanhaAberta(Campanha campanha) {

        Vacina vacina = new Vacina();
        vacina.setId_vacina(campanha.getVacina().getId_vacina());

        VacinaDAO vacinaDAO = new VacinaDAO();

        //busca os dados vacina
        campanha.setVacina(vacinaDAO.buscarVacinaUnica(vacina));

        PostoDAO postoDAO = new PostoDAO();
        ArrayList<Posto> listaposto = new ArrayList<Posto>();
        /* Busca apenas os referente a cidade */
        //Busca todos os postos do sistema
        listaposto = postoDAO.buscaPostos();

        EnderecoDAO enderecoDAO = new EnderecoDAO();

        //Busca endereço dos postos 
        for (Posto ps : listaposto) {
            Endereco endecoposto = enderecoDAO.consultarId(ps.getPosto_endereco().getId_endereco());
            ps.setPosto_endereco(endecoposto);
        }

        ArrayList<Usuario> listUsuariosEnvioEmail = new ArrayList<>();

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Cidade cidade = new Cidade();
        CidadeDAO cidadeDAO = new CidadeDAO();

        for (String camp : campanha.getCidade().getCidades()) {

            cidade.setId_cidade(Integer.parseInt(camp));

            String cidade_nome = cidadeDAO.consultarEnderecoExistente(cidade);

            cidade.setNome_cidade(cidade_nome);

            ArrayList<Usuario> listUsuarioDaCidade = usuarioDAO.buscar_Usuario_Por_Cidade(cidade);

            for (Usuario us : listUsuarioDaCidade) {

                int retorno_idvacina = vacinaDAO.ConsultarVacinaComrestricoesIguaisAoUsuario(campanha.getVacina().getId_vacina(), us.getUsuario_id());

                if (retorno_idvacina == 0) {
                    listUsuariosEnvioEmail.add(us);
                }
            }
        }

        CorpoEmail cp = new CorpoEmail();

        ArrayList<String> cepPostos = new ArrayList<>();

        for (Usuario user : listUsuariosEnvioEmail) {
            for (Posto posto : listaposto) {
                //Busca os ceps dos postos que existe na cidade o usuário em questão              
                if (posto.getPosto_endereco().getCidade().equals(user.getEndereco().getCidade())) {

                    cepPostos.add(String.valueOf(posto.getPosto_endereco().getCep()));
                }
            }

            cp.setCepPosto(cepPostos);
            cp.setCepUsuario(String.valueOf(user.getEndereco().getCep()));
            cp.setCidadeUsuario(user.getEndereco().getCidade());
            cp.setEmailUsuario(user.getEmail());
            cp.setEstadoUsuario(user.getEndereco().getUf());
            cp.setNomeCampanha(campanha.getCampanha_nome());
            cp.setNomeUsuario(user.getNome());

            emailCampanha(cp);
        }

        return listUsuariosEnvioEmail;
    }

    public void finalizarcampanha() {

        CampanhaDAO campanhaDAO = new CampanhaDAO();

        //FINALIZANDO AS CAMPANHAS NECESSARIAS E RETORNANDO UM ARRAYLIST DOS IDS
        ArrayList<Integer> listIdsCampanhas = campanhaDAO.jobCampanha();

        Cidade cidade = new Cidade();
        CidadeDAO cidadeDAO = new CidadeDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VacinaDAO vacinaDAO = new VacinaDAO();
        CidadeCampanhaDAO cidadecampanhaDAO = new CidadeCampanhaDAO();
        Campanha campanha = new Campanha();

        for (Integer Ids : listIdsCampanhas) {

            campanha.setCampanha_id(Ids);            
            
            //Buscando todas as Cidades da campanha referenciada
            ArrayList<Integer> listIdsCidades = cidadecampanhaDAO.consultarCidadesdaCampanha(campanha);

            //Buscando todas as informações da campanha referenciada
            campanha = campanhaDAO.buscaCampanhaUnico(campanha);
            
            //LIST DOS USUARIOS QUE RECEBERAM O AVISO DA CAMPANHA
            ArrayList<Usuario> listUsuariosEnvioEmail = new ArrayList<>();
            
            //LISTANDO IDS DAS CIDADES
            for (Integer IdsCidades : listIdsCidades) {

                cidade.setId_cidade(IdsCidades);

                String cidade_nome = cidadeDAO.consultarEnderecoExistente(cidade);

                cidade.setNome_cidade(cidade_nome);

                ArrayList<Usuario> listUsuarioDaCidade = usuarioDAO.buscar_Usuario_Por_Cidade(cidade);

                for (Usuario us : listUsuarioDaCidade) {

                    //VERIFICA RESTRIÇÃO DA VACINA COM O USUARIO
                    int retorno_idvacina = vacinaDAO.ConsultarVacinaComrestricoesIguaisAoUsuario(campanha.getVacina().getId_vacina(), us.getUsuario_id());

                    if (retorno_idvacina == 0) {
                        listUsuariosEnvioEmail.add(us);
                    }
                }
                
                for (Usuario usuarios : listUsuariosEnvioEmail){
                    finalizandoCampanha(campanha,usuarios);
                }
            }
            
        }

        System.out.println("Jobcampanha is run");
    }
}
