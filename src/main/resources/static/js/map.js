function addPosi(obj, flag) {
	var parent = $("#landInfo");
	var classNameLng = "longitude";
	var classNameLat = "latitude";
	if(flag != null && flag != "undifined" && flag == "children") {
		parent = $("#childLandInfo");
		classNameLng = "childLongitude";
		classNameLat = "childLatitude";
	}
	if($(obj).val() < 3) {
		alert("不能小于三条边");
		return;
	}
	var addValue = $(obj).val() - parent.children().length / 4;
	if(addValue == 0) {
		return;
	}
	parent.html('');
	for(var i = 0; i < $(obj).val(); i++) {
		parent.append('坐标' + (i + 1));
		parent.append($(':<br>经度<input class="' + classNameLng + '"> 纬度 <input class="' + classNameLat + '"><br>'));
	}
}

function addLands(posi, flag) {
	var longitudes = $("input.longitude");
	var latitudes = $("input.latitude");
	var judgePoint = false;
	var name = $("#landName").val();
	if(flag != null && flag != "undifined" && flag == "children") {
		longitudes = $("input.childLongitude");
		latitudes = $("input.childLatitude");
		name = $("#childLandName").val();
		judgePoint = true;
	}
	var pathArray = new Array();
	var param = {
		name: name,
	};
	if(param.name == null || param.name == "undifined" || param.name == "") {
		alert("地块名字不能为空");
		return;
	}
	var isSuccess = true;
	var countSame = 0;
	debugger;
	longitudes.each(function(index, ele) {
		var longiAndLati = new Array()
		longiAndLati[0] = $(longitudes[index]).val();
		longiAndLati[1] = $(latitudes[index]).val();
		pathArray[index] = longiAndLati;
		if(judgePoint) {
			//三个点都在父地块内
			if(!polygonArray[posi].contains(longiAndLati)) {
				alert("子地块的：" + longiAndLati + "不在父地块范围内");
				isSuccess = false;
				return false;
			}
			//三个点都不在父地块的其他子地块内
			if(polygonArray[posi].children != null) {
				for(var i = 0; i < polygonArray[posi].children.length; i++) {
					//边界点重叠除外
					var LngLatArray = polygonArray[posi].children[i].getPath();
					for(var j = 0; j < LngLatArray.length; j++) {
						if(LngLatArray[j].getLng().toString() == longiAndLati[0] && LngLatArray[j].getLat().toString() == longiAndLati[1]) {
							countSame++;
							return;
						}
					}
					if(polygonArray[posi].children[i].contains(longiAndLati)) {
						alert("子地块的：" + longiAndLati + "坐标已经,在父地块的" + polygonArray[posi].children[i].getExtData().name + "中了");
						isSuccess = false;
						return false;
					}
				}
			}
			console.log(countSame);
			param.parent = polygonArray[posi];
			param.level = Number(polygonArray[posi].getExtData().level) + 1;
			$("#childDiv").hide();
		}
	});
	console.log("相同节点个数:" + countSame);
	//当子地块的所有点有重叠地块时，判断是否是与单个地块完全比配(不可创建)，否则是重叠系统中两个地块以上的点(可创建)
	/* if(countSame == pathArray.length){
		if(polygonArray[posi].children != null){
			var LngLatArray = polygonArray[posi].children[i].getPath();
			//比较模块的点的个数，如果小于则是跳过，因为当点
			if(LngLatArray.length < 5){
				
			}
			for(var j = 0; j < LngLatArray.length; j++){
				if(LngLatArray[j].getLng().toString() == longiAndLati[0] && LngLatArray[j].getLat().toString() == longiAndLati[1]){
					countSame ++;
					return ;
				}
			}
		}
		alert("子地块的："+longiAndLati+"坐标已经,在父地块的"+polygonArray[posi].children[i].getExtData().name+"中了");
		isSuccess = false;
		return false;
	} */
	if(isSuccess) {
		if(param.level == null || param.level == "undifined" || param.level == "") {
			param.level = "1";
		}
		param.id = Math.random().toString(36).substr(2);
		save(pathArray, param);
	}
}

