<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasAnyRole('HACKER','COMPANY')">

	<table class="displayStyle">
		<tr>
			<td><strong> <spring:message code="application.hacker" /> :
			</strong></td>
			<td><jstl:out value="${application.hacker.name}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.position" />
					:
			</strong></td>
			<td><jstl:out value="${application.position.title}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="application.applicationMoment" /> :
			</strong></td>
			<td><jstl:out value="${application.applicationMoment}">
				</jstl:out></td>
		</tr>
		
		<tr>
			<td><strong> <spring:message code="application.status" /> :
			</strong></td>
			<td><jstl:out value="${application.status}">
				</jstl:out></td>
		</tr>
		
		<tr>
			<td><strong> <spring:message code="application.position.company" /> :
			</strong></td>
			<td><jstl:out value="${application.position.company.commercialName}">
				</jstl:out></td>
		</tr>

		<jstl:if test="${application.status != 'PENDING'}">
		
			<tr>
				<td><strong> <spring:message code="application.submitMoment" /> :
				</strong></td>
				<td><jstl:out value="${application.submitMoment}">
					</jstl:out></td>

			</tr>
			
			<tr>
				<td><strong> <spring:message code="application.explanation" /> :
				</strong></td>
				<td><jstl:out value="${application.explanation}">
					</jstl:out></td>
			</tr>

			<tr>
				<td><strong> <spring:message code="application.linkCode" /> :
				</strong></td>
				<td><jstl:out value="${application.linkCode}">
					</jstl:out></td>

			</tr>
			
			<tr>
				<td><strong> <spring:message code="application.copyCurricula" /> :
				</strong></td>
				<td><a href="curricula/hacker/display.do?curriculaId=${application.copyCurricula.id}"> <spring:message
						code="curricula.display" /></a></td>

			</tr>
		</jstl:if>


	</table>
	<div></div>
	
	<h3><spring:message code="application.problem" /></h3>

	<table class="displayStyle">
		
			<tr>
				<td><strong> <spring:message code="application.problem.title" /> :
				</strong></td>
				<td><jstl:out value="${application.problem.title}">
					</jstl:out></td>
			</tr>
			<tr>
				<td><strong> <spring:message code="application.problem.statement" /> :
				</strong></td>
				<td><jstl:out value="${application.problem.statement}">
					</jstl:out></td>
			</tr>
			<jstl:if test="${not empty application.problem.optionalHint}">
			<tr>
				<td><strong> <spring:message code="application.problem.optionalHint" /> :
				</strong></td>
				<td><jstl:out value="${application.problem.optionalHint}">
					</jstl:out></td>
			</tr>
			</jstl:if>
			<tr>
				<td><strong> <spring:message code="application.problem.attachments" /> :
				</strong></td>
				<td><jstl:out value="${application.problem.attachments}">
					</jstl:out></td>
			</tr>
	</table>
	

	<input type="button" name="back"
		value="<spring:message code="application.back" />"
		onclick="window.history.back()" />

</security:authorize>
