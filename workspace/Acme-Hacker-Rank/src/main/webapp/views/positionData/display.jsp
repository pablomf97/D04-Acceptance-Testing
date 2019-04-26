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

	<table class="displayStyle">
		<tr>

			<td><strong><spring:message
						code="positionData.title" /> : </strong></td>
			<td><jstl:out value="${data.title}">

				</jstl:out></td>


		</tr>


		<tr>

			<td><strong><spring:message
						code="positionData.description" /> : </strong></td>
			<td><jstl:out value="${data.description}">

				</jstl:out></td>

		</tr>
		
		<tr>

			<td><strong><spring:message
						code="positionData.startDate" /> : </strong></td>
			<td><jstl:out value="${data.startDate}">
				
				</jstl:out></td>

		</tr>
		
		<tr>

			<td><strong><spring:message
						code="positionData.endDate" /> : </strong></td>
			<td><jstl:out value="${data.endDate}">
				
				</jstl:out></td>

		</tr>

	</table>

	<input type="button" name="edit"
		value="<spring:message code="positionData.edit"	/>"
		onclick="redirect: location.href = 'positionData/hacker/edit.do?dataId=${data.id}&curriculaId=${curriculaId}';" />


	<input type="button" name="back"
		value="<spring:message code="positionData.cancel" />"
		onclick="window.history.back()" />
</security:authorize>