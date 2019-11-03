package br.com.cresol.desafio.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.EmprestimoService;
import io.swagger.annotations.Api;

/**
 * @author monica
 *
 */
@Api
@Path("/emprestimo")
public class EmprestimoResource {

	/**
	 * Metodo para simular emprestimo
	 * 
	 * @param payload
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/simular")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response simular(SimularEmprestimoPayload payload) throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = new EmprestimoService().simular(payload);
		if (simulacaoEmprestimo == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
		return Response.ok(simulacaoEmprestimo).build();
	}

}
