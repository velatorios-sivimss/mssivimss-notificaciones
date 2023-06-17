package com.imss.sivimss.notificaciones.beans;

import com.imss.sivimss.notificaciones.model.request.UsuarioDto;
import com.imss.sivimss.notificaciones.utils.AppConstantes;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class Vehiculos {

	public String verificaInicio(UsuarioDto usuario) {
		
		StringBuilder query = new StringBuilder("SELECT CONVERT(COUNT(sv.ID_VEHICULO),CHAR) as total_vehiculos, CONVERT(COUNT(smvi.ID_MTTOVEHICULAR),CHAR) as total_verficacion, ");
		query.append("CONCAT ('Tienes ', CONVERT((COUNT(sv.ID_VEHICULO)-COUNT(smvi.ID_MTTOVEHICULAR)),CHAR), ' sin registrar la verificación al inicio de la jornada, te recordamos que debes registrar diariamente') as total_sin_verficacion \n");
		query.append("FROM SVT_VEHICULOS sv \n");
		query.append("LEFT JOIN SVT_MTTO_VEHICULAR smv ON sv.ID_VEHICULO = smv.ID_VEHICULO \n");
		query.append("LEFT JOIN SVT_MTTO_VERIF_INICIO smvi on smvi.ID_MTTOVEHICULAR = smv.ID_MTTOVEHICULAR \n");
		if (usuario.getIdOficina() > AppConstantes.NIVEL_CENTRAL) {
	    	query.append("LEFT JOIN SVC_VELATORIO vel on sv.ID_VELATORIO = vel.ID_VELATORIO ");
	    	if (usuario.getIdOficina() == AppConstantes.NIVEL_DELEGACION) {
	    	    query.append(" WHERE vel.ID_DELEGACION = " + usuario.getIdDelegacion());
	    	} else {
	    		query.append(" WHERE vel.ID_VELATORIO = " + usuario.getIdVelatorio());
	    	}
	    }
		
		return query.toString();
	}
	
	public String programacionMantenimiento(UsuarioDto usuario) {
		
		StringBuilder query = new StringBuilder();
		if (usuario.getIdOficina() == AppConstantes.NIVEL_CENTRAL) {
	       query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_MTTOESTADO=3), ");
	       query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
	       query.append("UNION ALL \n");
	       query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_MTTOESTADO=2), ");
	       query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
	       query.append("UNION ALL \n");
	       query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_MTTOESTADO=4), ");
	       query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL ");
		} else if (usuario.getIdOficina() == AppConstantes.NIVEL_DELEGACION) {
			query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_DELEGACION = " + usuario.getIdDelegacion() + " AND ef.ID_MTTOESTADO=3), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		    query.append("UNION ALL \n");
		    query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_DELEGACION = " + usuario.getIdDelegacion() + " AND ef.ID_MTTOESTADO=2), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		    query.append("UNION ALL \n");
		    query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_DELEGACION = " + usuario.getIdDelegacion() + " AND ef.ID_MTTOESTADO=4), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		} else if (usuario.getIdOficina() == AppConstantes.NIVEL_VELATORIO) {
			query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_VELATORIO = " + usuario.getIdVelatorio() + " AND ef.ID_MTTOESTADO=3), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		    query.append("UNION ALL \n");
		    query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_VELATORIO = " + usuario.getIdVelatorio() + " AND ef.ID_MTTOESTADO=2), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		    query.append("UNION ALL \n");
		    query.append("SELECT CONCAT('Tienes ', (SELECT CONVERT(COUNT(ef.ID_MTTOVEHICULAR),CHAR) FROM SVT_MTTO_VEHICULAR ef WHERE ef.ID_VELATORIO = " + usuario.getIdVelatorio() + " AND ef.ID_MTTOESTADO=4), ");
		    query.append("' en la Programacion de Mantenimiento, te recordamos que debes realizarlo') AS total FROM DUAL \n");
		}
		
		return query.toString();
	}
	
}
