<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Sahitya Sharma">

    <title>${currentProject.title}</title>

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

<script src="/tmdmodel/vendor/jquery/jquery.min.js"></script>
<script src="/tmdmodel/webservice/js/TMService.js"></script>
<script src="/tmdmodel/webservice/js/project.js"></script>
<script>


function downloadFile(fileName,content) 
{
var link = document.createElement("a");
var obj=content
link.download =fileName;
link.href = URL.createObjectURL(new Blob([obj]));
link.click();
}

var projectDetails=null;
var unsavedChanges=false;

function initProject()
{
tables=projectDetails.tables;
tableComponents.length=0;
var tableComponent=null;
var drawableTable=null;
var databaseTable=null;
var databaseTableField=null;
var fields=null;
var tmpFieldsList=null;
for(var i=0;i<tables.length;i++)
{
databaseTable=new DatabaseTable()
databaseTable.name=tables[i].name;
databaseTable.note=tables[i].note;
databaseTable.engine=tables[i].engine.code;
fields=tables[i].fields
if(fields!=null)tmpFieldsList=[]
if(fields!=null)for(var j=0;j<fields.length;j++)
{
databaseTableField=new DatabaseTableField()
databaseTableField.name=fields[j].name;
datatype=fields[j].datatype.datatype;
if(fields[j].width && fields[j].width!=null)datatype+=('('+fields[j].width)
if(fields[j].numberOfDecimalPlaces && fields[j].numberOfDecimalPlaces!=null)datatype+=(','+fields[j].numberOfDecimalPlaces)
datatype+=')'
databaseTableField.datatype=datatype;
databaseTableField.isPrimaryKey=fields[j].isPrimaryKey;
databaseTableField.isAutoIncrement=fields[j].isAutoIncrement;
databaseTableField.isNotNull=fields[j].isNotNull;
databaseTableField.isUnique=fields[j].isUnique;
databaseTableField.defaultValue=fields[j].defaultValue;
databaseTableField.constraints=fields[j].checkConstraints;
databaseTableField.note=fields[j].note;
tmpFieldsList.push(databaseTableField);
}
databaseTable.fields=tmpFieldsList;
drawableTable=new DrawableTable(databaseTable)
drawableTable.x=tables[i].xcor;
drawableTable.y=tables[i].ycor;

tableComponent=new TableComponent(databaseTable,drawableTable)
tableComponents.push(tableComponent);
}
canvas.get(0).width=projectDetails.canvasWidth;
canvas.get(0).height=projectDetails.canvasHeight;
}

var getTextHeight = function(font) {

  var text = $('<span>Hg</span>').css({ fontFamily: font });
  var block = $('<div style="display: inline-block; width: 1px; height: 0px;"></div>');

  var div = $('<div></div>');
  div.append(text, block);

  var body = $('body');
  body.append(div);

  try {

    var result = {};

    block.css({ verticalAlign: 'baseline' });
    result.ascent = block.offset().top - text.offset().top;

    block.css({ verticalAlign: 'bottom' });
    result.height = block.offset().top - text.offset().top;

    result.descent = result.height - result.ascent;

  } finally {
    div.remove();
  }

  return result;
};

////////////////////////////////Server related code starts
/*function Point()
{
this.x=null;
this.y=null;
}*/

function DatabaseTableFieldPojo()
{
this.name=null;
this.datatype=null;
this.width=null;
this.numberOfDecimalPlaces=null;
this.isPrimaryKey=null;
this.isAutoIncrement=null;
this.isUnique=null;
this.isNotNull=null;
this.defaultValue=null;
this.checkConstraints=null;
this.note=null;
}

function DatabaseTablePojo()
{
this.name=null;
this.fields=null;
this.engine=null;
this.note=null;
this.numberOfFields=null;
this.xcor=null;
this.ycor=null;
}
function ProjectPojo()
{
this.canvasWidth=null;
this.canvasHeight=null;
this.databaseTables=null;
}

