<%@ include file="../templates/header.jsp" %>

<%@page import="com.model.produit.*"%>
<%@page import="com.model.produit.base.ProduitBase"%>
<%@page import="com.model.produit.saveur.Saveur"%>
<%@page import="com.model.produit.variete.Variete"%>


<% 

    Produit produit = (Produit) request.getAttribute("produit");
    Produit[] produits = (Produit[]) request.getAttribute("produits");
    HistoriqueProduit[] historiqueProduits = (HistoriqueProduit[]) request.getAttribute("historiqueProduits");
    // ProduitBase[] produitBases = (ProduitBase[]) request.getAttribute("produitBases");
    // Saveur[] saveurs = (Saveur[]) request.getAttribute("saveurs") ;
    // Variete[] varietes = (Variete[]) request.getAttribute("varietes") ;

  
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
                <h5 class="card-title">Historique Produit</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/historiqueProduit">
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                       <select id="inputState" class="form-select" name="idProduit">

                       <% if(produit !=null) { %>
                            <option value="<%= produit.getId() %>"><%= produit.getProduitBase().getNom() +"-"+produit.getSaveur().getNom() %></option>
                        <% } else { 
                            for(Produit p : produits ) { %>
                                <option value="<%= p.getId() %>"><%= p.getProduitBase().getNom() +"-"+p.getSaveur().getNom() %></option>

                            <% }

                        } %> 
                        
                        
                      </select>    
                    </div>
                  </div>
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Date de Début</label>
                    <div class="col-sm-10">
                      <input type="date" class="form-control" id="inputText" name="dateDebut" >
                    </div>
                  </div>

                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Date de Fin</label>
                    <div class="col-sm-10">
                      <input type="date" class="form-control" id="inputText" name="dateFin" >
                    </div>
                  </div>

                   <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nouveau Prix vente</label>
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
              <h5 class="card-title">Historique des prix produits</h5>
                <form action="${pageContext.request.contextPath}/CRUD/historiqueProduit" method="get">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Nom produit</label>
                        <div class="col-sm-10">
                           <select id="inputState" class="form-select" name="idProduit">
                             <% for(Produit p : produits) {  %>
                                <option value="<%= p.getId() %>"><%= p.getProduitBase().getNom() +"-"+p.getSaveur().getNom() %></option>
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
                    <th scope="col">Produit</th>
                    <th scope="col">Prix</th>
                    <th scope="col">Date Début</th>
                    <th scope="col">Date Fin</th>
                  </tr>
                </thead>
                <tbody>
                   <% if(historiqueProduits!=null && historiqueProduits.length>0) {
                    for(HistoriqueProduit historiqueProduit : historiqueProduits){ %>
                          <tr>
                            <th scope="row"><%= historiqueProduit.getId() %></th>
                            <td><%= historiqueProduit.getProduit().getProduitBase().getNom() %> - <%=  historiqueProduit.getProduit().getSaveur().getNom() %></td>
                            <td><%= historiqueProduit.getPrix() %></td>
                            <td><%= historiqueProduit.getDateDebut() %></td>
                            <td><%= historiqueProduit.getDateFin() %></td>
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
