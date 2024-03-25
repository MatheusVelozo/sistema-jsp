<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	
	
	<title>Curso JSP</title>
	
<style type="text/css">

	form {
	position: absolute;
	top: 40%;
	left: 35%;
	right: 35%
	
	}
	
	h3 {
	position: absolute;
	top: 30%;
	left: 35%;
	
	}
	
	h5 {
	position: absolute;
	top: 80%;
	left: 35%;
	color: red;
	}

</style>
	
</head>
<body>

	<h3>Bem vindo!</h3>
	
	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
	<input type="hidden" value="<%= request.getParameter("url") %>" name="url">
	
	<div class="mb-3">
	<label class="form-label">Login:</label>
	<input class="form-control" name="login" type="text" required="required">
	<div class="invalid-feedback">
        Informe Login.
      </div>
       <div class="valid-feedback">
      OK
    </div>
      
	</div>
	
	<div class="mb-3">
	<label class="form-label">Senha:</label>
	<input class="form-control" name="senha" type="password" required="required">
	<div class="invalid-feedback">
        Informe senha.
      </div>
	</div>
	 <div class="valid-feedback">
      OK
    </div>
	
	
	<input type="submit" value="Acessar" class="btn btn-primary">
	
	
	</form>
	
	<h5>
	${msg}
	</h5>


    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>


	<script type="text/javascript">
	(() => {
		  'use strict'

		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  const forms = document.querySelectorAll('.needs-validation')

		  // Loop over them and prevent submission
		  Array.from(forms).forEach(form => {
		    form.addEventListener('submit', event => {
		      if (!form.checkValidity()) {
		        event.preventDefault()
		        event.stopPropagation()
		      }

		      form.classList.add('was-validated')
		    }, false)
		  })
		})()
	</script>
</body>
</html>