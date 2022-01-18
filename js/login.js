function submitForm()
{
//TODO perform validations 
var email=$("#loginForm").find("input[name=argument-1]").val();
var password=$("#loginForm").find("input[name=argument-2]").val();
document.getElementById("loginForm").submit();
}

