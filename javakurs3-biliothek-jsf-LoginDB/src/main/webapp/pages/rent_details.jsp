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
	<link rel="stylesheet" type="text/css" href="css/styles.css"/>
</head>

	<jsp:useBean id="rentsBean" scope= "request" class= "at.bibliothek.web.RentsBean"/>
	<c:set target="${rentsBean}" property="filterByKassaZettelNummer" value="${param.kassaZettelId}" /> 

<body>
	<%@ include file="menu.html"%>
	<br />
	<br />
	<h2>All Rentables of customer ${rentsBean.rentForKassaZettelNummer.kunde.vorname} on ${rentsBean.rentForKassaZettelNummer.rentedOn}</h2>
	<br />
	<table>
	 	<tr>
			<th>Überschrift</th>
		 </tr>	
		<c:forEach items="${rentsBean.rentForKassaZettelNummer.rentables}" var="rentable">
		  <tr>
		   	<td><c:out value= "${rentable.title}"/></td>
		  </tr>
		</c:forEach>
	</table>
</body>
</html>
