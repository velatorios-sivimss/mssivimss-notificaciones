package com.imss.sivimss.notificaciones.beans;

import com.imss.sivimss.notificaciones.model.request.UsuarioDto;
import com.imss.sivimss.notificaciones.utils.AppConstantes;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class ServicioSalas {

	public String tiempoSalas(UsuarioDto usuario) {
	
		StringBuilder query = new StringBuilder("SELECT SBS.ID_REGISTRO AS idRegistro, SS.IND_TIPO_SALA AS indTipoSala, \n");
	    query.append("SBS.ID_SALA AS idSala, SS.DES_SALA AS nombreSala, \n");
	    query.append("CASE WHEN SBS.ID_TIPO_OCUPACION = 1 THEN 'MANTENIMIENTO' WHEN SBS.ID_TIPO_OCUPACION = 2 THEN 'SERVICIO ODS' END usoSala, \n");
	    query.append("IFNULL( CONVERT(CASE \n"
	    		 + "	WHEN TIMESTAMPDIFF(MINUTE, SBS.TIM_HORA_ENTRADA, NOW()) >= 210 \n"
	    		 + "	AND SBS.TIM_HORA_SALIDA IS NULL \n"
	    		 + "	AND SBS.TIM_RENOVACION IS NULL \n");
	    query.append("THEN CONCAT('En la sala ' , SS.DES_SALA, ' el tiempo de atención del servicio ha excedido de las 3 horas y media, te recordamos que debes registrar la fecha y hora del término del servicio.') \n"
	    		+ "	WHEN SBS.TIM_RENOVACION  IS NOT null \n"
	    		+ "	AND TIMESTAMPDIFF(MINUTE, SBS.TIM_RENOVACION, NOW()) >= 210 \n"
	    		+ "	THEN CONCAT('En la sala ' , SS.DES_SALA, ' el tiempo de atención del servicio ha excedido de las 3 horas y media, te recordamos que debes registrar la fecha y hora del término del servicio.') \n"
	    		+ "END USING UTF8), '') mensaje, \n");
	    query.append("'reservar-salas' AS path \n");
	    query.append("FROM SVC_BITACORA_SALAS SBS LEFT JOIN SVC_SALA SS on SBS.ID_SALA = SS.ID_SALA ");
	    
	    if (usuario.getIdOficina() > AppConstantes.NIVEL_CENTRAL) {
	    	query.append("LEFT JOIN SVC_VELATORIO vel on SS.ID_VELATORIO = vel.ID_VELATORIO ");
	    	if (usuario.getIdOficina().equals(AppConstantes.NIVEL_DELEGACION)) {
	    	    query.append(" WHERE vel.ID_DELEGACION = " + usuario.getIdDelegacion());
	    	} else {
	    		query.append(" WHERE vel.ID_VELATORIO = " + usuario.getIdVelatorio());
	    	}
	    }
	   	
		return query.toString();
	}

}
