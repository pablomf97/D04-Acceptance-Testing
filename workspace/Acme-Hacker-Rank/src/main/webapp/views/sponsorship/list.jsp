<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('PROVIDER')">

	<jstl:choose>
		<jstl:when test="${permission}">
			<h1><spring:message	code="sponsorship.title.list" /></h1>
			<display:table class="displaytag" name="sponsorships"
				requestURI="sponsorships/list.do" id="sponsorship">

				<display:column titleKey="sponsorship.banner">
					<jstl:out value="${sponsorship.banner}" />
				</display:column>

				<fmt:parseNumber var = "posId" type = "number" value = "${sponsorship.target}" />
				<display:column>
					<a href="position/display.do?Id=${posId}"> <spring:message
							code="sponsorship.target" />
					</a>
				</display:column>
				
				<display:column titleKey="sponsorship.provider">
					<a href="provider/display.do?providerId=${sponsorship.provider.id}"> 
						<jstl:out value="${sponsorship.provider.name}" />
					</a>
				</display:column>
				
				<display:column>
					<a href="sponsorship/display.do?sponsorshipId=${sponsorship.id}"> <spring:message
							code="sponsorship.display" />
					</a>
				</display:column>
				
				<display:column>
					<a href="sponsorship/edit.do?sponsorshipId=${sponsorship.id}"> <spring:message
							code="sponsorship.edit" />
					</a>
				</display:column>
				
				<display:column>
					<a href="sponsorship/delete.do?sponsorshipId=${sponsorship.id}"> <spring:message
							code="sponsorship.delete" />
					</a>
				</display:column>
				
			</display:table>
		</jstl:when>
		<jstl:otherwise>
			<p>
				<jstl:out value="${errMsg.getMessage()}" /><br>
			</p>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>