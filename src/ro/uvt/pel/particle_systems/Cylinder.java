
package ro.uvt.pel.particle_systems;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.util.texture.Texture;

import ro.uvt.pel.util.Calculator;
import ro.uvt.pel.util.Material;
import ro.uvt.pel.util.Vertex;

class Cylinder extends ParticleSystem {

  private Vertex source;
  private Vertex destination;
  private float radius;

  Cylinder(Vertex source, Vertex destination, float radius, Texture texture, Material material,
      float fadeQuotient) {
    super(texture, material, fadeQuotient);
    this.radius = radius;
    this.source = source;
    this.destination = destination;
  }

  List<Vertex> generateSpeedVectors(List<Vertex> sources, float speedScalar) {
    List<Vertex> list = new ArrayList<>();
    for (Vertex vertex : sources) {
      Vertex difference = Calculator.subtract(vertex, source);
      Vertex pointInSecondSphere = Calculator.add(destination, difference);
      Vertex movementVector = Calculator.subtract(pointInSecondSphere, vertex);
      list.add(Calculator.scaleUp(movementVector, speedScalar));
    }
    return list;
  }

  List<Vertex> generatePositionVectors(int count) {
    List<Vertex> list = new ArrayList<>();
    for (int i = 1; i <= count; ++i) {
      list.add(Calculator.generateVertexInSphere(source, radius));
    }
    return list;
  }

  Vertex getSource() {
    return source;
  }

  void setSource(Vertex source) {
    this.source = source;
  }

  Vertex getDestination() {
    return destination;
  }

  void setDestination(Vertex destination) {
    this.destination = destination;
  }

  float getRadius() {
    return radius;
  }

  void setRadius(float radius) {
    this.radius = radius;
  }
}
