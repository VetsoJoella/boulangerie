<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">

  <title>L'Atelier des Pains</title>
  <meta content="" name="description">
  <meta content="" name="keywords">

  <!-- Favicons -->
  <link href="${pageContext.request.contextPath}/assets/img/favicon.png" rel="icon">
  <link href="${pageContext.request.contextPath}/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

  <!-- Google Fonts -->
  <link href="${pageContext.request.contextPath}/assets/css/fonts.googleapis.css" rel="stylesheet">

  <!-- Vendor CSS Files -->
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">
  <!-- <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.snow.css" rel="stylesheet"> -->
  <!-- <link href="${pageContext.request.contextPath}/assets/vendor/quill/quill.bubble.css" rel="stylesheet"> -->
  <!-- <link href="${pageContext.request.contextPath}/assets/vendor/remixicon/remixicon.css" rel="stylesheet"> -->
  <!-- <link href="${pageContext.request.contextPath}/assets/vendor/simple-datatables/style.css" rel="stylesheet"> -->

  <!-- Template Main CSS File -->
  <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">

</head>

<body>

  <!-- ======= Header ======= -->
  <header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between">
      <a href="/index" class="logo d-flex align-items-center">
        <img src="#" alt="">
        <span class="d-none d-lg-block">L'Atelier des Pains</span>
      </a>
      <i class="bi bi-list toggle-sidebar-btn"></i>
    </div><!-- End Logo -->
  </header><!-- End Header -->

  <!-- ======= Sidebar ======= -->
  <aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

      <li class="nav-item">
        <a class="nav-link " href="${pageContext.request.contextPath}/index">
          <i class="bi bi-grid"></i>
          <span>Comptoir</span>
        </a>
      </li><!-- Titre -->
      <br>

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#components-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-pencil-square"></i><span>CRUD</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="components-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/unite">
              <i class="bi bi-circle"></i><span>Unité</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/ingredient">
              <i class="bi bi-circle"></i><span>Ingredients</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/produit">
              <i class="bi bi-circle"></i><span>Produit</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/fabrication">
              <i class="bi bi-circle"></i><span>Fabrication</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/recette">
              <i class="bi bi-circle"></i><span>Recette</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/achatIngredient">
              <i class="bi bi-circle"></i><span>Achat Ingredient</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/CRUD/vente">
              <i class="bi bi-circle"></i><span>Vente</span>
            </a>
          </li>
        </ul>
      </li><!-- End Components Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-server"></i><span>Etat de stock</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="forms-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${pageContext.request.contextPath}/stock/ingredient">
              <i class="bi bi-circle"></i><span>Ingredient</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/stock/produit">
              <i class="bi bi-circle"></i><span>Produit</span>
            </a>
          </li>
        </ul>
      </li><!-- End Forms Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#caisse-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-calculator"></i><span>Caisse</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="caisse-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${pageContext.request.contextPath}/caisse/sortie">
              <i class="bi bi-circle"></i><span>Sortie</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/caisse/entree">
              <i class="bi bi-circle"></i><span>Entree</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/caisse/etat">
              <i class="bi bi-circle"></i><span>Etat de caisse</span>
            </a>
          </li>
        </ul>
      </li><!-- End Tables Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-bar-chart"></i><span>Statistique et Résultat</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="charts-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${pageContext.request.contextPath}/stat/achat">
              <i class="bi bi-circle"></i><span>Achat - Vente</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/stat/produit">
              <i class="bi bi-circle"></i><span>Meilleur vente produit</span>
            </a>
          </li>
         
        </ul>
      </li><!-- End Charts Nav -->

      <li class="nav-item">
        <a class="nav-link collapsed" data-bs-target="#tables-nav" data-bs-toggle="collapse" href="#">
          <i class="bi bi-calendar-plus"></i><span>Prévision</span><i class="bi bi-chevron-down ms-auto"></i>
        </a>
        <ul id="tables-nav" class="nav-content collapse " data-bs-parent="#sidebar-nav">
          <li>
            <a href="${pageContext.request.contextPath}/index">
              <i class="bi bi-circle"></i><span>Production ~ Ingredient </span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/index">
              <i class="bi bi-circle"></i><span>Achat Ingredient</span>
            </a>
          </li>
          <li>
            <a href="${pageContext.request.contextPath}/prediction/production">
              <i class="bi bi-circle"></i><span>Vente produit</span>
            </a>
          </li>
        </ul>
      </li><!-- End Tables Nav -->

    </ul>

  </aside><!-- End Sidebar-->