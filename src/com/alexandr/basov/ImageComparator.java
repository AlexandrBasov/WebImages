package com.alexandr.basov;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ImageComparator {

    //Возвращаем изображение в котором выделены отличия между двумя изображениями
    public BufferedImage getDifferencesImage(BufferedImage image1, BufferedImage image2) throws IOException {
        if ((image1.getWidth() != image2.getWidth()) || image1.getHeight() != image2.getHeight()) {
            throw new RuntimeException("Images have different dimensions!");
        }
        BufferedImage differentImage = new BufferedImage(image2.getColorModel(), image2.getRaster(), image2.isAlphaPremultiplied(), null);

        return highlightDifferences(differentImage, groupPixels(getDifferentPixels(image1, image2)));
    }

    //Рисуем крассные квадраты вокруг каждой из групп пикселей
    private BufferedImage highlightDifferences(BufferedImage differentImage, Collection<Collection<Integer[]>> groupsOfPixels) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(differentImage.getColorModel(), differentImage.getRaster(), differentImage.isAlphaPremultiplied(), null);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        for (Collection<Integer[]> group : groupsOfPixels) {
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Integer[] integers : group) {
                if (integers[0] < minX) {
                    minX = integers[0];
                }
                if (integers[1] < minY) {
                    minY = integers[1];
                }
                if (integers[0] > maxX) {
                    maxX = integers[0];
                }
                if (integers[1] > maxY) {
                    maxY = integers[1];
                }
            }
            int width = maxX - minX;
            int height = maxY - minY;
            graphics2D.setColor(Color.red);
            graphics2D.drawRect(minX, minY, width, height);
        }
        return bufferedImage;
    }

    //Групируем пиксели. Точки, которые лежат левее отметки 250 по ширине, попадают в группу 2
    private Collection<Collection<Integer[]>> groupPixels(Collection<Integer[]> differentPixels) {

        Collection<Collection<Integer[]>> groups = new ArrayList<>();
        Collection<Integer[]> groupOne = new ArrayList<>();
        Collection<Integer[]> groupTwo = new ArrayList<>();
        for (Integer[] coordinates : differentPixels) {
            if ((coordinates[1] > 0) && (coordinates[0] < 250)) {
                groupOne.add(coordinates);
            } else if (coordinates[0] > 250) {
                groupTwo.add(coordinates);
            }
        }
        groups.add(groupOne);
        groups.add(groupTwo);
        return groups;
    }

    //Выборка всех пикселей которые у картинок разные
    private Collection<Integer[]> getDifferentPixels(BufferedImage image1, BufferedImage image2) {
        Collection<Integer[]> differentPixels = new ArrayList<>();
        for (int i = 0; i < image2.getWidth(); i++) {
            for (int j = 0; j < image2.getHeight(); j++) {
                if (isDifferentPixelse(image1.getRGB(i, j), image2.getRGB(i, j))) {
                    differentPixels.add(new Integer[]{i, j});
                }
            }
        }
        return differentPixels;
    }

    //Сравнение двух пикселей
    private boolean isDifferentPixelse(int pixelOne, int pixelTwo) {

        int r1 = (pixelOne >> 16) & 0xff;
        int g1 = (pixelOne >>  8) & 0xff;
        int b1 = (pixelOne      ) & 0xff;
        int r2 = (pixelTwo >> 16) & 0xff;
        int g2 = (pixelTwo >>  8) & 0xff;
        int b2 = (pixelTwo      ) & 0xff;

        int difference = (Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2))/3 * 100;

        return difference > 10;
    }
}
