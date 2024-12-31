<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.ingredient.unite.Unite"%>
<%@page import="com.model.ingredient.Ingredient"%>

<% 
    Unite[] unites = (Unite[]) request.getAttribute("unites");
    Ingredient[] ingredients = (Ingredient[]) request.getAttribute("ingredients");

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
          <li class="breadcrumb-item active">CRUD Ingredient</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Ingredient</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/ingredient">
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputText" name="nom" placeholder="kg, litre, paquet, ...">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Unite</label>
                    <div class="col-sm-10">
                        <select id="inputState" class="form-select" name="idUnite">
                            <% for(Unite unite : unites) {  %>
                                <option value="<%= unite.getId() %>"><%= unite.getNom() %></option>
                            <% } %>
                        </select>                                  
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
              <h5 class="card-title">Liste des ingredients</h5>

              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nom</th>
                    <th scope="col">Unité</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                   <% if(ingredients!=null && ingredients.length>0) {
                    for(Ingredient ingredient : ingredients){ %>
                          <tr>
                            <th scope="row"><%= ingredient.getId() %></th>
                            <td><%= ingredient.getNom() %></td>
                            <td><%= ingredient.getUnite().getNom() %></td>
                            <td><a href="${pageContext.request.contextPath}/CRUD/ingredient?idIngredient=<%= ingredient.getId() %>"><i class="bi bi-trash" ></i></a></td>
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
