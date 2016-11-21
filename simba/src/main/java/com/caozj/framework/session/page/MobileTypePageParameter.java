package com.caozj.framework.session.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

@Component
public class MobileTypePageParameter implements PageParameter {

  @Override
  public String getKey() {
    return SessionKey.mobileTypeKey;
  }

  @Override
  public String getValue(HttpServletRequest request, HttpServletResponse response) {
    return DeviceUtils.getCurrentDevice(request).getDevicePlatform().name().toLowerCase();
  }

}
