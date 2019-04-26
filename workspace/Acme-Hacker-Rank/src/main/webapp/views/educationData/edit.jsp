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

	<form:form modelAttribute="educationData"
		action="educationData/hacker/edit.do?dataId=${educationData.id}&curriculaId=${currentCurricula.id}"
		id="form">

		<form:hidden path="id" />

		<acme:textbox code="educationData.degree" path="degree" />
		<br>
		<br>

		<spring:message code="educationData.institution" />
		<br>
		<form:textarea code="educationData.institution" path="institution" />
		<form:errors path="institution" cssClass="error" />

		<br>
		<br>

		<form:label path="mark">
			<spring:message code="educationData.mark" />:
		</form:label>
		<form:input path="mark" />
		<form:errors cssClass="error" path="mark" />

		<br />
		<br />



		<form:label path="startDate">
			<spring:message code="educationData.startDate" />:
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="startDate" />

		<br />
		<br />


		<form:label path="endDate">
			<spring:message code="educationData.endDate" />:
		</form:label>
		<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="endDate" />

		<br />
		<br />


		<acme:submit code="educationData.save" name="save" />&nbsp;
		
		<acme:cancel
			url="educationData/hacker/list.do?curriculaId=${currentCurricula.id}"
			code="educationData.cancel" />

		<jstl:if test="${educationData.id != 0 }">
			<input type="submit" name="delete"
				value="<spring:message code="educationData.delete"/>" />

		</jstl:if>


	</form:form>

</security:authorize>