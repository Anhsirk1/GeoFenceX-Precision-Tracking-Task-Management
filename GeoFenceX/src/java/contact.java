import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

@WebServlet("/contact")
public class contact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Recipient's email ID needs to be mentioned
        String to = "geofencexcare@gmail.com"; // Replace with your recipient email address

        // Sender's email ID needs to be mentioned
        String from = email; // Sender's email address

        // Gmail SMTP configuration
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");

        // Get Session object with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("geofencexuser1@gmail.com", "essa osqb woyn klut");
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage mimeMessage = new MimeMessage(session);

            // Set From: header field of the header
            mimeMessage.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            mimeMessage.setSubject("New Contact Form Submission from GeoFenceX");

            // Now set the actual message
            mimeMessage.setText("Name: " + name + "\nEmail: " + email + "\nMessage: " + message);

            // Send message
            Transport.send(mimeMessage);
            System.out.println("Sent message successfully....");
            response.sendRedirect("contact-success.html"); // Redirect to a success page
        } 
        catch (MessagingException mex) {
            mex.printStackTrace(); // Print stack trace for debugging
            System.out.println("Failed to send message: " + mex.getMessage());
    // You can add more detailed logging or error handling here
        }
    }
}
