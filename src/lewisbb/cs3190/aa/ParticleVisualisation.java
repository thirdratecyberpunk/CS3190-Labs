package lewisbb.cs3190.aa;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;

class Surface extends JPanel
{
    double[][] swarmPositions;

    private void doDrawing(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;

        if (swarmPositions != null)
        {
            double scale = 300;
            for (int i = 0; i < swarmPositions.length; i++)
            {
                double[] particlePosition = swarmPositions[i];
                Color colour = colours[i];
                g2d.setColor(colour);
                g2d.fillOval((int)(particlePosition[0] * scale), (int)(particlePosition[1] * scale), 10, 10);
            }
        }
    }

    private Color[] colours = new Color[]{
            new Color(255, 0, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(255, 125, 0),
            new Color(255, 0, 255),
            new Color(0, 255, 255),
            new Color(230, 25, 75),
            new Color(60, 180, 75),
            new Color(255, 225, 25),
            new Color(0, 130, 200),
            new Color(245, 130, 48),
            new Color(145, 30, 180),
            new Color(70, 240, 240),
            new Color(240, 50, 230),
            new Color(210, 245, 60),
            new Color(250, 190, 190),
            new Color(0, 128, 128),
            new Color(230, 190, 255),
            new Color(170, 110, 40),
            new Color(255, 250, 200),
            new Color(128, 0, 0),
            new Color(170, 255, 195),
            new Color(128, 128, 0),
            new Color(255, 215, 180)
    };

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        doDrawing(g);
    }

    /**
     * updates positions of particles in a swarm
     * @param swarm
     */
    public void updatePositions(Swarm swarm)
    {
    	int swarmSize = swarm.getSwarmSize();
        double[][] swarmPositions = new double[swarmSize][];
        for (int i = 0; i < swarmSize; i++)
        {
            swarmPositions[i] = swarm.getParticleAtLocation(i).getPosition();
        }
        this.swarmPositions = swarmPositions;
        repaint();
    }
}

class BasicEx extends JFrame
{
    private Surface surface;

    public BasicEx()
    {
        initUI();
    }

    private void initUI()
    {
        surface = new Surface();
        add(surface);

        setTitle("Particle Swarm Optimisation Antenna Array Solution Visualisation");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void updatePositions(Swarm swarm)
    {
        surface.updatePositions(swarm);
    }
}



