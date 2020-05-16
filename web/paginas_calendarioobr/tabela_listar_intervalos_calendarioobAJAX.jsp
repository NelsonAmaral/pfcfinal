<%-- 
    Document   : listar_calendario
    Created on : 12/05/2019, 16:48:05
    Author     : Victor_Aguiar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%-- 
    Document   : exibir_usuario
    Created on : 25/11/2018, 05:52:21
    Author     : nelson_amaral
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@page import="java.util.ArrayList"%>

<script src="../framework/js/jQuery.js" type="text/javascript"></script>
<script src="framework/js/jQuery.js" type="text/javascript"></script>

<div class="card z-depth-2">
    <div class="card-content">
        <center><div style="overflow: scroll; width: auto; height: 240px; border:solid 1px">
                <table>
                    <thead>
                        <tr>
                            <td><div class="center-align letra">Dose</div></td>
                            <td><div class="center-align letra">Dias de Vida</div></td>
                            <td><div class="center-align letra">Anos de Vida</div></td>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="intervalos" items="${listaintervalos}">
                            <tr>
                                <td><div class="center-align blue-text letra">${intervalos.dose}</div></td>
                                <td><div class="center-align blue-text letra">${intervalos.dias}</div></td>
                                <td><div class="center-align black-text letra">${intervalos.anosdeVida}</div></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div></center>
    </div>
</div>
