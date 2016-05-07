$(document).ready(function () {
    var ajaxConnect;
    var pwd;

    function buildLightbox(name, url, created, modified, size, linkname) {
        var lightbox = $('<div></div>', {id: 'lightbox'});
        var content = $('<div></div>', {class: 'container'});
        var jumbo = $('<div></div>', {class: 'jumbotron'});
        var titlerow = $('<div class="row"></div>').appendTo(jumbo);
        $('<h2>' + name + '</h2>').appendTo(titlerow);
        var closeSpan = $('<span></span>');
        var closeLink = $('<a id="closeLink">[X]</a>');
        closeLink.appendTo(closeSpan);
        closeSpan.appendTo(titlerow);
        $('<div>Date Created: ' + created + '</div>').appendTo(jumbo);
        $('<div>Last Modified: ' + modified + '</div>').appendTo(jumbo);
        $('<div>Size: ' + size + ' bytes</div>').appendTo(jumbo);
        $('<a href="' + url + '">Download</a>').appendTo(jumbo);
        $('<br>').appendTo(jumbo);
        var editUrl = common.buildUrl('edit', {filename: linkname, pwd: pwd});
        $('<a href="' + editUrl + '" target="_blank">Edit</a>').appendTo(jumbo);

        content.append(jumbo);
        lightbox.append(content);

        content.click(stopProp);
        lightbox.click(closeLightbox);
        closeLink.click(closeLightbox);
        return lightbox;
    }

    function stopProp(e) { // and roll!
        e.stopPropagation();
    }

    function closeLightbox(e) {
        e.stopPropagation();
        $("#lightbox").remove();
    }
    
    ajaxConnect = function(){
        var itemList = $('#items');
        itemList.text("Loading....");

        var params = common.getParameters();
        if (params.pwd === undefined){
            $.get("./pwd", function(a) {
                pwd = a;
                history.replaceState(a, "", common.buildUrl("files", {pwd: a}));
                getFileList();
            });
        } else {
            pwd = decodeURIComponent(params.pwd);
            getFileList();
        }
    };

    function getFileList(){
        $.get(common.buildUrl("./ls", {path: pwd}), function (r) {
            if (r === 'none'){
                //common.logoutTimeout();
            } else {
                pwd = r.path;
                history.replaceState(r.path, "", common.buildUrl("files", {pwd: r.path}));
                connect(r);
            }
        });
    }

    function connect(r) {
        var itemList = $('#items');
        itemList.text("");
        for (var i = 0; i < r.items.length; i++) {
            var item = r.items[i].filename;
            var size = item[3];
            var type = item[4]; // 1 is file, 2 is folder, 3 is symlink, 4 is other
            var created = item[2];
            var modified = item[1];
            var filename = item[0];
            var textType = 'type';
            var img = 'img';
            var path = pwd + "/" + filename;
            //var url = 'get?filename=' + path;
            var url = common.buildUrl('./get', {filename: path, name: filename});

            switch (Number(type)) {
                case 1:
                    img = 'img/icons/document.png';
                    //url = 'get.php?filename=' + filename;
                    textType = 'file';
                    break;
                case 2:
                    img = 'img/icons/folder.png';
                    url = '?pwd=' + encodeURIComponent(pwd) + "%2F" + filename;
                    textType = 'folder';
                    break;
                default:
                    img = 'img/icons/folder.png';
                    url = '#';
                    textType = 'other';
                    break;
            }
            itemList.append(buildListItem(url, modified, created, filename, size, textType, img, path));
        }
        $('.file').click(function (e) {
            e.preventDefault();
            var lightbox = buildLightbox(this.dataset.name, this.href, this.dataset.created, this.dataset.modified, this.dataset.size, this.dataset.linkname);
            $("body").append(lightbox);
        });

        $('.folder').click(function (e) {
            e.preventDefault();
            history.pushState(this.dataset.path, "", common.buildUrl("files", {pwd: this.dataset.path}));
            ajaxConnect();
        });
    }

    function buildListItem(url, modified, created, filename, size, textType, img, path) {
        var tag = "<li><a class='itemlink {5}' href='{0}' data-modified='{1}' data-created='{2}' data-name='{3}' data-size='{4}' data-type='{5}' data-linkname='{3}' data-path='{7}'><img src='{6}'>{3}</a></li>";
        return tag.format(url, modified, created, filename, size, textType, img, path);
    }
    
    $('#refresh').click(function(){
        ajaxConnect();
    });

    window.addEventListener('popstate', function(e) {
        ajaxConnect();
    });
    
    ajaxConnect();
});