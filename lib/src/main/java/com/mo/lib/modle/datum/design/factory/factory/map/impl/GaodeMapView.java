package com.mo.lib.modle.datum.design.factory.factory.map.impl;


import android.view.View;

import com.mo.lib.modle.datum.design.factory.factory.map.IMapView;


public class GaodeMapView implements IMapView {

	@Override
	public View getView() {
		System.out.println("调用了高德地图的getView");
		return null;
	}

	@Override
	public void setMapType(MapType mapType) {
		System.out.println("调用了高德地图的setMapType");
	}

}