function saveProject()
{
var point=null;
var databaseTablePojo=null;
var projectPojo=new ProjectPojo()
var databaseTablePojoList=[]
for(var i=0;i<tableComponents.length;i++)
{
databaseTablePojo=new DatabaseTablePojo();
databaseTablePojo.xcor=tableComponents[i].drawableTable.x;
databaseTablePojo.ycor=tableComponents[i].drawableTable.y;
databaseTablePojo.name=tableComponents[i].databaseTable.name;
databaseTablePojo.engine=tableComponents[i].databaseTable.engine;
if(databaseTablePojo.engine==null || databaseTablePojo.engine=='')databaseTablePojo.engine=3;
databaseTablePojo.note=tableComponents[i].databaseTable.note;
var pojoFieldsList=[]
var fieldsList=tableComponents[i].databaseTable.fields;
var fieldPojo=null;
var datatypeList=projectDetails.databaseArchitecture.datatypes;
var datatypeCode=null;
if(fieldsList && fieldsList.length!=0)
{for(var ii=0;ii<fieldsList.length;ii++)
{
fieldPojo=new DatabaseTableFieldPojo();
fieldPojo.name=fieldsList[ii].name;
var udatatype=fieldsList[ii].datatype;
var k=-1;
datatypeCode=null;
if(udatatype!=null)for(var j=0;j<datatypeList.length;j++)
{
k=udatatype.indexOf('(');
if(k==-1)k=udatatype.length;
if(udatatype.substr(0,k).toUpperCase()==datatypeList[j].datatype.toUpperCase())
{
datatypeCode=datatypeList[j].code;
break;
}
}
if(datatypeCode!=null)fieldPojo.datatype=datatypeCode;
else fieldPojo.datatype=1;///////////THROW EXCEPTION OR SOMETHING 
fieldPojo.width=10///////////change it later
fieldPojo.numberOfDecimalPlaces=0////change it later
if(udatatype!=null)if(udatatype.indexOf(',')!=-1)
{
var num=''
for(var n=udatatype.indexOf(',')+1;n<udatatype.indexOf(')');n++)num+=udatatype[n];
fieldPojo.numberOfDecimalPlaces=num;
}
fieldPojo.isPrimaryKey=fieldsList[ii].isPrimaryKey;
fieldPojo.isAutoIncrement=fieldsList[ii].isAutoIncrement;
fieldPojo.isUnique=fieldsList[ii].isUnique;
fieldPojo.isNotNull=fieldsList[ii].isNotNull;
fieldPojo.defaultValue=fieldsList[ii].defaultValue;
fieldPojo.checkConstraints=fieldsList[ii].constraints;
fieldPojo.note=fieldsList[ii].note;
pojoFieldsList.push(fieldPojo)
}
}
databaseTablePojo.fields=pojoFieldsList//ssdcdsnjcdw
databaseTablePojo.numberOfFields=pojoFieldsList.length
databaseTablePojoList.push(databaseTablePojo)
}
projectPojo.canvasWidth=canvas.get(0).width;
projectPojo.canvasHeight=canvas.get(0).height;
projectPojo.databaseTables=databaseTablePojoList;
console.log(JSON.stringify(projectPojo))
projectManager.saveProject(projectPojo,function(s){
alert("success")
alert(JSON.stringify(s))
},
function(e){
alert(JSON.stringify(e))
}

);


}

////////////////////////////////Server related code ends

function DatabaseTableField()
{
this.name=null;
this.datatype=null;
this.isPrimaryKey=null;
this.isAutoIncrement=null;
this.isUnique=null;
this.isNotNull=null;
this.defaultValue=null;
this.constraints=null;
this.note=null;
}

function DatabaseTable()
{
this.name=null;
this.fields=null;
this.engine=null;
this.note=null;
}

