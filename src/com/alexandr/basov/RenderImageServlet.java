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

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String imageName = req.getParameter("image");
        BufferedImage image = ImageIO.read(getServletContext().getResource("/"+imageName));
        ImageIO.write(image, "PNG", resp.getOutputStream());
    }
}
