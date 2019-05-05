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

<table class="displayStyle">

	<tr>
		<td><strong> <spring:message code="audit.auditor" /> :
		</strong></td>
		<td><jstl:out value="${audit.auditor.userAccount.username}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="audit.moment" /> :
		</strong></td>
		<td><jstl:out value="${audit.moment}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="audit.score" /> :
		</strong></td>
		<td><jstl:out value="${audit.score}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="audit.position" /> :
		</strong></td>
		<td><jstl:out value="${audit.position.title}" /></td>
	</tr>
		<tr>
		<td><strong> <spring:message code="audit.text" /> :
		</strong></td>
		<td><jstl:out value="${audit.text}" /></td>
	</tr>

</table>

<jstl:if test="${name == position.auditor.userAccount.username }">

	<button
		onClick="window.location.href='audit/delete.do?Id=${audit.id}'">
		<spring:message code="audit.confirm.delete" />
	</button>
</jstl:if>
<br />