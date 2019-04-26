<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('HACKER')">

	<form:form modelAttribute="application" action="application/edit.do"
		id="form">

		<form:hidden path="id" />

		<spring:message code="application.explanation" />
		<br>
		<form:textarea code="application.explanation" path="explanation" />
		<form:errors path="explanation" cssClass="error"/>
		<br>
		<br>

		<acme:textbox code="application.linkCode" path="linkCode" />
		<br>
		<br>

		<form:select path="copyCurricula" items="${curriculas}"
			itemLabel="personalData.statement" />
		<form:errors path="copyCurricula" cssClass="error" />
		<br>
		<br>

		<acme:submit code="application.save" name="save" />&nbsp;
		
		<acme:cancel url="application/listHacker.do" code="application.cancel" />

		<br />

	</form:form>

</security:authorize>