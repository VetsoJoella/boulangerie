<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.produit.Produit"%>
<%@page import="com.model.produit.base.ProduitBase"%>
<%@page import="com.model.produit.saveur.Saveur"%>
<%@page import="com.model.produit.variete.Variete"%>


<% 

    Produit[] produits = (Produit[]) request.getAttribute("produits");
    ProduitBase[] produitBases = (ProduitBase[]) request.getAttribute("produitBases");
    Saveur[] saveurs = (Saveur[]) request.getAttribute("saveurs") ;
    Variete[] varietes = (Variete[]) request.getAttribute("varietes") ;

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
          <li class="breadcrumb-item active">CRUD Produit</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Produit</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/produit">
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                       <select id="inputState" class="form-select" name="idProduitBase">
                        <% for(ProduitBase produitBase : produitBases ) {  %>
                          <option value="<%= produitBase.getId() %>"><%= produitBase.getNom() %></option>
                        <% } %>
                      </select>    
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Saveur</label>
                    <div class="col-sm-10">
                      <select id="inputState" class="form-select" name="idSaveur">
                        <% for(Saveur saveur : saveurs ) {  %>
                          <option value="<%= saveur.getId() %>"><%= saveur.getNom() %></option>
                        <% } %>
                      </select>                      
                    </div>
                  </div>
                  
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Prix vente</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="prix" >
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
            <div class="card-body">
              <h5 class="card-title">Liste des produits</h5>
                <form action="${pageContext.request.contextPath}/CRUD/produit" method="get">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Nom produit</label>
                        <div class="col-sm-10">
                           <select id="inputState" class="form-select" name="idProduitBase">
                              <option></option>

                             <% for(ProduitBase produitBase : produitBases) {  %>
                                <option value="<%= produitBase.getId() %>"><%= produitBase.getNom() %></option>
                            <% } %>
                          </select>                                 
                        </div>
                    </div>
                     <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Variété</label>
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
                        <button type="submit" class="btn btn-primary">Filtrer</button>
                        <button type="reset" class="btn btn-secondary">Reset</button>
                    </div>
                </form>
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Prix</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                   <% if(produits!=null && produits.length>0) {
                    for(Produit produit : produits){ %>
                          <tr>
                            <th scope="row"><%= produit.getId() %></th>
                            <td><%= produit.getProduitBase().getNom() %> - <%=  produit.getSaveur().getNom() %></td>
                            <td><%= produit.getPrixVente() %></td>
                            <td><a href="${pageContext.request.contextPath}/CRUD/historiqueProduit?idProduit=<%= produit.getId() %>">Modifier</a></td>
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
