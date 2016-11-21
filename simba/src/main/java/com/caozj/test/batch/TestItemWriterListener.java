package com.caozj.test.batch;

import java.util.List;

import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import com.caozj.model.RegistryTable;

@Component
public class TestItemWriterListener implements ItemWriteListener<RegistryTable> {

  @Override
  public void beforeWrite(List<? extends RegistryTable> items) {
    System.out.println("====before writer===========");
  }

  @Override
  public void afterWrite(List<? extends RegistryTable> items) {
    System.out.println("====after writer==========="); 
  }

  @Override
  public void onWriteError(Exception exception, List<? extends RegistryTable> items) {
    System.out.println("====writer error===========");
  }

}
