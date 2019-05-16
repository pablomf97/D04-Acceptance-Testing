<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<script>
	function addFields() {
		// Container <div> where dynamic content will be placed
		var container = document.getElementById("container");
		// Create an <input> element, set its type and name attributes
		var input = document.createElement("input");
		input.type = "text";
		input.name = "attachments";
		container.appendChild(input);
	}
</script>
<form:form action="problem/edit.do" modelAttribute="problem" id="form">
	<fieldset>
		<br>
		<form:hidden path="id" />
		<form:hidden path="isDraft" />

		<acme:textbox code="problem.title" path="title" />
		<br> <br>

		<acme:textbox code="problem.statement" path="statement" />
		<br> <br>

		<acme:textbox code="problem.optionalHint" path="optionalHint" />
		<br> <br>

		<spring:message code="problem.attachments.add" />
		:
		<button type="button" onClick="addFields()">
			<spring:message code="problem.attachments" />
		</button>
		<div id="container"></div>
		<jstl:forEach items="${problem.attachments}" var="at">
			<input name="attachments" value="${at}" />
		</jstl:forEach>
		<form:errors path="attachments" cssClass="error" />

		<br /> <br />
	</fieldset>
	<br />

	<jstl:if test="${problem.isDraft == true || problem.id == 0}">
		<acme:submit code="problem.save" name="save" />&nbsp;
					<acme:submit code="problem.save.final" name="saveFinal" />&nbsp; 
				</jstl:if>


</form:form>
<jstl:if test="${problem.id != 0}">
	<button
		onClick="window.location.href='problem/delete.do?Id=${problem.id}'">
		<spring:message code="position.confirm.delete" />
	</button>
</jstl:if>
<acme:cancel code="problem.back" url="problem/list.do" />
<br />
<br />