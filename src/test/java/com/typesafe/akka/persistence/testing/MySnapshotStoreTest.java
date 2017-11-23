package com.typesafe.akka.persistence.testing;

import akka.persistence.japi.snapshot.JavaSnapshotStoreSpec;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MySnapshotStoreTest extends JavaSnapshotStoreSpec {

  public MySnapshotStoreTest() {
    super(ConfigFactory.parseString(""));
  }

  List<File> storageLocations = new ArrayList<File>();

  {
    storageLocations.add(new File(system().settings().config().getString("akka.persistence.journal.leveldb.dir")));
    storageLocations.add(new File(system().settings().config().getString("akka.persistence.snapshot-store.local.dir")));
  }

  @Test
  public void noop() {}

  public void beforeAll() {
    for (File storageLocation : storageLocations) {
      try {
        FileUtils.deleteDirectory(storageLocation);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    super.beforeAll();
  }


  public void afterAll() {
    super.afterAll();
    for (File storageLocation : storageLocations) {
      try {
        FileUtils.deleteDirectory(storageLocation);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
