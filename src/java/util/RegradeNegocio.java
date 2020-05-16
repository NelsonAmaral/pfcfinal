/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import dao.EnderecoDAO;
import dao.UsuarioDAO;
import model.Usuario;

/**
 *
 * @author Victor_Aguiar
 */
public class RegradeNegocio {

    public String consultando_RG_CPF_EMAIL_existente(Usuario usuario) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        int aux;

        //Validando RG
        if (!"".equals(usuario.getRg())) {

            aux = usuarioDAO.consultarRgExistente(usuario);

            if (aux != 0) {

                return "Rg Existente!";

            }
        }

        //Validando CPF
        if (!"".equals(usuario.getCpf())) {
            aux = usuarioDAO.consultarCpfExistente(usuario);

            if (aux != 0) {

                return "Cpf Existente!";

            }
        }

        //Validando EMAIL
        if (!"".equals(usuario.getEmail())) {
            aux = usuarioDAO.consultarEmailExistente(usuario);

            if (aux != 0) {

                return "Email Existente!";

            }
        }

        return "";
    }

    public int consultaResponsavelMenorDe18(Usuario usuario) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuario.getRg_responsavel() == null || usuario.getRg_responsavel().contentEquals("")) {

            usuario.setResponsavel(0);

        } else {

            Usuario responsavel = usuarioDAO.buscarResponsavelDoUsuarioPorRg(usuario);

            if (responsavel.getUsuario_id() == 0) {

                return 0;

            } else {

                usuario.setResponsavel(responsavel.getUsuario_id());
                
                //atribuindo ao cadastro do dependente o e-mail do responsavel
                usuario.setEmail(responsavel.getEmail());
            }
        }

        return 1;

    }

    public void consultandoEnderecoJaExistente(Usuario usuario) {

        EnderecoDAO enderecoDAO = new EnderecoDAO();

        int id_endereco = enderecoDAO.consultarEnderecoExistente(usuario.getEndereco());

        if (id_endereco == 0) {
            usuario.getEndereco().setId_endereco(enderecoDAO.cadastraNovoEndereco(usuario.getEndereco()));
        } else {
            usuario.getEndereco().setId_endereco(id_endereco);
        }
    }
    
}
