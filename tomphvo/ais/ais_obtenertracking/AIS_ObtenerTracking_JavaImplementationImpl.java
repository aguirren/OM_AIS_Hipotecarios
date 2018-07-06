package tomphvo.ais.ais_obtenertracking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_ObtenerTracking_JavaImplementationImpl {
	
	public AIS_ObtenerTracking_JavaImplementationImpl() {
		super();
	}

	
	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	public DataObject invoke(DataObject inputObtenerTracking) {
		
		Log.logTOM_info("Inicio: AIS_ObtenerTracking_JavaImplementationImpl");
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		DataObject salida = boFactory.create("http://TOMPHVO", "out_AIS_ObtenerTracking");
		
		
		String query = "SELECT  track.FECHA as fecha " +
				",track.LEGAJO as legajo " +
				",est.DESCRIPCION as actividad " +
				",track.ID_ATRIBUTO as atributo " +
				",track.NOMBREYAPELLIDO as nombreApellido " +
				",track.DETALLE as detalle " + 
				" FROM OM_TRACKING as track left outer join OM_ESTADO as est on est.ID_ESTADO = track.ID_ESTADO " +
				" WHERE NRO_SOLICITUD ='" + inputObtenerTracking.getString("solicitud")+"'";

			
		Log.logTOM_debug(query);	
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
		
				DataObject listaTracking = boFactory.create("http://TOMPHVO", "Tracking");
				listaTracking.setDate("fecha", rs.getDate("fecha"));
				listaTracking.setString("legajo", rs.getString("legajo"));
				listaTracking.setString("actividad", rs.getString("actividad"));
				listaTracking.setString("atributo", rs.getString("atributo"));
				listaTracking.setString("nombreApellido", rs.getString("nombreApellido"));
				listaTracking.setString("detalle", rs.getString("detalle"));
			
				salida.getList("tracking").add(listaTracking);
				
			}
			rs.close();
		}catch(Exception e ){
			Log.logTOM_error("Error AIS_ObtenerTracking_JavaImplementationImpl " + e.toString());
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
		Log.logTOM_info("Fin: AIS_ObtenerTracking_JavaImplementationImpl");
		
		return salida;
		

		}
		
}