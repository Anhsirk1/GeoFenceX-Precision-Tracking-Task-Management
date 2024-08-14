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

@WebServlet(urlPatterns = {"/profile"})
public class profile extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");

            String profileId = request.getParameter("id");
            String query = "SELECT pname, cname, pemail, cemail, pphno, cphno, ppwd, cpwd FROM pregistration WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, profileId);
            
            rs = pst.executeQuery();
            if (rs.next()) {
                String pname = rs.getString("pname");
                String cname = rs.getString("cname");
                String pemail = rs.getString("pemail");
                String cemail = rs.getString("cemail");
                String pphno = rs.getString("pphno");
                String cphno = rs.getString("cphno");
                String ppwd = rs.getString("ppwd");
                String cpwd = rs.getString("cpwd");
                
                JSONObject json = new JSONObject();
                json.put("pname", pname);
                json.put("cname", cname);
                json.put("pemail", pemail);
                json.put("cemail", cemail);
                json.put("pphno", pphno);
                json.put("cphno", cphno);
                json.put("ppwd", ppwd);
                json.put("cpwd", cpwd);

                out.print(json.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Profile data not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while fetching profile data\"}");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/22r21a05d1", "root", "root");

            String pname = request.getParameter("pname");
            String cname = request.getParameter("cname");
            String pemail = request.getParameter("pemail");
            String cemail = request.getParameter("cemail");
            String pphno = request.getParameter("pphno");
            String cphno = request.getParameter("cphno");
            String ppwd = request.getParameter("ppwd");
            String cpwd = request.getParameter("cpwd");
            String id = request.getParameter("id");

            String query = "UPDATE pregistration SET pname=?, cname=?, pemail=?, cemail=?, pphno=?, cphno=?, ppwd=?, cpwd=? WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, pname);
            pst.setString(2, cname);
            pst.setString(3, pemail);
            pst.setString(4, cemail);
            pst.setString(5, pphno);
            pst.setString(6, cphno);
            pst.setString(7, ppwd);
            pst.setString(8, cpwd);
            pst.setString(9, id);

            int updated = pst.executeUpdate();
            if (updated > 0) {
                JSONObject json = new JSONObject();
                json.put("success", true);
                out.print(json.toString());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Profile data not found\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"An error occurred while updating profile data\"}");
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}