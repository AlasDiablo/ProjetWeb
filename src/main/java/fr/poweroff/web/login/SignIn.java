package fr.poweroff.web.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "/signIn", value = "/sign-in")
public class SignIn extends HttpServlet {
    private Database database;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        this.getServletContext().getRequestDispatcher("/login/signIn.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("mail") != null && request.getParameter("password") != null) {

            String mail = request.getParameter("mail");
            String password = request.getParameter("password");

            database = new Database();

            Connection connec = this.database.loadDatabase();

            try {
                Statement stat = connec.createStatement();

                ResultSet result = stat.executeQuery("SELECT email, password_hash FROM user;");

                boolean existe = false;
                //Récupération des données
                while (result.next()){
                    if(mail.equals(result.getString("email")) && password.equals(result.getString("password_hash"))){
                        existe = true;
                    }
                }

                if(existe){
                    //redirection si c'est valide
                    response.sendRedirect("hello-servlet");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            //si ca ne fonctionne pas
            response.sendRedirect("sign-in");
        }
    }
}
