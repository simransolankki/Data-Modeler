function validateAndSubmit()
{
//TODO Perform validations
var registrationForm=$("#registrationForm");
var captchaCode=registrationForm.find("#captchaField").val().trim();
captchaManager.validate(captchaCode,function(result){
if(result)
{
var member=new Member()
member.firstName=registrationForm.find("#firstName").val();
member.lastName=registrationForm.find("#lastName").val();
member.emailId=registrationForm.find("#inputEmail").val();
member.password=registrationForm.find("#inputPassword").val();
member.mobileNumber=registrationForm.find("#mobileNumber").val();
alert(JSON.stringify(member));
memberManager.createMember(member,function(s){
if(s)
{
var registrationDiv=$("#registrationDiv");
registrationDiv.addClass("d-none");
var registeredDiv=$("#registeredDiv");
registeredDiv.removeClass("d-none");



}
else alert("could not create member")
},
function(e){
//TODO add is-invalid class to all error fields and show error messages
if(e.firstName)
{
registrationForm.find("#firstName").addClass("is-invalid");
registrationForm.find("#firstNameError").html(e.firstName);
}

}
);
}
else
{
var url="/tmdmodel/webservice/captcha?sdwew="+encodeURI((new Date()).getTime());
alert(url);
alert(registrationForm.find("#captchaImage").attr("src",url));
registrationForm.find("#captchaField").addClass("is-invalid");
}
},
function(exception){
alert(exception)
});



}

