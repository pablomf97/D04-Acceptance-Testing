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
		<display:table class="displayStyle" name="providers" id="provider"
			requestURI="provider/list.do">

			<display:column style="text-align: center;">
				<button
					onclick="location.href = 'provider/display.do?id=${provider.id}';">
					<spring:message code="actor.view" />
				</button>
			</display:column>

			<display:column style="text-align: center;">
				<button
					onclick="location.href = 'item/listAll.do?providerId=${provider.id}';">
					<spring:message code="item.list" />
				</button>
			</display:column>

			<display:column sortable="true" titleKey="actor.commercialName" property="providerMake" />

			<display:column titleKey="actor.email" property="email" />

			<display:column titleKey="actor.phone" property="phoneNumber" />

			<display:column sortable="true" titleKey="actor.address" property="address" />

		</display:table>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="some.error" />
	</jstl:otherwise>
</jstl:choose>
