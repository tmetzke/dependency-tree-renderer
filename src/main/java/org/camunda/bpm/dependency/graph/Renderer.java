package org.camunda.bpm.dependency.graph;

import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.Parser;
import fr.dutra.tools.maven.deptree.extras.JQueryJSTreeRenderer;
import fr.dutra.tools.maven.deptree.extras.JQueryTreeTableRenderer;
import fr.dutra.tools.maven.deptree.extras.VelocityRenderer;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;

public class Renderer {

  public static void main(String... args) {
    try {
      InputType type = InputType.TEXT;
      Reader r = new InputStreamReader(Renderer.class.getClassLoader().getResourceAsStream("dependency-graph.txt"), "UTF-8");
      Parser parser = type.newParser();
      Node tree = parser.parse(r);
      File outputDir = Files.createTempDirectory("depgraph").toFile();
      // create TreeTable
      VelocityRenderer renderer = new JQueryTreeTableRenderer();
      renderer.setOutputDir(outputDir);
      renderer.setFileName("index_treetable.html");
      renderer.visit(tree);
      // create JSTree
      renderer = new JQueryJSTreeRenderer();
      renderer.setOutputDir(outputDir);
      renderer.setFileName("index_jstree.html");
      renderer.visit(tree);
    } catch (Exception e) {
      throw new RuntimeException("Did not work", e);
    }
  }
}