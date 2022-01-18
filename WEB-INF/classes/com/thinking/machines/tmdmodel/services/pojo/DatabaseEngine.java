package com.thinking.machines.tmdmodel.services.pojo;
public class DatabaseEngine
{
private int code;
private String name;
private int databaseArchitectureCode;
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
public void setDatabaseArchitectureCode(int code)
{
this.databaseArchitectureCode=code;
}
public int getDatabaseArchitectureCode()
{
return this.databaseArchitectureCode;
}
}
