package tomphvo.ais.ais_insertarasignacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_InsertarAsignacion_JavaImplementationImpl {
	
	public AIS_InsertarAsignacion_JavaImplementationImpl() {
		super();
	}

	
	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	
	public void invoke(DataObject inputInsertarAsignacion) {
		Log.logTOM_info("Inicio: AIS_InsertarAsignacion_JavaImplementationImpl");
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		String query = 
		  " INSERT INTO [ProcesoCILI].[dbo].[OM_ASIGNACION] " +
		  "(" +
		 // "[ID_TERCERO] " +
		  "[OBSERVACION] " +
    	  ",[FECHA_ASIGNACION] " +
    	  ",[NRO_SOLICITUD] " +
    	  ",[TIPO_ASIGNACION] " +
    	  ",[NOMBRE_APELLIDO]) " +
    	  "VALUES" +

 		//  "('"+inputInsertarAsignacion.getDataObject("asignacion").getString("idTercero")+"' " +
 		  "('"+inputInsertarAsignacion.getDataObject("asignacion").getString("observacion")+"' " +
 		  ",'"+inputInsertarAsignacion.getDataObject("asignacion").getString("fechaAsignacion")+"' " +
 		  ",'"+inputInsertarAsignacion.getString("solicitud")+"' " +
 		  ",'"+inputInsertarAsignacion.getDataObject("asignacion").getString("tipoAsignacion")+"' " +
          ",'"+inputInsertarAsignacion.getDataObject("asignacion").getString("nombreApellido")+" ' )";
		
		Log.logTOM_debug(query);
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
		} catch (Exception e) {
			Log.logTOM_error("Error en AIS_InsertarAsignacion_JavaImplementationImpl. " + e.getMessage());
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: ps" + e.getMessage());
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: conn" + e.getMessage());
				}
			}
		}		
		Log.logTOM_info("Fin: AIS_InsertarAsignacion_JavaImplementationImpl");
	}

}