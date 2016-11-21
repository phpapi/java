package com.caozj.test.batch;

import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatListener;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class TestRepeatListener implements RepeatListener {

  @Override
  public void before(RepeatContext context) {
    System.out.println("====before repeat===========");
  }

  @Override
  public void after(RepeatContext context, RepeatStatus result) {
    System.out.println("====after repeat===========");
  }

  @Override
  public void open(RepeatContext context) {
    System.out.println("====open repeat===========");
  }

  @Override
  public void onError(RepeatContext context, Throwable e) {
    System.out.println("====error repeat===========");
  }

  @Override
  public void close(RepeatContext context) {
    System.out.println("====close repeat===========");
  }

}
