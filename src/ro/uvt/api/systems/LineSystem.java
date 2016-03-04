package ro.uvt.api.systems;

import java.util.Random;

import javax.media.opengl.GL2;

import ro.uvt.api.util.Calculator;
import ro.uvt.api.util.Material;
import ro.uvt.api.util.Vertex;

import com.jogamp.opengl.util.texture.Texture;

public class LineSystem extends ParticleSystem {

    private Random rand;

    public LineSystem(GL2 gl, Vertex[] positions, Texture texture,
	    Material material, float systemRadius) {
	super(gl, positions, texture, material, systemRadius);
	rand = new Random();
    }

    @Override
    protected void generateParticleDirectionVector() {
	startPosition = generateRandomPointOnLine(source, destination, 200);
	speed = new Vertex(0.0f, 0.0f, 0.0f);
    }

    private Vertex generateRandomPointOnLine(Vertex left, Vertex right,
	    int scalar) {
	Vertex differenceVector = Calculator.subtract(right, left);
	Vertex stepVector = Calculator.scaleDown(differenceVector, scalar);
	return Calculator.add(source, Calculator.scaleUp(stepVector, rand.nextFloat() * scalar));
    }
}
