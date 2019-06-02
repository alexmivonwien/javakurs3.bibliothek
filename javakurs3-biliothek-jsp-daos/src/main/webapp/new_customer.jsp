<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored ="false" %> <%-- if it is true, EL expressions are ignored when
									  they appear in static text or tag attributes. 
									  If it is false, EL expressions are evaluated by 
									  the container.
@see
https://www.tutorialspoint.com/jsp/jsp_expression_language.htm 

									--%>
									
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
 	<script src="js/commons.js"></script> 
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
</head>
	<jsp:useBean id="newCustomerBean" scope= "session" class= "at.bibliothek.web.NewCustomerBean"/>
<body>
	<%@ include file="menu.html"%>
	<br />
	<br />
	<h2>Create a new customer</h2>
	<br />
	<form id = "new_customer_form" method = "post" action="/javakurs3-biliothek-jsp-daos/customers.jsp">
		<table>	
		 <tr>
			  <td>Kunde Typ: </td>
			  <td>
			  	  <select id = "kundeTypId" name = "kundeTyp" onchange="javascript: hideUndideFields();"> 
			  	 	  <option value = "0" selected="selected">Bitte auswählen</option>
			    	  <option value = "1">natürliche Person</option>
			    	  <option value = "2">Firma</option>
			      </select>
			    </td>
			  </tr>
			  <tr id ="vorNameRowId" style="visibility:hidden">
			   	 <td>Vorname: </td>
			     <td><input name = "vorname" value = "${newCustomerBean.vorname}"/></td>
			  </tr>
			  <tr id ="nachNameRowId" style="visibility:hidden">
			   	 <td>Nachname: </td>
			     <td><input name = "nachname" value = "${newCustomerBean.nachname}"/></td>
			  </tr>
			  <tr id = "gebDatumId" style="visibility:hidden">
			   	 <td>Geburtsdatum: </td>
			     <td><input name = "gebDatum" type = "date" max = "1999-12-31"  min= "1900-01-01" required="required" value = "${newCustomerBean.gebDatum}"/></td>
			  </tr>
			  <tr id = "adresseId" style="visibility:hidden">
			   	 <td>Adresse: </td>
			     <td><input name = "addresse" value = "${newCustomerBean.addresse}"/></td>
			  </tr>
			  <tr id = "steuerNummerId" style="visibility:hidden">
			   	 <td>Steuernummer: </td>
			     <td><input name = "steuerNummer" type = "number" required="required" value = "${newCustomerBean.steuerNummer}"/></td>
			  </tr>
			  <tr >
			    <td> <button type = "reset" value ="Abbrechen">Abbrechen</button> </td>
			    <td> <input type = "submit" value = "Kunde anlegen" /></td>
			  </tr>
		</table>	
	</form>
</body>
</html>
