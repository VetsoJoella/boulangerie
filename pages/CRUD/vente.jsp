<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.produit.Produit"%>
<%@page import="com.model.production.vente.Vente"%>
<%@page import="com.model.produit.variete.Variete"%>
<%@page import="com.model.produit.saveur.Saveur"%>

<% 
    Produit[] produits = (Produit[]) request.getAttribute("produits");
    Variete[] varietes = (Variete[]) request.getAttribute("varietes");
    Saveur[] saveurs = (Saveur[]) request.getAttribute("saveurs");
    Vente[] ventes = (Vente[]) request.getAttribute("ventes") ;

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
          <li class="breadcrumb-item active">CRUD Vente</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Vente des Produits</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/vente">
                  <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Produit</label>
                        <div class="col-sm-10">
                            <select id="inputState" class="form-select" name="idProduit">
                              <% for(Produit produit : produits) {  %>
                                <option value="<%= produit.getId() %>"><%= produit.getProduitBase().getNom() %>- <%= produit.getSaveur().getNom() %> </option>
                              <% } %>
                            </select>                                  
                        </div>
                      </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Quantité</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="quantite" placeholder="8">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Date vente</label>
                    <div class="col-sm-10">
                      <input type="date" class="form-control" id="inputText" name="date" default="2024-12-27">
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
            <h5 class="card-title">Liste des ventes</h5>
            <div class="card-body">
                <form method="get" action="${pageContext.request.contextPath}/CRUD/vente">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Variete</label>
                        <div class="col-sm-10">
                             <select id="inputState" class="form-select" name="idVariete">
                                <option></option>
                              <% for(Variete variete : varietes) {  %>
                                <option value="<%= variete.getId() %>"><%= variete.getNom() %></option>
                              <% } %>
                            </select>                                      
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Saveur</label>
                        <div class="col-sm-10">
                             <select id="inputState" class="form-select" name="idSaveur">
                                <option></option>
                              <% for(Saveur saveur : saveurs) {  %>
                                <option value="<%= saveur.getId() %>"><%= saveur.getNom() %></option>
                              <% } %>
                            </select>                                      
                        </div>
                    </div>
                    <div class="text-center">
                      <button type="submit" class="btn btn-primary">Submit</button>
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
                    <th scope="col">Date vente</th>
                    <th scope="col">Produit</th>
                    <th scope="col">Quantité</th>
                  </tr>
                </thead>
                <tbody>
                  <% if(ventes!=null && ventes.length>0) {
                    for(Vente vente : ventes){ %>
                          <tr>
                            <th scope="row"><%= vente.getId() %></th>
                            <td><%= vente.getDate() %></td>
                            <td><%= vente.getProduit().getProduitBase().getNom() %>-<%= vente.getProduit().getSaveur().getNom() %></td>
                            <td><%= vente.getQuantite() %></td>
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
