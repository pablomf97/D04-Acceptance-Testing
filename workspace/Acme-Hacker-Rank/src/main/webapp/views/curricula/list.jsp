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

	<display:table pagesize="10" class="displaytag" name="curriculas"
		requestURI="curricula/hacker/list.do" id="row">

		
			<display:column titleKey="curricula.personal.name">
				<jstl:out value="${row.personalData.fullName}"></jstl:out>
			</display:column>

			<display:column titleKey="curricula.display">
				<a href="curricula/hacker/display.do?curriculaId=${row.id}"> <spring:message
						code="curricula.display" />
				</a>
			</display:column>

	</display:table>

	<a href="curricula/hacker/create.do"> <spring:message
			code="curricula.create" />

	</a>
</security:authorize>