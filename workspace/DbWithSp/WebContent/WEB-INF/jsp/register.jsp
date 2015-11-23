<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Spring DI</title>
<style>
body {
	font-size: 20px;
	color: teal;
	font-family: Calibri;
	richness: inherit;
	border: thick;
	margin-top: 10%;
	margin-left: 30%;
	
}

td {
	font-size: 15px;
	color: black;
	width: 100px;
	height: 22px;
	text-align: left;
}

.heading {
	font-size: 18px;
	color: white;
	font: bold;
	background-color: orange;
	border: thick;
}
</style>
</head>
<body>
		<b>Registration Form </b>
		<div>
			<form:form method="post" action="/DbWithSp/insert"
				modelAttribute="user">
				<table>
					<tr>
						<td>First Name :</td>
						<td><form:input path="firstName" /></td>
					</tr>
					<tr>
						<td>Last Name :</td>
						<td><form:input path="lastName" /></td>
					</tr>
					<tr>
						<td>Gender :</td>
						<td><form:radiobuttons path="gender"
								items="${map.genderList}" /></td>
					</tr>
					<tr>
						<td>City :</td>
						<td><form:select path="city" items="${map.cityList}" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Save" /></td>

					</tr>
					<tr>

						<td colspan="2"><a href="getList">Click Here to See User
								List</a></td>
					</tr>
				</table>
				
			</form:form>
		</div>
		<c:url var="logoutUrl" value="/logout" />
		<form action="${logoutUrl}" method="post">
			<input type="submit" value="Log out" /> <input type="hidden"
				name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
</body>
</html>
