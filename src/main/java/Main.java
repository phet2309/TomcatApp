import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Main")
public class Main extends HttpServlet {
    public static StringBuilder generateBasePage() {
        StringBuilder htmlStr = new StringBuilder();

        htmlStr.append("<html>\n");
        htmlStr.append("<head>\n");
        htmlStr.append("    <title>Enter Your Name</title>\n");
        htmlStr.append("    <link rel=\"stylesheet\" type=\"text/css\" href=\"resources/styles.css\">\n");
        htmlStr.append("</head>\n");
        htmlStr.append("<body>\n");
        htmlStr.append("<h1>Please Enter Your Name</h1>\n");
        htmlStr.append("<form action=\"Main\" method=\"post\">\n");

        // First Name field
        htmlStr.append("    <div class=\"form-field\">\n");
        htmlStr.append("        <label for=\"firstName\" class=\"name-field\">First Name:</label>\n");
        htmlStr.append("        <input type=\"text\" id=\"firstName\" name=\"firstName\" placeholder=\"Enter your first name ([A-Z] and [a-z])\">\n");
        htmlStr.append("    </div>\n");

        // Last Name field
        htmlStr.append("    <div class=\"form-field\">\n");
        htmlStr.append("        <label for=\"lastName\" class=\"name-field\">Last Name:</label>\n");
        htmlStr.append("        <input type=\"text\" id=\"lastName\" name=\"lastName\" placeholder=\"Enter your first name ([A-Z] and [a-z])\">\n");
        htmlStr.append("    </div>\n");

        // Gender radio buttons
        htmlStr.append("    <div class=\"form-field\">\n");
        htmlStr.append("        <label class=\"name-field\">Gender: </label>\n");
        htmlStr.append("        <input type=\"radio\" id=\"male\" name=\"gender\" value=\"Male\" style=\"margin-right: 5px;\"> Male\n");
        htmlStr.append("    </div>\n");
        htmlStr.append("    <div class=\"form-field\">\n");
        htmlStr.append("        <input type=\"radio\" id=\"female\" name=\"gender\" value=\"Female\"> Female\n");
        htmlStr.append("    </div>\n");
        htmlStr.append("    <div class=\"form-field\">\n");
        htmlStr.append("        <input type=\"radio\" id=\"other\" name=\"gender\" value=\"Other\"> Other\n");
        htmlStr.append("    </div>\n");

        // Submit button
        htmlStr.append("    <div>\n");
        htmlStr.append("        <button type=\"submit\">Submit</button>\n");
        htmlStr.append("    </div>\n");

        htmlStr.append("</form>\n");

        return htmlStr;
    }

    public static String generateDiv(String title, String msg) {
        StringBuilder htmlStr = new StringBuilder();

        htmlStr.append("    <div>\n");
        htmlStr.append("        <h1>");
        htmlStr.append(title);
        htmlStr.append("</h1>\n");
        htmlStr.append("        <div class=\"message-div\">\n");
        htmlStr.append(msg);
        htmlStr.append("\n");
        htmlStr.append("        </div>\n");
        htmlStr.append("    </div>\n");
        htmlStr.append("</body>\n");
        htmlStr.append("</html>");

        return htmlStr.toString();
    }

    protected boolean isValid(String name) {
        String[] words = name.split(" ");
        for(String s : words) {
            for(char c : s.toCharArray()) {
                if(c<'A' || c>'z') return false;
            }
        }
        return true;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");

            if (firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) {
                throw new IllegalArgumentException("You missed the first name and/or last name field.");
            } else if (!isValid(firstName) || !isValid(lastName)) {
                throw new IllegalArgumentException("You need to provide first name and last name in the range [A-Z] and [a-z].");
            } else if (gender == null) {
                throw new IllegalArgumentException("Please provide a valid gender.");
            }

            StringBuilder sb = new StringBuilder("Thank you");
            if (gender.equals("Male")) sb.append(" Mr.");
            else if (gender.equals("Female")) sb.append(" Ms.");
            sb.append(" ").append(firstName).append(" ").append(lastName).append(".");

            StringBuilder baseDiv = generateBasePage();
            String div = generateDiv("Welcome To My Shop", sb.toString());
            StringBuilder finalPage = baseDiv.append(div);

            response.setContentType("text/html");
            response.getWriter().write(finalPage.toString());

        } catch (Exception e) {
            StringBuilder baseDiv = generateBasePage();
            String div = generateDiv("Invalid Values!", e.getMessage());
            StringBuilder finalPage = baseDiv.append(div);
            response.setContentType("text/html");
            response.getWriter().write(finalPage.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Main.html");
    }
}
