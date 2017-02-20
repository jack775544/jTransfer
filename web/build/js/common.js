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

common.displayError = function(message){
    document.getElementById("error").innerHTML = message;
    document.getElementById("error").style.display = "block";
};

common.removeError = function(){
    document.getElementById("error").innerHTML = "";
    document.getElementById("error").style.display = "none";
};