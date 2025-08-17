// --- ImagePanel.java ---
// A custom JPanel that can display a background image from a URL.

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imageUrl) {
        try {
            // Load the image from the provided URL
            backgroundImage = new ImageIcon(new URL(imageUrl)).getImage();
        } catch (Exception e) {
            // If loading fails, print an error message
            System.err.println("Failed to load background image from URL: " + imageUrl);
            e.printStackTrace();
            // Set a plain background color as a fallback
            setBackground(new Color(230, 240, 255));
        }
        // Use a null layout to allow for absolute positioning of components
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image, scaled to fill the entire panel
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
