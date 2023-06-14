package com.imss.sivimss.notificaciones.beans;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class Permisos {
	
	public String getPermiso(Integer idRol, Integer idFuncionalidad) { 
	    StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM SVC_PERMISO PER ");
	    query.append("INNER JOIN SVC_ROL_FUNCIONALIDAD_PERMISO RFP ON RFP.ID_PERMISO = PER.ID_PERMISO ");
	    query.append("WHERE RFP.ID_ROL = " + idRol);
	    query.append(" AND RFP.ID_FUNCIONALIDAD = " + idFuncionalidad);
	    query.append(" AND PER.ID_PERMISO = 3 AND RFP.IND_ACTIVO = '1' ");
	    
	    return query.toString();
	}

}
