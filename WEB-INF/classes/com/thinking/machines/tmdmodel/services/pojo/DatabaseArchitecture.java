package com.thinking.machines.tmdmodel.services.pojo;
import java.util.*;
public class DatabaseArchitecture
{
private int code;
private String name;
private LinkedList<Datatype> datatypes;
private LinkedList<DatabaseEngine> databaseEngines;
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
public void setDatatypes(LinkedList datatypes)
{
this.datatypes=datatypes;
}
public LinkedList getDatatypes()
{
return this.datatypes;
}
public void setDatabaseEngines(LinkedList databaseEngines)
{
this.databaseEngines=databaseEngines;
}
public LinkedList getDatabaseEngines()
{
return this.databaseEngines;
}

}
