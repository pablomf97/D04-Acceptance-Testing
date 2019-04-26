<%--
 * action-2.jsp
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

<security:authorize access="hasRole('ADMIN')">
	
	
	<table class="displayStyle" style="width: 50%">
			<tr>
				<th colspan="2"><spring:message
						code="administrator.statistics.C" /></th>
			</tr>
			<tr>
				<td><spring:message code="administrator.minSalarayPositions" /></td>
				<td style="text-align: right">${minSalarayPositions}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.maxSalaryPositions" /></td>
				<td style="text-align: right">${maxSalaryPositions}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.AVGSalaryPositions" /></td>
				<td style="text-align: right">${AVGSalaryPositions}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.STDDEVSalaryPositions" /></td>
				<td style="text-align: right">${STDDEVSalaryPositions}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.bestPositionSalary" /></td>
				<td style="text-align: right">${bestPositionSalary}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.worstPositionSalary" /></td>
				<td style="text-align: right">${worstPositionSalary}</td>
			</tr>
			
			<tr>
				<td><spring:message code="administrator.companyWithMorePositions" /></td>
				<td style="text-align: right">${companyWithMorePositions}</td>
			</tr>
				<tr>
				<td><spring:message code="administrator.maxPositionPerCompany" /></td>
				<td style="text-align: right">${maxPositionPerCompany}</td>
			</tr>
			
				<tr>
				<td><spring:message code="administrator.minPositionPerCompany" /></td>
				<td style="text-align: right">${minPositionPerCompany}</td>
			</tr>
			
				<tr>
				<td><spring:message code="administrator.avgPositionPerCompany" /></td>
				<td style="text-align: right">${avgPositionPerCompany}</td>
			</tr>
				<tr>
				<td><spring:message code="administrator.sttdevPositionPerCompany" /></td>
				<td style="text-align: right">${sttdevPositionPerCompany}</td>
			</tr>
				<tr>
				<td><spring:message code="administrator.maxApplicationsPerHacker" /></td>
				<td style="text-align: right">${maxApplicationsPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.minApplicationsPerHacker" /></td>
				<td style="text-align: right">${minApplicationsPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.avgApplicationsPerHacker" /></td>
				<td style="text-align: right">${avgApplicationsPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.sttdevApplicationsPerHacker" /></td>
				<td style="text-align: right">${sttdevApplicationsPerHacker}</td>
			</tr>
			<tr>
			<td><spring:message code="administrator.hackerWithMoreApplications" /></td>
				<td style="text-align: right">${hackerWithMoreApplications}</td>
			</tr>
			
			

</table>
		
	
		<table class="displayStyle" style="width: 50%">
			<tr>
				<th colspan="2"><spring:message
						code="administrator.statistics.B" /></th>
			</tr>
			<tr>
				<td><spring:message code="administrator.AvgCurriculaPerHacker" /></td>
				<td style="text-align: right">${AvgCurriculaPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.MaxCurriculaPerHacker" /></td>
				<td style="text-align: right">${MaxCurriculaPerHacker}</td>
			</tr>
				<tr>
				<td><spring:message code="administrator.MinCurriculaPerHacker" /></td>
				<td style="text-align: right">${MinCurriculaPerHacker}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.stdevCurriculaPerHacker" /></td>
				<td style="text-align: right">${stdevCurriculaPerHacker}</td>
			</tr>
			
			
			<tr>
				<td><spring:message code="administrator.emptyFinder" /></td>
				<td style="text-align: right">${ratioFinders}</td>
			</tr>
			
			<tr>
				<td><spring:message code="administrator.resultsFinder" /></td>
				<td style="text-align: right">${statsFinder[0]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.min" /></td>
				<td style="text-align: right">${statsFinder[1]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.avg" /></td>
				<td style="text-align: right">${statsFinder[2]}</td>
			</tr>
			<tr>
				<td><spring:message code="administrator.resultsFinder.stdev" /></td>
				<td style="text-align: right">${statsFinder[3]}</td>
			</tr>
	
	
	
			

		</table>

	</security:authorize>