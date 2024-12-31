<%@ include file="../templates/header.jsp" %>


  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">Prediction de produit</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
        <div class="row">
            <div class="col-lg">
                <h5 class="card-title">Prédiction possible avec les restes des ingredients</h5>
                <div class="card-body mt-3">
                    <!-- Table with hoverable rows -->
                    <table class="table table-hover">
                      <thead>
                        <tr>
                          <th scope="col">#</th>
                          <th scope="col">Produit</th>
                          <th scope="col">Nombre</th>
                          <th scope="col">Estimation (Ar)</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <th scope="row">1</th>
                          <td>Pain au raisin</td>
                          <td>30</td>
                          <td>100 0000 Ar</td>
                        </tr>
                        <tr>
                          <th scope="row">2</th>
                          <td>Pain au chocolat</td>
                          <td>100</td>
                          <td>200 000 Ar</td>
                        </tr>
                        <tr>
                          <th scope="row">3</th>
                          <td>Pain complet</td>
                          <td>50</td>
                          <td>100 000 Ar</td>
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
