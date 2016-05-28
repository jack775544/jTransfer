var common = {};

/**
 * Adds an sprintf-like method to the string object prototype
 * For this to work, this code snippet needs to be included at the start of the javascript
 * Code sourced from here: https://stackoverflow.com/questions/610406/javascript-equivalent-to-printf-string-format
 */
if (!String.prototype.format) {
    String.prototype.format = function() {
        var args = arguments;
        return this.replace(/{(\d+)}/g, function(match, number) {
            return typeof args[number] != 'undefined'
                ? args[number]
                : match
                ;
        });
    };
}

/**
 * Get the parameters from the pages GET args
 * @returns A map of the key value get pairs
 */
common.getParameters = function(){
    var p = {};
    var location = window.location.search.substring(1);
    var args = location.split("&");
    for (var i=0; i<args.length; i++){
        var param = args[i].split("=");
        p[param[0]] = param[1];
    }
    return p;
};

/**
 * Builds a URL from a base and a map of params
 * @param base
 * @param params
 * @returns {string}
 */
common.buildUrl = function(base, params){
    var strParams = $.param(params);
    return base + '?' + strParams;
};

common.logoutTimeout = function(){
    window.location = common.buildUrl('./logout', {'flash': 'Error: SFTP Connection Timeout'});
};

function validateForm () {
    document.getElementById("error").style.display = "none";
    //store the data
    var a=document.forms["loginpart"]["username"].value;
    var b=document.forms["loginpart"]["password"].value;
    var message=document.getElementById("error");

    //If the input is null display error
    if (a==null || a=="")
    {
        document.getElementById("error").innerHTML = "Error: You must enter a username";
        document.getElementById("error").style.display = "block";
        return false;
    }
    if (a.length!=8)
    {
        document.getElementById("error").innerHTML = "Error: Username must be 8 characters";
        document.getElementById("error").style.display = "block";
        return false;
    }

    if (b==null || b=="")
    {
        document.getElementById("error").innerHTML = "Error: You must enter a password";
        document.getElementById("error").style.display = "block";
        return false;
    }





}
