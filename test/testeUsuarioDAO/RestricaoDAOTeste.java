///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package testeUsuarioDAO;
//
//import dao.RestricaoDAO;
//import java.util.ArrayList;
//import java.util.Random;
//import model.Restricao;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Nelson_Amaral
// */
//public class RestricaoDAOTeste {
//    
//    public RestricaoDAOTeste() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void testeCadastaNovoRestricao (){
//        Restricao restricao = criarRestricaoTeste();
//        RestricaoDAO retricaoDAO = new RestricaoDAO();
//        ArrayList<Restricao> listaRestricao = new ArrayList<Restricao>();
//        restricao.setRestricao_id(retricaoDAO.cadastaNovoRestricao(restricao));
//        listaRestricao = retricaoDAO.consultarRestricao();
//        for(Restricao r: listaRestricao){
//            if(r.getRestricao_id() == restricao.getRestricao_id()){
//                assertEquals(r.getRestricao_nome(),restricao.getRestricao_nome());
//                assertEquals(r.getRestricao_tipo(),restricao.getRestricao_tipo());
//                assertEquals(r.isRestricao_status(), restricao.isRestricao_status());
//            }
//        }
//      //  retricaoDAO.deletarRestricao(restricao.getRestricao_id());
//    }
//    
//    @Test
//    public void testeConsultarRestricao (){
//        RestricaoDAO retricaoDAO = new RestricaoDAO();
//        ArrayList<Restricao> listaRestricao = new ArrayList<Restricao>();
//        assertTrue(retricaoDAO.consultarRestricao().containsAll(listaRestricao));
//    }
//    
//    @Test
//    public void testeDeletarRestricao (){
//        Restricao restricao = criarRestricaoTeste();
//        Restricao restricaoBusca = new Restricao();
//        RestricaoDAO retricaoDAO = new RestricaoDAO();
//        ArrayList<Restricao> listaRestricao = new ArrayList<Restricao>();
//        
//        restricao.setRestricao_id(retricaoDAO.cadastaNovoRestricao(restricao));
////        retricaoDAO.deletarRestricao(restricao.getRestricao_id());
//        listaRestricao = retricaoDAO.consultarRestricao();
//        
//        for(Restricao r: listaRestricao){
//            if(r.getRestricao_id() == restricao.getRestricao_id()){
//                restricaoBusca.setRestricao_id(r.getRestricao_id());
//                restricaoBusca.setRestricao_nome(r.getRestricao_nome());
//                restricaoBusca.setRestricao_status(r.isRestricao_status());
//                restricaoBusca.setRestricao_tipo(r.getRestricao_tipo());
//            }
//        }
//        assertTrue(restricaoBusca.getRestricao_id() != restricao.getRestricao_id());
//      //  retricaoDAO.deletarRestricao(restricao.getRestricao_id());
//    }
//    @Test
//    public void testesAtualizaRestricao (){
//        Restricao restricao = criarRestricaoTeste();
//        Restricao restricaoAtualizado = criarRestricaoTeste();
//        Restricao restricaoBusca = new Restricao();
//        
//        RestricaoDAO restricaoDAO = new RestricaoDAO();
//        ArrayList<Restricao> listaRestricao = new ArrayList<Restricao>();
//        
//        restricao.setRestricao_id(restricaoDAO.cadastaNovoRestricao(restricao));
//        restricaoAtualizado.setRestricao_id(restricao.getRestricao_id());
//        
//        restricaoDAO.atualizaRestricao(restricaoAtualizado);
//        
//        listaRestricao = restricaoDAO.consultarRestricao();
//        
//        for(Restricao r: listaRestricao){
//            if(r.getRestricao_id() == restricao.getRestricao_id()){
//                restricaoBusca.setRestricao_id(r.getRestricao_id());
//                restricaoBusca.setRestricao_nome(r.getRestricao_nome());
//                restricaoBusca.setRestricao_status(r.isRestricao_status());
//                restricaoBusca.setRestricao_tipo(r.getRestricao_tipo());
//            }
//        }
//        assertTrue(restricaoBusca.getRestricao_id() == restricao.getRestricao_id() && restricaoAtualizado.getRestricao_id() == restricao.getRestricao_id());
//        assertTrue(!restricaoBusca.getRestricao_nome().equals(restricao.getRestricao_nome()) && restricaoAtualizado.getRestricao_nome().equals(restricaoBusca.getRestricao_nome()));
//        assertTrue(!restricaoBusca.getRestricao_tipo().equals(restricao.getRestricao_tipo()) && restricaoAtualizado.getRestricao_tipo().equals(restricaoBusca.getRestricao_tipo()));
//    //    restricaoDAO.deletarRestricao(restricao.getRestricao_id());
//        
//    }
//    @Test
//    public void testesConsultarRestricao (){
//        RestricaoDAO restricaoDAO = new RestricaoDAO();
//        ArrayList<Restricao> listaRestricao = new ArrayList<Restricao>();
//        assertTrue(restricaoDAO.consultarRestricao().containsAll(listaRestricao));
//    }
//    
//    private Restricao criarRestricaoTeste(){
//        Restricao restricao = new Restricao();
//        restricao.setRestricao_nome(randon());
//        restricao.setRestricao_status(true);
//        restricao.setRestricao_tipo(randon());
//        return restricao;
//    }
//    private String randon(){
//        // Determia as letras que poderão estar presente nas chaves
//        String letras = "ABCDEFGHIJKLMNOPQRSTUVYWXZ";
//
//        Random random = new Random();
//
//        String armazenaChaves = "";
//        int index = -1;
//        for (int i = 0; i < 9; i++) {
//            index = random.nextInt(letras.length());
//            armazenaChaves += letras.substring(index, index + 1);
//        }
//        return armazenaChaves;
//    }
//}
