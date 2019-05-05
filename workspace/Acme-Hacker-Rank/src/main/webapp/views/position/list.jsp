<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" name="positions"
	requestURI="${requestURI}" id="row">

	<display:column titleKey="position.title" sortable="true">
		<jstl:out value="${row.title }"></jstl:out>
	</display:column>
	<display:column titleKey="position.deadline" sortable="true">
		<jstl:out value="${row.deadline }"></jstl:out>
	</display:column>
	<display:column titleKey="position.profileRequired" sortable="true">
		<jstl:out value="${row.profileRequired }"></jstl:out>
	</display:column>
	<display:column titleKey="position.salary" sortable="true">
		<fmt:formatNumber maxFractionDigits="2" value="${row.salary }" />
	</display:column>
	<display:column titleKey="position.ticker" sortable="true">
		<jstl:out value="${row.ticker }"></jstl:out>
	</display:column>
	<display:column titleKey="position.company" sortable="true">
		<a href="company/display.do?id=${row.company.id}"><jstl:out
				value="${row.company.commercialName }"></jstl:out></a>
	</display:column>

	<jstl:choose>
		<jstl:when test="${row.isDraft}">
			<spring:message var="statusD" code='not.final.it.is' />
		</jstl:when>
		<jstl:otherwise>
			<spring:message var="statusD" code='final.it.is' />
		</jstl:otherwise>
	</jstl:choose>

	<display:column titleKey="position.isDraft" sortable="true">
		<jstl:out value="${statusD}" />
	</display:column>



	<security:authorize access="hasRole('COMPANY')">
		<jstl:choose>
			<jstl:when test="${row.isCancelled}">
				<spring:message var="status" code='cancelled.it.is' />
			</jstl:when>
			<jstl:otherwise>
				<spring:message var="status" code='not.cancelled.it.is' />
			</jstl:otherwise>
		</jstl:choose>

		<display:column titleKey="position.isCancelled" sortable="true">
			<jstl:out value="${status}" />
		</display:column>

		<!-- Action links -->
		<display:column titleKey="position.edit" sortable="true">
			<jstl:if
				test="${row.isDraft eq true and row.company.userAccount.username == name}">
				<a href="position/edit.do?Id=${row.id}"> <spring:message
						code="position.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>

	<display:column>
		<a href="position/display.do?Id=${row.id}"> <spring:message
				code="position.display" />
		</a>
	</display:column>

	<security:authorize access="hasRole('PROVIDER')">


		<display:column>
			<a href="sponsorship/create.do?positionId=${row.id}"> <spring:message
					code="position.sponsor" />
			</a>
		</display:column>
	</security:authorize>

	<display:column>
		<jstl:if
			test="${row.isDraft eq true and row.company.userAccount.username == name}">
			<a href="position/delete.do?Id=${row.id}"> <spring:message
					code="position.delete" />
			</a>
		</jstl:if>
	</display:column>
	<display:column>
		<jstl:if
			test="${row.isDraft eq false and row.company.userAccount.username == name}">
			<a href="position/delete.do?Id=${row.id}"> <spring:message
					code="position.cancel" />
			</a>
		</jstl:if>
	</display:column>
	<display:column>
		<security:authorize access="hasRole('AUDITOR')">

			<a href="audit/create.do?Id=${row.id}"> <spring:message
					code="position.audit" />
			</a>
		</security:authorize>
	</display:column>

</display:table>
