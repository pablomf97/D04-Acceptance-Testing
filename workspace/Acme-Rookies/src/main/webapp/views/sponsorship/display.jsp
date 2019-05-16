<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasAnyRole('PROVIDER')">

<jstl:choose>
	<jstl:when test="${isPrincipal}">
	<h1><spring:message	code="sponsorship.title.display" /></h1>
	<table class="displayStyle">
		<tr>
			<td><strong> <spring:message code="sponsorship.provider" /> :
			</strong></td>
			<td><jstl:out value="${sponsorship.provider.name}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="sponsorship.target" />
					:
			</strong></td>
			<td><jstl:out value="${sponsorship.target}">
				</jstl:out></td>
		</tr>

		<tr>
			<td><strong> <spring:message code="sponsorship.banner" /> :
			</strong></td>
			<td><br><img style="width: 150px; height: 150px" src="${sponsorship.banner}"
			alt="Banner"/></td>
			
		</tr>

	</table>
		

	<input type="button" name="back"
		value="<spring:message code="sponsorship.back" />"
		onclick="window.history.back()" />
	</jstl:when>
	<jstl:otherwise>
		<spring:message	code="sponsorship.not.allowed" /><br>
	</jstl:otherwise>
	</jstl:choose>

</security:authorize>
