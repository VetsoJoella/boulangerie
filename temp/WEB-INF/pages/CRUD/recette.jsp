<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.ingredient.Ingredient"%>
<%@page import="com.model.produit.Produit"%>
<%@page import="com.model.produit.recette.Recette"%>
<%@page import="com.model.produit.variete.Variete"%>
<%@page import="com.model.produit.base.ProduitBase"%>

<% 
    Ingredient[] ingredients = (Ingredient[]) request.getAttribute("ingredients");
    Produit[] produits = (Produit[]) request.getAttribute("produits") ;
    ProduitBase[] produitBases = (ProduitBase[]) request.getAttribute("produitBases") ;
    Recette[] recettes = (Recette[]) request.getAttribute("recettes") ;
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
          <li class="breadcrumb-item active">CRUD Recette</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Recette</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/recette">
                  <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Produit</label>
                        <div class="col-sm-10">
                            <select id="inputState" class="form-select" name="idProduit">
                              <% for(Produit produit : produits) {  %>
                                <option value="<%= produit.getId() %>"><%= produit.getProduitBase().getNom() %>-<%= produit.getSaveur().getNom() %></option>
                              <% } %>
                            </select>                                  
                        </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Ingredient</label>
                    <div class="col-sm-10">
                        <select id="inputState" class="form-select" name="idIngredient">
                             <% for(Ingredient ingredient : ingredients) {  %>
                                <option value="<%= ingredient.getId() %>"><%= ingredient.getNom() %></option>
                            <% } %>
                        </select>                                  
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Quantité</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputText" name="quantite" placeholder="1 000">
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
            <h5 class="card-title">Liste des Recettes</h5>

            <div class="card-body">
                <form action="${pageContext.request.contextPath}/CRUD/recette" method="get">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Produit</label>
                        <div class="col-sm-10">
                             <select id="inputState" class="form-select" name="idProduit">
                                <option></option>
                              <% for(Produit produit : produits) {  %>
                                <option value="<%= produit.getId() %>"><%= produit.getProduitBase().getNom() %>-<%= produit.getSaveur().getNom() %></option>
                              <% } %>
                            </select>                                   
                        </div>
                   </div>
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Produit Base</label>
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
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Ingredient</label>
                        <div class="col-sm-10">
                           <select id="inputState" class="form-select" name="idIngredient">
                              <option></option>

                             <% for(Ingredient ingredient : ingredients) {  %>
                                <option value="<%= ingredient.getId() %>"><%= ingredient.getNom() %></option>
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
                    <th scope="col">Ingredient</th>
                    <th scope="col">Quantité</th>
                  </tr>
                </thead>
                <tbody>
                  <% if(recettes!=null && recettes.length>0) {
                    for(Recette recette : recettes){ %>
                          <tr>
                            <th scope="row"><%= recette.getId() %></th>
                            <td><%= recette.getProduit().getProduitBase().getNom() %>-<%= recette.getProduit().getSaveur().getNom() %></td>
                            <td><%= recette.getIngredient().getNom() %></td>
                            <td><%= recette.getQuantite() %></td>
                            <td><a href="#"><i class="bi bi-trash" ></i></a></td>
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
