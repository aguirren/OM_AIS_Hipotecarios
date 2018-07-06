package tomphvo.ais.ais_obtenercomentarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_ObtenerComentarios_JavaImplementationImpl {
	
	public AIS_ObtenerComentarios_JavaImplementationImpl() {
		super();
	}

	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	public DataObject invoke(DataObject inputObtenerComentarios) {
		
Log.logTOM_info("Inicio: AIS_ObtenerComentarios_JavaImplementationImpl.");
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		DataObject salida = boFactory.create("http://TOMPHVO", "out_AIS_ObtenerComentarios");
		
		
		String query = 
						"SELECT  com.[LEGAJO] as legajo " +
						", com.[FECHA] as fecha " +
				        ", com.[COMENTARIO] as comentario " +
				        ", com.[ACTIVIDAD] as actividad " +
				        ", com.[NOMBRE_APELLIDO] as nombreYApellido " +
				        
				        "FROM  OM_COMENTARIO as com  " +
				        "WHERE com.[NRO_SOLICITUD] = '"+inputObtenerComentarios.getString("solicitud")+"' " +
				        "ORDER BY fecha desc " ;	
		
		
	
		Log.logTOM_debug(query);	

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				DataObject listaComentario = boFactory.create("http://TOMPHVO", "Comentario");
			
				listaComentario.setString("legajo", rs.getString("legajo"));
				listaComentario.setDate("fecha", rs.getDate("fecha"));
				listaComentario.setString("comentario", rs.getString("comentario"));
				listaComentario.setString("actividad", rs.getString("actividad"));
				listaComentario.setString("nombreYApellido", rs.getString("nombreYApellido"));
				
				
				
				salida.getList("comentarios").add(listaComentario);
					
			}
			rs.close();
		}catch(Exception e){
			Log.logTOM_error("Error AIS_ObtenerComentarios_JavaImplementationImpl" + e.toString());
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: ps" + e.toString());
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: conn" + e.toString());
				}
			}
		}
		Log.logTOM_info("Fin: AIS_ObtenerComentarios_JavaImplementationImpl");

		return salida;
	}
}