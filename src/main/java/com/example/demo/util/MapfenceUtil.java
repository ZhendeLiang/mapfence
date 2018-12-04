package com.example.demo.util;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.dto.LatitudeAndLongitude;

public class MapfenceUtil {
	public static void main(String[] args) {
		System.out.println("isInPolygon:"+isInPolygon("110.35858,21.278144","110.35858,21.278144:110.369717,21.267935:110.375961,21.274685:110.364437,21.284433"));
		
		System.out.println("isInPolygon:"+isInPolygon("110.36373,21.279384","110.35858,21.278144:110.369717,21.267935:110.375961,21.274685:110.364437,21.284433"));
		System.out.println("isInPolygon:"+isInPolygon("110.368451,21.273574","110.35858,21.278144:110.369717,21.267935:110.375961,21.274685:110.364437,21.284433"));
		System.out.println("isInPolygon:"+isInPolygon("110.371691,21.275005","110.35858,21.278144:110.369717,21.267935:110.375961,21.274685:110.364437,21.284433"));
	
		//true
		System.out.println("isCross:"+isCross("110.35858,21.278144:110.369717,21.267935:110.375961,21.274685:110.364437,21.284433","110.356005,21.278944:110.349633,21.269375:110.349095,21.278304:110.354824,21.285633"));
		//true
		System.out.println("isCross:"+isCross("110.356005,21.278944:110.349633,21.269375:110.349095,21.278304:110.354824,21.285633","110.356005,21.278944:110.349633,21.269375:110.362528,21.278464:110.354824,21.285633"));
		//false
		System.out.println("isCross:"+isCross("110.356005,21.278944:110.352851,21.271934:110.362528,21.278464:110.354824,21.285633","110.356005,21.278944:110.349633,21.269375:110.362528,21.278464:110.354824,21.285633"));
	}
	
	/**
	 * 判断两个区域是否相交
	 * @param polyFirst
	 * @param polySecond
	 * @return
	 */
	public static boolean isCross(String polygon, String polygonOther) {
		String[] polygonArray = polygon.split(":");
		GeneralPath generalPath = new GeneralPath();
		for(int i = 0; i < polygonArray.length; i++) {
			String[] latLon = polygonArray[i].split(",");
			if(i == 0) {
				// 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
				generalPath.moveTo(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1]));
			}else {
				// 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
				generalPath.lineTo(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1]));
			}
		}
        // 将几何多边形封闭
		generalPath.closePath();
		
        String[] polygonOtherArray = polygonOther.split(":");
		GeneralPath generalPathOther = new GeneralPath();
		for(int i = 0; i < polygonOtherArray.length; i++) {
			String[] latLon = polygonOtherArray[i].split(",");
			if(i == 0) {
				// 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
				generalPathOther.moveTo(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1]));
			}else {
				// 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
				generalPathOther.lineTo(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1]));
			}
		}
        // 将几何多边形封闭
		generalPathOther.closePath();
        
        Area area = new Area(generalPath);
		area.intersect(new Area(generalPathOther));
		return area.isEmpty();
	}
	
	/**
     * 判断是否在多边形区域内
     * @param point 经纬度点
     * @param latitudeAndLongitude 
     * @return
     */
	public static boolean isInPolygon(String point, String latitudeAndLongitude) {
		String pointLon = point.split(",")[0];
		String pointLat = point.split(",")[1];
		if(StringUtils.isNotBlank(latitudeAndLongitude)) {
			// 将区域各顶点的横纵坐标放到一个点集合里面
			List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
			String[] latLonArray = latitudeAndLongitude.split(":");
			for(String latLonInfo : latLonArray) {
				String[] latLon = latLonInfo.split(",");
				if(new LatitudeAndLongitude(latLon[0], latLon[1]).equals(new LatitudeAndLongitude(pointLon, pointLat))) {
					return true;
				}
				pointList.add(new Point2D.Double(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1])));
			}
			// 将要判断的横纵坐标组成一个点
			return check(new Point2D.Double(Double.parseDouble(pointLon), Double.parseDouble(pointLat)), pointList);
		}
		return false;
    }
 
    /**
     * 一个点是否在多边形内
     * @param point 要判断的点的横纵坐标
     * @param polygon 组成的顶点坐标集合
     * @return
     */
    private static boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        GeneralPath peneralPath = new GeneralPath();
        Point2D.Double first = polygon.get(0);
        // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        peneralPath.closePath();
        // 判断指定的 Point2D 是否在 Shape的边界内
        return peneralPath.contains(point);
    }
}
