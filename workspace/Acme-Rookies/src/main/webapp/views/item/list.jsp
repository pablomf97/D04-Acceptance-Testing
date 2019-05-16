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
			<h1><spring:message	code="item.title.list" /></h1>
			<display:table class="displaytag" name="items" pagesize="5" 
				requestURI="item/list.do" id="item">

				<display:column titleKey="item.name" sortable="true">
					<jstl:out value="${item.name}" />
				</display:column>

				<display:column titleKey="item.description" sortable="true">
					<jstl:out value="${item.description}" />
				</display:column>
				
				<display:column titleKey="item.provider" sortable="true">
					<a href="provider/display.do?providerId=${item.provider.id}"> 
						<jstl:out value="${item.provider.name}" />
					</a>
				</display:column>
				
				<display:column >
					<a href="item/display.do?itemId=${item.id}"> <spring:message
							code="item.display" />
					</a>
				</display:column>
				
				<display:column>
					<a href="item/edit.do?itemId=${item.id}"> <spring:message
							code="item.edit" />
					</a>
				</display:column>
				
				<display:column>
					<a href="item/delete.do?itemId=${item.id}"> <spring:message
							code="item.delete" />
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