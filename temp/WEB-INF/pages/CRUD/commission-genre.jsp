<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.production.vente.Vente"%>
<%@page import="com.model.production.vente.commission.Commission"%>
<%@page import="com.model.production.vente.commission.Commission"%>
<%@page import=" java.util.*"%>
<%-- <%@page import=" java.util.HashMap"%> --%>

<%-- java.util.List;
import java.util.Map; --%>


<% 
    Map<String, Commission[]> commissions = (Map<String, Commission[]>) request.getAttribute("commissions") ;
    Double[] sommes = (Double[]) request.getAttribute("sommes") ;
%>

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">Etat Vendeur</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <h5 class="card-title">Liste des vendeurs</h5>
      <div class="card-body">
          <form method="get" action="${pageContext.request.contextPath}/CRUD/commissionGenre">
              <div class="row mb-3">
                  <label for="inputEmail3" class="col-sm-2 col-form-label">Date début</label>
                  <div class="col-sm-10">
                    <input type="date" class="form-control" id="inputText" name="dateDebut">
                  </div>
              </div>
               <div class="row mb-3">
                  <label for="inputEmail3" class="col-sm-2 col-form-label">Date fin</label>
                  <div class="col-sm-10">
                    <input type="date" class="form-control" id="inputText" name="dateFin">
                  </div>
              </div>
              <div class="text-center">
                <button type="submit" class="btn btn-primary">Submit</button>
                <button type="reset" class="btn btn-secondary">Reset</button>
              </div>
          </form>
      </div>
      <div class="row">
        
        <div class="card">
           
            <% int compte = 0 ;
            for (Map.Entry<String, Commission[]> commission : commissions.entrySet()) { 
                String genre = commission.getKey();
                Commission[] listes = commission.getValue();
                %>
                <h5 class="card-title">Liste des commissions :  <%= genre %></h5>
                <div class="card-body">
                <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Vendeur</th>
                    <th scope="col">Genre</th>
                    <th scope="col">Commission</th>
                 
                  </tr>
                </thead>
                <tbody>
                  <% 
                    for(Commission c : listes){ %>
                          <tr>
                            <th scope="row"><%= c.getVendeur().getId() %></th>
                            <td><%= c.getVendeur().getNom() %></td>
                            <td><%= c.getVendeur().getGenre().getNom() %></td>
                            <td><%= c.getCommission() %></td>
                          </tr>
                    <% } %>
                 
                </tbody>
                <tfoot>
                    <td></td>
                    <td></td>
                    <td>Somme </td>
                    <td><%= sommes[compte] %></td>
                </tfoot>
              </table>
              <!-- End Table with hoverable rows -->
            <% compte++; }  %>
              

            </div>
        </div>
      </div>
    </section>

  </main><!-- End #main -->
  
<%@ include file="../templates/footer.jsp" %>
