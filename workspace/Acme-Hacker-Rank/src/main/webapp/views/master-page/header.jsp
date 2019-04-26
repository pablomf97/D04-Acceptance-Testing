<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<div>
	<a href="#"><img src="${banner}" alt="Acme-Hacker-Rank Co., Inc."
		style="margin-bottom: 0.5em;" /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<!-- Register admin -->
			<li><a class="fNiv"
				href="administrator/administrator/register.do"><spring:message
						code="master.page.register.admin" /></a></li>

			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>

					<li><a href="administrator/action-2.do"><spring:message
								code="master.page.administrator.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">

			<!-- Sign up -->
			<li><a class="fNiv"><spring:message
						code="master.page.singup" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="company/company/register.do"><spring:message
								code="master.page.register.company" /></a></li>
					<li><a href="hacker/hacker/register.do"><spring:message
								code="master.page.register.hacker" /></a></li>
				</ul></li>

		</security:authorize>
		<li><a class="fNiv"><spring:message
					code="master.page.position" /></a>
			<ul>
				<security:authorize access="!hasRole('HACKER')">
					<li class="arrow"></li>
					<li><a href="position/listAll.do"><spring:message
								code="master.page.position.list.all" /></a></li>
					<li><a href="finder/anon/search.do"><spring:message
								code="master.page.finder" /></a></li>
				</security:authorize>
				<security:authorize access="hasRole('HACKER')">
					<li class="arrow"></li>
					<li><a href="finder/hacker/search.do"><spring:message
								code="master.page.finder" /></a></li>

				</security:authorize>
				<security:authorize access="hasRole('COMPANY')">
					<li><a href="position/list.do"><spring:message
								code="master.page.position.list" /></a></li>


					<li><a href="position/create.do"><spring:message
								code="master.page.position.edit" /></a></li>
				</security:authorize>

			</ul></li>
		<security:authorize access="hasRole('COMPANY')">

			<li><a class="fNiv"><spring:message
						code="master.page.problem" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="problem/list.do"><spring:message
								code="master.page.problem.list" /></a></li>
					<security:authorize access="hasRole('COMPANY')">

						<li><a href="problem/create.do"><spring:message
									code="master.page.problem.edit" /></a></li>
					</security:authorize>

				</ul></li>
		</security:authorize>


		<li><a class="fNiv" href="company/list.do"><spring:message
					code="master.page.company.list" /></a></li>

		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>
		</security:authorize>
		<security:authorize access="hasRole('HACKER')">
			<li><a class="fNiv"><spring:message
						code="master.page.applications" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/listHacker.do"><spring:message
								code="master.page.hacker.applications" /></a></li>
					<li><a href="position/hacker/listAll.do"><spring:message
								code="master.page.position.list" /></a></li>
				</ul></li>

			<li><a class="fNiv"><spring:message
						code="master.page.curricula" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="curricula/hacker/list.do"><spring:message
								code="master.page.curricula.list" /></a></li>
					<li><a href="curricula/hacker/create.do"><spring:message
								code="master.page.curricula.create" /></a></li>
				</ul>
		</security:authorize>

		<security:authorize access="hasRole('COMPANY')">
			<li><a class="fNiv"><spring:message
						code="master.page.applications" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/listCompany.do"><spring:message
								code="master.page.company.applications" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="statistics/administrator/display.do"><spring:message
									code="master.page.dashboard" /></a></li>

						<li><a href="sysconfig/administrator/display.do"><spring:message
									code="master.page.system" /></a></li>

						<li><a href="administrator/display.do"><spring:message
									code="actor.view" /></a></li>
						<li><a href="administrator/export.do"><spring:message
									code="export" /></a></li>
						<li><a href="administrator/administrator/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>

					</security:authorize>
					<security:authorize access="hasRole('COMPANY')">
						<li><a href="company/display.do"><spring:message
									code="actor.view" /></a></li>
						<li><a href="company/export.do"><spring:message
									code="export" /></a></li>
						<li><a href="company/company/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>

					<li><a href="finder/hacker/search.do"><spring:message
								code="master.page.finder" /></a></li>

					<security:authorize access="hasRole('HACKER')">
						<li><a href="hacker/export.do"><spring:message
									code="export" /></a></li>
						<li><a href="hacker/display.do"><spring:message
									code="actor.view" /></a></li>
						<li><a href="hacker/hacker/edit.do"><spring:message
									code="master.page.actor.edit" /></a></li>
					</security:authorize>

					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
		</security:authorize>

	</ul>

</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

<security:authorize access="isAuthenticated()">
	<jstl:if test="${pageContext.response.locale.language == 'es'}">
		<h2>
			<strong style="color: red;"><jstl:out
					value="${breachNotification.get('Español')}"></jstl:out><br /> </strong>
		</h2>
	</jstl:if>
	<jstl:if test="${pageContext.response.locale.language == 'en'}">
		<h2>
			<strong style="color: red;"> <jstl:out
					value="${breachNotification.get('English')}"></jstl:out><br />
			</strong>
		</h2>
	</jstl:if>
</security:authorize>
