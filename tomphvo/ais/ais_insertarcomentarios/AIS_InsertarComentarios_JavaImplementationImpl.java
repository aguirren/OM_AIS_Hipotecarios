package tomphvo.ais.ais_insertarcomentarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_InsertarComentarios_JavaImplementationImpl {
	
	public AIS_InsertarComentarios_JavaImplementationImpl() {
		super();
	}

	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	
	public void invoke(DataObject inputInsertarComentarios) {
		Log.logTOM_info("Inicio: AIS_InsertarComentarios_JavaImplementationImpl");
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		String query ="INSERT INTO [ProcesoCILI].[dbo].[OM_COMENTARIO] " +
                "( " +
           
                "[LEGAJO] " +
                ",[FECHA] " +
                ",[COMENTARIO] " +
                ",[ACTIVIDAD] " +
                ",[NOMBRE_APELLIDO] " +
                ",[NRO_SOLICITUD]) " + 
                " VALUES " +
                
             
                "('"+inputInsertarComentarios.getDataObject("comentarios").getString("legajo")+"' " +
                ",'"+inputInsertarComentarios.getDataObject("comentarios").getString("fecha")+"' " +
                ",'"+inputInsertarComentarios.getDataObject("comentarios").getString("comentario")+"' " +
                ",'"+inputInsertarComentarios.getDataObject("comentarios").getString("actividad")+ "' " +
                ",'"+inputInsertarComentarios.getDataObject("comentarios").getString("nombreYApellido")+ "' " +
                ",'"+inputInsertarComentarios.getString("solicitud")+" ')"; 
	
		
		
		
		
		
		Log.logTOM_debug(query);
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		} catch (Exception e) {
			Log.logTOM_error("Error en AIS_InsertarComentarios_JavaImplementationImpl. " + e.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: ps");
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: conn");
				}
			}
		}		
		Log.logTOM_info("Fin:AIS_InsertarComentarios_JavaImplementationImpl");
		
		
	}

}