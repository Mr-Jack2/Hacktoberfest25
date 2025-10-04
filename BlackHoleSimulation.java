/**
 * BlackHoleSimulation.java
 *
 * Description:
 * A graphical Java program simulating particles falling into a black hole.
 * Demonstrates advanced Java features:
 *   - Swing GUI for visualization
 *   - OOP design with Particle and Panel classes
 *   - Virtual threads (Java 21+) for lightweight concurrency
 *
 * Requirements:
 *   - Java 21 or higher (for virtual threads support)
 *
 * Usage:
 *   javac BlackHoleSimulation.java
 *   java BlackHoleSimulation
 *
 * Features:
 *   - Configurable number of particles
 *   - Particles rotate and fall into the black hole
 *   - Automatically closes the GUI after all particles have fallen
 *   - Colorful particle visualization
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlackHoleSimulation {

    static final int NUM_PARTICLES = 500;           // Total number of particles
    static final int WINDOW_WIDTH = 800;           // Window width
    static final int WINDOW_HEIGHT = 600;          // Window height
    static final int BLACK_HOLE_SIZE = 60;         // Diameter of black hole
    static final int PARTICLE_SIZE = 5;            // Diameter of each particle
    static final double PARTICLE_SPEED_MIN = 1.0;  // Minimum particle speed
    static final double PARTICLE_SPEED_MAX = 3.0;  // Maximum particle speed
    static final double PARTICLE_ROTATION_STEP = 0.05; // Rotation angle per move
    static final int TIMER_DELAY = 30;             // ms per repaint
    static final int PARTICLE_THREAD_DELAY = 50;   // ms per particle movement update

    /**
     * Represents a particle moving towards the black hole.
     */
    static class Particle {
        double angle;     // Current angle for rotation
        double distance;  // Current distance from black hole center
        double speed;     // Distance reduction per step
        Color color;      // Particle color

        Particle() {
            angle = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);
            distance = ThreadLocalRandom.current().nextDouble(50, 250);
            speed = ThreadLocalRandom.current().nextDouble(PARTICLE_SPEED_MIN, PARTICLE_SPEED_MAX);
            color = new Color(ThreadLocalRandom.current().nextFloat(),
                    ThreadLocalRandom.current().nextFloat(),
                    ThreadLocalRandom.current().nextFloat());
        }

        /**
         * Updates particle position by reducing distance and rotating slightly.
         */
        void move() {
            distance -= speed;
            if (distance < 0) distance = 0;
            angle += PARTICLE_ROTATION_STEP;
        }

        /**
         * Returns X coordinate for drawing.
         */
        int getX(int centerX) {
            return (int) (centerX + distance * Math.cos(angle));
        }

        /**
         * Returns Y coordinate for drawing.
         */
        int getY(int centerY) {
            return (int) (centerY + distance * Math.sin(angle));
        }

        /**
         * Checks if the particle has reached the black hole.
         */
        boolean hasFallen() {
            return distance <= 0;
        }
    }

    /**
     * Custom JPanel to draw black hole and particles.
     */
    static class BlackHolePanel extends JPanel {
        java.util.List<Particle> particles;

        BlackHolePanel(java.util.List<Particle> particles) {
            this.particles = particles;
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int centerX = WINDOW_WIDTH / 2;
            int centerY = WINDOW_HEIGHT / 2;

            // Draw black hole
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillOval(centerX - BLACK_HOLE_SIZE / 2,
                    centerY - BLACK_HOLE_SIZE / 2,
                    BLACK_HOLE_SIZE,
                    BLACK_HOLE_SIZE);

            // Draw active particles
            for (Particle p : particles) {
                if (!p.hasFallen()) {
                    g2d.setColor(p.color);
                    g2d.fillOval(p.getX(centerX),
                            p.getY(centerY),
                            PARTICLE_SIZE,
                            PARTICLE_SIZE);
                }
            }
        }
    }

    public static void main(String[] args) {
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < NUM_PARTICLES; i++) {
            particles.add(new Particle());
        }

        JFrame frame = new JFrame("Black Hole Simulation");
        BlackHolePanel panel = new BlackHolePanel(particles);
        frame.add(panel);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start particle movement using virtual threads
        particles.forEach(p -> Thread.startVirtualThread(() -> {
            while (!p.hasFallen()) {
                p.move();
                try {
                    Thread.sleep(PARTICLE_THREAD_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));

        Timer timer = new Timer(TIMER_DELAY, e -> {
            panel.repaint();
            if (particles.stream().allMatch(Particle::hasFallen)) {
                ((Timer) e.getSource()).stop();
                frame.dispose();
                System.out.println("All particles have fallen into the black hole!");
            }
        });
        timer.start();
    }
}
