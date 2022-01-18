<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>SB Admin - Blank Page</title>

    <!-- Bootstrap core CSS-->
    <link href="/tmdmodel/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="/tmdmodel/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Page level plugin CSS-->
    <link href="/tmdmodel/vendor/datatables/dataTables.bootstrap4.css" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="/tmdmodel/css/sb-admin.css" rel="stylesheet">

<style>
body, html {
  height: 100%;
width: 100%;
}
.navbar-height{
min-height:50px;
}
.big-icon{
font-size:35px;
}
</style>

<script>


////////////////////////////////////////////////////////////////////////////////////
var canvas;

var currentTool=null;
var createTableTool=1;


function createTableToolClicked()
{
currentTool=createTableTool;
createTableToolObj=$("#createTableTool");
createTableToolObj.addClass("border border-dark rounded");

}

</script>

  </head>

  <body id="page-top">

<div class="row h-100">
<div class="col-md-2 pr-1">
<div class="sidebar navbar-nav col-md-12 h-100 pr-0">
</div>
</div>
<div class="col-md-10 ">
<div class="row ">
<nav class="navbar navbar-nav  border  w-75 pt-0 pb-0 mt-2 ">
<ul class="navbar-nav mr-auto ml-3 ">
<li class="nav-item ">
<a id="createTableTool" class="nav-item  big-icon" data-toggle="modal" data-target="#createTableModal" onclick="createTableToolClicked()">
<i class="fas fa-table ml-1 mr-1 "></i>
</a>
</li>
</ul>
</nav>
</div>
<div class="row border mr-1 mt-1 ">
<canvas id="canvasArea" >
</canvas>
</div>
</div>
</div>

<div id="createTableModal" class="modal " role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Create table</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
	<div class="row">
	<label class="col-md-2 mt-1 h5">Name</label>
	<input class="col-md-9 form-control"id="tableNameInput"  type="text"></input>
	</div><!-- input row-->
	<div class="row mt-4 ml-1">
	<ul class="nav nav-tabs ">
	<li class=" nav-item mr-1"><a data-toggle="tab" class="nav-link active "  href="#tableConfPane">Table Conf</a></li>
	<li class="nav-item"><a data-toggle="tab" class="nav-link " href="#fieldsPane">Fields</a></li>
	</ul>
	</div>
	<div class="tab-content ml-1">
	<div id="tableConfPane" class="tab-pane in active border">
	<div class="row mt-4">
	<label class="col-md-2 h5 ml-4 mt-1 mr-4">  Engine </label>
	<select class="form-control col-md-8" name="selectedEngine" id="selectedEngine">
	<c:forEach items="${databaseEngines}" var="each">
	<option value="${each.code}">${each.name}</option>
	</c:forEach>
	</select>
	</div><!--engine selection row-->
	<div class="row ml-2 mr-1">
	<textarea rows="5" class="form-control col-md-11 mt-4 mb-4 " placeholder="Note" ></textarea>
	</div>
	</div><!-- table conf pane -->
	<div id="fieldsPane" class="tab-pane border">
	<table class="table table-editable">
	<thead>
	<th scope="col">S.No.</th>
	<th scope="col">Name</th>
	<th scope="col">Datatype</th>
	</thead>
	<tbody>
	<tr id="fieldTableTrLast">
	<td colspan="3"><a href="#" > Add Field +</a></td>
	</tr>
	</tbody>
	</table>
	</div><!-- fields pane -->
	</div><!-- tab content-->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">cancel</button>
        <button type="button" class="btn btn-default" >create</button>
      </div>
    </div>

  </div>
</div>


    <!-- Bootstrap core JavaScript-->
    <script src="/tmdmodel/vendor/jquery/jquery.min.js"></script>
    <script src="/tmdmodel/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/tmdmodel/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/tmdmodel/js/sb-admin.min.js"></script>

  </body>
<script>
canvas=document.getElementById("canvasArea");
canvas.height="800"
</script>

</html>
