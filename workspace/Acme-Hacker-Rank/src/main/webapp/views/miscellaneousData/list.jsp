<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasAnyRole('ROOKIE','COMPANY')">

	<display:table pagesize="10" class="displaytag"
		name="miscellaneousData"
		requestURI="miscellaneousData/rookie/list.do?curriculaId=${currentCurricula.id}"
		id="row">

		<display:column titleKey="miscellaneousData.text">
			<jstl:out value="${row.text}"></jstl:out>
		</display:column>

		<display:column titleKey="miscellaneousData.display">
			<a
				href="miscellaneousData/rookie/display.do?dataId=${row.id}&curriculaId=${currentCurricula.id}">
				<spring:message code="miscellaneousData.display" />
			</a>
		</display:column>
	</display:table>
	<security:authorize access="hasRole('ROOKIE')">

		<a
			href="miscellaneousData/rookie/create.do?curriculaId=${currentCurricula.id}">
			<spring:message code="miscellaneousData.create" />

		</a>&nbsp;&nbsp;&nbsp;
		</security:authorize>
	<acme:cancel
		url="curricula/rookie/display.do?curriculaId=${currentCurricula.id}"
		code="miscellaneousData.cancel" />

</security:authorize>