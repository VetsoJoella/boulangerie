<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.produit.Produit"%>
<%@page import="com.model.produit.variete.Variete"%>
<%@page import="com.model.ingredient.Ingredient"%>
<%@page import="com.model.production.fabrication.Fabrication"%>

<% 
    Produit[] produits = (Produit[]) request.getAttribute("produits") ;
    Fabrication[] fabrications = (Fabrication[]) request.getAttribute("fabrications");
    Variete[] varietes = (Variete[]) request.getAttribute("varietes");
    Ingredient[] ingredients = (Ingredient[]) request.getAttribute("ingredients");


%>


  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">CRUD Fabrication</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Fabrication</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/fabrication">
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
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Quantité</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="quantite" placeholder="8">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Date fabrication</label>
                    <div class="col-sm-10">
                      <input type="date" class="form-control" id="inputText" name="date">
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
            <h5 class="card-title">Liste des fabrications</h5>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/CRUD/fabrication" method="get">
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
                    <th scope="col">Date fabrication</th>
                    <th scope="col">Produit</th>
                    <th scope="col">Quantité</th>
                  </tr>
                </thead>
                <tbody>
                    <% if(fabrications!=null && fabrications.length>0) {
                    for(Fabrication fabrication : fabrications){ %>
                          <tr>
                            <th scope="row"><%= fabrication.getId() %></th>
                            <td><%= fabrication.getDate() %></td>
                            <td><%= fabrication.getProduit().getProduitBase().getNom() %>-<%= fabrication.getProduit().getSaveur().getNom() %></td>
                            <td><%= fabrication.getQuantite() %></td>
                          </tr>
                    <% } 
                   } %>
                </tbody>
              </table>
              <!-- End Table with hoverable rows -->

            </div>
          </div>
      </div>
    </section>

  </main><!-- End #main -->


<%@ include file="../templates/footer.jsp" %>
