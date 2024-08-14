import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet("/task")
public class task extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");

            String taskId = request.getParameter("id");
            String query = "SELECT Latitude, Longitude, Radius, fromTime, toTime, Days, TaskName FROM task WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, taskId);
            
            // Log the SQL query being executed
            System.out.println("Executing SQL query: " + pst.toString());
            
            rs = pst.executeQuery();

            if (rs.next()) {
                double latitude = rs.getDouble("Latitude");
                double longitude = rs.getDouble("Longitude");
                double radius = rs.getDouble("Radius");
                String fromTime = rs.getString("fromTime");
                String toTime = rs.getString("toTime");
                String Days = rs.getString("Days");
                String TaskName = rs.getString("TaskName");

                JSONObject json = new JSONObject();
                json.put("Latitude", latitude);
                json.put("Longitude", longitude);
                json.put("Radius", radius);
                json.put("fromTime", fromTime);
                json.put("toTime", toTime);
                json.put("Days", Days);
                json.put("TaskName", TaskName);

                out.print(json.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Geofence data not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while fetching geofence data\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}