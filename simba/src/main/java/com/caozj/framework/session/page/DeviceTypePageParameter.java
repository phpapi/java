package com.caozj.framework.session.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Component;

@Component
public class DeviceTypePageParameter implements PageParameter {

  @Override
  public String getKey() {
    return SessionKey.deviceTypeKey;
  }

  @Override
  public String getValue(HttpServletRequest request, HttpServletResponse response) {
    Device device = DeviceUtils.getCurrentDevice(request);
    String deviceType = "pc";
    if (device.isMobile()) {
      deviceType = "mobile";
    } else if (device.isTablet()) {
      deviceType = "tablet";
    }
    return deviceType;
  }

}
