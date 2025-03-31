<%@ include file="../templates/header.jsp" %>


  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">Sortie Caisse</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
   
      <div class="row">
        
        <div class="card">
            <h5 class="card-title">Sortie Caisse</h5>
            <div class="card-body">
                <form action="">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Date min</label>
                        <div class="col-sm-10">
                          <input type="date" class="form-control" id="inputText" name="dateMin" default="2024-12-27">
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
            <div class="card-body mt-3">
              <!-- Table with hoverable rows -->
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Date</th>
                    <th scope="col">Description</th>
                    <th scope="col">Valeur</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">1</th>
                    <td>12-12-2024</td>
                    <td>Vente produit</td>
                    <td>1 000 000 Ar</td>
                  </tr>
                  <tr>
                    <th scope="row">2</th>
                    <td>12-12-2024</td>
                    <td>Vente materiaux non utilisé</td>
                    <td>1 000 000 Ar</td>
                  </tr>
                  <tr>
                    <th scope="row">3</th>
                    <td>31-12-2024</td>
                    <td>Vente produit</td>
                    <td>1 000 000 Ar</td>
                  </tr>
                </tbody>
              </table>
              <!-- End Table with hoverable rows -->

            </div>
        </div>
      </div>
    </section>

  </main><!-- End #main -->


  <%@ include file="../templates/footer.jsp" %>
