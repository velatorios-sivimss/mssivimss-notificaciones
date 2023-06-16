package com.imss.sivimss.notificaciones.model.request;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GenericoDto<T> {
     
	String mensaje;
	T botones;
	String cu;
	
}
