<%@taglib uri="/WEB-INF/tlds/mytags.tld" prefix="tm" %>
<tm:AdministratorLoggedIn>
<jsp:forward page="/homepage.jsp" />
</tm:AdministratorLoggedIn>
<tm:AdministratorLoggedOut>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin - Login</title>

    <!-- Bootstrap core CSS-->
    <link href="/tmdmodel/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/tmdmodel/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin.css" rel="stylesheet">
<script src="webservice/js/TMService.js"></script>
<script src="webservice/js/member.js"></script>
<script src="/tmdmodel/js/login.js"></script>

  </head>

  <body class="bg-dark">

    <div class="container">
      <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
          <form id="loginForm" action="/tmdmodel/webservice/member/login" method="POST">
            <div class="form-group">
              <div class="form-label-group">
                <input type="email" name="argument-1" id="argument-1" class="form-control ${errorBean.emailIsInvalid}" placeholder="Email address" required="required" autofocus="autofocus">
                <label for="argument-1">Email address</label>
              </div>
            </div>
<span id="emailError" class="help-block ">${errorBean.emailError}</span>
            <div class="form-group">
              <div class="form-label-group">
                <input type="password" name="argument-2" id="argument-2" class="form-control ${errorBean.passwordIsInvalid}" placeholder="Password" required="required">
                <label for="argument-2">Password</label>
              </div>
<span id="passwordError" class="help-block ">${errorBean.passwordError}</span>
<span id="errorMessage" class="help-block label-danger">${errorBean.errorMessage}</span>
            </div>
	
            <a class="btn btn-primary btn-block" onclick="javascript:submitForm()">Login</a>
          </form>
          <div class="text-center">
            <a class="d-block small mt-3" href="register.html">Register an Account</a>
          </div>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript-->
    <script src="/tmdmodel/vendor/jquery/jquery.min.js"></script>
    <script src="/tmdmodel/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/tmdmodel/vendor/jquery-easing/jquery.easing.min.js"></script>

  </body>

</html>
</tm:AdministratorLoggedOut>
