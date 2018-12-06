var landBlockArray = new Array;

/**
 * 添加父地块边数信息
 * */
function chooseSize(obj) {
	var parent = $("#landInfo");
	var classNameLng = "longitude";
	var classNameLat = "latitude";
	//地块边数不能小于3
	if($(obj).val() < 3) {
		alert("不能小于三条边");
		return;
	}
	var addValue = $(obj).val() - parent.children().length / 4;
	if(addValue == 0) {
		return;
	}
	parent.html('');
	//增加填入边数
	for(var i = 0; i < $(obj).val(); i++) {
		parent.append('坐标' + (i + 1));
		parent.append($(':<br>经度<input class="' + classNameLng + '"> 纬度 <input class="' + classNameLat + '"><br>'));
	}
}

/**
 * 添加父地块
 * */
function addLandBlock() {
	var longitudes = $("input.longitude");
	var latitudes = $("input.latitude");
	var pathArray = new Array();
	var pathString = '';
	//遍历所有经纬度
	var isEmpty = false;
	if(verify()){
		return false;
	}
	longitudes.each(function(index, ele) {
		var longiAndLati = new Array()
		longiAndLati[0] = $(longitudes[index]).val();
		longiAndLati[1] = $(latitudes[index]).val();
		if(typeof(longiAndLati[0]) == "undefined" || longiAndLati[0] == "" || typeof(longiAndLati[1]) == "undefined" || longiAndLati[1] == ""){
			isEmpty = true;
			return false;
		}
		pathArray[index] = longiAndLati;
		pathString += longiAndLati[0]+","+longiAndLati[1]+":";
	});
	if(isEmpty){
		return "经纬度不能为空";
	}
	var landBlock = {
		name : $("#name").val(),
		type : $("#type").val(),
		latitudeAndLongitude : pathString,
		jsonData : $("#jsonData").val(),
		status : $("#status").val(),
		orderNo : $("#orderNo").val(),
	}
	console.log(JSON.stringify(landBlock));
	$.ajax({
	  type: 'POST',
	  url: '/landBlock',
	  data: JSON.stringify(landBlock),
	  dataType: "json",
      contentType: "application/json",
	  success: function(data){
		  if(data.code == '0'){
			  create(pathArray, landBlock);
		  }
		  alert(data.msg);
	  },
	  error: function() {
		  alert("error");
	  }
	});
}

function verify(){
	var name = $("#name").val();
	if(typeof(name) == "undefined" || name == ""){
		alert('地块名称不能为空');
		return false;
	}
	/*var type = $("#type").val();
	if(typeof(type) == "undefined" || type == ""){
		alert('地块类型不能为空');
		return false;
	}
	var jsonData = $("#jsonData").val();
	if(typeof(jsonData) == "undefined" || jsonData == ""){
		alert('地块数据不能为空');
		return false;
	}
	var status = $("#status").val();
	if(typeof(status) == "undefined" || status == ""){
		alert('地块状态不能为空');
		return false;
	}
	var orderNo = $("#orderNo").val();
	if(typeof(orderNo) == "undefined" || orderNo == ""){
		alert('地块排序号不能为空');
		return false;
	}*/
}

/**
 * 在地图上生成地块信息
 * @param pathArray
 * @param param
 * @returns
 */
function create(pathArray, param) {
	debugger;
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
	polygon.setExtData(landBlock);
	polygon.setMap(map);
	// 缩放地图到合适的视野级别
	map.setFitView([polygon]);
	var polygonEditor = new AMap.PolyEditor(map, polygon);
	var landBlock = new Object();
	landBlock.id = param.id;
	landBlock.polygon = polygon;
	landBlock.polygonEditor = polygonEditor;
	landBlockArray[landBlockArray.length] = landBlock;
	var posi = landBlockArray.length - 1;

	polygon.on('mouseover', function(event) {})

	polygon.on('mouseout', function(event) {})

	polygon.on('dblclick', function(event) {
		debugger;
		polygonEditor.open();
	})
	
	polygon.on('click', function(event) {
		debugger;
		console.log('click');
	})

	polygon.on('rightclick', function(event) {
		polygonEditor.close();
	})

	polygonEditor.on('addnode', function(event) {
		log.info('触发事件：addnode');
	})

	polygonEditor.on('adjust', function(event) {
		log.info('触发事件：adjust');
	})

	polygonEditor.on('removenode', function(event) {
		log.info('触发事件：removenode');
	})

	polygonEditor.on('end', function(event) {
		log.info('触发事件： end');
	})
}

