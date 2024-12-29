package com.bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/amount")
public class UserAmount extends HttpServlet
{
		
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String tempamount=request.getParameter("amount");
			double amount=Double.parseDouble(tempamount);
			String update="update bank_user_details set User_Amount=? where User_EmailId=?";
			PrintWriter writer=response.getWriter();
			response.setContentType("text/html");
			HttpSession session=request.getSession();
			String emailid=(String) session.getAttribute("emailid");
			double dbamount=(Double) session.getAttribute("amount");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/teca64_advancedjava?user=root&password=12345");
			    if(amount>0)
			    {
			    	if(amount<=dbamount)
			    	{
			    		double updatedamount=dbamount-amount;
			    		PreparedStatement preparedStatement=connection.prepareStatement(update);
						preparedStatement.setDouble(1, updatedamount);
						preparedStatement.setString(2,emailid);
						int res=preparedStatement.executeUpdate();
						if(res>0)
						{
							session.setAttribute("amount", updatedamount);
							RequestDispatcher dispatcher=request.getRequestDispatcher("amount.html");
							dispatcher.include(request, response);
							writer.println("<center><h1>Amount debited Successfully....</h1></center>");
						}
						
			    	}
			    	else
			    	{
			    		RequestDispatcher dispatcher=request.getRequestDispatcher("amount.html");
						dispatcher.include(request, response);
				    	writer.println("<center><h2>Insufficient Amount</h2></center>");
			    	}
			    }
			    else
			    {
			    	RequestDispatcher dispatcher=request.getRequestDispatcher("amount.html");
					dispatcher.include(request, response);
			    	writer.println("<center><h2>Invalid Amount</h2></center>");
			    }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


