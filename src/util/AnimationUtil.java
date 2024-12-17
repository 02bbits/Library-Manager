package util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AnimationUtil {

    public static void changeColor(Color startColor, Color endColor, JComponent component) {
        final int ANIMATION_DURATION = 200;
        final int FRAME_INTERVAL = 16;
        final int totalFrames = ANIMATION_DURATION / FRAME_INTERVAL;

        Timer timer = new Timer(FRAME_INTERVAL, null);
        timer.addActionListener(new AbstractAction() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                float progress = (float) currentFrame / totalFrames;

                int red = (int) (startColor.getRed() + progress * (endColor.getRed() - startColor.getRed()));
                int green = (int) (startColor.getGreen() + progress * (endColor.getGreen() - startColor.getGreen()));
                int blue = (int) (startColor.getBlue() + progress * (endColor.getBlue() - startColor.getBlue()));

                component.setBackground(new Color(red, green, blue));

                currentFrame++;

                if (currentFrame > totalFrames) {
                    component.setBackground(endColor);
                    timer.stop();
                }
            }
        });

        timer.start();
    }

    public static void changeForegroundColor(Color startColor, Color endColor, JComponent component) {
        final int ANIMATION_DURATION = 200;
        final int FRAME_INTERVAL = 16;
        final int totalFrames = ANIMATION_DURATION / FRAME_INTERVAL;

        Timer timer = new Timer(FRAME_INTERVAL, null);
        timer.addActionListener(new AbstractAction() {
            int currentFrame = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                float progress = (float) currentFrame / totalFrames;

                int red = (int) (startColor.getRed() + progress * (endColor.getRed() - startColor.getRed()));
                int green = (int) (startColor.getGreen() + progress * (endColor.getGreen() - startColor.getGreen()));
                int blue = (int) (startColor.getBlue() + progress * (endColor.getBlue() - startColor.getBlue()));

                component.setForeground(new Color(red, green, blue));

                currentFrame++;

                if (currentFrame > totalFrames) {
                    component.setForeground(endColor);
                    timer.stop();
                }
            }
        });

        timer.start();
    }
}
