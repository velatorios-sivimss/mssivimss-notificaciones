package com.imss.sivimss.notificaciones.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;

public interface NotificacionesService {
	
	List<Map<String, Object>> tiempoSalas(Authentication authentication) throws IOException;

}
