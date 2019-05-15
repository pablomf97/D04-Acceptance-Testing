<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
	function checkPhone(msg) {
		var phone = document.getElementById("phoneNumber").value;
		var pattern = /^(((([+][1-9]{1}[0-9]{0,2}[\s]){0,1}([(][1-9]{1}[0-9]{0,2}[)][\s]){0,1})){0,1}([0-9]{4}){1}([0-9]{0,}))$/;
		var pat = pattern.test(phone);
		if (pat) {
			return true;
		} else {
			return confirm(msg);
		}
	}
</script>

<style>
.tooltip {
	position: relative;
	display: inline-block;
	border-bottom: 1px dotted black;
}

.tooltip .tooltiptext {
	visibility: hidden;
	width: 120px;
	background-color: black;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 5px 0;
	position: absolute;
	z-index: 1;
	bottom: 150%;
	left: 50%;
	margin-left: -60px;
	width: 200px;
}

.tooltip .tooltiptext::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: black transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
	visibility: visible;
}
</style>

<spring:message code="phone.confirmation" var="confirmTelephone" />
<security:authorize access="hasRole('ADMIN')">
	<form:form modelAttribute="registerFormObject"
		action="administrator/administrator/register.do"
		onsubmit="javascript: return checkPhone('${confirmTelephone}');">

		<!-- User Account Attributes -->
		<fieldset>
			<legend style="font-size: 21px">
				<spring:message code="actor.userAccount" />
			</legend>

			<div>
				<p>
					<form:errors path="username" cssClass="error" />
				</p>
				<strong><form:label path="username">
						<spring:message code="actor.username" />
					</form:label></strong>
				<form:input path="username" />
			</div>

			<br />

			<div>
				<form:errors path="password" cssClass="error">
					<p class="error">
						<spring:message code="pass.confirm.error" />
					</p>
				</form:errors>
				<strong><form:label path="password">
						<spring:message code="actor.password" />
					</form:label></strong>
				<form:password path="password" />
			</div>

			<div>
				<p>
					<form:errors path="passConfirmation" cssClass="error" />
				</p>
				<strong><form:label path="passConfirmation">
						<spring:message code="actor.passConfirmation" />
					</form:label></strong>
				<form:password path="passConfirmation" />
			</div>
			<br />
		</fieldset>
		<br />

		<!-- Actor Attributes -->
		<fieldset>
			<legend style="font-size: 21px">
				<spring:message code="actor.personalData" />
			</legend>

			<div>
				<p>
					<form:errors path="name" cssClass="error" />
				</p>
				<strong><form:label path="name">
						<spring:message code="actor.name" />
					</form:label></strong>
				<form:input path="name" />
			</div>

			<div>
				<p>
					<form:errors path="surname" cssClass="error" />
				</p>
				<strong><form:label path="surname">
						<spring:message code="actor.surname" />
					</form:label></strong>
				<form:input path="surname" />
			</div>

			<br />

			<div>
				<div class="tooltip">
					<form:errors path="VAT" cssClass="error">
						<p class="error">
							<spring:message code="VAT.error" />
						</p>
					</form:errors>
					<strong><form:label path="VAT">

							<spring:message code="actor.VAT" />

							<span class="tooltiptext"><spring:message
									code="VAT.placeholder" /> </span>

						</form:label></strong>
					<form:input path="VAT" />
				</div>
			</div>

			<br />

			<div>
				<form:errors path="photo" cssClass="error">
					<p class="error">
						<spring:message code="photo.error" />
					</p>
				</form:errors>
				<strong><form:label path="photo">
						<spring:message code="actor.photo" />
					</form:label></strong>
				<form:input path="photo" />
			</div>

			<br />

			<div>
				<form:errors path="email" cssClass="error">
					<p class="error">
						<spring:message code="email.error" />
					</p>
				</form:errors>
				<strong><form:label path="email">
						<spring:message code="actor.email" />
					</form:label></strong>
				<form:input path="email" />
			</div>

			<div>
				<p>
					<form:errors path="phoneNumber" cssClass="error" />
				</p>
				<strong><form:label path="phoneNumber">
						<spring:message code="actor.phone" />
					</form:label></strong>
				<form:input path="phoneNumber" />
			</div>

			<div>
				<p>
					<form:errors path="address" cssClass="error" />
				</p>
				<strong><form:label path="address">
						<spring:message code="actor.address" />
					</form:label></strong>
				<form:input path="address" />
			</div>

			<br />
		</fieldset>
		<br />

		<!-- Credit card -->
		<fieldset>
			<legend style="font-size: 21px">
				<spring:message code="card.data" />
			</legend>

			<div>
				<p>
					<form:errors path="holder" cssClass="error" />
				</p>
				<strong><form:label path="holder">
						<spring:message code="card.holder" />
					</form:label></strong>
				<form:input path="holder" />
			</div>

			<div>
				<p>
					<form:errors path="make" cssClass="error" />
				</p>
				<strong><form:label path="make">
						<spring:message code="card.make" />
					</form:label></strong>
				<form:input path="make" />
			</div>

			<br />

			<div>
				<form:errors path="number" cssClass="error">
					<p class="error">
						<spring:message code="number.error" />
					</p>
				</form:errors>
				<strong><form:label path="number">
						<spring:message code="card.number" />
					</form:label></strong>
				<form:input path="number" />
			</div>

			<br>

			<div>
				<form:errors path="expirationMonth" cssClass="error">
					<p class="error">
						<spring:message code="card.date.error" />
					</p>
				</form:errors>
				<strong><form:label path="expirationMonth">
						<spring:message code="card.expirationMonth" />
					</form:label></strong>
				<form:input placeholder="MM" path="expirationMonth" />
			</div>

			<br>

			<div>
				<form:errors path="expirationYear" cssClass="error">
					<p class="error">
						<spring:message code="card.date.error" />
					</p>
				</form:errors>
				<strong><form:label path="expirationYear">
						<spring:message code="card.expirationYear" />
					</form:label></strong>
				<form:input placeholder="YY" path="expirationYear" />
			</div>

			<br />

			<div>
				<spring:message var="cvvmessage" code="cvv.message" />
				<form:errors path="CVV" cssClass="error">
					<p class="error">
						<spring:message code="CVV.error" />
					</p>
				</form:errors>
				<strong><form:label path="CVV">
						<spring:message code="card.CVV" />
					</form:label></strong>
				<form:input style="width:245px;" placeholder="${cvvmessage}"
					path="CVV" />
			</div>
			<br />
			<br />
		</fieldset>
		<br />

		<!-- Terms&Conditions -->
		<fieldset>
			<legend style="font-size: 21px">
				<spring:message code="actor.terms" />
			</legend>

			<form:errors path="termsAndConditions" cssClass="error">
				<p class="error">
					<spring:message code="terms.error" />
				</p>
			</form:errors>
			<form:checkbox path="termsAndConditions" />
			<strong><spring:message code="actor.acceptTerms" /></strong> <br />
		</fieldset>
		<br />

		<!-- Buttons -->
		<input type="submit" name="save"
			value="<spring:message code="form.save" />" />

		<button type="button" onclick="javascript: relativeRedir('/')">
			<spring:message code="form.cancel" />
		</button>

	</form:form>
</security:authorize>