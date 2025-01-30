<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.produit.variete.Variete"%>

<% 
    Variete[] varietes = (Variete[]) request.getAttribute("varietes");

  
%>

  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/variete">Accueil</a></li>
          <li class="breadcrumb-item active">CRUD Variété</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
      <div class="row">
        <div class="col-lg">

            <div class="card">
              <div class="card-body">
                <h5 class="card-title">Variété</h5>
  
                <!-- Horizontal Form -->
                <form method="post" action="${pageContext.request.contextPath}/CRUD/variete">
                  <div class="row mb-3">
                    <label for="inputEmail3" class="col-sm-2 col-form-label">Nom</label>
                    <div class="col-sm-10">
                      <input type="text" class="form-control" id="inputText" name="nom" placeholder="pain, viennoiserie, ...">
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
              <h5 class="card-title">Liste des variétés</h5>
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nom</th>
                  </tr>
                </thead>
                <tbody>
                  <% if(varietes!=null && varietes.length>0) {
                    for(Variete variete : varietes){ %>
                          <tr>
                            <th scope="row"><%= variete.getId() %></th>
                            <td><%= variete.getNom() %></td>
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
