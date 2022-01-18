package com.thinking.machines.tmdmodel.tags;
import javax.servlet.jsp.*;
import java.util.*;
import javax.servlet.jsp.tagext.*;
public class AdministratorLoggedOutTagHandler extends TagSupport
{
public int doStartTag()
{
try
{
if(pageContext.getAttribute("member",PageContext.SESSION_SCOPE)==null)
{
return super.EVAL_BODY_INCLUDE;
}
}catch(Exception e)
{
System.out.println(e);
}
return super.SKIP_BODY;
}
public int doEndTag()
{
return super.EVAL_PAGE;
}
}
