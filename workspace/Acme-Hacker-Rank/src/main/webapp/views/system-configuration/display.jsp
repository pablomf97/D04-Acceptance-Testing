<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

	<jstl:choose>
		<jstl:when test="${err}">
			<p>
				<jstl:out value="${errMsg.getMessage()}" />
			</p>
		</jstl:when>
		<jstl:otherwise>
		
		<jstl:if test="${AlreadyRebranded==false}">
			<div><a href="sysconfig/administrator/rebrand.do"><spring:message code="anuncio" /></a></div>
			
			</jstl:if>
			<table class="displayStyle">

				<tr>
					<td><spring:message code="system.name" />:</td>
					<td><jstl:out value="${sysConfig.systemName}" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="system.bannerURL" />:</td>
					<td><a href="${sysConfig.banner}"><jstl:out
								value="${sysConfig.banner}" /></a></td>
				</tr>

				<tr>
					<td><spring:message code="system.resultscached" />:</td>
					<td><jstl:out value="${sysConfig.timeResultsCached}" /></td>
				</tr>

				<tr>
					<td><spring:message code="system.resultspersearch" />:</td>
					<td><jstl:out value="${sysConfig.maxResults}" /></td>
				</tr>

				<tr>
					<td><spring:message code="system.countrycode" />:</td>
					<td><jstl:out value="${sysConfig.countryCode}" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="system.VATTax" />:</td>
					<td><jstl:out value="${sysConfig.VATTax}" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="system.flat" />:</td>
					<td><jstl:out value="${sysConfig.flatRate}" /></td>
				</tr>
			</table>

			<div>
				<table class="displayStyle">
					<tr>
						<td><display:table pagesize="5" class="displaytag"
								name="welcomeMessage"
								requestURI="sysconfig/administrator/display.do"
								id="welcomeMessage">

								<display:column titleKey="system.welcomemessage" sortable="true">
									<jstl:out value="${welcomeMessage}" />
								</display:column>

							</display:table></td>
				</table>
			</div>

			<div>
				<table class="displayStyle">
					<tr>
						<td><display:table pagesize="5" class="displaytag"
								name="breachNotification"
								requestURI="sysconfig/administrator/display.do"
								id="breachNotification">

								<display:column titleKey="system.breachNotification"
									sortable="true">
									<jstl:out value="${breachNotification}" />
								</display:column>

							</display:table></td>
				</table>
			</div>

			<input type="button" name="edit"
				value='<spring:message code="system.edit"/>'
				onclick="redirect: location.href = 'sysconfig/administrator/edit.do?systemconfigurationID=${sysConfig.id}';" />

		</jstl:otherwise>
	</jstl:choose>


</security:authorize>