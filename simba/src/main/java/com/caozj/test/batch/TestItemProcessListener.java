package com.caozj.test.batch;

import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import com.caozj.model.RegistryTable;

@Component
public class TestItemProcessListener implements ItemProcessListener<RegistryTable, RegistryTable> {

  @Override
  public void beforeProcess(RegistryTable item) {
    System.out.println("====before process===========");
  }

  @Override
  public void afterProcess(RegistryTable item, RegistryTable result) {
    System.out.println("====after process===========");
  }

  @Override
  public void onProcessError(RegistryTable item, Exception e) {
    System.out.println("====process error===========");
  }

}
