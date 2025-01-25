<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.production.vente.Vente"%>
<%@page import="com.model.production.vente.commission.Commission"%>


<% 
    Commission[] commissions = (Commission[]) request.getAttribute("commissions") ;

    if (request.getAttribute("message") != null) { %>
      <script type="text/javascript">
          alert('<%= request.getAttribute("message").toString().replace("'", "\\'") %>');
      </script>
  <% } 
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
          <form method="get" action="${pageContext.request.contextPath}/CRUD/commission">
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
            <h5 class="card-title">Liste des commissions</h5>
            <div class="card-body">
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Vendeur</th>
                    <th scope="col">Commission</th>
                 
                  </tr>
                </thead>
                <tbody>
                  <% if(commissions!=null && commissions.length>0) {
                    for(Commission commission : commissions){ %>
                          <tr>
                            <th scope="row"><%= commission.getVendeur().getId() %></th>
                            <td><%= commission.getVendeur().getNom() %></td>
                            <td><%= commission.getCommission() %></td>
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
