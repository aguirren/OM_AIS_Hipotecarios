package tomphvo.ais.ais_obtenertercero;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_ObtenerTercero_JavaImplementationImpl {
	
	public AIS_ObtenerTercero_JavaImplementationImpl() {
		super();
	}

	
	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	
	public DataObject invoke(DataObject inputObtenerTercero) {
		Log.logTOM_info("Inicio: AIS_ObtenerTercero_JavaImplementationImpl");
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		DataObject salida = boFactory.create("http://TOMPHVO", "out_AIS_ObtenerTercero");
		
		
		String query = "SELECT  terc.NOMBRE as nombre " +
		", terc.APELLIDO as apellido " +
		", terc.DNI as dni " +
		", terc.MAIL as mail " +
		", terc.MATRICULA as matricula " +
		", terc.DOMICILIO as domicilio " +
		", terc.TELEFONO as telefono " +
		", terc.CUIT as CUIT " +
		", terc.LOCALIDAD as localidad " +
		", terc.PROVINCIA as provincia " +
		"  FROM [OM_TERCERO] as terc" +
		" WHERE terc.ID_TIPO_TERCERO =" + inputObtenerTercero.getString("tipoTercero")+" AND ACTIVO =1";

		Log.logTOM_debug(query);	
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				DataObject listaTercero = boFactory.create("http://TOMPHVO", "Tercero");
				listaTercero.setString("matricula", rs.getString("matricula"));
				listaTercero.setString("nombre", rs.getString("nombre"));
				listaTercero.setString("CUIT", rs.getString("CUIT"));
				listaTercero.setString("domicilio", rs.getString("domicilio"));	
				listaTercero.setString("telefono", rs.getString("telefono"));
				listaTercero.setString("mail", rs.getString("mail"));
				listaTercero.setString("dni", rs.getString("dni"));
				listaTercero.setString("apellido", rs.getString("apellido"));
				listaTercero.setString("localidad", rs.getString("localidad"));
				listaTercero.setString("provincia", rs.getString("provincia"));
				
					
				salida.getList("listaTercero").add(listaTercero);
								
			}
			rs.close();
		}catch(Exception e ){
			Log.logTOM_error("Error AIS_ObtenerTercero_JavaImplementationImpl " + e.toString());
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
		Log.logTOM_info("Fin: AIS_ObtenerTercero_JavaImplementationImpl");
		
		return salida;
		
		
		
		
		
		
	}

}