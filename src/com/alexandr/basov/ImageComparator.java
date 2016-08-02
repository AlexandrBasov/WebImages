package com.alexandr.basov;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageComparator {

    public BufferedImage getDifferencesImage(BufferedImage image1, BufferedImage image2){
        BufferedImage differentImage = null;
        if((image1.getWidth() != image2.getWidth()) || image1.getHeight() != image2.getHeight()){
            throw new RuntimeException("Images have different dimensions!");
        }
        try {
            differentImage = ImageIO.read(new ByteArrayInputStream(((DataBufferByte) image2.getRaster().getDataBuffer()).getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highlightDifferences(differentImage, groupPixels(getDifferentPixels(image1, image2)));
    }

    private BufferedImage highlightDifferences(BufferedImage differentImage, Collection<Collection<Integer[]>> collections) {
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new ByteArrayInputStream(((DataBufferByte) differentImage.getRaster().getDataBuffer()).getData()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Collection<Collection<Integer[]>> groupPixels(Collection<Integer[]> differentPixels){

        Collection<Collection<Integer[]>> groups = new ArrayList<>();
        Collection<Integer[]> groupOne = new ArrayList<>();
        Collection<Integer[]> groupTwo = new ArrayList<>();
        groupOne.add(differentPixels.iterator().next());
        for(int j=0; j<groupOne.size(); j++){
            for(int i=1; i < differentPixels.size(); i++){
                if(distanceBetweenPixels(((Double[])((List)differentPixels).get(i)), ((Double[])((List)groupOne).get(j))) > 50.0){
                    groupTwo.add((Integer[]) ((List) differentPixels).get(i));
                } else {
                    groupOne.add((Integer[]) ((List) differentPixels).get(i));
                }
            }
        }
        groups.add(groupOne);
        groups.add(groupTwo);
        return groups;
    }

    private double distanceBetweenPixels(Double[] pixelOne, Double[] pixelTwo){
        double x = Math.abs(pixelOne[0] - pixelTwo[0]);
        double y = Math.abs(pixelOne[0] - pixelTwo[1]);
        return Math.abs(Math.sqrt(Math.pow(x,2)+Math.pow(y,2)));
    }

    private Collection<Integer[]> getDifferentPixels(BufferedImage image1, BufferedImage image2){
        int[] pixelsFromImageOne = image1.getRGB(0,0,image1.getWidth(), image1.getHeight(), null, 0, image1.getWidth());
        int[] pixelsFromImageTwo = image2.getRGB(0,0,image2.getWidth(), image2.getHeight(), null, 0, image2.getWidth());
        Collection<Integer[]> differentPixels = new ArrayList<>();
        for(int i=0; i<pixelsFromImageOne.length; i++){
            if(isDifferentPixelse(pixelsFromImageOne[i], pixelsFromImageTwo[i])){
                Integer x = i/image1.getWidth();
                Integer y = i/image1.getHeight();
                differentPixels.add(new Integer[]{x,y});
            }
        }
        return differentPixels;
    }

    private boolean isDifferentPixelse(int pixelOne, int pixelTwo) {

        return pixelOne != pixelTwo;
    }
}
