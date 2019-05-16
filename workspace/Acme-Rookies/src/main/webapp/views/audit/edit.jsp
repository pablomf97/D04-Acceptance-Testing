<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="audit" action="audit/edit.do" id="form">
	<fieldset>
		<br>
		<form:hidden path="id" />
		<form:hidden path="isDraft" />
		<form:hidden path="position" />

		<form:label path="text">
			<spring:message code="audit.text" />:*</form:label>
		<form:textarea type="text" path="text" />
		<form:errors path="text" cssClass="error" />
		<br />

		<form:label path="score">
			<spring:message code="audit.score" />:*</form:label>
		<form:input type="text" path="score" />
		<form:errors path="score" cssClass="error" />
		<br />

	</fieldset>
	<br />
	<jstl:if test="${audit.isDraft == true || audit.id == 0}">
		<acme:submit code="audit.save" name="save" />&nbsp;
		<acme:submit code="audit.save.final" name="saveFinal" />&nbsp; 
	</jstl:if>
	<jstl:if test="${audit.id != 0}">

		<jstl:if test="${audit.isDraft == true }">
			<button
				onClick="window.location.href='audit/delete.do?Id=${audit.id}'">
				<spring:message code="audit.confirm.delete" />
			</button>

		</jstl:if>
	</jstl:if>
	<acme:cancel code="audit.back" url="audit/list.do" />
	<br />
</form:form>
