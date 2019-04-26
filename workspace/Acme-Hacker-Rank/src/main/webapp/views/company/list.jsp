<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:choose>
	<jstl:when test="${!err}">
		<display:table class="displayStyle" name="allCompanies" id="company"
			requestURI="company/list.do">

			<display:column style="text-align: center;">
				<button
					onclick="location.href = 'company/display.do?id=${company.id}';">
					<spring:message code="actor.view" />
				</button>
			</display:column>

			<display:column style="text-align: center;">
				<button
					onclick="location.href = 'position/listAll.do?id=${company.id}';">
					<spring:message code="position.list" />
				</button>
			</display:column>

			<display:column titleKey="actor.commercialName"
				property="commercialName" />

			<display:column titleKey="actor.email" property="email" />

			<display:column titleKey="actor.phone" property="phoneNumber" />

			<display:column titleKey="actor.address" property="address" />

		</display:table>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="some.error" />
	</jstl:otherwise>
</jstl:choose>
