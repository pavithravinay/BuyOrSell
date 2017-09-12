package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import bean.Image;
import bean.Result;
import bean.User;
import service.AdvertisementService;

public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private File file ;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String idstr = request.getParameter("id");
    	String adidstr =request.getParameter("adid");
    	Integer id = 0;
    	AdvertisementService service = new AdvertisementService();
		Blob b= null;
    	if(idstr != null){
    	     id = Integer.parseInt(idstr);
    	     b= service.getAdvertisementImage(id);
    	} else if(adidstr != null) {
    	     id = Integer.parseInt(adidstr);
    	     b= service.getAdvertisementImageByAdId(id);
    	}
    	
    	 try {
	    	
			response.setContentType("image/jpeg");
			if(b != null) {
			response.setContentLength( (int) b.length());
		
	        InputStream is = b.getBinaryStream();
	        OutputStream os = response.getOutputStream();
	        byte buf[] = new byte[(int) b.length()];
	        is.read(buf);
	        os.write(buf);
	        os.close();
			}
    	 } catch (Exception e) {
 			e.printStackTrace();
 		}
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        int userId = user.getId();
       // int userId = 2132178509;
        
        AdvertisementService service = new AdvertisementService();
        Image insertedImage = null ;
        Image image = new Image();
        
        try{
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);

            if (! ServletFileUpload.isMultipartContent(request)) {
                System.out.println("sorry. No file uploaded");
                return;
            }
        
        	// parse request
            List items = sfu.parseRequest(request);
           // FileItem  id = (FileItem) items.get(0);
            //String photoid =  id.getString();
            
           // FileItem title = (FileItem) items.get(1);
            //String phototitle =  title.getString();

            Iterator iter = items.iterator();
            Iterator iter1 = items.iterator();
            while (iter.hasNext()) {
            	FileItem item = (FileItem) iter.next();
            	if (item.isFormField()) {

            	      String name = item.getFieldName();//text1
            	      String value = item.getString();
            	      if(name.equals("adId")) {
	            	      Integer adId = Integer.parseInt(value);
	            	      image.setAdId(adId);
            	      }
            	 } 
            }
            
            service.deleteAdvertisementImage(image.getAdId());
            
            while (iter1.hasNext()) {
            	FileItem item = (FileItem) iter1.next();
                String fileName = item.getName();

                if (!item.isFormField()) {
                   
                	 file = new File(fileName);
                     
                     // saves the file to upload directory
                     image.setName(fileName);
                     //image.setFile(item);
                     //image.setOwnerId(ownerId);
                     insertedImage = service.addAdvertisementImage(image, item);
                   //insertedImage = service.addAdvertisementImage(image);
                }
            }
     	
            boolean isSuccess =true;
            String msg = "Image added successfully.";
			if(insertedImage == null) {
				 msg = "Error occured";
				 isSuccess =false;
			}
			Result r = new Result(isSuccess, msg);
			r.setObj(insertedImage);
			response.setContentType("application/json");
			String rStr = new Gson().toJson(r);
			response.getWriter().write(rStr);
			
        }  catch (Exception ex) {
            throw new ServletException(ex);
        }
        response.sendRedirect(response.encodeRedirectURL("my-advertisements?action=postsuccess"));

    }

}