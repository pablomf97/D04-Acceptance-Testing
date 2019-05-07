<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasAnyRole('ROOKIE','COMPANY')">
	<form:form modelAttribute="curricula"
		action="curricula/rookie/display.do" id="form">
		<form:hidden path="id" />
		<table class="displayStyle">

			<tr>
				<td><strong><spring:message
							code="curricula.personalData" />: </strong></td>


				<td><jstl:out value="${personalData.fullName}"></jstl:out></td>
				<td><a
					href="personalData/rookie/display.do?dataId=${personalData.id}&curriculaId=${curricula.id}">
						<spring:message code="curricula.personalData.display" />
				</a></td>

			</tr>

			<tr>
				<td><strong><spring:message
							code="curricula.first.miscellaneousData" />: </strong></td>
				<jstl:if test="${!emptyMiscellaneous}">
					<td><jstl:out value="${miscellanousData.text}"></jstl:out></td>

					<td><a
						href="miscellaneousData/rookie/list.do?curriculaId=${curricula.id}">
							<spring:message code="curricula.miscellaneousData.list" />
					</a></td>
				</jstl:if>
				<jstl:if test="${emptyMiscellaneous}">
					<security:authorize access="hasRole('ROOKIE')">

						<td><spring:message code="curricula.empty.data" /></td>
						<td><a
							href="miscellaneousData/rookie/create.do?curriculaId=${curricula.id}">
								<spring:message code="curricula.miscellaneousData.create" />
						</a></td>
					</security:authorize>

				</jstl:if>
			</tr>

			<tr>
				<td><strong><spring:message
							code="curricula.first.educationData" />: </strong></td>
				<jstl:if test="${!emptyEducation}">
					<td><jstl:out value="${educationData.degree}"></jstl:out></td>

					<td><a
						href="educationData/rookie/list.do?curriculaId=${curricula.id}">
							<spring:message code="curricula.educationData.list" />
					</a></td>
				</jstl:if>
				<jstl:if test="${emptyEducation}">
					<security:authorize access="hasRole('ROOKIE')">

						<td><spring:message code="curricula.empty.data" /></td>
						<td><a
							href="educationData/rookie/create.do?curriculaId=${curricula.id}">
								<spring:message code="curricula.educationData.create" />
						</a></td>
					</security:authorize>

				</jstl:if>
			</tr>

			<tr>
				<td><strong><spring:message
							code="curricula.first.positionData" />: </strong></td>
				<jstl:if test="${!emptyPosition}">
					<td><jstl:out value="${positionData.title}"></jstl:out></td>

					<td><a
						href="positionData/rookie/list.do?curriculaId=${curricula.id}">
							<spring:message code="curricula.positionData.list" />
					</a></td>
				</jstl:if>

				<jstl:if test="${emptyPosition}">
					<security:authorize access="hasRole('ROOKIE')">

						<td><spring:message code="curricula.empty.data" /></td>
						<td><a
							href="positionData/rookie/create.do?curriculaId=${curricula.id}">
								<spring:message code="curricula.positionData.create" />
						</a></td>
					</security:authorize>

				</jstl:if>
			</tr>


		</table>
		<security:authorize access="hasRole('ROOKIE')">
		<jstl:if test="${ curricula.isCopy == false}">
			<input type="submit" name="delete"
				value="<spring:message code="curricula.delete"/>" />
				</jstl:if>
		</security:authorize>
	</form:form>
</security:authorize>