package com.thinking.machines.tmdmodel.services.exceptions;
import java.io.*;
import java.util.*;
public class ServiceException extends RuntimeException
{
private HashMap<String,String> exceptionMap;
public ServiceException(String m)
{
super(m);
this.exceptionMap=null;
}

public void addException(String n,String v)
{
if(this.exceptionMap==null)this.exceptionMap=new HashMap<String,String>();
this.exceptionMap.put(n,v);
}
public String getException(String n)
{
if(this.exceptionMap==null)return null;
return this.exceptionMap.get(n);
}
public Object getExceptions()
{
return this.exceptionMap;
}
}
