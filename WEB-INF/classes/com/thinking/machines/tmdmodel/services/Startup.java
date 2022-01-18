package com.thinking.machines.tmdmodel.services;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmdmodel.dl.*;
import com.thinking.machines.tmdmodel.services.pojo.*;
public class Startup
{
private ServletContext servletContext;
private HttpSession session;
public void setHttpSession(HttpSession session)
{
this.session=session;
}
public HttpSession getHttpSession()
{
return this.session;
}
public void setServletContext(ServletContext servletContext)
{
this.servletContext=servletContext;
}
public ServletContext getServletContext()
{
return this.servletContext;
}
@OnStart(0)
@InjectApplication
public void doSomething()
{
try
{
DataManager dm=new DataManager();
dm.begin();
List<com.thinking.machines.tmdmodel.dl.DatabaseEngine> dlEngineList=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseEngine.class).query();
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine> pojoEngineList=new LinkedList<>();
com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine pojode;
for(com.thinking.machines.tmdmodel.dl.DatabaseEngine each:dlEngineList)
{
pojode=new com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine();
pojode.setCode(each.getCode());
pojode.setName(each.getName());
pojode.setDatabaseArchitectureCode(each.getDatabaseArchitectureCode());
System.out.println("database engine-------->>>>>>"+each.getName());
pojoEngineList.add(pojode);
}
this.servletContext.setAttribute("databaseEngines",pojoEngineList);

List<com.thinking.machines.tmdmodel.dl.DatabaseArchitectureDataType> dlDatatypeList=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseArchitectureDataType.class).query();
com.thinking.machines.tmdmodel.services.pojo.Datatype pojoDatatype;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Datatype> pojoDatatypeList=new LinkedList<>();
for(com.thinking.machines.tmdmodel.dl.DatabaseArchitectureDataType each:dlDatatypeList)
{
pojoDatatype=new com.thinking.machines.tmdmodel.services.pojo.Datatype();
pojoDatatype.setCode(each.getCode());
pojoDatatype.setDatabaseArchitectureCode(each.getDatabaseArchitectureCode());
pojoDatatype.setDatatype(each.getDataType());
pojoDatatype.setMaxWidth(each.getMaxWidth());
pojoDatatype.setDefaultSize(each.getDefaultSize());
pojoDatatype.setMaxWidthOfPrecision(each.getMaxWidthOfPrecision());
pojoDatatype.setAllowAutoIncrement(each.getAllowAutoIncrement());
System.out.println("database datatype---------->>>>>>>"+each.getDataType());
pojoDatatypeList.add(pojoDatatype);
}
this.servletContext.setAttribute("datatypes",pojoDatatypeList);

List<com.thinking.machines.tmdmodel.dl.DatabaseArchitecture> dlArchitectureList=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseArchitecture.class).query();
com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture pojoDatabaseArchitecture;
List<com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture> pojoDatabaseArchitectureList=new LinkedList<>();
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine> tmpEngineList;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Datatype> tmpDatatypeList;
for(com.thinking.machines.tmdmodel.dl.DatabaseArchitecture each:dlArchitectureList)
{
pojoDatabaseArchitecture=new com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture();
pojoDatabaseArchitecture.setCode(each.getCode());
pojoDatabaseArchitecture.setName(each.getName());
tmpEngineList=new LinkedList<>();
for(com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine eachEngine:pojoEngineList)
{
if(eachEngine.getDatabaseArchitectureCode()==each.getCode())
{
tmpEngineList.add(eachEngine);
System.out.println("engine added");
}
}
pojoDatabaseArchitecture.setDatabaseEngines(tmpEngineList);
tmpDatatypeList=new LinkedList<>();
for(com.thinking.machines.tmdmodel.services.pojo.Datatype eachDatatype:pojoDatatypeList)
{
if(eachDatatype.getDatabaseArchitectureCode()==each.getCode())
{
tmpDatatypeList.add(eachDatatype);
System.out.println("datatype added");
}
}
pojoDatabaseArchitecture.setDatatypes(tmpDatatypeList);
pojoDatabaseArchitectureList.add(pojoDatabaseArchitecture);

}
this.servletContext.setAttribute("databaseArchitectures",pojoDatabaseArchitectureList);
System.out.println("Database arch list size->"+pojoDatabaseArchitectureList.size());

