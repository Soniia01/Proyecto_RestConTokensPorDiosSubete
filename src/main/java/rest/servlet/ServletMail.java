package rest.servlet;

import domain.model.Credenciales;
import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import useCases.credenciales.GetCredencialesUserCase;
import useCases.credenciales.MandarMail;
import useCases.utils.RandomCodeGenerator;

import java.io.IOException;

@WebServlet(name = "ServletMail", urlPatterns = {"/Mail"})

@ServletSecurity(@HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.NONE))
public class ServletMail extends HttpServlet {
    private final GetCredencialesUserCase getCredencialesUserCase;

    @Inject
    public ServletMail(GetCredencialesUserCase getCredencialesUserCase) {
        this.getCredencialesUserCase = getCredencialesUserCase;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correoElectronico = request.getParameter("email");

        if (correoElectronico == null || correoElectronico.isEmpty()) {
            response.getWriter().println("Falta el parámetro de correo electrónico");
            return;
        }
        MandarMail mandarMail = new MandarMail();
        try {
            RandomCodeGenerator randomCodeGenerator = new RandomCodeGenerator();
            String randomCode = randomCodeGenerator.randomCode();
            Credenciales cred=getCredencialesUserCase.getCredenciales(correoElectronico).getOrNull();
            cred.setCodigoAuth(randomCode);
            mandarMail.generateAndSendEmail(correoElectronico, "<html><body><a href=\"http://localhost:8080/LibrosSoniaSanchez-1.0-SNAPSHOT/Activacion?codigo=" + randomCode + "\">Activación</a></body></html>", "Activación de la cuenta");
            response.getWriter().println("Correo enviado");
        } catch (MessagingException e) {
            response.getWriter().println(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
