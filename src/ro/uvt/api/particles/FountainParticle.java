
package ro.uvt.api.particles;

import javax.media.opengl.GL2;
import ro.uvt.api.util.Material;
import ro.uvt.api.util.Vertex;
import com.jogamp.opengl.util.texture.Texture;

public class FountainParticle extends Particle {

  private Vertex gravityVector = new Vertex(0.0f, -0.006f, 0.0f);

  private float maxRadius = 0.4f;

  private float radiusStep = 0.01f;

  public FountainParticle(GL2 gl, Vertex position, Vertex speed, Vertex acceleration, Vertex cameraPosition, double cameraAngle, Texture texture, float radius,
                          float fade, Material material) {
    super(gl, position, speed, acceleration, cameraPosition, cameraAngle, texture, radius, fade, material);
  }

  @Override
  public void move() {
    acceleration.add(gravityVector);
    if (particlePosition.getPositionY() <= 0.0f) {
      lifespan = 0.0f;
    }

    if (particleRadius < maxRadius) {
      particleRadius += radiusStep;
    }

    super.move();
  }

  public Vertex getGravityVector() {
    return gravityVector;
  }

  public void setGravityVector(Vertex gravityVector) {
    this.gravityVector = gravityVector;
  }
}