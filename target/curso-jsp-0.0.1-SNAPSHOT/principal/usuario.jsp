<%@page import="model.ModelLogin"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
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
										<div class="row">
											<div class="col-md-10">
												<div class="card">
													<div class="card-header">
														<h5>Cadastro de Usuário</h5>
														<!--<span>Add class of <code>.form-control</code> with <code>&lt;input&gt;</code> tag</span>-->
													</div>

													<div class="card-block">
														<form class="form-material" enctype="multipart/form-data"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="formUser">

															<input type="hidden" name="acao" id="acao" value="">

															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" readonly="readonly"
																	value="${modelLogin.id}"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>
															
															<div class="form-group form-default input-group mb-4">
																	<div class="input-group-prepend">
																	<c:if test="${modelLogin.fotouser != '' && modelLogin.fotouser != null}">
																		<a href="<%= request.getContextPath()%>/ServletUsuarioController?acao=downloadFoto&id=${modelLogin.id}">
																		<img alt="imagem User" id="fotoembase64" src="${modelLogin.fotouser}" width="70px">
																		</a>
																	</c:if>
																	<c:if test="${modelLogin.fotouser == '' || modelLogin.fotouser == null}">
																		<img alt="imagem User" id="fotoembase64" src="assets/images/user-icon.png" width="70px">
																	</c:if>
																		
																	</div>
																	<input type="file" id="fileFoto" name="fileFoto" accept="image/*" onchange="visualizarImg('fotoembase64', 'fileFoto');" class="form-control-file" style="margin-top: 25px; margin-left: 5px;">
																	
																	
															</div>
															<div class="form-group form-default">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${modelLogin.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>

															<div class="form-group form-default form-static-label">
																<input type="date" name="dataNascimento" id="dataNascimento"
																	   class="form-control" required="required"
																	   value="${modelLogin.dataNascimento}"> <span
																	class="form-bar"></span> <label class="float-label">Data de Nascimento:</label>
															</div>

															<div class="form-group form-default">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.email}">
																<span class="form-bar"></span> <label
																	class="float-label">Email:</label>
															</div>

															<div class="form-group form-default">
																<input onblur="pesquisaCep();" type="text" name="cep" id="cep"
																	   class="form-control" required="required"
																	   value="${modelLogin.cep}"> <span
																	class="form-bar"></span> <label class="float-label">CEP:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="logradouro" id="logradouro"
																	   class="form-control" required="required"
																	   value="${modelLogin.logradouro}"> <span
																	class="form-bar"></span> <label class="float-label">Logradouro:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="bairro" id="bairro"
																	   class="form-control" required="required"
																	   value="${modelLogin.bairro}"> <span
																	class="form-bar"></span> <label class="float-label">Bairro:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="localidade" id="localidade"
																	   class="form-control" required="required"
																	   value="${modelLogin.localidade}"> <span
																	class="form-bar"></span> <label class="float-label">Localidade:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="uf" id="uf"
																	   class="form-control" required="required"
																	   value="${modelLogin.uf}"> <span
																	class="form-bar"></span> <label class="float-label">UF:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="numero" id="numero"
																	   class="form-control" required="required"
																	   value="${modelLogin.numero}"> <span
																	class="form-bar"></span> <label class="float-label">Número:</label>
															</div>

															<div class="form-group form-default">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	value="${modelLogin.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login:</label>
															</div>

															<div class="form-group form-default">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${modelLogin.senha}">
																<span class="form-bar"></span> <label
																	class="float-label">Senha:</label>
															</div>
															
															<div class="form-group form-default form-static-label">
																<input type="radio" name="sexo" checked="checked" value="Masculino" <%
																
																ModelLogin modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																
																if(modelLogin != null && modelLogin.getSexo().equals("Masculino")) {
																	out.print(" ");
																	out.print("checked=\"checked\"");
																	out.print(" ");
																	
																	} 
																
																%>>Masculino</>
																<input type="radio" name="sexo" value="Feminino" <%
																
																modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																
																if(modelLogin != null && modelLogin.getSexo().equals("Feminino")) {
																	out.print(" ");
																	out.print("checked=\"checked\"");
																	out.print(" ");
																	
																	} 
																
																%>>Feminino</>
																</div>

															<div class="form-group row">
																<label class="col-sm-2 col-form-label">Tipo de
																	acesso:</label>
																
																<div class="col-sm-10">
																	<select name="perfil" class="form-control">
																		<option value="ADMIN" <% 
																		
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if(modelLogin != null && modelLogin.getPerfil().equals("ADMIN")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																			
																			} %> >Admin</option>
																		
																		<option value="secretaria" <%
																		
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if(modelLogin != null && modelLogin.getPerfil().equals("secretaria")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																			
																			} %>>Secretária</option>
																		<option value="financeiro" <%
																		
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if(modelLogin != null && modelLogin.getPerfil().equals("financeiro")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																			
																			} %>>Financeiro</option>
																		<option value="administrativo" <%
																		
																		modelLogin = (ModelLogin) request.getAttribute("modelLogin");
																		
																		if(modelLogin != null && modelLogin.getPerfil().equals("administrativo")) {
																			out.print(" ");
																			out.print("selected=\"selected\"");
																			out.print(" ");
																			
																			} %>>Administrativo</option>

																	</select>
																</div>
																
															</div>

															<button type="button"
																class="btn btn-primary waves-effect waves-light"
																onclick="limparForm();">Novo</button>

															<button id="saveButton" class="btn btn-success waves-effect waves-light">Salvar
															</button>

															<button type="button"
																class="btn btn-danger waves-effect waves-light"
																onclick="deleteComAjax();">Excluir</button>

															<c:if test="${modelLogin.id > 0}">
															<a href="<%=request.getContextPath()%>/ServletTelefone?iduser=${modelLogin.id}"
															 class="btn btn-primary waves-effect waves-light" >Telefone
															</a>
															</c:if>

															<button type="button" class="btn btn-secondary"
																data-toggle="modal" data-target="#exampleModal">
																Pesquisar</button>

														</form>
													</div>
												</div>
											</div>
										</div>
										<span id="msg">${msg}</span>

										<div style="height: 300px; overflow: scroll;">

											<table class="table" id="tabelaResultadosview">
												<thead>
													<tr>
														<th scope="col">ID:</th>
														<th scope="col">Nome:</th>
														<th scope="col">Ver</th>
														<th scope="col">Excluir</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${modelLogins}" var="ml">

														<tr>

															<td><c:out value="${ml.id}"></c:out></td>
															<td><c:out value="${ml.nome}"></c:out></td>
															<td><a class="btn btn-success"
																href="ServletUsuarioController?acao=buscarEditar&id=${ml.id}"><i
																	class="ti-search"></i></a></td>
															<td><button
																	data-action="<%=request.getContextPath()%>/ServletUsuarioController"
																	class="btn btn-danger waves-effect waves-light"
																	onclick="deletarUsuario('${ml.id}');">
																	<i class="ti-trash"></i>
																</button></td>
														</tr>

													</c:forEach>
												</tbody>
											</table>
										</div>

									<nav aria-label="Page navigation example">
										<ul class="pagination">
											<%
											int totalPagina = (int) request.getAttribute("totalPagina");

											for (int p = 0; p < totalPagina; p++) {
												String url = request.getContextPath() + "/ServletUsuarioController?acao=paginar&pagina=" + (p * 5);
												out.print("<li class=\"page-item\"><a class=\"page-link\" href=\""+url+"\">"+(p + 1)+"</a></li>");
											}

											%>

										</ul>
									</nav>

									</div>

								</div>
								<!-- Page-body end -->

								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="javascriptfile.jsp"></jsp:include>

	<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisa de
						Usuário</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">

					<div class="input-group mb-3">
						<input type="text" class="form-control"
							placeholder="Insira um nome" aria-label="nome" id="nomeBusca"
							aria-describedby="basic-addon2">
						<div class="input-group-append">
							<button class="btn btn-success" type="button"
								onclick="buscarUsuario();">Buscar</button>
						</div>
					</div>
				</div>
				<div style="height: 300px; overflow: scroll;">

					<table class="table" id="tabelaResultados">
						<thead>
							<tr>
								<th scope="col">ID:</th>
								<th scope="col">Nome:</th>
								<th scope="col">Ver</th>
							</tr>
						</thead>
						<tbody>

						</tbody>
					</table>
				</div>

				<nav aria-label="Page navigation example">
					<ul class="pagination" id="ulPaginacaoUserAjax">

					</ul>
				</nav>

				<span id="totalResultados"></span>

				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>

			</div>
		</div>
	</div>
	

	<script type="text/javascript">


		$("#numero").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});
		$("#cep").keypress(function (event) {
			return /\d/.test(String.fromCharCode(event.keyCode));
		});

		function pesquisaCep() {
			var cep = $("#cep").val();

			$.getJSON("https://viacep.com.br/ws/"+ cep +"/json/?callback=?", function(dados) {

				if (!("erro" in dados)) {
					$("#cep").val(dados.cep);
					$("#logradouro").val(dados.logradouro);
					$("#bairro").val(dados.bairro);
					$("#localidade").val(dados.localidade);
					$("#uf").val(dados.uf);

				}

			});
		}
	
	function visualizarImg(fotoembase64, fileFoto) {
		
		var preview = document.getElementById(fotoembase64); //campo img do html.
		var fileUser = document.getElementById(fileFoto).files[0];
		var reader = new FileReader();
		
		reader.onloadend = function () {
			preview.src = reader.result; /*carrega a foto na tela*/
		};
	
		if (fileUser) {
			reader.readAsDataURL(fileUser); /* preview da IMG */
		} else {
			preview.src = '';
		}
		
	}
	
	
		function verEditar(id) {

			var urlAction = document.getElementById('formUser').action;

			window.location.href = urlAction + '?acao=buscarEditar&id= ' + id;

		}

		function buscaUserPagAjax(url) {

			var urlAction = document.getElementById('formUser').action;
			var nomeBusca = document.getElementById('nomeBusca').value;

			$
					.ajax(
							{

								method : "get",
								url : urlAction,
								data: url,
								success : function(response, textStatus, xhr) {

									var json = JSON.parse(response);

									$('#tabelaResultados > tbody > tr')
											.remove();
									$('#ulPaginacaoUserAjax > li').remove();

									for (var p = 0; p < json.length; p++) {
										$('#tabelaResultados > tbody')
												.append(
														'<tr> <td> '
														+ json[p].id
														+ ' </td> <td> '
														+ json[p].nome
														+ ' </td> <td> <button onclick="verEditar('
														+ json[p].id
														+ ')" type="button" class="btn btn-info"> <i class="ti-search"></i> </button> </td> </tr>');

									}
									document
											.getElementById('totalResultados').textContent = 'Resultados: '
											+ json.length

									var totalPagina = xhr.getResponseHeader("totalPagina");

									for (var p = 0; p < totalPagina; p++) {
										var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
										$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\'' + url + '\')">' + (p + 1) + '</a></li>');
									}
								}


							}).fail(
					function(xhr, status, errorThrown) {
						alert('Erro ao buscar usuário por nome:'
								+ xhr.responseText);
					});

		}

		function buscarUsuario() {

			var nomeBusca = document.getElementById('nomeBusca').value;

			if (nomeBusca != null && nomeBusca != '' && nomeBusca.trim() != '') { /*Validando que tem que ter valor para buscar no banco*/

				var urlAction = document.getElementById('formUser').action;

				$
						.ajax(
								{

									method : "get",
									url : urlAction,
									data : "nomeBusca=" + nomeBusca
											+ '&acao=buscarUserAjax',
									success : function(response, textStatus, xhr) {

										var json = JSON.parse(response);

										$('#tabelaResultados > tbody > tr')
												.remove();
										$('#ulPaginacaoUserAjax > li').remove();

										for (var p = 0; p < json.length; p++) {
											$('#tabelaResultados > tbody')
													.append(
															'<tr> <td> '
															+ json[p].id
															+ ' </td> <td> '
															+ json[p].nome
															+ ' </td> <td> <button onclick="verEditar('
															+ json[p].id
															+ ')" type="button" class="btn btn-info"> <i class="ti-search"></i> </button> </td> </tr>');

										}
										document
												.getElementById('totalResultados').textContent = 'Resultados: '
												+ json.length

										var totalPagina = xhr.getResponseHeader("totalPagina");

										for (var p = 0; p < totalPagina; p++) {
											var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);
											$("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\'' + url + '\')">' + (p + 1) + '</a></li>');
										}
									}


								}).fail(
								function(xhr, status, errorThrown) {
									alert('Erro ao buscar usuário por nome:'
											+ xhr.responseText);
								});

			}

		}

		function deletarUsuario(idUser) {

			if (confirm('Deseja realmente excluir os dados?')) {

				var urlAction = event.target.getAttribute('data-action');

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + '&acao=deletarajax',
					success : function(response) {

						location.reload(true);
						limparForm();
						document.getElementById('msg').textContent = response;
					}

				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por ID:'
									+ xhr.responseText);
						});

			}

		}

		function deleteComAjax() {

			if (confirm('Deseja realmente excluir os dados?')) {

				var urlAction = document.getElementById('formUser').action;
				var idUser = document.getElementById('id').value;

				$.ajax({

					method : "get",
					url : urlAction,
					data : "id=" + idUser + '&acao=deletarajax',
					success : function(response) {

						location.reload(true);
						limparForm();
						document.getElementById('msg').textContent = response;
					}

				}).fail(
						function(xhr, status, errorThrown) {
							alert('Erro ao deletar usuário por ID:'
									+ xhr.responseText);
						});

			}

		}

		function criarDelete() {

			if (confirm('Deseja realmente excluir os dados?')) {

				document.getElementById("formUser").method = 'get';
				document.getElementById("acao").value = 'deletar';
				document.getElementById("formUser").submit();
			}
		}

		function limparForm() {
			var element = document.getElementById("formUser").elements; /*Retorna os elementos HTML dentro do form*/

			for (p = 0; p < element.length; p++) {
				element[p].value = '';
			}
		}
	</script>

</body>

</html>