function DrawableTable(dbTable)
{
this.x=null;
this.y=null;
this.width=null;
this.height=null;
this.dbTable=dbTable;
this.fontHeight=null;
this.tempList=null;//to store string values of fields and name of table
this.calcDimensions=function(){
//calc dimensions with dbTable
this.tempList=[]
this.tempList.push(this.dbTable.name)
var i=0;
var field;
if(this.dbTable.fields!=null)for(i=0;i<this.dbTable.fields.length;i++)
{
field=this.dbTable.fields[i];
this.tempList.push(field.name+'('+field.datatype+')')
}
//alert(JSON.stringify(tempList))
var maxWidth=ctx.measureText(this.tempList[0]).width;
var totalHeight=0;
ctx.font="30px Arial";
var metrics=null;
var tmpDiv=document.createElement("div")
fontHeight=getTextHeight(ctx.font).height;;
for(i=0;i<this.tempList.length;i++)
{
metrics=ctx.measureText(this.tempList[i]);
totalHeight+=fontHeight;
if(metrics.width>maxWidth)maxWidth=metrics.width;
}
//alert(totalHeight)
//alert(maxWidth)
this.height=totalHeight+fontHeight+12+30;
this.width=maxWidth+15;
};
this.draw=function(){
this.calcDimensions()
//draw....
ctx.clearRect(this.x,this.y,this.width,this.height)
canvasWidth=canvas.get(0).width
canvasHeight=canvas.get(0).height
//if((this.x+this.width)>canvasWidth)canvas.get(0).width=canvasWidth+((this.x+this.width)-canvasWidth)+50
//if((this.y+this.height)>canvasHeight)canvas.get(0).height=canvasHeight+((this.y+this.height)-canvasHeight)+50
ctx.beginPath()
ctx.rect(this.x,this.y,this.width,this.height);
ctx.stroke();
ctx.beginPath()
ctx.rect(this.x,this.y,this.width,fontHeight+12)
ctx.stroke();
ctx.fillText(this.tempList[0],this.x+8,this.y+fontHeight+6)
var yoff=this.y+(fontHeight*2)+12+8;
for(i=1;i<this.tempList.length;i++)
{
ctx.fillText(this.tempList[i],this.x+5,yoff)
yoff+=fontHeight+4
}


};
}

var tableComponentIdCounter=1;

function TableComponent(dbTable,drawableTable)
{
this.id=tableComponentIdCounter;
tableComponentIdCounter+=1;
this.databaseTable=dbTable;
this.drawableTable=drawableTable;
this.draw=function(){
drawableTable.draw();
};
}

var tableComponents=[];

var canvas=$('<canvas/>');
var ctx=canvas.get(0).getContext("2d");

function initCanvas()
{
var domCanvas=canvas.get(0);
domCanvas.width=$("#canvasDiv").width();
domCanvas.height=$("#canvasDiv").height();
canvas.on('click',canvasClick);
canvas.on('dblclick',canvasDblClick);
}

var lastX=null;
var lastY=null;
var selectedTable=null;

