package edu.upc.eetac.dsa.dsaqt1314g3.futbol.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.dsaqt1314g3.futbol.api.model.Campeonatos;
import edu.upc.eetac.dsa.dsaqt1314g3.futbol.api.model.Jugadores;


@Path("/{idclub}/{idequipo}/jugadores")

public class JugadoresResource {

	@Context
    private UriInfo uriInfo;
    private DataSource ds = DataSourceSPA.getInstance().getDataSource();	
	@GET
	@Path("/{dni}")
	@Produces(MediaType.FUTBOL_API_JUGADORES)
    public Jugadores getJugador(@PathParam("dni") String dni, @PathParam("idclub") String idclub, @PathParam("idequipo") String idequipo, @Context Request req){
		Jugadores jugador = new Jugadores();
		Connection conn = null;
        Statement stmt = null;
         try {
                 conn = ds.getConnection();
         } catch (SQLException e) {
                 throw new ServiceUnavailableException(e.getMessage());
         }
         
         try {
                 stmt = conn.createStatement();
                 String query = "SELECT * FROM Jugadores WHERE dni=" + dni + " and idequipo ="+idequipo+";";
                 ResultSet rs = stmt.executeQuery(query);
                 if (rs.next()) {
                         jugador.setDni(rs.getString("dni"));
                         jugador.setNombre(rs.getString("nombre"));
                         jugador.setApellidos(rs.getString("apellidos"));
                         jugador.setIdequipo(rs.getInt("IdEquipo"));
                 } else
                       throw new JugadorNotFoundException();
         } catch (SQLException e) {
                 throw new InternalServerException(e.getMessage());
         }
         finally {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
		}
	}
	return jugador;
}
	
	@POST
    @Consumes(MediaType.FUTBOL_API_JUGADORES)
    @Produces(MediaType.FUTBOL_API_JUGADORES)
    public Jugadores createJugador(@PathParam("idclub") String idclub, @PathParam("idequipo") String idequipo) {
		{
//			if (!security.isUserInRole("administrator"))
//			{
//				throw new ForbiddenException("Solo administrador puede crear un jugador");
//			}
			
			Jugadores jugador = new Jugadores();
			Connection conn = null;
			try{
				Statement stmt = conn.createStatement();
				String sql = "insert into Jugadores (dni,nombre, apellidos, idequipo) values('"
				+ jugador.getDni()
				+ "', '"
				+ jugador.getNombre()
				+ "', '"
				+jugador.getApellidos()
				+ "', '"
				+jugador.getIdequipo()
				+"')";
				stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
				rs.close();
				stmt.close();
				conn.close();
				}
				else {
					throw new JugadorNotFoundException();
				}
				
				
				}
			 catch (SQLException e) {
					throw new InternalServerException(e.getMessage());
				}
			return jugador;
			}
 }
	
	
	@DELETE
	@Path("/{dni}")
	public void borrarjugador (
			@PathParam("dni") String dni) {
		Connection conn = null;
		
		//if (!security.isUserInRole("administrator"))
//		{
//			throw new ForbiddenException("Solo el administrador puede borrar un campeonato");
//		}
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		Statement stmt = null;
		String sql;
		try {
			stmt = conn.createStatement();
			sql = "delete from Jugadores where dni='"
					+ dni + "'";

			int rs2 = stmt.executeUpdate(sql);
			if (rs2 == 0)
				throw new BadRequestException("No se puede realizar tal acción");

		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				conn.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}	
	
	@PUT
	@Path("/{dni}")
	@Produces(MediaType.FUTBOL_API_JUGADORES)
	@Consumes(MediaType.FUTBOL_API_JUGADORES)
	public Jugadores actualizarCampeonato(
			@PathParam("dni") String dni , 
			@PathParam("idequipo") int idequipo ,
			Jugadores jugador
			) {

		//if (!security.isUserInRole("administrator"))
//			{
//				throw new ForbiddenException("Solo el administrador puede realizar una actualización a los campeonatos");
//			}
		//Jugadores jugador = new Jugadores();
		Connection conn = null;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			Statement stmt = conn.createStatement();
			jugador.setDni(dni);
			jugador.setIdequipo(idequipo);
			String sql = "update Jugadores set Jugadores.nombre='" + jugador.getNombre()
					+ "',Jugadores.apellidos='" + jugador.getApellidos()					
					  + "' where Jugadores.dni=" + dni;

			int rs2 = stmt.executeUpdate(sql);
			if (rs2 == 0)
				throw new CampeonatoNotFoundException();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}
		return jugador;
	}

	
	
	
}

		



