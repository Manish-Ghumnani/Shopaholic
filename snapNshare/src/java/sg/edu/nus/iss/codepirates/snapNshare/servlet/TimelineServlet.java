/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.codepirates.snapNshare.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.UUID;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import sg.edu.nus.iss.codepirates.snapNshare.ejb.TimelineEJB;
import sg.edu.nus.iss.codepirates.snapNshare.entity.Photos;

/**
 *
 * @author Prasanna
 */
@WebServlet("/timeline/*")
@MultipartConfig
public class TimelineServlet extends HttpServlet {

    @EJB
    private TimelineEJB timeline;

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String userName = request.getRequestURI().
                substring(request.getRequestURI().lastIndexOf('/')+1,request.getRequestURI().length());
        System.out.println(userName);
        Photos newPhoto = new Photos();
        
        String comment = new String(covertToByteArray(request.getPart("comment")));        
        byte[] file = covertToByteArray(request.getPart("image"));

        newPhoto.setComment(comment);
        newPhoto.setPostedTime(new Timestamp(System.currentTimeMillis()));
        newPhoto.setPostedBy(userName);
        newPhoto.setImage(file);
        newPhoto.setId(UUID.randomUUID().toString());

        timeline.UploadImage(newPhoto);

    }

    private byte[] covertToByteArray(Part p) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int sz = 0;
        try (InputStream is = p.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (sz = bis.read(buffer))) {
                    baos.write(buffer, 0, sz);
                }
                buffer = baos.toByteArray();
            }
        }
        return (buffer);
    }

}
