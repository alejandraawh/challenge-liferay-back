<%@ include file="/init.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="challenge.service.model.Registration" %>

<style>
  /* Tabla centrada y con ancho adecuado */
  .table-container {
    max-width: 960px;
    margin: 30px auto;
    font-family: Arial, sans-serif;
  }

  /* Tabla con estilos Bootstrap-like y sombra */
  table.styled-table {
    width: 100%;
    border-collapse: collapse;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    background-color: white;
  }

  /* Encabezado con azul #0b5fff, texto blanco y centrado */
  table.styled-table thead th {
    background-color: #0b5fff;
    color: white;
    font-weight: 600;
    text-align: center;
    padding: 12px 15px;
    border: 1px solid #ddd;
  }

  /* Celdas con padding y borde */
  table.styled-table tbody td {
    padding: 12px 15px;
    border: 1px solid #ddd;
  }

  /* Filas alternas con color de fondo claro */
  table.styled-table tbody tr:nth-of-type(even) {
    background-color: #f8f9fa;
  }

  /* Hover suave */
  table.styled-table tbody tr:hover {
    background-color: #e3f2fd;
    cursor: pointer;
  }

  /* Títulos y textos arriba */
  h1, p {
    text-align: center;
  }

  .total-registrations {
    font-weight: 600;
    font-size: 1.2rem;
    color: hsl(217, 10%, 50.8%);
    margin-bottom: 2rem;
  }
</style>

<%
    List<Registration> registrations = (List<Registration>) request.getAttribute("registrations");
%>

<section>
  <div class="px-4 py-5 px-md-5 text-center" style="background-color: hsl(0, 0%, 96%)">
    <div class="container">
      <h1 class="display-5 fw-bold mb-3">Registered Users</h1>
      <p class="lead" style="color: hsl(217, 10%, 50.8%)">
        Welcome! Here you can find the list of users that have registered.
      </p>
      <p class="total-registrations">
        Total registered users: <%= registrations != null ? registrations.size() : 0 %>
      </p>

      <div class="table-container">
        <table class="styled-table">
          <thead>
            <tr>
              <th>Name</th>
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
    </div>
  </div>
</section>
