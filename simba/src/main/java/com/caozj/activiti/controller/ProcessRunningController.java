package com.caozj.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 运行中流程
 * 
 * @author caozj
 *
 */
@Controller
@RequestMapping("/processRunning")
public class ProcessRunningController {

  @RequestMapping
  public String list() {
    return "activiti/processRunningList";
  }

}
