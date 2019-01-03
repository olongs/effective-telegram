package com.test.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.test.model.FileInfo;
import com.test.service.IOrgModel;

@WebServlet(urlPatterns = "/downfile")
public class DownFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private IOrgModel orgmodel;
    
    public DownFileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		FileInfo fi = orgmodel.getFileById(new Integer(id));
		if(fi != null)
		{
			response.setCharacterEncoding("utf-8");
			response.setContentType(fi.getContextType());
			response.setHeader("Content-Disposition","attachment; filename="+fi.getName()+"");
			ServletOutputStream sos = response.getOutputStream();
			sos.write(fi.getContent());
			sos.close();
		}
	}

}
