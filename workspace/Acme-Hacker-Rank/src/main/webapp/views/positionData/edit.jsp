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

	<form:form modelAttribute="positionData"
		action="positionData/hacker/edit.do?dataId=${positionData.id}&curriculaId=${currentCurricula.id}"
		id="form">

		<form:hidden path="id" />

		<acme:textbox code="positionData.title" path="title" />
		<br>
		<br>

		<spring:message code="positionData.description" />
		<br>
		<form:textarea code="positionData.description"
			path="description" />
		<form:errors path="description" cssClass="error" />

		<br>
		<br>
		
		<form:label path="startDate">
			<spring:message code="positionData.startDate" />:
		</form:label>
		<form:input path="startDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="startDate" />
		
		<br />
		<br />
		
		
			<form:label path="endDate">
			<spring:message code="positionData.endDate" />:
		</form:label>
		<form:input path="endDate" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="endDate" />
		
		<br />
		<br />
		

		<acme:submit code="positionData.save" name="save" />&nbsp;
		
		<acme:cancel
			url="positionData/hacker/list.do?curriculaId=${currentCurricula.id}"
			code="positionData.cancel" />

		<jstl:if test="${positionData.id != 0 }">
			<input type="submit" name="delete"
				value="<spring:message code="positionData.delete"/>" />

		</jstl:if>

		
	</form:form>

</security:authorize>