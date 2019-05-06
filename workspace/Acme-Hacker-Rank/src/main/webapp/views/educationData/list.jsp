<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ROOKIE')">

	<display:table pagesize="10" class="displaytag"
		name="educationData"
		requestURI="educationData/rookie/list.do?curriculaId=${currentCurricula.id}" id="row">

		<display:column titleKey="educationData.degree">
			<jstl:out value="${row.degree}"></jstl:out>
		</display:column>
		
		<display:column titleKey="educationData.institution">
			<jstl:out value="${row.institution}"></jstl:out>
		</display:column>

		<display:column titleKey="educationData.display">
			<a
				href="educationData/rookie/display.do?dataId=${row.id}&curriculaId=${currentCurricula.id}">
				<spring:message code="educationData.display" />
			</a>
		</display:column>
	</display:table>

	<a href="educationData/rookie/create.do?curriculaId=${currentCurricula.id}"> <spring:message
			code="educationData.create" />

	</a>&nbsp;&nbsp;&nbsp;
		
	<acme:cancel url="curricula/rookie/display.do?curriculaId=${currentCurricula.id}"
		code="educationData.cancel" />

</security:authorize>