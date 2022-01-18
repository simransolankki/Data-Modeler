package com.thinking.machines.tmdmodel.services.pojo;
public class DatabaseTableField
{
private int code;
private String name;
private Datatype datatype;
private int width;
private int numberOfDecimalPlaces;
private boolean isPrimaryKey;
private boolean isAutoIncrement;
private boolean isUnique;
private boolean isNotNull;
private String defaultValue;
private String checkConstraints;
private String note;

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
public void setDatatype(Datatype datatype)
{
this.datatype=datatype;
}
public Datatype getDatatype()
{
return this.datatype;
}
public void setWidth(int width)
{
this.width=width;
}
public int getWidth()
{
return this.width;
}
public void setNumberOfDecimalPlaces(int numberOfDecimalPlaces)
{
this.numberOfDecimalPlaces=numberOfDecimalPlaces;
}
public int getNumberOfDecimalPlaces()
{
return this.numberOfDecimalPlaces;
}
public void setIsPrimaryKey(boolean isPrimaryKey)
{
this.isPrimaryKey=isPrimaryKey;
}
public boolean getIsPrimaryKey()
{
return this.isPrimaryKey;
}
public void setIsAutoIncrement(boolean isAutoIncrement)
{
this.isAutoIncrement=isAutoIncrement;
}
public boolean getIsAutoIncrement()
{
return this.isAutoIncrement;
}
public void setIsUnique(boolean isUnique)
{
this.isUnique=isUnique;
}
public boolean getIsUnique()
{
return this.isUnique;
}
public void setIsNotNull(boolean isNotNull)
{
this.isNotNull=isNotNull;
}
public boolean getIsNotNull()
{
return this.isNotNull;
}
public void setDefaultValue(String defaultValue)
{
this.defaultValue=defaultValue;
}
public String getDefaultValue()
{
return this.defaultValue;
}
public void setCheckConstraints(String checkConstraints)
{
this.checkConstraints=checkConstraints;
}
public String getCheckConstraints()
{
return this.checkConstraints;
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
