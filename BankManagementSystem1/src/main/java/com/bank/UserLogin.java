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
@WebServlet("/login")
public class UserLogin extends HttpServlet
{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailid=request.getParameter("emailid");
		String temppin=request.getParameter("pin");
		int pin=Integer.parseInt(temppin);
		String select="select * from bank_user_details where User_EmailId=? and User_PIN=?";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/teca64_advancedjava?user=root&password=12345");
			PreparedStatement preparedStatement=connection.prepareStatement(select);
			preparedStatement.setString(1,emailid);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet= preparedStatement.executeQuery();
			PrintWriter writer=response.getWriter();
			response.setContentType("text/html");
			HttpSession session=request.getSession();
			session.setAttribute("emailid",emailid);
			if(resultSet.next())
			{
				double amount=resultSet.getDouble("User_Amount");
				session.setAttribute("amount",amount);
				RequestDispatcher dispatcher=request.getRequestDispatcher("debit.html");
				dispatcher.forward(request, response);
			}else
			{
				RequestDispatcher dispatcher=request.getRequestDispatcher("UserLogin.html");
				dispatcher.forward(request, response);
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

