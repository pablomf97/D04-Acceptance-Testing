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

	<form:form modelAttribute="personalData"
		action="personalData/rookie/save.do"
		id="form">

		<form:hidden path="id" />

		<acme:textbox code="personalData.github" path="githubProfile" />
		<br>
		<br>
		
		<acme:textbox code="personalData.linkedIn" path="linkedIn" />
		<br>
		<br>
		
		<acme:textbox code="personalData.fullName" path="fullName" />
		<br>
		<br>
		
		<acme:textbox code="personalData.statement" path="statement" />
		<br>
		<br>
		
		<acme:textbox code="personalData.phoneNumber" path="phoneNumber" />
		<br>
		<br>

		<acme:submit code="personalData.save" name="save" />&nbsp;
		
		<acme:cancel
			url="curricula/rookie/list.do"
			code="personalData.cancel" />
	</form:form>

</security:authorize>