function canvasClick(event)
{
if(currentTool==null)
{
selectedTable=null;
for(var i=0;i<tableComponents.length;i++)
{
var x1=tableComponents[i].drawableTable.x;
var x2=tableComponents[i].drawableTable.x+tableComponents[i].drawableTable.width;
var y1=tableComponents[i].drawableTable.y;
var y2=tableComponents[i].drawableTable.y+tableComponents[i].drawableTable.height;
if(event.offsetX>x1 && event.offsetX<x2 && event.offsetY>y1 && event.offsetY<y2)
{
selectedTable=tableComponents[i];
tableComponents.splice(i,1);
tableComponents.push(selectedTable)
redraw()
return;
}
}
}
if(currentTool==createTableTool)
{
//if(selectedTable!=null)return;

for(var i=0;i<tableComponents.length;i++)
{
var x1=tableComponents[i].drawableTable.x;
var x2=tableComponents[i].drawableTable.x+tableComponents[i].drawableTable.width;
var y1=tableComponents[i].drawableTable.y;
var y2=tableComponents[i].drawableTable.y+tableComponents[i].drawableTable.height;
if(event.offsetX>x1 && event.offsetX<x2 && event.offsetY>y1 && event.offsetY<y2)
{
selectedTable=tableComponents[i];
tableComponents.splice(i,1);
tableComponents.push(selectedTable)
redraw()
return;
}
}


lastX=event.offsetX;
lastY=event.offsetY;
//done done
var dbTable=new DatabaseTable()
var i=1;
for(var x=0;x<tableComponents.length;x++)
{
if(tableComponents[x].databaseTable.name=="Table "+i)
{
i++;
x=0;
}
}
dbTable.name="Table "+i;
var drawableTable=new DrawableTable(dbTable)
drawableTable.x=event.offsetX;
drawableTable.y=event.offsetY;
var tableComponent=new TableComponent(dbTable,drawableTable);
tableComponents.push(tableComponent);
selectedTable=tableComponent;
redraw()
unsavedChanges=true
}
}

function canvasDblClick(event)
{
if(currentTool==createTableTool)
{
for(var i=0;i<tableComponents.length;i++)
{
var x1=tableComponents[i].drawableTable.x;
var x2=tableComponents[i].drawableTable.x+tableComponents[i].drawableTable.width;
var y1=tableComponents[i].drawableTable.y;
var y2=tableComponents[i].drawableTable.y+tableComponents[i].drawableTable.height;
if(event.offsetX>x1 && event.offsetX<x2 && event.offsetY>y1 && event.offsetY<y2)
{
selectedTable=tableComponents[i]
initializeModal(selectedTable)
$("#createTableModal").modal("show");
lastX=event.offsetX;
lastY=event.offsetY;
break;
}
}
}
}

function redraw()
{
ctx.lineWidth=2
ctx.clearRect(0,0,canvas.get(0).width,canvas.get(0).height)
for(var i=0;i<tableComponents.length;i++)tableComponents[i].draw()
if(selectedTable!=null)
{
var x1=selectedTable.drawableTable.x
var y1=selectedTable.drawableTable.y
var width=selectedTable.drawableTable.width
var height=selectedTable.drawableTable.height
var lw=ctx.lineWidth
ctx.fillStyle="DodgerBlue"
var fontHeight=getTextHeight(ctx.font).height
ctx.fillRect(x1+1,y1+1,width-2,fontHeight+10)
ctx.fillStyle="white"
ctx.fillText(selectedTable.databaseTable.name,x1+8,y1+fontHeight+6);
ctx.fillStyle="black"
//ctx.strokeStyle="DodgerBlue"
ctx.lineWidth=2
ctx.beginPath()
ctx.arc(x1,y1,3,0,2*Math.PI)
ctx.stroke()
ctx.beginPath()
ctx.arc(x1+width,y1,3,0,2*Math.PI)
ctx.stroke()
ctx.beginPath()
ctx.arc(x1+width,y1+height,3,0,2*Math.PI)
ctx.stroke()
ctx.beginPath()
ctx.arc(x1,y1+height,3,0,2*Math.PI)
ctx.stroke()
ctx.strokeStyle="black"
ctx.lineWidth=lw;
}
}

////////////////////////////////////////////////////////////////////////////////////

var createTableModal=null;
function initModal()
{
createTableModal=$("#createTableModal");
}

var currentTool=null;
var createTableTool=1;

function createTableToolClicked()
{
currentTool=createTableTool;
createTableToolObj=$("#createTableTool");
createTableToolObj.addClass("border border-dark shadow");
}

