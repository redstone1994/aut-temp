package com.ljc.utils;

import com.codeborne.selenide.Configuration;

public class DriverInit {

	private static final String URI = "https://www.baidu.com";
	private static final String REMOTEIP="127.0.0.1";

	/**
	 * @return void
	 * @Author Lijc
	 * @Description 初始化浏览器-远程
	 * @Date 11:20 2018/7/11
	 * @Param []
	 **/
	public static void setRemote() {
		Configuration.browser = "chrome";
		Configuration.remote = "http://"+REMOTEIP+":4444/wd/hub";
		Configuration.timeout = 4000;
		Configuration.baseUrl = URI;
		Configuration.startMaximized = true;
	}

	/**
	 * @return void
	 * @Author Lijc
	 * @Description 初始化浏览器-本地
	 * @Date 11:26 2018/7/11
	 * @Param []
	 **/
	public static void setLocal() {
		Configuration.browser = "chrome";
		Configuration.timeout = 4000;
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		Configuration.baseUrl = URI;
		Configuration.startMaximized = true;
	}
	
}
