<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('HACKER')">

	<display:table pagesize="10" class="displaytag"
		name="positionData"
		requestURI="positionData/hacker/list.do?curriculaId=${currentCurricula.id}" id="row">

		<display:column titleKey="positionData.title">
			<jstl:out value="${row.title}"></jstl:out>
		</display:column>
		
		<display:column titleKey="positionData.description">
			<jstl:out value="${row.description}"></jstl:out>
		</display:column>

		<display:column titleKey="positionData.display">
			<a
				href="positionData/hacker/display.do?dataId=${row.id}&curriculaId=${currentCurricula.id}">
				<spring:message code="positionData.display" />
			</a>
		</display:column>
	</display:table>

	<a href="positionData/hacker/create.do?curriculaId=${currentCurricula.id}"> <spring:message
			code="positionData.create" />

	</a>&nbsp;&nbsp;&nbsp;
		
	<acme:cancel url="curricula/hacker/display.do?curriculaId=${currentCurricula.id}"
		code="positionData.cancel" />

</security:authorize>