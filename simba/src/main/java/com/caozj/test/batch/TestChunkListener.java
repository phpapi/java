package com.caozj.test.batch;

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class TestChunkListener implements ChunkListener {

  @Override
  public void beforeChunk(ChunkContext context) {
    System.out.println("====before chunk===========");

  }

  @Override
  public void afterChunk(ChunkContext context) {
    System.out.println("====after chunk===========");
  }

  @Override
  public void afterChunkError(ChunkContext context) {
    System.out.println("====chunk error===========");
  }

}
