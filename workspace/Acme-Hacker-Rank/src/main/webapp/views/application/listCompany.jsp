<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('COMPANY')">

	<jstl:choose>
		<jstl:when test="${permission}">
			<display:table class="displaytag" name="applications"
				requestURI="application/listCompany.do" id="app">

				<display:column titleKey="application.applicationMoment" sortable="true">
					<jstl:out value="${app.applicationMoment}" />
				</display:column>

				<display:column titleKey="application.position.title">
					<jstl:out value="${app.position.title}" />
				</display:column>

				<spring:message code="application.pending" var="message1" />
				<spring:message code="application.submitted" var="message2" />
				<spring:message code="application.not.accepted" var="message3" />
				<spring:message code="application.accepted" var="message4" />
				
				<display:column titleKey="application.status" sortable="true">
					<jstl:choose>
						<jstl:when test="${app.status == 'PENDING'}">
							<jstl:out value="${message1}" />
						</jstl:when>
						<jstl:when test="${app.status == 'SUBMITTED'}">
							<jstl:out value="${message2}" />
						</jstl:when>
						<jstl:when test="${app.status == 'REJECTED'}">
							<jstl:out value="${message3}" />
						</jstl:when>
						<jstl:otherwise>
							<jstl:out value="${message4}" />
						</jstl:otherwise>
					</jstl:choose>
				</display:column>
				
				<display:column>
					<a href="problem/display.do?Id=${app.problem.id}"> <jstl:out value="${app.problem.title}" />
					</a>
				</display:column>
				
				<display:column titleKey="application.submitMoment">
					<jstl:if test="${app.status != 'PENDING'}">
						<jstl:out value="${app.submitMoment}" />
					</jstl:if>
				</display:column>
				
				<display:column>
					<a href="application/display.do?applicationId=${app.id}"> <spring:message
							code="application.display" />
					</a>
				</display:column>
				
				<display:column>
					<jstl:if test="${app.status == 'SUBMITTED'}">
						<a id="edit" href="application/company/action.do?action=accept&applicationId=${app.id}"> <spring:message
								code="application.accept" /></a>
					</jstl:if>
				</display:column>
				
				<display:column>
					<jstl:if test="${app.status == 'SUBMITTED'}">
						<a id="edit" href="application/company/action.do?action=reject&applicationId=${app.id}"> <spring:message
								code="application.reject" /></a>
					</jstl:if>
				</display:column>
				
			</display:table>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<jstl:out value="${errMsg.getMessage()}" />
			</p>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>