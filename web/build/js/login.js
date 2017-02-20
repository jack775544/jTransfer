$(document).ready(function(){
    document.getElementById("loginform").onsubmit = function(e) {

        document.getElementById("error").style.display = "none";
        var a = document.getElementById("username").value;
        var b = document.getElementById("password").value;

        //If the input is null display error
        if (a==null || a=="") {
            common.displayError("Error: You must enter a username");
            e.preventDefault();
        }
        if (b==null || b=="") {
            common.displayError("Error: You must enter a password");
            e.preventDefault();
        }
    };
});