<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>

  <body>
  
  <jsp:include page="theme-loader.jsp"></jsp:include>

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
                                        <div class="row">
                                            
                                            <div class="col-xl-3 col-md-6">
                                            <div class="card">
                                                <div class="card-block">
                                                    <div class="row align-items-center">
                                                        <div class="col-8">
                                                            <h4 class="text-c-purple">Cadastro de Usuários</h4>
                                                        </div>
                                                        <div class="col-4 text-right">
                                                            <i class="fa fa-calendar-check-o f-28"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="card-footer bg-c-purple">
                                                    <div class="row align-items-center">
                                                        <div class="col-9">
                                                            <a href="<%= request.getContextPath() %>/ServletUsuarioController?acao=listarUser" class="button-cards">Cadastrar</a>
                                                        </div>
                                                        <div class="col-3 text-right">
                                                            <i class="fa fa-calendar-check-o f-16"></i>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                        
                                        <div class="col-xl-3 col-md-6">
                                            <div class="card">
                                                <div class="card-block">
                                                    <div class="row align-items-center">
                                                        <div class="col-8">
                                                            <h4 class="text-c-green">Relatório de Usuários</h4>
                                                        </div>
                                                        <div class="col-4 text-right">
                                                            <i class="fa fa-file-text-o f-28"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="card-footer bg-c-green">
                                                    <div class="row align-items-center">
                                                        <div class="col-9">
                                                            <a href="<%=request.getContextPath()%>/principal/relUser.jsp" class="button-cards">Gerar relatório de usuários</a>
                                                        </div>
                                                        <div class="col-3 text-right">
                                                            <i class="fa fa-file-text-o f-16"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xl-3 col-md-6">
                                            <div class="card">
                                                <div class="card-block">
                                                    <div class="row align-items-center">
                                                        <div class="col-8">
                                                            <h4 class="text-c-red">Gráfico de Cargos</h4>
                                                        </div>
                                                        <div class="col-4 text-right">
                                                            <i class="fa fa-bar-chart f-28"></i>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="card-footer bg-c-red">
                                                    <div class="row align-items-center">
                                                        <div class="col-9">
                                                            <a href="" class="button-cards">Gerar gráfico de cargos</a>
                                                        </div>
                                                        <div class="col-3 text-right">
                                                            <i class="fa fa-bar-chart-o f-16"></i>
                                                        </div>
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
   </div>
    
    <jsp:include page="javascriptfile.jsp"></jsp:include>
    
</body>

</html>
    