///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package testeUsuarioDAO;
//
//import dao.VacinaDAO;
//import java.util.ArrayList;
//import java.util.Random;
//import model.Restricao;
//import model.TipoVacina;
//import model.Vacina;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Nelson.Amaral
// */
//public class VacinaDAOTeste {
//    
//    public VacinaDAOTeste() {
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
////    @Test
////    public void testeCadastaNovoVacina(){
////        VacinaDAO vacinaDAO = new VacinaDAO();
////        Vacina vacinaCadastrada = criaVacinaTeste();
////        
////        vacinaCadastrada.setId_vacina(vacinaDAO.cadastaNovoVacina(vacinaCadastrada));
////        
////        Vacina vacina = vacinaDAO.buscarVacinaUnica(vacinaCadastrada.getId_vacina());
////        
////        assertEquals(vacina.getId_vacina(), vacinaCadastrada.getId_vacina());
////        assertEquals(vacina.getNome(), vacinaCadastrada.getNome());
////        assertEquals(vacina.getDose(), vacinaCadastrada.getDose());
////        assertEquals(vacina.getTipo(), vacinaCadastrada.getTipo());
////        assertEquals(vacina.getIntervalo(), vacinaCadastrada.getIntervalo());
////        
////       // vacinaDAO.excluirVacina(vacina.getId_vacina());
////        
////    }
//    @Test
//    public void testeConsultarVacinas(){
//        VacinaDAO VacinaDAO = new VacinaDAO();
//        ArrayList<Vacina> listaVacinas = new ArrayList<>();
//        assertTrue(VacinaDAO.consultarVacinas().containsAll(listaVacinas));        
//    }
//    
//    @Test
//    public void testeExcluirVacina(){
//        VacinaDAO vacinaDAO = new VacinaDAO();       
//        
//        Vacina vacina = criaVacinaTeste();        
//        vacina.setId_vacina(vacinaDAO.cadastaNovoVacina(vacina));
//        
//        vacina.setStatus(vacinaDAO.excluirVacina(vacina.getId_vacina()));
//        
//        assertNotEquals(vacinaDAO.buscarVacinaUnica(vacina.getId_vacina()).getNome(),vacina.getId_vacina());
//        
//      //  vacinaDAO.excluirVacina(vacina.getId_vacina());
//                
//    }
//    
//    @Test
//    public void testeBuscarVacinaUnica(){
//        Vacina vacina = criaVacinaTeste();
//        VacinaDAO vacinaDAO = new VacinaDAO();
//        
//        vacina.setId_vacina(vacinaDAO.cadastaNovoVacina(vacina));
//        
//        Vacina vacinaBusaca = vacinaDAO.buscarVacinaUnica(vacina.getId_vacina());
//        
//        assertEquals(vacina.getId_vacina(), vacinaBusaca.getId_vacina());
//    }
//    
//    @Test   
//    public void testesAtualizar_vacina(){
//        Vacina vacina = criaVacinaTeste();
//        VacinaDAO vacinaDAO = new VacinaDAO();
//        
//        vacina.setId_vacina(vacinaDAO.cadastaNovoVacina(vacina));
//        
//        Vacina vacinaAtualizada = criaVacinaTeste();
//        vacinaAtualizada.setId_vacina(vacina.getId_vacina());
//        
//        vacinaDAO.atualizar_vacina(vacinaAtualizada);
//        
//        assertTrue(vacinaAtualizada.getId_vacina() == vacina.getId_vacina());
//        assertTrue((vacinaAtualizada.getNome() == null ? vacina.getNome() != null : !vacinaAtualizada.getNome().equals(vacina.getNome())));
//        
//        
//        
//    }
//    
////    private Vacina criaVacinaTeste(){
////        
////        ArrayList<Restricao> restricoes = new ArrayList<Restricao>();
////        Vacina vacina = new Vacina();
////        vacina.setNome(randon());
////        vacina.setDose(0);
////        vacina.setIntervalo(0);
////        vacina.setTipo(TipoVacina.Vacinas_mortas_inactivadas);
////        vacina.setStatus(true);
////        vacina.setRestricoes(restricoes);
////        return vacina;
////    }
////    
//    
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
//    
//}
