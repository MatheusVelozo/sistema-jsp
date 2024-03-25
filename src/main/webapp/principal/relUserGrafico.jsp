<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

  <body>
  
  <jsp:include page="theme-loader.jsp"></jsp:include>
  
  <!-- Pre-loader end -->
  <div id="pcoded" class="pcoded">
      <div class="pcoded-overlay-box"></div>
      <div class="pcoded-container navbar-wrapper">
          
          <jsp:include page="navbar.jsp"></jsp:include>

          <div class="pcoded-main-container">
              <div class="pcoded-wrapper">
                  
                  <jsp:include page="navbarmainmenu.jsp"></jsp:include>
                  
                  <div class="pcoded-content">
                      <!-- Page-header start -->
                      
                      <jsp:include page="page-head.jsp"></jsp:include>
                      
                      <!-- Page-header end -->
                        <div class="pcoded-inner-content">
                            <!-- Main-body start -->
                            <div class="main-body">
                                <div class="page-wrapper">
                                    <!-- Page-body start -->
                                    <div class="page-body">

                                        <div class="page-body">
                                            <div class="row">
                                                <div class="col-md-10">
                                                    <div class="card">
                                                        <div class="card-header">
                                                            <h5>Relatório de Usuário</h5>
                                                            <!--<span>Add class of <code>.form-control</code> with <code>&lt;input&gt;</code> tag</span>-->
                                                        </div>

                                                        <form class="form-material"
                                                              action="<%=request.getContextPath()%>/ServletUsuarioController"
                                                              method="get" id="formUser">
                                                            <input type="hidden" id="acaoRelatorioImprimirTipo" name="acao" value="imprimirRelatorioUser">

                                                            <div class="form-row align-items-center" style="margin-top: 20px">

                                                                <div class="col-sm-3" style="margin-left: 10px">
                                                                    <label class="sr-only" for="dataInicial">Data Inicial:</label>
                                                                    <input value="${dataInicial}" type="date" class="form-control" id="dataInicial" name="dataInicial">
                                                                </div>

                                                                <div class="col-sm-3">
                                                                    <label class="sr-only" for="dataFinal">Data Final</label>
                                                                        <input value="${dataFinal}" type="date" class="form-control" id="dataFinal" name="dataFinal">
                                                                </div>

                                                                <div class="col-auto">
                                                                    <button type="button" onclick="gerarGrafico();" class="btn btn-primary">Gerar Gráfico</button>
                                                                </div>

                                                            </div>

                                                        </form>

                                                        <div style="height: 500px">

                                                            <div>
                                                                <canvas id="myChart"></canvas>
                                                            </div>


                                                        </div>

                                                        <div class="card-block">

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    <!-- Page-body end -->
                                </div>
                                <div id="styleSelector"> </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="javascriptfile.jsp"></jsp:include>

      <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

      <script type="text/javascript">
      
      var myChart;

          function gerarGrafico() {

              var urlAction = document.getElementById('formUser').action;
              var dataInicial = document.getElementById('dataInicial').value;
              var dataFinal = document.getElementById('dataFinal').value;

              $.ajax({

                  method : "get",
                  url : urlAction,
                  data : "dataInicial=" + dataInicial + '&dataFinal=' + dataFinal + '&acao=graficoPessoas',
                  success : function(response) {

                      var json = JSON.parse(response);
                      
                      if (myChart) {
                    	  myChart.data.labels = json.perfils;
                    	  myChart.data.datasets[0].data = json.totPessoas;
                    	  myChart.update();
                      } else {

                      myChart = new Chart(document.getElementById('myChart'),
                    	{
                          type: 'polarArea',
                          data: {
                              labels: json.perfils,
                              datasets: [{
                                  label: 'Gráfico de usuários',
                                  data: json.totPessoas,
                                  borderWidth: 1
                              }]
                          },
                          options: {
                              aspectRatio: 2,
                              scales: {
                                  y: {
                                      beginAtZero: true
                                  }
                              }
                          }
                      });
                     }
                  }

              }).fail(
                  function(xhr, status, errorThrown) {
                      alert('Erro ao buscar dados para o gráfico'
                          + xhr.responseText);
                  });

         }


      </script>
    
</body>

</html>
    