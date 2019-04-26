
<%--
 * login.jsp
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


<security:authorize access="hasRole('HACKER')">

	<form:form action="finder/hacker/search.do" modelAttribute="finder">

		<form:hidden path="id" />
		<form:hidden path="version" />
		

		<form:label path="keyWord">
			<spring:message code="finder.keyWord" />:
		</form:label>
		<form:input path="keyWord" />
		<form:errors cssClass="error" path="keyWord" />
		<br />
		<br />

		<form:label path="deadline">
			<spring:message code="finder.deadline" />:
		</form:label>
		<form:input path="deadline" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="deadline" />
		
	

		<form:label path="maximumDeadline">
			<spring:message code="finder.maximumDeadline" />:
		</form:label>
		<form:input path="maximumDeadline" placeholder="dd/MM/yyyy HH:mm" />
		<form:errors cssClass="error" path="maximumDeadline" />
		<br />
		<br />

		<form:label path="minimumSalary">
			<spring:message code="finder.minimumSalary" />:
		</form:label>
		<form:input path="minimumSalary" placeholder="1000.00" />
		<form:errors cssClass="error" path="minimumSalary" />
		<br />
		<br />

		<input type="submit" name="save" id="save"
			value="<spring:message code="finder.showResults" />" />
		
	&#160;
		<jstl:if test="${finder.id!=0}">
			<input type="submit" name="delete" id="delete"
				value='<spring:message code="finder.delete"/>' />
		</jstl:if>

	</form:form>
	
	<jstl:if test="${not empty positions}">
		<display:table name="positions" id="row"
		requestURI="finder/hacker/list.do" pagesize="10" class="displaytag">

		<!-- Attributes-->

		<display:column titleKey="position.title" sortable="true">
			<jstl:out value="${row.title}" />
		</display:column>
		<display:column titleKey="position.deadline" sortable="true">
			<jstl:out value="${row.deadline}" />
		</display:column>
		<display:column property="salary"
			titleKey="position.salary" sortable="true">
			<jstl:out value="${row.description}" />
		</display:column>
		<display:column titleKey="position.ticker" sortable="true">
			<jstl:out value="${row.ticker}" />
		</display:column>

		<!-- Action links -->

		<display:column>
			<a href="position/display.do?Id=${row.id}"> <spring:message
					code="position.display" />
			</a>
		</display:column>
	</display:table>
	</jstl:if>
</security:authorize>

<security:authorize access="!hasRole('HACKER')">

		<form>
		<b>Enter a keyword to search by:&#160;</b>
        <input id="test" type="text" name="keyWord" size="20" />
        
		<script>
		    var keyWord = "";
		    document.getElementById("test").value = keyWord;
		</script>
	
        <input type="submit" value="Search" name="submit"/>

      	</form>
		
		<br><br>
	<jstl:if test="${not empty positions}">
		<display:table name="positions" id="row"
		requestURI="finder/hacker/list.do" pagesize="10" class="displaytag">

		<!-- Attributes-->

		<display:column titleKey="position.title" sortable="true">
			<jstl:out value="${row.title}" />
		</display:column>
		<display:column titleKey="position.deadline" sortable="true">
			<jstl:out value="${row.deadline}" />
		</display:column>
		<display:column property="salary"
			titleKey="position.salary" sortable="true">
			<jstl:out value="${row.description}" />
		</display:column>
		<display:column titleKey="position.ticker" sortable="true">
			<jstl:out value="${row.ticker}" />
		</display:column>

		<!-- Action links -->

		<display:column>
			<a href="position/display.do?Id=${row.id}"> <spring:message
					code="position.display" />
			</a>
		</display:column>
	</display:table>
	</jstl:if>
</security:authorize>