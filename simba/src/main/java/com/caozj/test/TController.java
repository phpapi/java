package com.caozj.test;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.caozj.annotation.MobileViewAnnotation;
import com.caozj.annotation.TimeAnnotation;
import com.caozj.dubbo.provider.DubboServiceInterface;
import com.caozj.framework.util.data.ThreadDataUtil;
import com.caozj.framework.util.jdbc.Jdbc;
import com.caozj.framework.util.schedule.AsyncUtil;


@Controller
@RequestMapping("/test")
public class TController {

  private static final Log logger = LogFactory.getLog(TController.class);

  @Autowired
  private Jdbc jdbc;

  @Autowired
  private TestService testService;

  @Resource
  private DubboServiceInterface dubboRemoteService;

  @Autowired
  private AsyncUtil asyncUtil;

  private final ConcurrentTaskScheduler ct =
      new ConcurrentTaskScheduler(Executors.newScheduledThreadPool(100));


  @TimeAnnotation(overtime = 30)
  @RequestMapping("testChinese.do")
  public String testChinese(String info, ModelMap model) {
    System.out.println("收到信息:" + info);
    model.put("message", info);
    return "message";
  }

  @TimeAnnotation
  @RequestMapping
  public String testTaglib(String info, ModelMap model) {
    model.put("message", info);
    return "test/taglib";
  }

  @TimeAnnotation(overtime = 70)
  @PostConstruct
  public String init() {
    return "1";
  }

  @RequestMapping("/chatOnly.do")
  public String chatOnly() {
    return "chat/only";
  }

  @RequestMapping("/chatBoth.do")
  public String chatBoth() {
    return "chat/both";
  }

  @RequestMapping
  public String imagePreview() {
    return "test/imagePreview";
  }

  @RequestMapping
  public String uploadImages(MultipartHttpServletRequest request, ModelMap model)
      throws IOException {
    Map<String, MultipartFile> filesMap = request.getFileMap();
    System.out.println(filesMap.keySet().toString());
    for (Map.Entry<String, MultipartFile> entry : filesMap.entrySet()) {
      MultipartFile file = entry.getValue();
      FileUtils.writeByteArrayToFile(
          new File("d:/test/" + System.currentTimeMillis() + file.getOriginalFilename()),
          file.getBytes());
    }
    model.put("message", filesMap.keySet().toString() + "上传成功");
    return "message";
  }

  @RequestMapping
  public String showSlider() {
    return "test/slider";
  }

  @RequestMapping("/showMove.do")
  public String showMove() {
    return "test/move";
  }

  @RequestMapping
  public String dubbo(String info, ModelMap model) {
    String message = dubboRemoteService.test(info);
    model.put("message", message);
    return "message";
  }

  @RequestMapping
  public String cache(String info, ModelMap model) {
    testService.testCache(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String cache4(String info, ModelMap model) {
    testService.testCache4(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String cache3(String info, ModelMap model) {
    testService.testCache3(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String cache2(String info, ModelMap model) {
    testService.testCache2(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String clearCache(ModelMap model) {
    testService.clearCache();
    model.put("message", "clear successfully!!!");
    return "message";
  }

  @RequestMapping
  public String clear(String info, ModelMap model) {
    testService.clear(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String count(ModelMap model) {
    model.put("message", jdbc.queryForLong("select count(*) from menu"));
    return "message";
  }

  @RequestMapping
  public String redisCache(String info, ModelMap model) {
    testService.testRedisCache(info);
    model.put("message", info);
    return "message";
  }

  @RequestMapping
  public String clearRedisCache(ModelMap model) {
    testService.clearRedisCache();
    model.put("message", "clear redis successfully!!!");
    return "message";
  }

  @RequestMapping
  public String react() {
    return "react/show";
  }

  @RequestMapping
  public String reactRichText() {
    return "react/reactRichText";
  }

  @RequestMapping
  public String reactUEditor() {
    return "react/ueditor";
  }

  @RequestMapping
  public String showUEditor() {
    return "ueditor/show";
  }

  @RequestMapping
  public String addJob(ModelMap model) throws SchedulerException {
    ScheduledFuture<?> scheduledFuture = ct.schedule(() -> {
      logger.info("==================exe!!!!!!!");
    }, new CronTrigger("30 0/1 * * * *"));
    model.put("message", "addJob successfully!!!");
    return "message";
  }

  @RequestMapping
  public String async(ModelMap model) throws SchedulerException {
    testService.async();
    for (int i = 0; i < 100; i++) {
      logger.info("async");
    }
    model.put("message", "async successfully!!!");
    return "message";
  }

  @RequestMapping
  public String asyncUtil(ModelMap model) {
    asyncUtil.execute("com.caozj.test.JobExecute", "execute");
    for (int i = 0; i < 100; i++) {
      logger.info("asyncUtil");
    }
    model.put("message", "asyncUtil successfully!!!");
    return "message";
  }

  @RequestMapping
  @MobileViewAnnotation
  public String device(String sessDeviceType, String sessMobileType, ModelMap model) {
    model.put("message", sessDeviceType + "," + sessMobileType);
    return "message";
  }

  @RequestMapping
  public String view(String page) {
    return page;
  }

  @RequestMapping
  public String content(@RequestBody String content, ModelMap model) {
    model.put("message", "receive body:" + content);
    return "message";
  }

  @RequestMapping
  public String thread(String info, ModelMap model) {
    ThreadDataUtil.set("test", info);
    model.put("message", "receive info:****" + testService.threadData() + "****");
    return "message";
  }

}
