package com.alexandr.basov;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageComparator {

    public BufferedImage getDifferencesImage(BufferedImage image1, BufferedImage image2){
        if((image1.getWidth() != image2.getWidth()) || image1.getHeight() != image2.getHeight()){
            throw new RuntimeException("Images have different dimensions!");
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

    private Collection<int[]> getDifferentPixels(BufferedImage image1, BufferedImage image2){
        int[] pixelsFromImageOne = image1.getRGB(0,0,image1.getWidth(), image1.getHeight(), null, 0, image1.getWidth());
        int[] pixelsFromImageTwo = image2.getRGB(0,0,image2.getWidth(), image2.getHeight(), null, 0, image2.getWidth());
        Collection<int[]> differentPixels = new ArrayList<>();
        for(int i=0; i<pixelsFromImageOne.length; i++){
            if(isDifferentPixelse(pixelsFromImageOne[i], pixelsFromImageTwo[i])){
                int x = i/image1.getWidth();
                int y = i/image1.getHeight();
                differentPixels.add(new int[]{x,y});
            }
        }
        return differentPixels;
    }

    private boolean isDifferentPixelse(int pixelOne, int pixelTwo) {

        return pixelOne != pixelTwo;
    }
}
