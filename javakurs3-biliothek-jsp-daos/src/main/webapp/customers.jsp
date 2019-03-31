<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	<jsp:useBean id="newCustomerBean" scope= "session" class= "at.bibliothek.web.NewCustomerBean"/>
	<jsp:useBean id="kundenBean" scope= "request" class= "at.bibliothek.web.KundenBean"/>
	
	<c:set target="${newCustomerBean}" property="kundeTyp" value="${param.kundeTyp}" /> 
	<c:set target="${newCustomerBean}" property="vorname" value="${param.vorname}" /> 
	<c:set target="${newCustomerBean}" property="nachname" value="${param.nachname}" /> 
	<c:set target="${newCustomerBean}" property="gebDatum" value="${param.gebDatum}" /> 
	<c:set target="${newCustomerBean}" property="addresse" value="${param.addresse}" /> 
	<c:set target="${newCustomerBean}" property="steuerNummer" value="${param.steuerNummer}" /> 
	<c:set target="${kundenBean}" property="newCustomer" value="${newCustomerBean}" /> 

	
<body>
	<%@ include file="menu.html"%>
	<br />
	<br />
	<h2>All customers in our application:</h2>
	<br />
	<table class="center">
		<tr>
			<th class="center"> Kunden Name</th>
			<th class="center"> Adresse </th>
		</tr>
		<c:forEach items="${kundenBean.allKundenList}" var="kunde"> 
		  <tr>
		    <td class="center"><a href="<c:url value="rents.jsp"> <c:param name="custId" value="${kunde.id}"/></c:url>"><c:out value= "${kunde.vorname}"/> </a></td>
		    <td class="center"><c:out value= "${kunde.adresse}"/></td>
		  </tr>
		</c:forEach>
	</table>
	  
</body>
</html>
