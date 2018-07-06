package tomphvo.ais.ais_insertartercero;

import commonj.sdo.DataObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;



public class AIS_InsertarTercero_JavaImplementationImpl {
	
	public AIS_InsertarTercero_JavaImplementationImpl() {
		super();
	}

	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	
	public void invoke(DataObject inputInsertarTercero) {
		
		Log.logTOM_info("Inicio: AIS_InsertarTercero_JavaImplementationImpl");
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		String query = "INSERT INTO [ProcesoCILI].[dbo].[OM_TERCERO] " +
		
                "([DNI] " +
                ",[TELEFONO] " +
                ",[ID_TIPO_TERCERO] " +
                ",[APELLIDO] " +
                ",[MAIL] " +
                ",[MATRICULA] " +
                ",[CUIT] " +
                ",[NOMBRE] " +
                ",[DOMICILIO]) " +
                " VALUES " +
                
     
                "('"+inputInsertarTercero.getString("dni")+"' " +
                ",'"+inputInsertarTercero.getString("telefono")+"' " +
                ",'"+inputInsertarTercero.getString("idTipoTercero")+"' " +
                ",'"+inputInsertarTercero.getString("apellido")+"' " +
                ",'"+inputInsertarTercero.getString("mial")+"' " +
                ",'"+inputInsertarTercero.getString("matricula")+"' " +
                ",'"+inputInsertarTercero.getString("cuit")+"' " +
                ",'"+inputInsertarTercero.getString("nombre")+"' " + 
                ",'"+inputInsertarTercero.getString("domicilio")+" ')"; 
		
		
		
     
		
	Log.logTOM_debug(query);
	
	DataSourceConector conector = new DataSourceConector();
	Connection conn = conector.crearConexion_procesoCILIMASIVO();
	PreparedStatement ps = null;
	
	try {
		ps = conn.prepareStatement(query);
		ps.executeUpdate();
		
	} catch (Exception e) {
		Log.logTOM_error("Error en AIS_InsertarTercero_JavaImplementationImpl. " + e.getMessage());
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
	Log.logTOM_info("Fin: AIS_InsertarTercero_JavaImplementationImpl");
}
		
		
		
		//Log.logTOM_info("Fin: AIS_InsertarTercero_JavaImplementationImpl");
	

	

}