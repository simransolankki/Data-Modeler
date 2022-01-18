package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;
public class DatabaseTablePojo
{
private String name;
private LinkedList<DatabaseTableFieldPojo> fields;
private String engine;
private String note;
private int numberOfFields;
private int xcor;
private int ycor;
public void setXcor()
{
this.xcor=xcor;
}
public int getXcor()
{
return this.xcor;
}
public void setYcor(int ycor)
{
this.ycor=ycor;
}
public int getYcor()
{
return this.ycor;
}
public void setNumberOfFields(int numberOfFields)
{
this.numberOfFields=numberOfFields;
}
public int getNumberOfFields()
{
return this.numberOfFields;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setFields(LinkedList<DatabaseTableFieldPojo> fields)
{
this.fields=fields;
}
public LinkedList<DatabaseTableFieldPojo> getFields()
{
return this.fields;
}
public void setEngine(String engine)
{
this.engine=engine;
}
public String getEngine()
{
return this.engine;
}
public void setNote(String note)
{
this.note=note;
}
public String getNote()
{
return this.note;
}
}
