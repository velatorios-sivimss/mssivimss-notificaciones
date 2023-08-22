package com.imss.sivimss.notificaciones.beans;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
public class Mensajes {
	
	public String obtenerMensaje(Integer idMensaje) {
	    return "SELECT DES_MENSAJE AS msg FROM SVC_MENSAJE WHERE ID_MENSAJE = " + idMensaje;	
	}

}
