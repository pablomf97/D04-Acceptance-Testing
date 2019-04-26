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

	<form:form modelAttribute="miscellaneousData"
		action="miscellaneousData/hacker/edit.do?dataId=${miscellaneousData.id}&curriculaId=${currentCurricula.id}"
		id="form">

		<form:hidden path="id" />
		<form:label path="text">
			<spring:message code="miscellaneousData.text"/> :
		</form:label>
		<form:textarea path="text"/>
		<form:errors cssClass="error" path="text"	/>
		
		<br>
		<br>

		<spring:message code="miscellaneousData.attachements" />
		<br>
		<form:textarea code="miscellaneousData.attachements"
			path="attachements" />
		<form:errors path="attachements" cssClass="error" />

		<br>
		<br>

		<acme:submit code="miscellaneousData.save" name="save" />&nbsp;
		
		<acme:cancel
			url="miscellaneousData/hacker/list.do?curriculaId=${currentCurricula.id}"
			code="miscellaneousData.cancel" />

		<jstl:if test="${miscellaneousData.id != 0 }">
			<input type="submit" name="delete"
				value="<spring:message code="miscellaneousData.delete"/>" />

		</jstl:if>

		<br />
		<br />

	</form:form>

</security:authorize>