List<com.thinking.machines.tmdmodel.dl.DatabaseTableField> dlDatabaseTableFieldList=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseTableField.class).query();
List<com.thinking.machines.tmdmodel.dl.DatabaseTable> dlDatabaseTableList=dm.select(com.thinking.machines.tmdmodel.dl.DatabaseTable.class).query();
List<com.thinking.machines.tmdmodel.dl.Project> dlProjectList=dm.select(com.thinking.machines.tmdmodel.dl.Project.class).query();

List<com.thinking.machines.tmdmodel.services.pojo.Project> pojoProjectList=new LinkedList<>();
com.thinking.machines.tmdmodel.services.pojo.Project tmpProject;
com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField tmpDatabaseTableField;
com.thinking.machines.tmdmodel.services.pojo.DatabaseTable tmpDatabaseTable;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTable> tmpTableList;
LinkedList<com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField> tmpFieldList;

for(com.thinking.machines.tmdmodel.dl.Project eachProject:dlProjectList)
{
tmpProject=new com.thinking.machines.tmdmodel.services.pojo.Project();
tmpProject.setCode(eachProject.getCode());
tmpProject.setMemberCode(eachProject.getMemberCode());
tmpProject.setTitle(eachProject.getTitle());
for(com.thinking.machines.tmdmodel.services.pojo.DatabaseArchitecture eachDatabaseArchitecture:pojoDatabaseArchitectureList)
{
if(eachDatabaseArchitecture.getCode()==eachProject.getDatabaseArchitectureCode())
{
tmpProject.setDatabaseArchitecture(eachDatabaseArchitecture);
System.out.println("architecture set for project");
break;
}
}
tmpProject.setDateOfCreation(eachProject.getDateOfCreation().toString());
tmpProject.setTimeOfCreation(eachProject.getTimeOfCreation().toString());
tmpProject.setCanvasWidth(eachProject.getCanvasWidth());
tmpProject.setCanvasHeight(eachProject.getCanvasHeight());
tmpTableList=new LinkedList<>();
for(com.thinking.machines.tmdmodel.dl.DatabaseTable eachTable:dlDatabaseTableList)
{
if(eachTable.getProjectCode()==eachProject.getCode())
{
tmpDatabaseTable=new com.thinking.machines.tmdmodel.services.pojo.DatabaseTable();
tmpFieldList=new LinkedList<>();
for(com.thinking.machines.tmdmodel.dl.DatabaseTableField eachTableField:dlDatabaseTableFieldList)
{
if(eachTableField.getTableCode()==eachTable.getCode())
{
tmpDatabaseTableField=new com.thinking.machines.tmdmodel.services.pojo.DatabaseTableField();
//TODO FILL THIS OBJECTTTTT
tmpDatabaseTableField.setCode(eachTableField.getCode());
tmpDatabaseTableField.setName(eachTableField.getName());
for(com.thinking.machines.tmdmodel.services.pojo.Datatype eachDatatype:pojoDatatypeList)
{
if(eachDatatype.getCode()==eachTableField.getDatabaseArchitectureDataTypeCode())
{
tmpDatabaseTableField.setDatatype(eachDatatype);
break;
}
}
tmpFieldList.add(tmpDatabaseTableField);
}
}
tmpDatabaseTable.setFields(tmpFieldList);
tmpDatabaseTable.setCode(eachTable.getCode());
tmpDatabaseTable.setName(eachTable.getName());
tmpDatabaseTable.setNote(eachTable.getNote());
tmpDatabaseTable.setXcor(eachTable.getXlocation());
tmpDatabaseTable.setYcor(eachTable.getYlocation());
//Set engine
for(com.thinking.machines.tmdmodel.services.pojo.DatabaseEngine eachEngine:pojoEngineList)
{
if(eachEngine.getCode()==eachTable.getDatabaseEngineCode())
{
tmpDatabaseTable.setEngine(eachEngine);
break;
}
}
tmpTableList.add(tmpDatabaseTable);
}
}
tmpProject.setTables(tmpTableList);

pojoProjectList.add(tmpProject);
}
this.servletContext.setAttribute("allProjects",pojoProjectList);
System.out.println("Projects->"+pojoProjectList.size());

}catch(Exception e)
{
System.out.println(e);
}
}
}






