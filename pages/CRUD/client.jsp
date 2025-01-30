<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.production.vente.Vente"%>
<%@page import="com.model.client.Client"%>

<% 
    Vente[] ventes = (Vente[]) request.getAttribute("ventes") ;

%>

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">CRUD Vente</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <h5 class="card-title">Liste des clients</h5>
      <div class="card-body">
          <form method="get" action="${pageContext.request.contextPath}/CRUD/client">
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
            <h5 class="card-title">Liste des ventes</h5>
            </div>
            <div class="card-body">
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Client</th>
                 
                  </tr>
                </thead>
                <tbody>
                  <% if(ventes!=null && ventes.length>0) {
                    for(Vente vente : ventes){ %>
                          <tr>
                            <th scope="row"><%= vente.getClient().getId() %></th>
                            <td><%= vente.getClient().getNom() %></td>
                          </tr>
                    <% } %>
                  <% } %>
                </tbody>
              </table>
              <!-- End Table with hoverable rows -->

            </div>
          </div>
      </div>
    </section>

  </main><!-- End #main -->
  
<%@ include file="../templates/footer.jsp" %>
