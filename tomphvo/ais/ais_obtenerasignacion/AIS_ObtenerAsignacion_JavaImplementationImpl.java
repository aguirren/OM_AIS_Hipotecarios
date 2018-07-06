package tomphvo.ais.ais_obtenerasignacion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_ObtenerAsignacion_JavaImplementationImpl {
	
	public AIS_ObtenerAsignacion_JavaImplementationImpl() {
		super();
	}


	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
		
	}
	public DataObject invoke(DataObject inputObtenerAsignacion) {
		Log.logTOM_info("Inicio: AIS_ObtenerAsignacion_JavaImplementationImpl");
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");

		DataObject salida = boFactory.create("http://TOMPHVO", "out_AIS_ObtenerAsignacion");
		
		String query =" SELECT  asig.ID_TERCERO as idTercero " +
				 ", asig.FECHA_ASIGNACION as fechaAsignacion " +
				 ", asig.OBSERVACION as observacion " +
				 ", asig.TIPO_ASIGNACION as tipoAsignacion " +
				 ", asig.NOMBRE_APELLIDO as nombreApellido " +
				 ", asig.ACTIVIDAD as actividad " +
				 " FROM OM_ASIGNACION as asig " +
				 " WHERE NRO_SOLICITUD ='" + inputObtenerAsignacion.getString("solicitud")+"'";
		
		
		Log.logTOM_debug(query);	
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
		
				DataObject listaAsignacion = boFactory.create("http://TOMPHVO", "Asignacion");
				listaAsignacion.setString("idTercero", rs.getString("idTercero"));
				listaAsignacion.setDate("fechaAsignacion", rs.getDate("fechaAsignacion"));
				listaAsignacion.setString("observacion", rs.getString("observacion"));
				listaAsignacion.setString("tipoAsignacion", rs.getString("tipoAsignacion"));
				listaAsignacion.setString("nombreApellido", rs.getString("nombreApellido"));
				listaAsignacion.setString("actividad", rs.getString("actividad"));
				
				salida.getList("asignacion").add(listaAsignacion);
				
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