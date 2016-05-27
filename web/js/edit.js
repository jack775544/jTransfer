$(document).ready(function(){
    var editor;
    var filename;
    var locationPath;

    var params = common.getParameters();
    filename = decodeURIComponent(params.filename);
    locationPath = decodeURIComponent(params.pwd) + '/' + filename;
    console.log(locationPath);
    $.ajax({
        type: "POST",
        url: './exec',
        mimeType: 'text/plain',
        data: {"pwd": locationPath},
        success: function (data) {
            var args = data.split(";");
            var type  = args[1].trim().split("=");

            console.log(type[1]);

            if (type[1] == 'binary') {
                alert('This is a binary file');
            }
        }
    });

    $.get(common.buildUrl('get', {filename: locationPath, name:filename}), function (e) {
        $('#editor').text(e);
        initEditor();
    });

    function initEditor() {
        // Create our editor
        editor = ace.edit("editor");
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

        $('#save').click(saveFile)

        $('#download').click(saveDownload)
    }

    function saveFile(){
        var fileData = editor.getValue();

        $.ajax({
            type: "POST",
            url: common.buildUrl('./raw', {path: locationPath}),
            data: {"contents": fileData},
            success: function () {
                alert('File has been saved');
            }
        });
    }

    function saveDownload(){
        var fileData = editor.getValue();
        $.ajax({
            type: "POST",
            url: common.buildUrl('./raw', {path: locationPath}),
            data: {"contents": fileData},
            success: function () {
                window.location = common.buildUrl('get', {filename: locationPath, name:filename});
            }
        });
    }
});
