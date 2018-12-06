//var isWinRT = (typeof Windows === "undefined") ? false : true;
(function() {
    // var isWinRT = (typeof Windows === "undefined") ? false : true;
    var r = new RegExp("(^|(.*?\\/))(PlottingPanel.Include\.js)(\\?|$)"),
        s = document.getElementsByTagName('script'),
        src, m, baseurl = "";
    for(var i=0, len=s.length; i<len; i++) {
        src = s[i].getAttribute('src');
        if(src) {
            var m = src.match(r);
            if(m) {
                baseurl = m[1];
                break;
            }
        }
    }
    function inputLink(inc){
        inc=inc;
        var link = '<' + 'link rel="stylesheet" type="text/css" media="screen,projection" href="' + inc + '"' + '/>';
        document.writeln(link);
    }

    function inputScript(inc){
        inc=inc;
        var script = '<' + 'script type="text/javascript" src=' +inc + '><' + '/script>';
        document.writeln(script);
    }
    inputLink("/css/colorpicker.css");
    inputLink("/css/layout.css");
    inputLink("/css/easyui.css");
    inputLink("/css/zTreeStyle.css");

    inputScript("/js/jquery.min.js");
    inputScript("/js/jquery-ui.js");
    inputScript("/js/jquery.easyui.min.js");

    inputScript("/js/colorpicker.js");
    inputScript("/js/colorpickerEditor.js");
    inputScript("/js/eye.js");
    inputScript("/js/utils.js");
    inputScript("/js/layout.js");

    inputScript("/js/jquery.ztree.core.js");

    inputScript("/js/PlotPanel.js");
    inputScript("/js/StylePanel.js");
    inputScript("/js/TreePanel.js");
})();
