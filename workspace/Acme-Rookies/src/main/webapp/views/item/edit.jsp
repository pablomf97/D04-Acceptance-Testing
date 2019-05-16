<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<script>
	function addFieldsLinks() {
		// Container <div> where dynamic content will be placed
		var containera = document.getElementById("containera");
		// Create an <input> element, set its type and name attributes
		var inputa = document.createElement("input");
		inputa.type = "text";
		inputa.name = "links";
		containera.appendChild(inputa);
	}
	
	function addFieldsPictures() {
		// Container <div> where dynamic content will be placed
		var containerb = document.getElementById("containerb");
		// Create an <input> element, set its type and name attributes
		var inputb = document.createElement("input");
		inputb.type = "text";
		inputb.name = "pictures";
		containerb.appendChild(inputb);
	}
</script>

<security:authorize access="hasRole('PROVIDER')">

	<jstl:choose>
		<jstl:when test="${isPrincipal}">
		<h1><spring:message	code="item.title.edit" /></h1>
		<form:form modelAttribute="item" action="item/edit.do"
			id="form">
	
			<form:hidden path="id" />
			
			<acme:textbox code="item.name" path="name" size="100px" /><br> <br>
			<acme:textbox code="item.description" path="description" size="100px" /><br> <br>
			
			<spring:message code="item.links.add" />
			:
			<button type="button" onClick="addFieldsLinks()">
				<spring:message code="item.add" />
			</button>
			<div id="containera"></div>
			<jstl:forEach items="${item.links}" var="at">
				<input name="links" value="${at}" size="150px"/>
			</jstl:forEach>
			<form:errors path="links" cssClass="error" />
			<br><br>
			
			
			<spring:message code="item.pictures.add" />
			:
			<button type="button" onClick="addFieldsPictures()">
				<spring:message code="item.add" />
			</button>
			<div id="containerb"></div>
			<jstl:forEach items="${item.pictures}" var="att">
				<input name="pictures" value="${att}" />
			</jstl:forEach>
			<form:errors path="pictures" cssClass="error" />
			<br><br>
	
			<acme:submit code="item.save" name="save" />&nbsp;
			<acme:cancel url="item/list.do" code="item.cancel" />
			<br />
	
		</form:form>
		</jstl:when>
	
	<jstl:otherwise>
		<p>
			<spring:message	code="item.not.allowed" /><br>
		</p>
	</jstl:otherwise>
	</jstl:choose>
</security:authorize>