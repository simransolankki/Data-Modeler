package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;
public class Project
{
private int code;
private int memberCode;
private String title;
private DatabaseArchitecture databaseArchitecture;
private String dateOfCreation;
private String timeOfCreation;
private LinkedList<DatabaseTable> tables;
private int canvasWidth;
private int canvasHeight;
public void setCanvasWidth(int canvasWidth)
{
this.canvasWidth=canvasWidth;
}
public int getCanvasWidth()
{
return this.canvasWidth;
}
public void setCanvasHeight(int canvasHeight)
{
this.canvasHeight=canvasHeight;
}
public int getCanvasHeight()
{
return this.canvasHeight;
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setMemberCode(int memberCode)
{
this.memberCode=memberCode;
}
public int getMemberCode()
{
return this.memberCode;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
public void setDatabaseArchitecture(DatabaseArchitecture databaseArchitecture)
{
this.databaseArchitecture=databaseArchitecture;
}
public DatabaseArchitecture getDatabaseArchitecture()
{
return this.databaseArchitecture;
}
public void setDateOfCreation(String dateOfCreation)
{
this.dateOfCreation=dateOfCreation;
}
public String getDateOfCreation()
{
return this.dateOfCreation;
}
public void setTimeOfCreation(String timeOfCreation)
{
this.timeOfCreation=timeOfCreation;
}
public String getTimeOfCreation()
{
return this.timeOfCreation;
}
public void setTables(LinkedList<DatabaseTable> tables)
{
this.tables=tables;
}
public LinkedList getTables()
{
return this.tables;
}

}