function createScriptToolClicked()
{
createTableToolObj=$("#createTableTool");
createTableToolObj.removeClass("border border-dark shadow");
//downloadFile('script.txt','nhdsejfcebhfdehduedhoiehjduijbheduihjbhewd')
modalContent=document.getElementById('createScriptModalContent');
var tmpString='';
for(var i=0;i<tableComponents.length;i++)
{
tmpString+='<li><input id="scriptTable'+tableComponents[i].id+'" type="checkbox" value="'+tableComponents[i].databaseTable.name+'" checked>'+tableComponents[i].databaseTable.name+'</input></li>'
}
modalContent.innerHTML='<div class="col-md-8 float-left"><ul style="overflow:scroll;max-height:600px;max-width:222px">'+
tmpString+
'</ul>'+
'<button class="btn mr-4" onclick="selectAllButtonClicked()">Select All</button>'+
'<button class="btn" onclick="unselectAllButtonClicked()">Unselect All</button></div>'+
'<div class="col-md-4 float-right"><button class="btn btn-primary" onclick="generateScriptClicked()">Generate Script</button></div>';
$("#createScriptModal").modal('show')
}

function selectAllButtonClicked()
{
$("[id^=scriptTable]").prop('checked',true)
}
function unselectAllButtonClicked()
{
$("[id^=scriptTable]").prop('checked',false)
}
function generateScriptClicked()
{
if(unsavedChanges)
{
alert('Please save changes first')
return;
}
var tableNames=[]
tables=$("[id^=scriptTable]")
for(var i=0;i<tables.length;i++)
{
tableNames.push(tables[i].value);
}
projectManager.generateScript({"names":tableNames},function(s){
alert('script generated')
alert(s)
},function(e){
alert('cannot gen script');
alert(e)
}
);
}


function initializeModal(tableComponent)
{
$("#tableNameInput").val(tableComponent.databaseTable.name)
$("#tableNote").val(tableComponent.databaseTable.note);
$("#selectedEngine").val(tableComponent.databaseTable.engine);
var fields=tableComponent.databaseTable.fields;
var fieldDiv=null;
var rows=$('#fieldTable tr')
tableBody=$("#fieldTable tbody")
$('#fieldTableTrLast').remove()
$("[id^=fieldTableTr]").remove()
var z;
if(fields!=null)for(var i=0;i<fields.length;i++)
{



tableBody.append('<tr id="'+('fieldTableTr'+fieldCount)+'" onclick="tableFieldSelected('+('fieldTableTr'+fieldCount)+')"><td>'+(i+1)+'</td>'+
'<td><input id="'+(('fieldTableTr'+fieldCount)+'Name')+'" type="text"></input></td>'+
'<td><input id="'+(('fieldTableTr'+fieldCount)+'Datatype')+'" type="text"></input></td>'+
'<td><input type="checkbox" class="checkbox"  id="'+(('fieldTableTr'+fieldCount)+'PK')+'"></input>PK&nbsp;&nbsp;&nbsp;<input type="checkbox" class="checkbox"  id="'+(('fieldTableTr'+fieldCount)+'AI')+'">AI&nbsp;&nbsp;&nbsp;</input><input type="checkbox" class="checkbox"  id="'+(('fieldTableTr'+fieldCount)+'UQ')+'">UQ&nbsp;&nbsp;&nbsp;</input><input type="checkbox" class="checkbox"  id="'+(('fieldTableTr'+fieldCount)+'NN')+'">NN&nbsp;&nbsp;&nbsp;</input></td>'+
+'</tr>');

fieldDiv=document.createElement("DIV");
fieldDiv.id='fieldTableTr'+fieldCount+'Div';
fieldDiv.innerHTML='<div class="row ml-1 mr-1">'+
'<textarea id="'+(('fieldTableTr'+fieldCount)+'DefaultValue')+'" rows="5" class="form-control " placeholder="Default Value">'+fields[i].defaultValue+'</textarea>'+
'<textarea id="'+(('fieldTableTr'+fieldCount)+'Constraints')+'" rows="2" class="form-control " placeholder="Constraints">'+fields[i].constraints+'</textarea>'+
'<textarea id="'+(('fieldTableTr'+fieldCount)+'Note')+'" rows="3" class="form-control " placeholder="Note">'+fields[i].note+'</textarea>'+
'</div>';



fieldsExtraInfoDivs.push(fieldDiv)


$('#'+('fieldTableTr'+fieldCount)+"Name").val(fields[i].name)
$('#'+('fieldTableTr'+fieldCount)+"Datatype").val(fields[i].datatype)
$('#'+('fieldTableTr'+fieldCount)+"PK").prop('checked',fields[i].isPrimaryKey)
$('#'+('fieldTableTr'+fieldCount)+"AI").prop('checked',fields[i].isAutoIncrement)
$('#'+('fieldTableTr'+fieldCount)+"UQ").prop('checked',fields[i].isUnique)
$('#'+('fieldTableTr'+fieldCount)+"NN").prop('checked',fields[i].isNotNull)

fieldCount++;
}
$("#fieldTable tbody").append('<tr id="fieldTableTrLast"><td colspan="3"><a onclick="addFieldClicked()" href="#" > Add Field ++</a></td></tr>');

}

