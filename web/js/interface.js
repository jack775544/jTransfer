var ajaxConnect;
$(document).ready(function () {
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
        var url = common.buildUrl('edit', {filename: linkname, pwd: pwd});
        $('<a href="' + url + '" target="_blank">Edit</a>').appendTo(jumbo);

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

    ajaxConnect = function() {
        var itemList = $('#items');
        itemList.text("Loading....");
        $.ajax({
            type: "GET",
            url: './ls',
            context: document.body
        }).done(function (r) {
            if (r === 'ERROR: Login Failed'){
                common.logoutTimeout();
            }
            $.get("./pwd", function(a){
                pwd = a;
                $('#pwd').text(a);
                connect(r);

            });
        });
    };

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
            var url = 'get?filename=' + item[0];

            switch (Number(type)) {
                case 1:
                    img = 'img/icons/document.png';
                    //url = 'get.php?filename=' + filename;
                    textType = 'file';
                    break;
                case 2:
                    img = 'img/icons/folder.png';
                    url = '#';
                    textType = 'folder';
                    break;
                default:
                    img = 'img/icons/folder.png';
                    url = '#';
                    textType = 'other';
                    break;
            }
            itemList.append(buildListItem(url, modified, created, filename, size, textType, img));
        }
        $('.file').click(function (e) {
            e.preventDefault();
            var url = this.href;
            var name = this.dataset.name;
            var lightbox = buildLightbox(this.dataset.name, this.href, this.dataset.created, this.dataset.modified, this.dataset.size, this.dataset.linkname);
            $("body").append(lightbox);
        });

        $('.folder').click(function (e) {
            var url = common.buildUrl("./cd", {path: this.dataset.name});
            $.get(url, function(){
                ajaxConnect();
            });
        });
    }

    function buildListItem(url, modified, created, filename, size, textType, img) {
        var tag = "<li><a class='itemlink {5}' href='{0}' data-modified='{1}' data-created='{2}' data-name='{3}' data-size='{4}' data-type='{5}' data-linkname='{3}'><img src='{6}'>{3}</a></li>";
        return tag.format(url, modified, created, filename, size, textType, img);
    }
    
    $('#refresh').click(function(){
        ajaxConnect();
    });

    ajaxConnect();
});