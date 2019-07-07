import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NotContextException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xpath.internal.FoundIndex;

public class ListJNDI extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			 {
		PrintWriter out = null;
		try
		{
			response.setContentType("text/html");
			out = response.getWriter();
			InitialContext t = new InitialContext();
			String root=request.getParameter("jndiroot");
			if (null==root) root="";
			if (!discoverJndi(root, t,out))
				out.print("Not able to list the JNDI entries");
			
		
		} 
		catch (Exception e1) {
			// TODO Auto-generated catch block
			out.print(e1.getLocalizedMessage());
		}
		finally
		{
		out.close();
		}
		out.close();
	}
	
	public boolean discoverJndi(String path, Context context, PrintWriter out) throws NamingException {
	     boolean found=false;
		try {
	        NamingEnumeration<NameClassPair> list = (new InitialContext()).list(path);
	    
	        while (list.hasMore()) {
	        	found=true;
	            String name = list.next().getName();
	            String child = path.equals("") ? name : path + "/" + name;
	            out.print(child);
	            discoverJndi(child, context, out);
	        	
	        }
	        
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

