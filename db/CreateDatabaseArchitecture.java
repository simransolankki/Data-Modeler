import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmdmodel.dl.*;
import java.sql.*;
public class CreateDatabaseArchitecture
{
public static void main(String aa[])
{
try{  
Class.forName("com.mysql.jdbc.Driver");  
Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306","root","Sahitya123@");  
DatabaseMetaData databaseMetaData=connection.getMetaData();  
DatabaseArchitecture databaseArchitecture=new DatabaseArchitecture();
databaseArchitecture.setName(databaseMetaData.getDatabaseProductName()+databaseMetaData.getDatabaseProductVersion());
databaseArchitecture.setMaxWidthOfColumnName(databaseMetaData.getMaxColumnNameLength());
databaseArchitecture.setMaxWidthOfTableName(databaseMetaData.getMaxTableNameLength());
databaseArchitecture.setMaxWidthOfRelationshipName(0);
DataManager dataManager=new DataManager();
dataManager.begin();
dataManager.insert(databaseArchitecture);  
Statement s=connection.createStatement();
ResultSet rs;
rs=s.executeQuery("show engines");
DatabaseEngine databaseEngine=new DatabaseEngine();
databaseEngine.setDatabaseArchitectureCode(databaseArchitecture.getCode());
while(rs.next()) 
{
databaseEngine.setName(rs.getString("Engine"));       
dataManager.insert(databaseEngine);  
}
rs.close();
connection.close();
DatabaseArchitectureDataType databaseArchitectureDataType=new DatabaseArchitectureDataType();
databaseArchitectureDataType.setDatabaseArchitectureCode(databaseArchitecture.getCode());
rs=databaseMetaData.getTypeInfo();
while(rs.next()) 
{        
databaseArchitectureDataType.setDataType(rs.getString("TYPE_NAME"));
databaseArchitectureDataType.setMaxWidth(rs.getInt("PRECISION"));
databaseArchitectureDataType.setDefaultSize(0);
databaseArchitectureDataType.setMaxWidthOfPrecision(rs.getInt("PRECISION"));
databaseArchitectureDataType.setAllowAutoIncrement(rs.getBoolean("AUTO_INCREMENT"));
System.out.println(databaseArchitectureDataType.getCode()+" "+databaseArchitectureDataType.getDataType()+" "+databaseArchitectureDataType.getMaxWidth()+" "+databaseArchitectureDataType.getMaxWidthOfPrecision()+" "+databaseArchitectureDataType.getAllowAutoIncrement());
dataManager.insert(databaseArchitectureDataType); 
}
dataManager.end();
}catch(ValidatorException e)
{
ExceptionIterator ie;
ExceptionsIterator i=e.getIterator();
while(i.hasNext())
{
ie=i.next();
while(ie.hasNext())
{
System.out.println(ie.property()+" , "+ie.exception());
ie.next();
}
}
}
catch(Exception e)
{
System.out.println(e);
}  
}
}
