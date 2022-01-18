package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;

public class DatabaseTable
{
private int code;
private String name;
private String note;
private DatabaseEngine engine;
private LinkedList<DatabaseTableField> fields;
private int numberOfFields;
private int xcor;
private int ycor;
public void setXcor(int xcor)
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
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setNote(String note)
{
this.note=note;
}
public String getNote()
{
return this.note;
}
public void setEngine(DatabaseEngine engine)
{
this.engine=engine;
}
public DatabaseEngine getEngine()
{
return this.engine;
}
public void setFields(LinkedList fields)
{
this.fields=fields;
}
public LinkedList getFields()
{
return this.fields;
}
public void setNumberOfFields(int numberOfFields)
{
this.numberOfFields=numberOfFields;
}
public int getNumberOfFields()
{
return this.numberOfFields;
}

}
