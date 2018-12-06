/* Copyright© 2000 - 2018 SuperMap Software Co.Ltd. All rights reserved.*/
(function () {
    var r = new RegExp("(^|(.*?\\/))(include-web\.js)(\\?|$)"),
        s = document.getElementsByTagName('script'), targetScript;
    for (var i = 0; i < s.length; i++) {
        var src = s[i].getAttribute('src');
        if (src) {
            var m = src.match(r);
            if (m) {
                targetScript = s[i];
                break;
            }
        }
    }

    function inputScript(url) {
        var script = '<script type="text/javascript" src="' + url + '"><' + '/script>';
        document.writeln(script);
    }

    function inputCSS(url) {
        var css = '<link rel="stylesheet" href="' + url + '">';
        document.writeln(css);
    }

    function inArray(arr, item) {
        for (i in arr) {
            if (arr[i] == item) {
                return true;
            }
        }
        return false;
    }

    //加载类库资源文件
    function load() {
        var includes = (targetScript.getAttribute('include') || "").split(",");
        var excludes = (targetScript.getAttribute('exclude') || "").split(",");

        var jQueryInclude = false;
        if (!inArray(excludes, 'example-i18n')) {
            inputScript("/js/jquery.min.js");

            inputScript("/js/i18next.min.js");
            inputScript("/js/jquery-i18next.min.js");

            inputScript("/js/utils.js");
            inputScript("/js/localization.js");
            document.writeln("<script>Localization.initializeI18N('./', function () {Localization.localize();Localization.initGlobal();}); </script>");
            jQueryInclude = true;
        }
        if (inArray(includes, 'jquery') && !jQueryInclude) {
            inputScript("/js/jquery.min.js");
        }

        if (inArray(includes, 'bootstrap')) {
            inputScript("/js/jquery.min.js");
            inputCSS("/css/bootstrap.min.css");
            inputScript("/js/bootstrap.min.js");
        }
        if (inArray(includes, 'bootstrap-css')) {
            inputCSS("/css/bootstrap.min.css")
        }

        if (inArray(includes, 'bootstrap-js')) {
            inputScript("/js/bootstrap.min.js");
        }

        if (inArray(includes, 'jquery-ui')) {
            inputCSS("/css/jquery-ui.css");
            inputScript("/js/jquery-ui.min.js");
        }

        if (inArray(includes, 'template')) {
            inputScript("/js/template-web.js");
        }
        if (inArray(includes, 'randomcolor')) {
            inputScript("/js/randomColor.min.js");
        }
        if (inArray(includes, 'papaparse')) {
            inputScript("/js/papaparse.min.js");
        }
        if (inArray(includes, 'moment')) {
            inputScript("/js/moment.min.js");
            inputScript("/js/zh-cn.js");
        }
        if (inArray(includes, 'bootstrap-datetimepicker')) {
            inputCSS("/css/bootstrap-datetimepicker.min.css");
            inputScript("/js/bootstrap-datetimepicker.min.js");
        }
        if (inArray(includes, 'bootstrap-select')) {
            inputCSS("/css/bootstrap-select.min.css");
            inputScript("/js/bootstrap-select.min.js");
        }
        if (inArray(includes, 'geohash')) {
            inputScript("/js/geohash.js");
        }
        if (inArray(includes, 'dat-gui')) {
            inputScript("/js/dat.gui.min.js");
            datGuiI18N();
        }
        if (inArray(includes, 'admin-lte')) {
            inputCSS("/css/AdminLTE.min.css");
            inputCSS("/css/skin-blue.min.css");
            inputCSS("/css/font-awesome.min.css");
            inputScript("/js/app.min.js");
        }
        if (inArray(includes, 'jquery.scrollto')) {
            inputScript("/js/jquery.scrollTo.min.js");
        }
        if (inArray(includes, 'ace')) {
            inputScript("/js/ace.js");
        }
        if (inArray(includes, 'widgets.alert')) {
            inputScript("/js/widgets.js");
        }

        if (inArray(includes, 'widgets')) {
            inputCSS("/css/css-loader.css");
            inputScript("/js/widgets.js");
        }
        if (inArray(includes, 'zTree')) {
            inputCSS("/css/zTreeStyle.min.css");
            inputScript("/js/jquery.ztree.all.min.js");
        }
        if (inArray(includes, 'jquery-scontextMenu')) {
            inputCSS("/css/jquery.contextMenu.min.css");
            inputScript("/js/jquery.contextMenu.min.js");
        }
        if (inArray(includes, 'colorpicker')) {
            inputScript("/js/jquery.js");
            inputScript("/js/jquery.colorpicker.js");
        }
        if (inArray(includes, 'fileupLoad')) {
            inputScript("/js/jquery.js");
            inputScript("/js/fileupLoad.js");
        }
        if (inArray(includes, 'sticklr')) {
            inputCSS("/css/jquery-sticklr.css");
            inputCSS("/css/icon.css");
        }
        if (inArray(includes, 'responsive')) {
            inputCSS("/css/bootstrap-responsive.min.css");
        }
        if (inArray(includes, 'lazyload')) {
            inputScript("/js/jquery.lazyload.min.js");
        }
        if (inArray(includes, 'i18n')) {
            inputScript("/js/i18next.min.js");
            inputScript("/js/jquery-i18next.min.js");
        }
        if (inArray(includes, 'react')) {
            inputScript("/js/react.production.min.js");
            inputScript("/js/react-dom.production.min.js");
            inputScript("/js/babel.min.js");
        }
        if (inArray(includes, 'vue')) {
            inputScript("/js/vue.min.js");
        }
        if (inArray(includes, 'ionRangeSlider')) {
            inputCSS("/css/ion.rangeSlider.css");
            inputCSS("/css/normalize.css");
            inputCSS("/css/ion.rangeSlider.skinHTML5.css");
            inputScript("/js/ion.rangeSlider.min.js");
        }
        if (inArray(includes, 'plottingPanel')) {
            inputScript("/js/jquery.ztree.core.js");
            inputCSS("/css/zTreeStyle.css");
            inputScript("/js/jquery.easyui.min.js");
            inputCSS("/css/easyui.css");
            inputScript("/js/colorpicker.js");
            inputCSS("/css/colorpicker.css");
        }
    }

    function datGuiI18N() {
        document.writeln("<script>function registerEventListener(evt,fn){" +
            "if(window.attachEvent){window.attachEvent('on'+evt,fn);}" +
            "else{window.addEventListener(evt,fn,false);}" +
            "}</script>");
        document.writeln("<script>registerEventListener('load',function() { " +
            "dat.GUI.TEXT_CLOSED=resources.text_close;dat.GUI.TEXT_OPEN=resources.text_open;" +
            "})</script>")
    }

    load();
    window.isLocal = false;
    window.server = document.location.toString().match(/file:\/\//) ? "http://localhost:8090" : document.location.protocol + "//" + document.location.host;
    window.version = "9.1.0";
    window.preRelease = "";
})();
