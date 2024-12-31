<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.produit.Produit"%>

<% 
    Produit[] produits = (Produit[]) request.getAttribute("produits");

    String id = "", nom = "";
    double prix = 0 ;

    if(request.getAttribute("id")!=null) id = (String)request.getAttribute("id");
    if(request.getAttribute("nom")!=null) nom = (String)request.getAttribute("nom");
    if(request.getAttribute("prix")!=null) prix = (Double)request.getAttribute("prix");

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
                      <input type="hidden" class="form-control" id="inputText" name="id" placeholder="kg, litre, paquet, ..." value="<%= id %>">
                      <input type="text" class="form-control" id="inputText" name="nom" placeholder="kg, litre, paquet, ..." value="<%= nom %>">
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Prix vente</label>
                    <div class="col-sm-10">
                      <input type="number" class="form-control" id="inputText" name="prix" placeholder="8 000" value="<%= prix %>">
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
              <h5 class="card-title">Liste des produit</h5>

              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nom</th>
                    <th scope="col">prix unitaire</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                   <% if(produits!=null && produits.length>0) {
                    for(Produit produit : produits){ %>
                          <tr>
                            <th scope="row"><%= produit.getId() %></th>
                            <td><%= produit.getNom() %></td>
                            <td><%= produit.getPrixVente() %></td>
                            <td><a href="${pageContext.request.contextPath}/CRUD/produit?idProduit=<%= produit.getId() %>"><i class="bi bi-trash" ></i></a></td>
                            <td><a href="${pageContext.request.contextPath}/CRUD/produit?action=u&&idProduit=<%= produit.getId() %>"><i class="bi bi-pencil" ></i></a></td>
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
