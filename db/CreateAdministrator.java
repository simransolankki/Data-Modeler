import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmdmodel.utilities.*;
import java.util.*;
public class CreateAdministrator
{
public static void main(String gg[])
{
try
{
DataManager dataManager;
dataManager=new DataManager();
dataManager.begin();
com.thinking.machines.tmdmodel.dl.Administrator administrator=new com.thinking.machines.tmdmodel.dl.Administrator();
administrator.setUsername("admin");
administrator.setPassword(Encryptor.encrypt("admin@123","ujjain"));
administrator.setPasswordKey("ujjain");
administrator.setEmailId("khanaamir11@gmail.com");
administrator.setFirstName("Aamir");
administrator.setLastName("Khan");
administrator.setMobileNumber("+914543654757");
dataManager.insert(administrator);
dataManager.end();
}catch(ValidatorException validatorException)
{
System.out.println(validatorException.getMessage());
}catch(DMFrameworkException dmFrameworkException)
{
System.out.println(dmFrameworkException.getMessage());
}
}

}
