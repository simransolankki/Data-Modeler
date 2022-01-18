package com.thinking.machines.tmdmodel.services;
import javax.servlet.*;
import javax.servlet.http.*;
import com.thinking.machines.tmws.annotations.*;
import com.thinking.machines.tmws.*;
import com.thinking.machines.tmws.captcha.*;
import com.thinking.machines.dmframework.*;
import com.thinking.machines.dmframework.exceptions.*;
import com.thinking.machines.tmdmodel.services.pojo.*;
import com.thinking.machines.tmdmodel.utilities.*;
import com.thinking.machines.tmdmodel.services.exceptions.*;
@Path("/captcha")
public class CaptchaService
{
HttpSession session;
public void setHttpSession(HttpSession s)
{
System.out.println("setsess");
this.session=s;
}
public HttpSession getHttpSession()
{
System.out.println("getsess");
return this.session;
}
@InjectSession
@Post
@Path("validate")
public Object validate(String captchaCode)
{
System.out.println("I hot called");
System.out.println(this.session);
Captcha captcha=(Captcha)session.getAttribute(Captcha.CAPTCHA_NAME);
if(captcha==null)return false;
if(captcha.isValid(captchaCode))
{
session.removeAttribute(Captcha.CAPTCHA_NAME);
return true;
}
else
{
return false;
}

}
}