function save(pathArray, param) {
	var polygon = new AMap.Polygon({
		path: pathArray,
		isOutline: true,
		borderWeight: 3,
		strokeColor: "#FF33FF",
		strokeWeight: 6,
		strokeOpacity: 0.2,
		fillOpacity: 0.4,
		fillColor: '#1791fc',
		zIndex: 50,
	})
	if(param.parent != null && param.parent != "undefined") {
		if(param.parent.children == null) {
			param.parent.children = new Array;
			param.parent.children[0] = polygon;
		} else {
			param.parent.children[param.parent.children.length] = polygon;
		}
	}
	polygon.setExtData(param);
	polygon.setMap(map);
	polygonArray[polygonArray.length] = polygon;
	// 缩放地图到合适的视野级别
	map.setFitView([polygon]);

	var polyEditor = new AMap.PolyEditor(map, polygon);
	landArray[landArray.length] = polyEditor;
	var posi = landArray.length - 1;

	polygon.on('mouseover', function(event) {})

	polygon.on('mouseout', function(event) {})

	polygon.on('dblclick', function(event) {
		polyEditor.open();
		showDetails(this, posi);
	})

	polygon.on('rightclick', function(event) {
		polyEditor.close();
	})

	polyEditor.on('addnode', function(event) {
		log.info('触发事件：addnode');
	})

	polyEditor.on('adjust', function(event) {
		log.info('触发事件：adjust');
	})

	polyEditor.on('removenode', function(event) {
		log.info('触发事件：removenode');
	})

	polyEditor.on('end', function(event) {
		log.info('触发事件： end');
	})
}

function showDetails(obj, index) {
	var details = '占地面积：' + obj.getArea() + '平方米<br>位置<br>';
	for(var i = 0; i < obj.getPath().length; i++) {
		details += "<div>&nbsp;&nbsp;&nbsp;&nbsp;经度:<input class='editLongitude' value='" + obj.getPath()[i].lng + "' >&nbsp;&nbsp;&nbsp;&nbsp;纬度:<input class='editLatitude' value='" + obj.getPath()[i].lat + "' ></div>";
		if(i != obj.getPath().length - 1) {
			details += "<br>";
		}
	}
	details += "<button class='btn' onclick='updateLandByPaths(" + index + ")'>确定</button> <button class='btn' onclick='addChildrenLand(" + index + ")'>添加子地块</button>";
	$('#landDetails').html(details);
	$('#landDetails').slideToggle();
}

function manageLands() {
	$('#manageMapFence').slideToggle();
	showManage();
}

function showManage() {
	$('#manageMapFence').html('');
	$(landArray).each(function(index, ele) {
		if(ele != null) {
			$('#manageMapFence').append($('<button class="btn" onclick="landArray[' + index + '].open()">开始地块:' + polygonArray[index].getExtData().name + '编辑</button> '));
			$('#manageMapFence').append($('<button class="btn" onclick="updateLand(' + index + ');">结束地块:' + polygonArray[index].getExtData().name + '编辑</button>'));
			$('#manageMapFence').append($('<button class="btn" onclick="removeLand(' + index + ');showManage();">删除地块:' + polygonArray[index].getExtData().name + '</button>'));
		}
	});
}

function removeLand(index) {
	polygonArray[index].setMap(null);
	for(var i = 0; i < landArray.length; i++) {
		if(i == index) {
			polygonArray[index] = null;
			landArray[index] = null;
		}
	}
}

function updateLandByPaths(index) {
	var pathArray = new Array();
	$("input.editLongitude").each(function(index, ele) {
		var longiAndLati = new Array()
		longiAndLati[0] = $($("input.editLongitude")[index]).val();
		longiAndLati[1] = $($("input.editLatitude")[index]).val();
		pathArray[index] = longiAndLati;
	});
	polygonArray[index].setPath(pathArray);
	landArray[index].close();
}

function updateLand(index) {
	landArray[index].close();
	console.log(polygonArray[index].getPath().toString());
}

function addChildrenLand(index) {
	$("#childDiv").show();
	$("#childManage").html('');
	$("#childManage").append("边数<input id='sides' type='text' value='3' style='width: 50px;' onBlur='addPosi(this,\"children\");'>");
	$("#childManage").append("地块名<input id='childLandName' type='text' style='width: 100px;margin-left: 2px;'>");
	$("#childManage").append("<button class='btn' style='margin-top: 5px' onclick='addLands(" + index + ", \"children\");'>添加地块</button>");
}