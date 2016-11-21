package com.caozj.test.batch;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

import com.caozj.model.RegistryTable;

@Component
public class TestSkipListener implements SkipListener<RegistryTable, RegistryTable> {

  @Override
  public void onSkipInRead(Throwable t) {
    System.out.println("====read skip===========" + t.getMessage());
  }

  @Override
  public void onSkipInWrite(RegistryTable item, Throwable t) {
    System.out.println("====write skip===========" + t.getMessage() + "," + item.toString());
  }

  @Override
  public void onSkipInProcess(RegistryTable item, Throwable t) {
    System.out.println("====process skip===========" + t.getMessage() + "," + item.toString());
  }

}
