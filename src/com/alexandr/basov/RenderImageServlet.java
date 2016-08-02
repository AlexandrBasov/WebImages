package com.alexandr.basov;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@WebServlet("/renderImageServlet")
public class RenderImageServlet extends HttpServlet {

    private ImageComparator imageComparator;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        imageComparator = new ImageComparator();

        BufferedImage image1 = ImageIO.read(getServletContext().getResource("/image1.png"));
        BufferedImage image2 = ImageIO.read(getServletContext().getResource("/image2.png"));
        BufferedImage differences = imageComparator.getDifferencesImage(image1, image2);
        ImageIO.write(differences, "PNG", resp.getOutputStream());
    }

    public ImageComparator getImageComparator() {
        return imageComparator;
    }

    public void setImageComparator(ImageComparator imageComparator) {
        this.imageComparator = imageComparator;
    }
}
