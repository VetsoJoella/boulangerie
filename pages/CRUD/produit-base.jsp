<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.produit.base.ProduitBase"%>
<%@page import="com.model.produit.variete.Variete"%>

<% 
    ProduitBase[] produits = (ProduitBase[]) request.getAttribute("produits");
    Variete[] varietes = (Variete[])request.getAttribute("varietes");

 
%>
  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">CRUD Produit base</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Produit base</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/produitBase">
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputText" name="nom" placeholder="Pain batard, baguette" >
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Variete</label>
                    <div class="col-sm-10">
                      <select id="inputState" class="form-select" name="idVariete">
                        <% for(Variete type : varietes ) {  %>
                          <option value="<%= type.getId() %>"><%= type.getNom() %></option>
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
              <h5 class="card-title">Liste des produit</h5>
                <form action="${pageContext.request.contextPath}/CRUD/produitBase" method="get">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Variete</label>
                        <div class="col-sm-10">
                           <select id="inputState" class="form-select" name="idVariete">
                              <option></option>

                             <% for(Variete type : varietes) {  %>
                                <option value="<%= type.getId() %>"><%= type.getNom() %></option>
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
                    <th scope="col">Variété</th>
                    <th scope="col"></th>
                  </tr>
                </thead>
                <tbody>
                   <% if(produits!=null && produits.length>0) {
                    for(ProduitBase produit : produits){ %>
                          <tr>
                            <th scope="row"><%= produit.getId() %></th>
                            <td><%= produit.getNom() %></td>
                            <td><%= produit.getVariete().getNom() %></td>
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
