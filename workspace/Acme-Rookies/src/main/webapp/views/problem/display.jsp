<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<%-- <jstl:if test="${name == problem.company.userAccount.username}">
 --%>
<table class="displayStyle">
	<tr>
		<td><strong> <spring:message code="problem.title" /> :
		</strong></td>
		<td><jstl:out value="${problem.title}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="problem.statement" />
				:
		</strong></td>
		<td><jstl:out value="${problem.statement}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="problem.optionalHint" />
				:
		</strong></td>
		<td><jstl:out value="${problem.optionalHint}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="problem.company" />
				:
		</strong></td>
		<td><jstl:out value="${problem.company.commercialName}" /></td>
	</tr>
	<tr>
		<td><strong> <spring:message code="problem.attachments" />
				:
		</strong></td>
		<jstl:forEach items="${attachments}" var="at">
			<td><a href="${at }" target=blank><jstl:out value="${at }"></jstl:out></a></td>
		</jstl:forEach>
	</tr>

</table>
<%-- </jstl:if> --%>
<jstl:if test="${problem.isDraft == true }">
	<button
		onClick="window.location.href='problem/delete.do?Id=${problem.id}'">
		<spring:message code="position.confirm.delete" />
	</button>
</jstl:if>