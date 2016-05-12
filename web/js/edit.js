$(document).ready(function(){
    var params = common.getParameters();
    var filename = decodeURIComponent(params.filename);
    var location = decodeURIComponent(params.pwd) + '/' + filename;
    $.get(common.buildUrl('get', {filename: location, name:filename}), function (e) {
        $('#editor').text(e);
        initEditor();
    });

    function initEditor() {
        // Create our editor
        var editor = ace.edit("editor");
        var modelist = ace.require("ace/ext/modelist");

        // Make our list of languages
        for (var i = 0; i < modelist.modes.length; i++) {
            var label = modelist.modes[i].caption;
            var name = modelist.modes[i].mode;
            $('<option value="' + name + '">' + label + '</option>').appendTo("#modes");
        }

        // Find the type of the file we are editing, if we can't get it then default to txt
        var fileMode;
        if (filename !== undefined) {
            fileMode = modelist.getModeForPath(filename).mode;
        } else {
            fileMode = modelist.getModeForPath('txt').mode;
        }

        // Create the editor and set the correct dropdown option
        editor.session.setMode(fileMode);
        document.getElementById('modes').value = fileMode;

        /**
         * Event handler for the language dropdown
         */
        $("#modes").change(function () {
            editor.session.setMode(this.value);
        });
    }
});
