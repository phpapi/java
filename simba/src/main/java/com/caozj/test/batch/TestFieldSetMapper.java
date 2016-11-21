package com.caozj.test.batch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import com.caozj.model.RegistryTable;

public class TestFieldSetMapper implements FieldSetMapper<RegistryTable> {

  @Override
  public RegistryTable mapFieldSet(FieldSet fieldSet) throws BindException {
    RegistryTable t = new RegistryTable();
    t.setId(fieldSet.readInt("id"));
    t.setCode(fieldSet.readString("code"));
    t.setDescription(fieldSet.readString("description"));
    t.setTypeID(fieldSet.readInt("typeID"));
    t.setValue(fieldSet.readString("value"));
    return t;
  }

}
