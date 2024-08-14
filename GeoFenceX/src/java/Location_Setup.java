import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/Location_Setup"})
public class Location_Setup extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet task</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet task at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Check if the setup button was clicked
        String setupButton = request.getParameter("setupButton");
        if (setupButton != null) {
            Connection con = null;
            PreparedStatement pstmt = null;
            String taskName = request.getParameter("Task-Name");
            String fromtime = request.getParameter("from-time");
            String totime = request.getParameter("to-time");
            String[] days = request.getParameterValues("days");
            String longitude = request.getParameter("longitude");
            String latitude = request.getParameter("latitude");
            String radius = request.getParameter("radius");

            try {
                double longitudeValue = Double.parseDouble(longitude);
                double latitudeValue = Double.parseDouble(latitude);
                double radiusValue = Double.parseDouble(radius);

                // Parse the time input from "HH:MM AM/PM" to "HH:mm:ss" in 24-hour format
                SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
                SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
                Date fromdate = inputFormat.parse(fromtime);
                Date todate = inputFormat.parse(totime);
                String fromTime = outputFormat.format(fromdate);
                String toTime = outputFormat.format(todate);

                // Convert selected days array to a single string
                String selectedDays = String.join(",", days);

                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");
                String sql = "INSERT INTO task (TaskName, fromTime, toTime, Days, Longitude, Latitude, Radius) VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, taskName);
                pstmt.setString(2, fromTime);
                pstmt.setString(3, toTime);
                pstmt.setString(4, selectedDays);
                pstmt.setDouble(5, longitudeValue);
                pstmt.setDouble(6, latitudeValue);
                pstmt.setDouble(7, radiusValue);
                pstmt.executeUpdate();

                RequestDispatcher rd = request.getRequestDispatcher("locationSetup.html");
                rd.forward(request, response);
            } catch (ParseException e) {
                out.println("Error parsing time: " + e.getMessage());
            } catch (Exception e) {
                out.println("Error: " + e.getMessage());
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                    if (out != null) {
                        out.close();
                    }
                } catch (SQLException ex) {
                    out.println("Error in closing resources: " + ex.getMessage());
                }
            }
        } else {
            // Handle other cases, if needed
            out.println("No setup button pressed");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}