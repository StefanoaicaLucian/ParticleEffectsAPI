
package ro.uvt.api.particles;

import static javax.media.opengl.GL.GL_TRIANGLE_STRIP;

import javax.media.opengl.GL2;

import ro.uvt.api.util.Calculator;
import ro.uvt.api.util.Vertex;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureCoords;

public class Particle implements Comparable<Particle> {

  private GL2 gl;

  private Vertex particlePosition;

  private Vertex leftBottom;
  private Vertex rightBottom;
  private Vertex rightTop;
  private Vertex leftTop;

  private Vertex cameraPosition;

  private double cameraAngle = 0.0f;

  protected Vertex acceleration;

  private float lifespan;

  private float particleRadius;

  private double cameraDistance;

  private Texture texture;

  private float fadeUnit;

  public Particle(GL2 gl, Vertex position, Vertex speed, Vertex acceleration, Vertex cameraPosition, double cameraAngle, Texture texture, float radius,
                  float fade) {
    this.particlePosition = position;
    this.particleRadius = radius;
    computeCornerCoordinates(this.particlePosition, this.particleRadius);

    this.acceleration = acceleration;

    this.cameraPosition = cameraPosition;
    this.cameraAngle = cameraAngle;

    fadeUnit = fade;

    lifespan = 1.0f;

    this.gl = gl;

    this.texture = texture;

    computeCameraDistance();
  }

  public void move() {
    // speed.add(acceleration);
    particlePosition.add(acceleration);
    lifespan -= fadeUnit;
    particleRadius += 0.01f;
  }

  public void draw() {
    computeCornerCoordinates(particlePosition, particleRadius);
    computeCameraDistance();

    gl.glColor4f(0.0f, 0.0f, 0.0f, lifespan);
    drawCorners();
  }

  public boolean died() {
    if (lifespan < 0) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public int compareTo(Particle that) {
    int result = 0;

    if (this.cameraDistance < that.cameraDistance) {
      result = -1;
    } else if (this.cameraDistance > that.cameraDistance) {
      result = 1;
    }
    return result;
  }

  private void computeCornerCoordinates(Vertex center, float particleSize) {
    leftBottom = new Vertex(center.getPositionX(), center.getPositionY(), center.getPositionZ());
    rightBottom = new Vertex(center.getPositionX(), center.getPositionY(), center.getPositionZ());
    rightTop = new Vertex(center.getPositionX(), center.getPositionY(), center.getPositionZ());
    leftTop = new Vertex(center.getPositionX(), center.getPositionY(), center.getPositionZ());

    float sinSize = particleSize * (float) Math.sin(cameraAngle);
    float cosSize = particleSize * (float) Math.cos(cameraAngle);

    Vertex positiveSin = new Vertex(0.0f, 0.0f, sinSize);
    rightBottom.add(positiveSin);
    rightTop.add(positiveSin);

    Vertex negativeSin = new Vertex(0.0f, 0.0f, -1 * sinSize);
    leftBottom.add(negativeSin);
    leftTop.add(negativeSin);

    Vertex positiveCos = new Vertex(cosSize, 0.0f, 0.0f);
    rightBottom.add(positiveCos);
    rightTop.add(positiveCos);

    Vertex negativeCos = new Vertex(-1 * cosSize, 0.0f, 0.0f);
    leftBottom.add(negativeCos);
    leftTop.add(negativeCos);

    Vertex negativeSize = new Vertex(0.0f, -1 * particleSize, 0.0f);
    leftBottom.add(negativeSize);
    rightBottom.add(negativeSize);

    Vertex positiveSize = new Vertex(0.0f, particleSize, 0.0f);
    leftTop.add(positiveSize);
    rightTop.add(positiveSize);
  }

  private void drawCorners() {
    TextureCoords textureCoords = texture.getImageTexCoords();

    float leftTex = textureCoords.left();
    float rightTex = textureCoords.right();
    float topTex = textureCoords.top();
    float bottomTex = textureCoords.bottom();

    gl.glPushMatrix();

    gl.glBegin(GL_TRIANGLE_STRIP);

    gl.glTexCoord2d(rightTex, bottomTex);
    gl.glVertex3f(rightBottom.getPositionX(), rightBottom.getPositionY(), rightBottom.getPositionZ());

    gl.glTexCoord2d(rightTex, topTex);
    gl.glVertex3f(rightTop.getPositionX(), rightTop.getPositionY(), rightTop.getPositionZ());

    gl.glTexCoord2d(leftTex, bottomTex);
    gl.glVertex3f(leftBottom.getPositionX(), leftBottom.getPositionY(), leftBottom.getPositionZ());

    gl.glTexCoord2d(leftTex, topTex);
    gl.glVertex3f(leftTop.getPositionX(), leftTop.getPositionY(), leftTop.getPositionZ());

    gl.glEnd();

    gl.glPopMatrix();
  }

  private void computeCameraDistance() {
    //    Don`t know if I will ever need this code, but i will keep it commented here just in case
    //    Vertex planeCameraVector = Calculator.subtract(cameraPosition, particlePosition);
    //
    //    Vertex pb = Calculator.subtract(rightTop, particlePosition);
    //    Vertex pa = Calculator.subtract(leftTop, particlePosition);
    //    Vertex planeNormal = Calculator.cross(pb, pa);
    //    cameraDistance = Calculator.computePointPlaneDistance(planeCameraVector, planeNormal);

    cameraDistance = Calculator.computeDistance(cameraPosition, particlePosition);
  }

  public void setCameraPosition(Vertex cameraPosition) {
    this.cameraPosition = cameraPosition;
  }

  public void setCameraAngle(double cameraAngle) {
    this.cameraAngle = cameraAngle;
  }
}