function closeModal()
{
//TODO ask to save changes
createTableModal.modal("hide");
//unload all the variables and remove elements from modal
$("#tableNameInput").val('')
$("#tableNote").val('');
$("#selectedEngine").val($("#selectedEngine option:first").val());
$("[id^=fieldTableTr]").remove()
$("#fieldTable tbody").append('<tr id="fieldTableTrLast"><td colspan="3"><a onclick="addFieldClicked()" href="#" > Add Field ++</a></td></tr>');
selectedFieldDiv=null;
fieldsExtraInfoDivs=[]
fieldCount=1;
}


function createButtonClicked()//creatingTable
{
selectedTable.databaseTable.name=$("#tableNameInput").val();
selectedTable.databaseTable.engine=$("#selectedEngine option:selected").val();
selectedTable.databaseTable.note=$("#tableNote").val();

//creating fields list
var tableRows=$("#fieldTable tr");
var i=0;
var fieldsList=[]
var field=null;
var z;
for(i=0;i<tableRows.length;i++)
{
z=true;
//if(selectedTable.databaseTable.fields)for(var y=0;y<selectedTable.databaseTable.fields.length;y++)
//{
//if(tableRows[i].id==selectedTable.databaseTable.fields[y].id)z=false;
//}
if(tableRows[i].id!='fieldTableTrLast' && tableRows[i].id!='' && z==true)
{
field=new DatabaseTableField()
field.name=$("#"+tableRows[i].id+"Name").val();
field.datatype=$("#"+tableRows[i].id+"Datatype").val();
field.isPrimaryKey=$("#"+tableRows[i].id+"PK").prop('checked');
field.isAutoIncrement=$("#"+tableRows[i].id+"AI").prop('checked');
field.isUnique=$("#"+tableRows[i].id+"UQ").prop('checked');
field.isNotNull=$("#"+tableRows[i].id+"NN").prop('checked');
field.defaultValue=$("#"+tableRows[i].id+"DefaultValue").val();
field.constraints=$("#"+tableRows[i].id+"Constraints").val();
field.note=$("#"+tableRows[i].id+"Note").val();
if(field.name=='')field.name=null;//show error 
if(field.datatype=='')field.datatype=null;//show error
if(field.isPrimaryKey=='')field.isPrimaryKey=false;//show error
if(field.isPrimaryKey=='on')field.isPrimaryKey=true;
if(field.isPrimaryKey=='off')field.isPrimaryKey=false;
if(field.isAutoIncrement=='')field.isAutoIncrement=false;//show error
if(field.isAutoIncrement=='on')field.isAutoIncrement=true;
if(field.isAutoIncrement=='off')field.isAutoIncrement=false;
if(field.isUnique=='')field.isUnique=false;//show error
if(field.isUnique=='on')field.isUnique=true;
if(field.isUnique=='off')field.isUnique=false;
if(field.isNotNull=='')field.isNotNull=false;//show error
if(field.isNotNull=='on')field.isNotNull=true;
if(field.isNotNull=='off')field.isNotNull=false;
fieldsList.push(field);
}
}
if(selectedTable.databaseTable.name=='')selectedTable.databaseTable.name=null;//show error
if(selectedTable.databaseTable.note=='')selectedTable.databaseTable.note=null;
selectedTable.databaseTable.fields=fieldsList;


redraw()
selectedTable=null;
closeModal();
unsavedChanges=true;
}

