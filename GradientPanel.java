import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color startColor;
    private Color endColor;
    private boolean vertical;

    public GradientPanel(Color startColor, Color endColor, boolean vertical) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.vertical = vertical;
    }

    public GradientPanel(Color startColor, Color endColor) {
        this(startColor, endColor, true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int width = getWidth();
        int height = getHeight();
        
        GradientPaint gp;
        if (vertical) {
            gp = new GradientPaint(0, 0, startColor, 0, height, endColor);
        } else {
            gp = new GradientPaint(0, 0, startColor, width, 0, endColor);
        }
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
