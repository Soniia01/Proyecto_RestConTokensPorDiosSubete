package rest.servlet;

import domain.model.Credenciales;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.HttpConstraint;
import jakarta.servlet.annotation.ServletSecurity;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import useCases.credenciales.EditarCredencialesUserCase;
import useCases.credenciales.GetCredencialesCodigoUseCase;

import java.io.IOException;

@WebServlet(name = "ServletActivacion", value = "/Activacion")
@ServletSecurity(@HttpConstraint(transportGuarantee = ServletSecurity.TransportGuarantee.NONE)
)
public class ServletActivacion extends HttpServlet {
    private final GetCredencialesCodigoUseCase getCredencialesCodigoUseCase;
    private final EditarCredencialesUserCase editarCredencialesUserCase;

    @Inject
    public ServletActivacion(GetCredencialesCodigoUseCase getCredencialesCodigoUseCase, EditarCredencialesUserCase editarCredencialesUserCase) {
        this.getCredencialesCodigoUseCase = getCredencialesCodigoUseCase;
        this.editarCredencialesUserCase = editarCredencialesUserCase;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codigoActivacion = request.getParameter("codigo");

        Credenciales credenciales = getCredencialesCodigoUseCase.getCredencialesCodigo(codigoActivacion).getOrNull();
        if (credenciales != null) {
            credenciales.setAutentificado(true);
        }
        // Actualizar el atributo de autenticación del usuario a "activado"
        editarCredencialesUserCase.editarCredenciales(credenciales);
        response.getWriter().println("Cuenta activada" + credenciales);
    }


}