function saveButtonClicked()
{
saveProject();
unsavedChanges=false;
}


var fieldCount=1;

function addFieldClicked()
{

var tableBody=$("#fieldTable").find("tbody");
$("#fieldTableTrLast").remove();
var rowCount=$("#fieldTable tr").length;
//TODO add ids to these inputs MAYBE
tableBody.append('<tr id="'+'fieldTableTr'+fieldCount+'" onclick="tableFieldSelected('+'fieldTableTr'+fieldCount+')"><td>'+(rowCount)+'</td>'+
'<td><input id="'+('fieldTableTr'+fieldCount+'Name')+'" type="text"></input></td>'+
'<td><input id="'+('fieldTableTr'+fieldCount+'Datatype')+'" type="text"></input></td>'+
'<td><input type="checkbox" class="checkbox"  id="'+('fieldTableTr'+fieldCount+'PK')+'"></input>PK&nbsp;&nbsp;&nbsp;<input type="checkbox" class="checkbox"  id="'+('fieldTableTr'+fieldCount+'AI')+'">AI&nbsp;&nbsp;&nbsp;</input><input type="checkbox" class="checkbox"  id="'+('fieldTableTr'+fieldCount+'UQ')+'">UQ&nbsp;&nbsp;&nbsp;</input><input type="checkbox" class="checkbox"  id="'+('fieldTableTr'+fieldCount+'NN')+'">NN&nbsp;&nbsp;&nbsp;</input></td>'+
+'</tr>');


tableBody.append('<tr id="fieldTableTrLast"><td colspan="3"><a onclick="addFieldClicked()" href="#" > Add Field ++</a></td></tr>');
//tableFieldSelected(document.getElementById('fieldTableTr'+fieldCount))
fieldCount++;
//TODO when new row is created prev selected row should not be deselected
}


var fieldsExtraInfoDivs=[];//Empty this array when table is created
var selectedFieldDiv=null;

function tableFieldSelected(fieldId)
{
//For fieldId  is not actually the id,
//it is the whole dom object of field.

var tableBody=$("#fieldTable").find("tbody");
var rows=tableBody.children("tr");
var i=0;
for(i=0;i<rows.length;i++)rows[i].classList.remove("bg-primary")
$("#"+fieldId.id).addClass("bg-primary");
var k;
fieldDiv=null;
for(k=0;k<fieldsExtraInfoDivs.length;k++)
{
if(fieldsExtraInfoDivs[k].id==(fieldId.id+'Div'))
{
fieldDiv=fieldsExtraInfoDivs[k];
break;
}
}
if(fieldDiv==null)
{
fieldDiv=document.createElement("DIV");
fieldDiv.id=fieldId.id+'Div';
fieldDiv.innerHTML='<div class="row ml-1 mr-1">'+
'<textarea id="'+(fieldId.id+'DefaultValue')+'" rows="5" class="form-control " placeholder="Default Value"></textarea>'+
'<textarea id="'+(fieldId.id+'Constraints')+'" rows="2" class="form-control " placeholder="Constraints"></textarea>'+
'<textarea id="'+(fieldId.id+'Note')+'" rows="3" class="form-control " placeholder="Note"></textarea>'+
'</div>';
fieldsExtraInfoDivs.push(fieldDiv)
}
fieldsPane=document.getElementById("fieldsPane");
//if(selectedFieldDiv!=null)fieldsPane.removeChild(selectedFieldDiv);
if(selectedFieldDiv!=null)selectedFieldDiv.classList.add('d-none')
selectedFieldDiv=fieldDiv;
//fieldsPane.appendChild(fieldDiv);
if(document.getElementById(fieldDiv.id)==null)fieldsPane.appendChild(fieldDiv)
fieldDiv.classList.remove('d-none')

}


