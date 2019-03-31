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
	<c:set target="${rentsBean}" property="filterByCustomerId" value="${param.custId}" /> 

<body>
	<%@ include file="menu.html"%>
	<br />
	<br />
	<h2>Rents</h2>
	<br />
	<table>	
	 	<tr>
			 <th>Kassazettel Nr</th>
	 		 <th>Kundenname</th>
	 		 <th>Ausgeliehen am</th>
	 		 <th>Ausgeliehen bis</th>
	 		 <th>Betrag</th>
	 		 <th>Abgeschlossen</th>
	 		 <th>Details</th>
		 </tr> 
		 <c:forEach items="${rentsBean.rentsList}" var="rent">
			  <tr>
			    <td><c:out value= "${rent.kassaZettelNummer}"/></td>
			    <td><c:out value= "${rent.kunde.vorname}"/></td>
			    <td><c:out value= "${rent.rentedOn}"/></td>
			    <td><c:out value= "${rent.rentedTill}"/></td>
			    <td><c:out value= "${rent.betragzuZahlen}"/></td>
			    <td><c:out value= "${rent.abgeschlossen}"/></td>
			    <c:if test="${not rent.abgeschlossen}">
			    	<td><a href="<c:url value="rent_details.jsp"> <c:param name="kassaZettelId" value="${rent.kassaZettelNummer}"/></c:url>"> Details</a></td>
			    </c:if>
			  </tr>
		</c:forEach>
	</table>
</body>
</html>
