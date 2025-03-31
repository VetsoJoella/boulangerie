<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.ingredient.Ingredient"%>
<%@page import="com.model.ingredient.AchatIngredient"%>

<% 
    Ingredient[] ingredients = (Ingredient[]) request.getAttribute("ingredients");
    AchatIngredient[] achats = (AchatIngredient[]) request.getAttribute("achats");
%>
  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">CRUD Achat</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Achat Ingredient</h5>
  
                <!-- Horizontal Form -->
                <form action="${pageContext.request.contextPath}/CRUD/achatIngredient" method="post">
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
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Date achat</label>
                    <div class="col-sm-10">
                      <input type="date" class="form-control" id="inputText" name="date">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Quantité achetée</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="quantite" placeholder="8">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Prix unitaire</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="prix" placeholder="8 000">
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
                <form action="${pageContext.request.contextPath}/CRUD/achatIngredient" method="get">
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
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Date min</label>
                        <div class="col-sm-10">
                          <input type="date" class="form-control" id="inputText" name="dateMin">
                        </div>
                    </div>
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Date max</label>
                        <div class="col-sm-10">
                          <input type="date" class="form-control" id="inputText" name="dateMax" default="2024-12-27">
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
                    <th scope="col">Date achat</th>
                    <th scope="col">Ingredient</th>
                    <th scope="col">Quantité</th>
                    <th scope="col">Prix Achat</th>
                  </tr>
                </thead>
                <tbody>
                   <% if(achats!=null && achats.length>0) {
                    for(AchatIngredient achat : achats){ %>
                          <tr>
                            <th scope="row"><%= achat.getId() %></th>
                            <td><%= achat.getDate() %></td>
                            <td><%= achat.getIngredient().getNom() %></td>
                            <td><%= achat.getQuantite() %></td>
                            <td><%= achat.getPrixUnitaire() %></td>
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
