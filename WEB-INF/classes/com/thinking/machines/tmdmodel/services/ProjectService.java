package com.thinking.machines.tmdmodel.services;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmws.*;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmdmodel.services.pojo.*;
import com.thinking.machines.tmdmodel.utilities.*;
import com.thinking.machines.tmdmodel.services.exceptions.*;
@Path("/project")
public class ProjectService
{
HttpServletRequest request;
HttpSession session;
ServletContext servletContext;
public void setHttpSession(HttpSession session){this.session=session;}
public HttpSession getHttpSession(){return this.session;}
public void setHttpRequest(HttpServletRequest request){this.request=request;}
public HttpServletRequest getHttpRequest(){return this.request;}
public void setServletContext(ServletContext servletContext){this.servletContext=servletContext;}
public ServletContext getServletContext(){return this.servletContext;}
@Path("create")
@InjectRequest
@InjectSession
@InjectApplication
public TMForward createProject(String name,String dbms)
{
try
{
request.removeAttribute("showCreateProjectModal");
request.removeAttribute("errorBean");
HashMap<String,String > map=null;
boolean galti=false;
if(name.trim().length()==0)
{
if(map==null)map=new HashMap<>();
map.put("name","Please enter the name of the project");
map.put("nameDnone","");
galti=true;
}
if(dbms.trim().length()==0)
{
if(map==null)map=new HashMap<>();
map.put("dbms","Please select a database architecture");
map.put("dbmsDnone","");
galti=true;
}
if(galti)
{
request.setAttribute("showCreateProjectModal","show");
request.setAttribute("errorBean",map);
return new TMForward("/homepage.jsp");
}
System.out.println("create proj called");
System.out.println("Project name:"+name+" Architecture:"+dbms);

com.thinking.machines.tmdmodel.dl.Member member=(com.thinking.machines.tmdmodel.dl.Member)session.getAttribute("member");
com.thinking.machines.tmdmodel.dl.Project dlProject=new com.thinking.machines.tmdmodel.dl.Project();
com.thinking.machines.tmdmodel.services.pojo.Project pojoProject=new com.thinking.machines.tmdmodel.services.pojo.Project();

dlProject.setTitle(name);
dlProject.setMemberCode(member.getCode());
dlProject.setDatabaseArchitectureCode(Integer.parseInt(dbms));
java.util.Date date=new java.util.Date();
dlProject.setDateOfCreation(new java.sql.Date(date.getTime()));
dlProject.setTimeOfCreation(new java.sql.Time(date.getTime()));
dlProject.setNumberOfTable(0);
dlProject.setCanvasWidth(0);
dlProject.setCanvasHeight(0);
DataManager dm=new DataManager();
dm.begin();
dm.insert(dlProject);
dm.end();

pojoProject.setCode(dlProject.getCode());
pojoProject.setMemberCode(member.getCode());
pojoProject.setTitle(dlProject.getTitle());
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture> dbarchList=(LinkedList)servletContext.getAttribute("databaseArchitectures");
com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture tmp=null;
for(com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture each:dbarchList)
{
if(each.getCode()==Integer.parseInt(dbms))
{
tmp=each;
break;
}
}
if(tmp==null)System.out.println("database arch not found ");
else System.out.println(tmp.getName());
if(tmp!=null)pojoProject.setDatabaseArchitecture(tmp);
else System.out.println("db arch not found bro");
DateFormat df=new SimpleDateFormat("MM/dd/yyyy");
pojoProject.setDateOfCreation(df.format(date));
df=new SimpleDateFormat("HH:mm:ss");
pojoProject.setTimeOfCreation(df.format(date));
pojoProject.setTables(new LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTable>());
LinkedList sessProjList=(LinkedList)session.getAttribute("projects");
sessProjList.add(pojoProject);
session.setAttribute("currentProject",pojoProject);
LinkedList appProjList=(LinkedList)servletContext.getAttribute("allProjects");
appProjList.add(pojoProject);
return new TMForward("/ProjectView.jsp");
}catch(Exception e)
{
System.out.println(e);
}
return new TMForward("/homepage.jsp");
}

@InjectSession
@InjectApplication
@Path("save")
@Post
public Object saveProject(ProjectPojo projectPojo)
{
Project currentProject=(Project)session.getAttribute("currentProject");
if(currentProject==null)
{
System.out.println("no project to save");
return "No project to save";
}
try
{
DataManager dm=new DataManager();
dm.begin();
List<com.thinking.machines.tmdmodel.dl.Project> dlProjectList=dm.select(com.thinking.machines.tmdmodel.dl.Project.class).where("code").eq(currentProject.getCode()).query();
com.thinking.machines.tmdmodel.dl.Project dlProject=dlProjectList.get(0);
dlProject.setCanvasWidth(projectPojo.getCanvasWidth());
dlProject.setCanvasHeight(projectPojo.getCanvasHeight());
dlProject.setNumberOfTable(projectPojo.getDatabaseTables().size());
dm.update(dlProject);

List<com.thinking.machines.tmdmodel.dl.DatabaseTable> dlTables=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseTable.class).where("projectCode").eq(dlProject.getCode()).query();
List<com.thinking.machines.tmdmodel.dl.DatabaseTableField> dlTableFields;
for(com.thinking.machines.tmdmodel.dl.DatabaseTable eachTable:dlTables)
{
dlTableFields=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseTableField.class).where("tableCode").eq(eachTable.getCode()).query();
for(com.thinking.machines.tmdmodel.dl.DatabaseTableField eachField:dlTableFields)
{
dm.delete(eachField);
}
dm.delete(eachTable);
}
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTable> newDSTables=new LinkedList<>();
currentProject.setTables(newDSTables);

System.out.println("old tables deleted");

LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine> engineList=currentProject.getDatabaseArchitecture().getDatabaseEngines();
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Datatype> datatypeList=currentProject.getDatabaseArchitecture().getDatatypes();

DatabaseTablePojo tablePojo=null;
com.thinking.machines.tmdmodel.services.pojo.DatabaseTable dsTable=null;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField> newDSFields=null;
com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField dsField=null;

com.thinking.machines.tmdmodel.dl.DatabaseTable dlTable=null;
LinkedList<DatabaseTablePojo> tablePojos=projectPojo.getDatabaseTables();
LinkedList<DatabaseTableFieldPojo> tableFieldPojos=null;
com.thinking.machines.tmdmodel.dl.DatabaseTableField dlTableField=null;
for(DatabaseTablePojo eachTablePojo:tablePojos)
{
dlTable=new com.thinking.machines.tmdmodel.dl.DatabaseTable();
dsTable=new com.thinking.machines.tmdmodel.services.pojo.DatabaseTable();
dlTable.setProjectCode(dlProject.getCode());
System.out.println("Project code"+dlTable.getProjectCode());
System.out.println("xloc"+eachTablePojo.getXcor());
System.out.println("yloc"+eachTablePojo.getYcor());
dlTable.setXlocation(eachTablePojo.getXcor());
dsTable.setXcor(eachTablePojo.getXcor());
dlTable.setYlocation(eachTablePojo.getYcor());
dsTable.setYcor(eachTablePojo.getYcor());
dlTable.setName(eachTablePojo.getName());
dsTable.setName(eachTablePojo.getName());
dlTable.setDatabaseEngineCode(Integer.parseInt(eachTablePojo.getEngine()));

for(com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine eachEngine:engineList)
{
if(eachEngine.getCode()==Integer.parseInt(eachTablePojo.getEngine()))
{
dsTable.setEngine(eachEngine);
break;
}
}

dlTable.setNote(eachTablePojo.getNote());
dsTable.setNote(eachTablePojo.getNote());
dlTable.setNumberOfFields(eachTablePojo.getNumberOfFields());
dsTable.setNumberOfFields(eachTablePojo.getNumberOfFields());
dm.insert(dlTable);
dsTable.setCode(dlTable.getCode());
newDSTables.add(dsTable);
System.out.println("Table code:"+dlTable.getCode());
tableFieldPojos=eachTablePojo.getFields();
newDSFields=new LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField>();
for(com.thinking.machines.tmdmodel.services.pojo.DatabaseTableFieldPojo eachFieldPojo:tableFieldPojos)
{
dlTableField=new com.thinking.machines.tmdmodel.dl.DatabaseTableField();
dsField=new com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField();
dlTableField.setTableCode(dlTable.getCode());
dlTableField.setName(eachFieldPojo.getName());
dsField.setName(eachFieldPojo.getName());
if(eachFieldPojo.getDatatype()!=null)
{
dlTableField.setDatabaseArchitectureDataTypeCode(Integer.parseInt(eachFieldPojo.getDatatype()));
for(com.thinking.machines.tmdmodel.services.pojo.Datatype eachDatatype:datatypeList)
{
if(eachDatatype.getCode()==Integer.parseInt(eachFieldPojo.getDatatype()))
{
dsField.setDatatype(eachDatatype);
break;
}
}
}
else 
{
dlTableField.setDatabaseArchitectureDataTypeCode(0);
dsField.setDatatype(datatypeList.get(0));
}

dlTableField.setWidth(eachFieldPojo.getWidth());
dsField.setWidth(eachFieldPojo.getWidth());
dlTableField.setNumberOfDecimalPlaces(eachFieldPojo.getNumberOfDecimalPlaces());
dsField.setNumberOfDecimalPlaces(eachFieldPojo.getNumberOfDecimalPlaces());
dlTableField.setIsPrimaryKey(eachFieldPojo.getIsPrimaryKey());
dsField.setIsPrimaryKey(eachFieldPojo.getIsPrimaryKey());
dlTableField.setIsAutoIncrement(eachFieldPojo.getIsAutoIncrement());
dsField.setIsAutoIncrement(eachFieldPojo.getIsAutoIncrement());
dlTableField.setIsNotNull(eachFieldPojo.getIsNotNull());
dsField.setIsNotNull(eachFieldPojo.getIsNotNull());
dlTableField.setIsUnique(eachFieldPojo.getIsUnique());
dsField.setIsUnique(eachFieldPojo.getIsUnique());
if(eachFieldPojo.getDefaultValue()!=null && eachFieldPojo.getDefaultValue().trim().length()!=0)
{
dlTableField.setDefaultValue(eachFieldPojo.getDefaultValue());
dsField.setDefaultValue(eachFieldPojo.getDefaultValue());
}
else 
{
dlTableField.setDefaultValue("defaultvalue");
dsField.setDefaultValue("defaultValue");
}
if(eachFieldPojo.getCheckConstraints()!=null && eachFieldPojo.getCheckConstraints().trim().length()!=0)
{
dlTableField.setCheckConstraint(eachFieldPojo.getCheckConstraints());
dsField.setCheckConstraints(eachFieldPojo.getCheckConstraints());
}
else 
{
dlTableField.setCheckConstraint("constraint");
dsField.setCheckConstraints("constraint");
}
if(eachFieldPojo.getNote()!=null && eachFieldPojo.getNote().trim().length()!=0)
{
dlTableField.setNote(eachFieldPojo.getNote());
dsField.setNote(eachFieldPojo.getNote());
}
else 
{
dlTableField.setNote("note");
dsField.setNote("note");
}
dm.insert(dlTableField);
newDSFields.add(dsField);
System.out.println("Field code:"+dlTableField.getCode());
}
dsTable.setFields(newDSFields);
}//iterating table pojos

dm.end();






}catch(ValidatorException ve)
{
return ve;
}
catch(DMFrameworkException dmfe)
{
System.out.println(dmfe);
return dmfe;
}


return true;
}

@InjectSession
@Path("getDetails")
public Project getDetails()
{
return (Project)session.getAttribute("currentProject");
}

@InjectSession
@Path("open")
public TMForward openProject(int projectCode)
{
boolean found=false;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Project> projList=(LinkedList<com.thinking.machines.tmdmodel.services.pojo.Project>)session.getAttribute("projects");
for(com.thinking.machines.tmdmodel.services.pojo.Project each:projList)
{
if(each.getCode()==projectCode)
{
session.setAttribute("currentProject",each);
found=true;
break;
}
}
System.out.println(found);
if(found)return new TMForward("/ProjectView.jsp");
else return new TMForward("/homepage.jsp");
}

@InjectSession
@Path("generateScript")
public String generateScript(TableNames tableNames)
{
System.out.println("generate script called");
LinkedList<String> names=tableNames.getNames();
for(String each:names)
{
System.out.println("Table "+each);
}
return "haooooooooooooooooooooooooo";
}

}//class ends
