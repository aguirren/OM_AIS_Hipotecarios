package tomphvo.ais.ais_obtenerdictamen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import commonj.sdo.DataObject;
import com.ibm.websphere.sca.ServiceManager;
import com.main.DataSourceConector;
import com.main.logger.Log;

public class AIS_ObtenerDictamen_JavaImplementationImpl {
	
	public AIS_ObtenerDictamen_JavaImplementationImpl() {
		super();
	}

	@SuppressWarnings("unused")
	private Object getMyService() {
		return (Object) ServiceManager.INSTANCE.locateService("self");
	}

	public DataObject invoke() {

		Log.logTOM_info("Inicio: AIS_ObtenerDictamen_JavaImplementationImpl ");
		
		DataSourceConector conector = new DataSourceConector();
		Connection conn = conector.crearConexion_procesoCILIMASIVO();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		com.ibm.websphere.bo.BOFactory boFactory = (com.ibm.websphere.bo.BOFactory) ServiceManager.INSTANCE.locateService("com/ibm/websphere/bo/BOFactory");
		
		DataObject salida = boFactory.create("http://TOMPHVO/AIS/AIS_ObtenerDictamen","ArrayOfCodigoValor");
		
		
		String query= " SELECT [DESCRIPCION] as name " +
						",CAST ([ID_DICTAMEN] as varchar(10)) as value " +
						"FROM [ProcesoCILI].[dbo].[OM_DICTAMEN] " +
						"order by ID_DICTAMEN " ;

		Log.logTOM_debug(query);	
		
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				DataObject elemento = boFactory.create("http://TOMPHVO", "CodigoValor");
				elemento.setString("name", rs.getString("name"));
				elemento.setString("value", rs.getString("value"));
				salida.getList("CodigoValor").add(elemento);				
			}
			rs.close();
		}catch(Exception e){
			Log.logTOM_error("Error en AIS_ObtenerDictamen_JavaImplementationImpl. " + e.getMessage());
		}finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: ps"+ e.getMessage());
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Log.logTOM_error("Fallo finally: conn"+ e.getMessage());
				}
			}
		}
		Log.logTOM_info("Fin: AIS_ObtenerDictamen_JavaImplementationImpl");	
		
		return salida;
  }
}