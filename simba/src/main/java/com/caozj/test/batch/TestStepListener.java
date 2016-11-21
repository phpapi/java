package com.caozj.test.batch;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class TestStepListener implements StepExecutionListener {

  @Override
  public void beforeStep(StepExecution stepExecution) {
    System.out.println("====before step===========");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    System.out.println("====after step===========");
    return stepExecution.getExitStatus();
  }

}