</script>

  </head>

  <body id="page-top">


<div class="row h-100">
<div class="">
<div class="sidebar navbar-nav shadow-lg w-100 h-100 mr-2 pr-4">
<ul >
<li class="nav-item" >
<a class="nav-link h3" onclick="saveButtonClicked()" href="#"><i class="fas fa-save"></i>&nbsp;&nbsp; Save</a>
</li>
</ul>
</div>
</div>
<div class="col-md-10 ml-0 " >
<div class="row ">
<nav class="navbar navbar-nav shadow border rounded mb-3 ml-2 w-75 pt-0 pb-0 mt-2" style="display:inline-block;">
<ul class="list-inline pl-0 mb-0 ml-2">
<li class="list-inline-item " >
<a id="createTableTool" class="nav-item  big-icon" onclick="createTableToolClicked()">
<i class="fas fa-table ml-1 mr-1 "></i>
</a>
</li>
<li class="list-inline-item" >
<a id="createScriptTool" class="nav-item big-icon" onclick="createScriptToolClicked()">
<i class="fas fa-file-alt"></i>
</a>
</li>
</ul>
</nav>
</div>
<div id="canvasDiv" class="row border border-dark rounded  mt-1 h-100 ">
</div>
</div>
</div>

<div id="createTableModal" class="modal" data-backdrop="static" data-keyboard="false" role="dialog">
  <div class="modal-dialog modal-lg">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Create table</h4>
        <button type="button" class="close" onclick="closeModal()">&times;</button>
      </div>
      <div class="modal-body">
	<div class="row">
	<label class="col-md-2 mt-1 h5">Name</label>
	<input class="col-md-9 form-control" id="tableNameInput"  type="text"></input>
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
	<c:forEach items="${currentProject.databaseArchitecture.databaseEngines}" var="each">
	<option value="${each.code}">${each.name}</option>
	</c:forEach>
	</select>
	</div><!--engine selection row-->
	<div class="row ml-2 mr-1">
	<textarea id="tableNote" rows="5" class="form-control col-md-11 mt-4 mb-4 " placeholder="Note" ></textarea>
	</div>
	</div><!-- table conf pane -->
	<div id="fieldsPane" class="tab-pane border">
	<table id="fieldTable" class="table table-editable">
	<thead>
	<th scope="col">S.No.</th>
	<th scope="col">Name</th>
	<th scope="col">Datatype</th>
	<th scope="col">Attributes</th>
	</thead>
	<tbody>
	<tr id="fieldTableTrLast">
	<td colspan="3"><a onclick="addFieldClicked()" href="#" > Add Field +</a></td>
	</tr>
	</tbody>
	</table>
	</div><!-- fields pane -->
	</div><!-- tab content-->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" onclick="closeModal()">cancel</button>
        <button type="button" class="btn btn-default" onclick="createButtonClicked()">create</button>
      </div>
    </div>

  </div>
</div>

<div id="createScriptModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">Download Script</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      <div class="modal-body">
	<div id="createScriptModalContent">
	</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>

  </div>
</div>


    <!-- Bootstrap core JavaScript-->
    <script src="/tmdmodel/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="/tmdmodel/vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="/tmdmodel/js/sb-admin.min.js"></script>

  </body>
<script>
$("#canvasDiv").append(canvas);
initModal();
projectManager.getDetails(function(s){
console.log(JSON.stringify(s))
projectDetails=s;
initProject()
initCanvas();
redraw()
},function(e){
alert(JSON.stringify(e));
}
);

</script>

</html>
