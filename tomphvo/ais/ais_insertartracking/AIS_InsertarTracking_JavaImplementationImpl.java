package tomphvo.ais.ais_insertartracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_InsertarTracking_JavaImplementationImpl {

	public AIS_InsertarTracking_JavaImplementationImpl() {
		super();
	}

	
	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	public void invoke(DataObject inputInsertarTracking) {
		

		Log.logTOM_info("Inicio: AIS_InsertarTracking_JavaImplementationImpl");
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		String query = 
				"INSERT INTO  [ProcesoCILI].[dbo].[OM_TRACKING] " +

						"( [NRO_SOLICITUD] " +
						" ,[FECHA] " +
						",[LEGAJO] " +
						",[ID_ESTADO] " +
					    ",[ID_ATRIBUTO] " +
						",[NOMBREYAPELLIDO]" +
						",[DETALLE]) " +
						" VALUES " +
                 
 						"('"+inputInsertarTracking.getString("solicitud")+"' " +
 						",'"+inputInsertarTracking.getDataObject("tracking").getString("fecha")+"' " +
 						",'"+inputInsertarTracking.getDataObject("tracking").getString("legajo")+"' " +
 						","+inputInsertarTracking.getDataObject("tracking").getString("actividad")+
 					    ","+inputInsertarTracking.getDataObject("tracking").getString("atributo")+
 						",'"+inputInsertarTracking.getDataObject("tracking").getString("nombreApellido")+"' " +
                 		",'"+inputInsertarTracking.getDataObject("tracking").getString("detalle")+" ' )";
		
			Log.logTOM_debug(query);
			
			DataSourceConector conector = new DataSourceConector();
			Connection conn = conector.crearConexion_procesoCILIMASIVO();
			PreparedStatement ps = null;
			
			try {
				ps = conn.prepareStatement(query);
				ps.executeUpdate();
				
			} catch (Exception e) {
				Log.logTOM_error("Error en AIS_InsertarTracking_JavaImplementationImpl. " + e.getMessage());
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
			Log.logTOM_info("Fin: AIS_InsertarTracking_JavaImplementationImpl");
		}
				
 					  

}