package com.imss.sivimss.notificaciones.model.request;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SalaDto {
	
	private String url;
	private String textoBoton; 
	private String nombreSala; 
	private String idSala;
	private Boolean indTipoSala;                    
	private String usoSala;
	private Integer idRegistro;   

}
