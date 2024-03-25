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
															<input type="hidden" id="acaoRelatorioImprimirTipo"
																name="acao" value="imprimirRelatorioUser">

															<div class="form-row align-items-center"
																style="margin-top: 20px">

																<div class="col-sm-3" style="margin-left: 10px">
																	<label class="sr-only" for="dataInicial">Data
																		Inicial:</label> <input value="${dataInicial}" type="date"
																		class="form-control" id="dataInicial"
																		name="dataInicial">
																</div>

																<div class="col-sm-3">
																	<label class="sr-only" for="dataFinal">Data
																		Final</label> <input value="${dataFinal}" type="date"
																		class="form-control" id="dataFinal" name="dataFinal">
																</div>

																<div class="col-auto">
																	<button type="button" onclick="imprimirHtml();"
																		class="btn btn-primary">Exibir Relatório</button>
																</div>

																<div class="col-auto">
																	<button type="button" onclick="imprimirPDF();"
																		class="btn btn-primary">Imprimir PDF</button>
																</div>

																<div class="col-auto">
																	<button type="button" onclick="imprimirExcel();"
																		class="btn btn-primary">Imprimir Excel</button>
																</div>
															</div>

														</form>

														<div style="height: 300px; overflow: scroll;">
															<table class="table" id="tabelaResultadosview">
																<thead>
																	<tr>
																		<th scope="col">ID:</th>
																		<th scope="col">Nome:</th>
																	</tr>
																</thead>
																<tbody>
																	<c:forEach items="${listaUser}" var="ml">

																		<tr>

																			<td><c:out value="${ml.id}"></c:out></td>
																			<td><c:out value="${ml.nome}"></c:out></td>
																		</tr>

																		<c:forEach items="${ml.telefones}" var="fone">
																			<tr>
																				<td style="font-size: 10px;">Telefone:</td>
																				<td style="font-size: 10px;"><c:out
																						value="${fone.numero}"></c:out></td>
																			</tr>
																		</c:forEach>

																	</c:forEach>
																</tbody>
															</table>
														</div>

														<div class="card-block"></div>
													</div>
												</div>
											</div>
										</div>

										<!-- Page-body end -->
									</div>
									<div id="styleSelector"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<script type="text/javascript">
		function imprimirHtml() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioUser';

			$("#formUser").submit();
		}

		function imprimirPDF() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioPDF';

			$("#formUser").submit();
			return false;
		}

		function imprimirExcel() {
			document.getElementById("acaoRelatorioImprimirTipo").value = 'imprimirRelatorioExcel';

			$("#formUser").submit();
			return false;
		}
	</script>

</body>

</html>
