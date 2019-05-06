<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%-- <security:authorize access="hasAnyRole('PROVIDER')">
 --%>		<h1>
			<spring:message code="item.title.display" />
		</h1>
		<table class="displayStyle">
			<tr>
				<td><strong> <spring:message code="item.provider" />
						:
				</strong></td>
				<td><jstl:out value="${item.provider.name}">
					</jstl:out></td>
			</tr>

			<tr>
				<td><strong> <spring:message code="item.name" /> :
				</strong></td>
				<td><jstl:out value="${item.name}">
					</jstl:out></td>
			</tr>

			<tr>
				<td><strong> <spring:message code="item.description" />
						:
				</strong></td>
				<td><jstl:out value="${item.description}">
					</jstl:out></td>
			</tr>

			<tr>
				<td><strong> <spring:message code="item.links" /> :
				</strong></td>
				<jstl:forEach items="${links}" var="at">
					<td><a href="${at }" target=blank><jstl:out value="${at }"></jstl:out></a></td>
				</jstl:forEach>
			</tr>

			<tr>
				<td><strong> <spring:message code="item.pictures" />
						:
				</strong></td>
				<jstl:forEach items="${pictures}" var="att">
					<td><a href="${att }" target=blank><jstl:out
								value="${att }"></jstl:out></a></td>
				</jstl:forEach>
			</tr>

		</table>


		<input type="button" name="back"
			value="<spring:message code="item.back" />"
			onclick="window.history.back()" />
<%-- 		<spring:message code="item.not.allowed" />
 --%><%-- </security:authorize>
 --%>