package com.caozj.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.caozj.framework.util.code.QRCodeUtil;
import com.google.zxing.WriterException;

/**
 * 二维码Controller
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/qrCode")
public class QRCodeController {

  /**
   * 获取二维码图片
   * 
   * @param text
   * @param height
   * @param width
   * @param response
   * @throws IOException
   * @throws WriterException
   */
  @RequestMapping
  public void getQRCode(String text, Integer height, Integer width, HttpServletResponse response)
      throws IOException, WriterException {
    ServletOutputStream sos = response.getOutputStream();
    if (height != null && width != null) {
      QRCodeUtil.writeToStream(text, height, width, sos);
    } else {
      QRCodeUtil.writeToStream(text, sos);
    }
    sos.close();
  }
}
