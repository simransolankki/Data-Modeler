package com.thinking.machines.tmdmodel.services;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmws.TMForward;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmdmodel.services.pojo.*;
import com.thinking.machines.tmdmodel.utilities.*;
import com.thinking.machines.tmdmodel.services.exceptions.*;
@Path("/member")
public class MemberService
{
HttpSession session;
HttpServletRequest request;
ServletContext servletContext;
public void setServletContext(ServletContext servletContext){this.servletContext=servletContext;}
public ServletContext getServletContext(){return this.servletContext;}
public void setHttpRequest(HttpServletRequest rq){this.request=rq;}
public HttpServletRequest getHttpRequest(){return this.request;}
public void setHttpSession(HttpSession s){this.session=s;}
public HttpSession getHttpSession(){return this.session;}
@Path("create")
@Post
public Object createMember(Member m)
{
System.out.println("create member called");
ServiceException se=new ServiceException("");
if(m.getFirstName()==null || m.getFirstName().trim().length()==0)se.addException("firstName","First name field cannot be left empty");
if(m.getLastName()==null || m.getLastName().trim().length()==0)se.addException("lastName","Last name field cannot be left empty");
if(m.getEmailId()==null || m.getEmailId().trim().length()==0)se.addException("emailId","Email Id field cannot be left empty");
if(m.getPassword()==null || m.getPassword().trim().length()==0)se.addException("password","Password field cannot be left empty");
if(m.getMobileNumber()==null || m.getMobileNumber().trim().length()==0)se.addException("mobileNumber","Mobile number field cannot be left empty");
if(se.getExceptions()!=null)return se;

//TODO verify email id by sending verification email
com.thinking.machines.tmdmodel.dl.Member dlm=new com.thinking.machines.tmdmodel.dl.Member();
dlm.setEmailId(m.getEmailId());
String passKey="kittiesarebetterthandogs";
String encPass="";
try
{
encPass=Encryptor.encrypt(m.getPassword(),passKey);
}catch(Exception e){System.out.println("hao yarr"+e);}
dlm.setPassword(encPass);
dlm.setPasswordKey(passKey);
dlm.setFirstName(m.getFirstName());
dlm.setLastName(m.getLastName());
dlm.setMobileNumber(m.getMobileNumber());
dlm.setStatus("a");
dlm.setNumberOfProjects(0);
DataManager dm=new DataManager();
try
{
dm.begin();
dm.insert(dlm);
dm.end();
}
catch(DMFrameworkException dmfe)
{
System.out.println("idhrrrr"+dmfe);
return new ServiceException(dmfe.getMessage());
}
catch(ValidatorException ve)
{
System.out.println("yooo!!!"+ve.getMessage());
System.out.println(ve.getExceptions());
//return new ServiceException(ve.getMessage());
return ve;
}
return true;
}

@Path("login")
@Post
@InjectSession
@InjectRequest
@InjectApplication
public TMForward login(String email,String password)
{
try
{
if(request==null){System.out.println("request object not set");}
request.removeAttribute("errorBean");
DataManager dm=new DataManager();
dm.begin();
List<com.thinking.machines.tmdmodel.dl.Member> dlMembers;
dlMembers=dm.select(com.thinking.machines.tmdmodel.dl.Member.class).where("emailId").eq(email).query();
HashMap<String,String> errorBean=new HashMap<>();
if(dlMembers.size()<=0)
{
errorBean.put("errorMessage","No such email id is registered with any account");
errorBean.put("errorMessageDnone","d-none");
request.setAttribute("errorBean",errorBean);
return new TMForward("/index.jsp");
}
com.thinking.machines.tmdmodel.dl.Member member=dlMembers.get(0);
String pass=Encryptor.decrypt(member.getPassword(),member.getPasswordKey());
if(pass.equals(password))
{
System.out.println("password matches");
session.setAttribute("member",member);
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Project> allProjects=(LinkedList)servletContext.getAttribute("allProjects");
LinkedList<com.thinking.machines.tmdmodel.services.pojo.Project> tmpProjects=new LinkedList<>();
for(com.thinking.machines.tmdmodel.services.pojo.Project each:allProjects)
{
if(each.getMemberCode()==member.getCode())tmpProjects.add(each);
}
session.setAttribute("projects",tmpProjects);
request.removeAttribute("errorBean");
return new TMForward("/homepage.jsp");
}
else
{
System.out.println("password does not  match");
errorBean.put("passwordError","incorrect password");
errorBean.put("passwordDnone","d-none");
request.setAttribute("errorBean",errorBean);
}
}catch(DMFrameworkException dmfe)
{
System.out.println(dmfe);
}
return new TMForward("/index.jsp");
}

@Path("logout")
@InjectSession
public TMForward logout()
{
session.removeAttribute("member");
return new TMForward("/index.jsp");
}

}
