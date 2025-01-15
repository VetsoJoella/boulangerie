<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.produit.Produit"%>
<%@page import="com.model.production.conseil.*"%>

<% 
   
   String[] mois = (String[])request.getAttribute("mois") ;
    Produit[] produits = (Produit[]) request.getAttribute("produits");
    ConseilDuMois[] conseilDuMois =(ConseilDuMois[]) request.getAttribute("conseilDuMois");
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
          <li class="breadcrumb-item active">Meilleur conseil</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Conseil du mois</h5>
  
                <!-- Horizontal Form -->
                <form action="${pageContext.request.contextPath}/CRUD/conseilDuMois" method="post">
                  <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Ingredient</label>
                        <div class="col-sm-10">
                            <select id="inputState" class="form-select" name="idProduit">
                              <% for(Produit produit : produits) {  %>
                                <option value="<%= produit.getId() %>"><%= produit.getProduitBase().getNom() %>-<%= produit.getSaveur().getNom() %></option>
                              <% } %>
                            </select>                                  
                        </div>
                  </div>
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
                </form><!-- End Horizontal Form -->
  
              </div>
            </div>
        </div>
      </div>
      <div class="row">
        
        <div class="card">
            <h5 class="card-title">Liste des achats</h5>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/CRUD/conseilDuMois" method="get">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Mois</label>
                        <div class="col-sm-10">
                          <select id="inputState" class="form-select" name="mois">
                            <% for(int i = 0 ; i<mois.length ; i++) {  %>
                              <option value="<%= i+1 %>"><%= mois[i] %></option>
                            <% } %>
                          </select>                          
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Année</label>
                        <div class="col-sm-10">
                          <input type="number" class="form-control" id="inputText" name="annee" min="2020">
                        </div>
                    </div>
                   
                    <div class="text-center">
                        <button type="submit" class="btn btn-primary">Filtrer</button>
                        <button type="reset" class="btn btn-secondary">Reset</button>
                    </div>
                </form>
            </div>
            <div class="card-body">
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Produit</th>
                  </tr>
                </thead>
                <tbody>
                   <% if(conseilDuMois!=null && conseilDuMois.length>0) {
                    for(ConseilDuMois c : conseilDuMois){ %>
                          <tr>
                            <th scope="row"><%= c.getId() %></th>
                            <td><%= c.getProduit().getProduitBase().getNom() %>-<%= c.getProduit().getSaveur().getNom() %></td>
                           
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
