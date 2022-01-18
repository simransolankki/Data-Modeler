package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;
public class ProjectPojo
{
private int canvasHeight;
private int canvasWidth;
private LinkedList<DatabaseTablePojo> databaseTables;
public void setDatabaseTables(LinkedList<DatabaseTablePojo> databaseTables)
{
this.databaseTables=databaseTables;
}
public LinkedList<DatabaseTablePojo> getDatabaseTables()
{
return this.databaseTables;
}
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
}
