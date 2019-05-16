<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('PROVIDER')">

	<jstl:choose>
		<jstl:when test="${isPrincipal}">
		<h1><spring:message	code="sponsorship.title.edit" /></h1>
		<form:form modelAttribute="sponsorship" action="sponsorship/edit.do"
			id="form">
	
			<form:hidden path="id" />
			<form:hidden path="target" />
	
			<acme:textbox code="sponsorship.banner" path="banner" size="100px" />
			<br><br>
	
			<acme:submit code="sponsorship.save" name="save" />&nbsp;
			
			<acme:cancel url="sponsorship/list.do" code="sponsorship.cancel" />
			<br />
	
		</form:form>
		</jstl:when>
	
	<jstl:otherwise>
		<p>
			<spring:message	code="sponsorship.not.allowed" /><br>
		</p>
	</jstl:otherwise>
	</jstl:choose>
</security:authorize>