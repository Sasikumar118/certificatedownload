

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadPdfServlet
 */
@WebServlet("/downloadPdf")
public class DownloadPdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = getServletContext().getRealPath("dp/Course.pdf");
        File pdfFile = new File(filePath);

        // Check if the file exists
        if (!pdfFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "PDF file not found");
            return;
        }

        // Set response headers for file download
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=your-file.pdf");
        response.setContentLength((int) pdfFile.length());

        // Write the PDF file to the response output stream
        try (FileInputStream fileInputStream = new FileInputStream(pdfFile);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error downloading file");
            e.printStackTrace();
        }
	}

}
