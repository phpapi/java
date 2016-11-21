package com.caozj.test.batch;

import java.util.List;

import com.caozj.model.RegistryTable;

public class ItemWriter implements org.springframework.batch.item.ItemWriter<RegistryTable> {

  @Override
  public void write(List<? extends RegistryTable> items) throws Exception {
    System.out.println("=================begin write=========================");
    items.forEach((r) -> {
      System.out.println("********************************" + r.toString());
    });
    System.out.println("=================end write=========================");
  }

}
