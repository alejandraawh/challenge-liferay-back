<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="challenge.service.model.Registration" %>

<style>
  .table-container {
    width: 80%;
    margin: 30px auto;
    font-family: Arial, sans-serif;
  }

  table.styled-table {
    border-collapse: collapse;
    margin: 0 auto;
    font-size: 16px;
    min-width: 400px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  }

  table.styled-table thead tr {
    background-color: #4CAF50;
    color: #ffffff;
    text-align: left;
  }

  table.styled-table th,
  table.styled-table td {
    padding: 12px 15px;
    border: 1px solid #dddddd;
  }

  table.styled-table tbody tr {
    border-bottom: 1px solid #dddddd;
  }

  table.styled-table tbody tr:nth-of-type(even) {
    background-color: #f3f3f3;
  }

  table.styled-table tbody tr:hover {
    background-color: #c1e1c1;
    cursor: pointer;
  }

  p.total-registros {
    text-align: center;
    font-weight: bold;
    font-size: 18px;
  }
</style>

<%
    List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
%>
<p class="total-registros">Bienvenido, acá podrás encontrar nuestros usuarios registrados</p>
<p class="total-registros">Total de usuarios registrados: <%= registrations != null ? registrations.size() : 0 %></p>

<div class="table-container">
  <table class="styled-table">
    <thead>
      <tr>
        <th>Nombre</th>
        <th>Email</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="reg" items="${registrations}">
        <tr>
          <td>${reg.name}</td>
          <td>${reg.email}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
