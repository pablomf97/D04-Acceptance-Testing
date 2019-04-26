<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<display:table pagesize="5" class="displaytag" name="problems"
	requestURI="${requestURI}" id="row">
	<jstl:if
		test="${row.company.userAccount.username eq name}">
		<display:column titleKey="problem.company" sortable="true">
			<jstl:out value="${row.company.userAccount.username}"></jstl:out>
		</display:column>
		<display:column titleKey="problem.title" sortable="true">
			<jstl:out value="${row.title }"></jstl:out>
		</display:column>
		<display:column titleKey="problem.optionalHint" sortable="true">
			<jstl:out value="${row.optionalHint }"></jstl:out>
		</display:column>
		<jstl:if test="${name == row.company.userAccount.username }">
			<display:column titleKey="problem.isDraft" sortable="true">
				<jstl:out value="${row.isDraft }"></jstl:out>
			</display:column>
		</jstl:if>

		<display:column>
			<a href="problem/display.do?Id=${row.id}"> <spring:message
					code="problem.display" />
			</a>
		</display:column>
		<display:column>
			<jstl:if
				test="${row.isDraft eq true and row.company.userAccount.username == name}">
				<a href="problem/edit.do?Id=${row.id}"> <spring:message
						code="problem.edit" />
				</a>
			</jstl:if>
		</display:column>
		<display:column>
			<jstl:if
				test="${row.isDraft eq true and row.company.userAccount.username == name}">
				<a href="problem/delete.do?Id=${row.id}"> <spring:message
						code="problem.delete" />
				</a>
			</jstl:if>
		</display:column>
	</jstl:if>
</display:table>

