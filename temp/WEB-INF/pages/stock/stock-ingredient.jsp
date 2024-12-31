<%@ include file="../templates/header.jsp" %>


  <main id="main" class="main">

    <div class="pagetitle">
      <h1>Comptoir</h1>
      <nav>
        <ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/index">Accueil</a></li>
          <li class="breadcrumb-item active">Stock Ingredient</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
   
      <div class="row">
        
        <div class="card">
            <h5 class="card-title">Stock Ingredient</h5>
            <div class="card-body">
                <form action="">
                    <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Ingredient</label>
                        <div class="col-sm-10">
                            <select id="inputState" class="form-select" name="ingredient">
                                <option>Farine</option>
                                <option>Huile</option>
                                <option>Sucre</option>
                            </select>                                  
                        </div>
                    </div>
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
                    <th scope="col">Ingredient</th>
                    <th scope="col">Quantité</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th scope="row">1</th>
                    <td>Farine</td>
                    <td>80 kg</td>
                  </tr>
                  <tr>
                    <th scope="row">2</th>
                    <td>Sucre</td>
                    <td>7 kg</td>
                  </tr>
                  <tr>
                    <th scope="row">3</th>
                    <td>Levure</td>
                    <td>80 kg</td>
                  </tr>
                  <tr>
                    <th scope="row">4</th>
                    <td>Huile</td>
                    <td>7 l</td>
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
