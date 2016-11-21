package com.caozj.test;

import com.caozj.framework.util.http.HttpClientUtil;

public class THttp {

  public static void main(String[] args) {
    String url = "http://localhost:8888/simba";
    url += "/test/content.do";
    String xml = "<a>testXML</a>";
    String response = HttpClientUtil.postXML(url, xml);
    System.out.println(response);
  }
}
