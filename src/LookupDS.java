import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.Out;

public class LookupDS extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			 {
		PrintWriter out = null;
		try
		{
	
		
			response.setContentType("text/html");
			out = response.getWriter();
			InitialContext t = new InitialContext();
			String root=request.getParameter("datasource");
			if (null==root) root="";
			if (!getDSJndi(root, t,out))
				out.print("not found");
		
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			out.print(e1.getLocalizedMessage());
		}
		finally
		{
		out.close();
		}
	}
	
	public boolean getDSJndi(String name, Context context, PrintWriter out) throws NamingException {
	     boolean found=false;
		try {
			DataSource ds = (DataSource) context.lookup(name);
	        out.print(name + " connection successful");	      
	        return true;
	    } catch (NotContextException e) {out.print(e.getLocalizedMessage());}
	    
return found;
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			  {
		try {
			doPost(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		   try {
			response.getWriter().print("Error Occured. Message : " + e.getLocalizedMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
	
	}
	}

