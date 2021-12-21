package fr.poweroff.web.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "/signUp", value = "/sign-up")
public class SignUp extends HttpServlet {
    private Database database;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        this.getServletContext().getRequestDispatcher("/login/signUp.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub

        database = new Database();

        if (request.getParameter("name") != null
                && request.getParameter("Lname") != null
                && request.getParameter("mail") != null
                && request.getParameter("password") != null
                && request.getParameter("born") != null) {

            String name = request.getParameter("name");
            String lname = request.getParameter("Lname");
            String mail = request.getParameter("mail");
            String password = request.getParameter("password");
            String born = request.getParameter("born");

            Connection connec = this.database.loadDatabase();

            try {
                PreparedStatement preparedStatement = connec.prepareStatement("INSERT INTO user(firstname, lastname, email, password_hash, born, level) VALUES(?, ?, ?, ?, ?, ?);");
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lname);
                preparedStatement.setString(3, mail);
                preparedStatement.setString(4, password);
                preparedStatement.setString(5, born);
                preparedStatement.setString(6, String.valueOf(0));

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //redirection si c'est valide
            response.sendRedirect("hello-servlet");
        } else {
            //si ca ne fonctionne pas
            response.sendRedirect("sign-up");
        }
    }


}