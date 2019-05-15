<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->
<display:table pagesize="5" class="displaytag" name="audits"
	requestURI="${requestURI}" id="row">

	<display:column titleKey="audit.moment" sortable="true">
		<jstl:out value="${row.moment }"></jstl:out>
	</display:column>
	<display:column titleKey="audit.score" sortable="true">
		<jstl:out value="${row.score }"></jstl:out>
	</display:column>
	<display:column titleKey="audit.position" sortable="true">
		<jstl:out value="${row.position.title }"></jstl:out>
	</display:column>

	<jstl:if test="${name == row.auditor.userAccount.username }">
		<display:column titleKey="audit.status" sortable="true">
			<jstl:if test="${row.isDraft == true }">
				<spring:message code="audit.isDraft" />
			</jstl:if>
			<jstl:if test="${row.isDraft == false}">
				<spring:message code="audit.final" />
			</jstl:if>
		</display:column>
	</jstl:if>

	<security:authorize access="hasRole('AUDITOR')">
		<!-- Action links -->
		<display:column titleKey="audit.edit" sortable="true">
			<jstl:if
				test="${row.isDraft eq true and row.auditor.userAccount.username == name}">
				<a href="audit/edit.do?Id=${row.id}"> <spring:message
						code="audit.edit" />
				</a>
			</jstl:if>
		</display:column>
		<display:column titleKey="audit.delete" sortable="true">
			<jstl:if
				test="${row.isDraft eq true and row.auditor.userAccount.username == name}">
				<a href="audit/delete.do?Id=${row.id}"> <spring:message
						code="audit.delete" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column titleKey="audit.display" sortable="true">
		<a href="audit/display.do?Id=${row.id}"> <spring:message
				code="audit.display" />
		</a>
	</display:column>
</display:table>
