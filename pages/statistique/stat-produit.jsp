<%@ include file="../templates/header.jsp" %>
<%@page import="com.model.production.vente.Vente"%>
<% 
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
          <li class="breadcrumb-item active">Statistique Achat</li>
        </ol>
      </nav>
    </div><!-- End Page Title -->

    <section class="section dashboard">
        <div class="row">
            <div class="col-lg">
    
                <div class="card">
                  <div class="card-body">
                    <h5 class="card-title">Résultat des Achats - Ventes</h5>
      
                    <!-- Horizontal Form -->
                    <form action="${pageContext.request.contextPath}/stat/produit" method="post">
                      <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Date Min</label>
                        <div class="col-sm-10">
                          <input type="date" class="form-control" id="inputText" name="dateMin">
                        </div>
                      </div>
                      <div class="row mb-3">
                        <label for="inputEmail3" class="col-sm-2 col-form-label">Date Max</label>
                        <div class="col-sm-10">
                          <input type="date" class="form-control" id="inputText" name="dateMax">
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
          <!-- Reports -->
          <div class="col-12">
            <div class="card">

              <div class="filter">
                <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                  <li class="dropdown-header text-start">
                    <h6>Filter</h6>
                  </li>

                  <li><a class="dropdown-item" href="#">Today</a></li>
                  <li><a class="dropdown-item" href="#">This Month</a></li>
                  <li><a class="dropdown-item" href="#">This Year</a></li>
                </ul>
              </div>
            <div class="card-body pb-0">
              <h5 class="card-title">Website Traffic <span>| Today</span></h5>

              <div id="trafficChart" style="min-height: 400px;" class="echart"></div>
              <script>
                document.addEventListener("DOMContentLoaded", () => {
                  echarts.init(document.querySelector("#trafficChart")).setOption({
                    tooltip: {
                      trigger: 'item'
                    },
                    legend: {
                      top: '5%',
                      left: 'center'
                    },
                    series: [{
                      name: 'Rapport vente',
                      type: 'pie',
                      radius: ['40%', '70%'],
                      avoidLabelOverlap: false,
                      label: {
                        show: false,
                        position: 'center'
                      },
                      emphasis: {
                        label: {
                          show: true,
                          fontSize: '18',
                          fontWeight: 'bold'
                        }
                      },
                      labelLine: {
                        show: false
                      },
                      data: [
                        <% 
                          if (request.getAttribute("ventes") != null) {
                            Vente[] ventes = (Vente[]) request.getAttribute("ventes");
                            for (Vente vente : ventes) { %>
                              { value: <%= vente.getQuantite() %>, name: '<%= vente.getProduit().getNom() %>' },
                            <% }
                          } 
                        %>
                      ]
                    }]
                  });
                });
              </script>


            </div>

            </div>
          </div><!-- End Reports -->
       <!-- Left side columns -->
       <div class="col-lg-12">
        <div class="row">

          <!-- Sales Card -->
          <div class="col-xxl-4 col-md-6">
            <div class="card info-card sales-card">

              <div class="filter">
                <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                  <li class="dropdown-header text-start">
                    <h6>Filtre</h6>
                  </li>

                  <li><a class="dropdown-item" href="#">Aujourd'hui</a></li>
                  <li><a class="dropdown-item" href="#">Ce mois</a></li>
                  <li><a class="dropdown-item" href="#">Cette année</a></li>
                </ul>
              </div>

              <div class="card-body">
                <h5 class="card-title">Ventes <span>| Aujourd'hui</span></h5>

                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-cart"></i>
                  </div>
                  <div class="ps-3">
                    <h6>145</h6>
                    <span class="text-success small pt-1 fw-bold">12%</span> <span class="text-muted small pt-2 ps-1">augmentation</span>

                  </div>
                </div>
              </div>

            </div>
          </div><!-- End Sales Card -->

          <!-- Revenue Card -->
          <div class="col-xxl-4 col-md-6">
            <div class="card info-card revenue-card">

              <div class="filter">
                <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                  <li class="dropdown-header text-start">
                    <h6>Filtre</h6>
                  </li>

                  <li><a class="dropdown-item" href="#">Aujourd'hui</a></li>
                  <li><a class="dropdown-item" href="#">Ce mois</a></li>
                  <li><a class="dropdown-item" href="#">Cette année</a></li>
                </ul>
              </div>

              <div class="card-body">
                <h5 class="card-title">Revenue <span>| Ce mois</span></h5>

                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-currency-dollar"></i>
                  </div>
                  <div class="ps-3">
                    <h6>$3,264</h6>
                    <span class="text-success small pt-1 fw-bold">8%</span> <span class="text-muted small pt-2 ps-1">augmentation</span>

                  </div>
                </div>
              </div>

            </div>
          </div><!-- End Revenue Card -->
        
          <!-- Customers Card -->
          <div class="col-xxl-4 col-xl-12">

            <div class="card info-card customers-card">

              <div class="filter">
                <a class="icon" href="#" data-bs-toggle="dropdown"><i class="bi bi-three-dots"></i></a>
                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow">
                  <li class="dropdown-header text-start">
                    <h6>Filtre</h6>
                  </li>

                  <li><a class="dropdown-item" href="#">Aujourd'hui</a></li>
                  <li><a class="dropdown-item" href="#">Ce mois</a></li>
                  <li><a class="dropdown-item" href="#">Cette année</a></li>
                </ul>
              </div>

              <div class="card-body">
                <h5 class="card-title">Clients <span>| Cette année</span></h5>

                <div class="d-flex align-items-center">
                  <div class="card-icon rounded-circle d-flex align-items-center justify-content-center">
                    <i class="bi bi-people"></i>
                  </div>
                  <div class="ps-3">
                    <h6>1244</h6>
                    <span class="text-danger small pt-1 fw-bold">12%</span> <span class="text-muted small pt-2 ps-1">diminution client</span>

                  </div>
                </div>

              </div>
            </div>

          </div><!-- End Customers Card -->

        
        </div>
      </div><!-- End Left side columns -->
    </section>

  </main><!-- End #main -->


<%@ include file="../templates/footer.jsp" %>
