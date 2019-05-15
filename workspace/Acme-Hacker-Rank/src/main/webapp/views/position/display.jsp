<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<table class="displayStyle">
	<tr>
		<td><strong> <spring:message code="position.title" /> :
		</strong></td>
		<td><jstl:out value="${position.title}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="position.description" />
				:
		</strong></td>
		<td><jstl:out value="${position.description}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="position.deadline" />
				:
		</strong></td>
		<td><jstl:out value="${position.deadline}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="position.profileRequired" /> :
		</strong></td>
		<td><jstl:out value="${position.profileRequired}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="position.technologiesRequired" /> :
		</strong></td>
		<td><jstl:out value="${position.technologiesRequired}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="position.salary" />
				:
		</strong></td>
		<td><fmt:formatNumber maxFractionDigits="2"
				value="${position.salary }" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="position.ticker" />
				:
		</strong></td>
		<td><jstl:out value="${position.ticker}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message
					code="position.skillsRequired" /> :
		</strong></td>
		<td><jstl:out value="${position.skillsRequired}" /></td>
	</tr>

	<tr>
		<td><strong> <spring:message code="position.company" />
				:
		</strong></td>
		<td><jstl:out value="${position.company.commercialName}" /></td>
	</tr>

	<jstl:choose>
		<jstl:when test="${position.isDraft == true}">
			<spring:message var="status" code='not.final.it.is' />
		</jstl:when>
		<jstl:otherwise>
			<spring:message var="status" code='final.it.is' />
		</jstl:otherwise>
	</jstl:choose>

	<tr>
		<td><strong> <spring:message code="position.isDraft" />
				:
		</strong></td>
		<td>${status}</td>
	</tr>

	<jstl:choose>
		<jstl:when test="${position.isCancelled == true}">
			<spring:message var="status" code='cancelled.it.is' />
		</jstl:when>
		<jstl:otherwise>
			<spring:message var="status" code='not.cancelled.it.is' />
		</jstl:otherwise>
	</jstl:choose>


	<tr>
		<td><strong> <spring:message code="position.isCancelled" />
				:
		</strong></td>
		<td>${status}</td>
	</tr>
	<security:authorize access="isAuthenticated()">

		<tr>
			<td><strong> <spring:message code="position.audit.list" />
					:
			</strong></td>
			<td><a href="audit/listAll.do?Id=${position.id}"><spring:message
						code="position.audit.list" /></a></td>
		</tr>
	</security:authorize>

	<jstl:if test="${name == position.company.userAccount.username}">
		<jstl:forEach items="${position.problems}" var="pro">

			<jstl:choose>
				<jstl:when test="${position.isDraft == true}">
					<spring:message var="status" code='not.final.it.is' />
				</jstl:when>
				<jstl:otherwise>
					<spring:message var="status" code='final.it.is' />
				</jstl:otherwise>
			</jstl:choose>

			<tr>
				<td>${pro.title}</td>
				<td>${status}</td>
				<td>
					<button
						onClick="window.location.href='problem/display.do?Id=${pro.id}'">
						<spring:message code="position.problem.display" />
					</button>
				</td>
			</tr>
		</jstl:forEach>
	</jstl:if>
</table>
<jstl:if test="${posBanner != ''}">
	<div>
		<h3>
			<spring:message code="position.sponsored" />
		</h3>
		<img style="width: 150px; height: 150px" src="${posBanner}"
			alt="Banner">
	</div>
</jstl:if>

<jstl:if test="${name == position.company.userAccount.username }">

	<button
		onClick="window.location.href='position/delete.do?Id=${position.id}'">
		<jstl:if test="${position.isDraft == false }">
			<spring:message code="position.cancel" />
		</jstl:if>
		<jstl:if test="${position.isDraft == true }">
			<spring:message code="position.confirm.delete" />
		</jstl:if>
	</button>
</jstl:if>


<br />