package com.caozj.test.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.caozj.model.RegistryTable;

@Component
public class Process implements ItemProcessor<RegistryTable, RegistryTable> {

  @Override
  public RegistryTable process(RegistryTable item) throws Exception {
    item.setValue(item.getCode() + "_" + item.getValue());
    return item;
  }

}
