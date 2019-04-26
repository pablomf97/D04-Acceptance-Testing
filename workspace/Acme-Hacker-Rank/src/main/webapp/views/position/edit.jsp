<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form modelAttribute="position" action="position/edit.do" id="form">
	<fieldset>
		<br>
		<form:hidden path="id" />
		<form:hidden path="isDraft" />
		<form:hidden path="isCancelled" />
		<form:hidden path="problems" />

		<form:label path="title">
			<spring:message code="position.title" />:*</form:label>
		<form:input type="text" path="title" />
		<form:errors path="title" cssClass="error" />
		<br />

		<form:label path="description">
			<spring:message code="position.description" />:*</form:label>
		<form:textarea type="text" path="description" />
		<form:errors path="description" cssClass="error" />
		<br />

		<form:label path="deadline">
			<spring:message code="position.deadline" />:*</form:label>
		<form:input type="text" path="deadline" placeholder="dd/mm/yyyy hh:mm" />
		<form:errors path="deadline" cssClass="error" />
		<br />

		<form:label path="profileRequired">
			<spring:message code="position.profileRequired" />:*</form:label>
		<form:textarea type="text" path="profileRequired" />
		<form:errors path="profileRequired" cssClass="error" />
		<br />

		<form:label path="technologiesRequired">
			<spring:message code="position.technologiesRequired" />:*</form:label>
		<form:textarea type="text" path="technologiesRequired" />
		<form:errors path="technologiesRequired" cssClass="error" />
		<br />

		<form:label path="salary">
			<spring:message code="position.salary" />:*</form:label>
		<form:input type="number" step="0.01" path="salary" min="0.00" />
		<form:errors path="salary" cssClass="error" />
		<br />

		<form:label path="skillsRequired">
			<spring:message code="position.skillsRequired" />:*</form:label>
		<form:textarea type="text" path="skillsRequired" />
		<form:errors path="skillsRequired" cssClass="error" />
		<br />

	</fieldset>
	<fieldset>

		<form:label path="problems">
			<spring:message code="position.problems" />
		</form:label>
		<br>
		<form:select multiple="true" path="problems" items="${problems}"
			itemLabel="title" />
		<br> <br>
		<form:errors path="problems" cssClass="error" />
	</fieldset>
	<br />
	<jstl:if test="${position.isDraft == true || position.id == 0}">
		<acme:submit code="position.save" name="save" />&nbsp;
		<acme:submit code="position.save.final" name="saveFinal" />&nbsp; 
	</jstl:if>
	<jstl:if test="${position.id != 0}">
		<button
			onClick="window.location.href='position/delete.do?Id=${position.id}'">
			<jstl:if test="${position.isDraft == true }">
				<spring:message code="position.confirm.delete" />

			</jstl:if>
			<jstl:if test="${position.isDraft == false }">
				<spring:message code="position.cancelled" />

			</jstl:if>
		</button>
	</jstl:if>
	<acme:cancel code="position.back" url="position/list.do" />
	<br />

</form:form>
