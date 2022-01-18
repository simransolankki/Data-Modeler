import com.thinking.machines.dmodel.utility.*;
import com.thinking.machines.dmodel.beans.*;
import com.thinking.machines.dmodel.services.pojo.*;
import com.thinking.machines.tmws.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.SimpleDateFormat;  
public class AddSubscriptionPlan
{
public static void main(String a[])
{
Date date = new Date();  
SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");  
String strDate= formatter.format(date);  
System.out.println(strDate);
System.out.println(date);
try
{
DataManager dataManager=new DataManager();
dataManager.begin();
com.thinking.machines.dmodel.dl.SubscriptionPlan sp=new com.thinking.machines.dmodel.dl.SubscriptionPlan();
sp.setEffectiveFrom(java.sql.Date.valueOf(strDate));
sp.setMonthlyRate(299);
sp.setYearlyRate(2999);
sp.setFreeProjects(10);
sp.setFreeTables(25);
dataManager.insert(sp);
dataManager.end();
System.out.println("Added");	
}catch(com.thinking.machines.dmframework.exceptions.ValidatorException v)
{
System.out.println(v);
}catch(com.thinking.machines.dmframework.exceptions.DMFrameworkException dme)
{
System.out.println(dme.getMessage());
}
}
}