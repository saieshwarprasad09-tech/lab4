package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/loginpage")
public class servletDemo extends HttpServlet {

    private static final String URL =
            "jdbc:mysql://localhost:3306/login_db";

    private static final String USER = "root";
    private static final String PASSWORD = "your_mysql_password";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sql = "SELECT * FROM users WHERE username=? AND password=?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    URL, USER, PASSWORD);

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<h2>Login Successful!</h2>");
                out.println("<p>Welcome, " + username + "</p>");
            } else {
                out.println("<h2>Invalid Username or Password</h2>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>Database Error</h2>");
        }
    }
}