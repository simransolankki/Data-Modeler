package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;
public class TableComponentPojo
{
private Point point;
private LinkedList<DatabaseTablePojo> databaseTables;
public void setPoint(Point point)
{
this.point=point;
}
public Point getPoint()
{
return this.point;
}
public void setDatabaseTables(LinkedList<DatabaseTablePojo> databaseTables)
{
this.databaseTables=databaseTables;
}
public LinkedList<DatabaseTablePojo> getDatabaseTables()
{
return this.databaseTables;
}
}
