package com.caozj.test.batch;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.caozj.model.RegistryTable;

@Component
public class TestItemReaderListener implements ItemReadListener<RegistryTable> {

  @Override
  public void beforeRead() {
    System.out.println("====before read===========");
  }

  @Override
  public void afterRead(RegistryTable item) {
    System.out.println("====after read===========");
  }

  @Override
  public void onReadError(Exception ex) {
    System.out.println("==== read error===========");
  }

}
