package com.imss.sivimss.notificaciones.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.notificaciones.configuration.MyBatisConnect;
import com.imss.sivimss.notificaciones.configuration.mymapper.Consultas;
import com.imss.sivimss.notificaciones.beans.ServicioSalas;
import com.imss.sivimss.notificaciones.service.NotificacionesService;

@Service
public class NotificacionesServiceImpl implements NotificacionesService {
	
	@Autowired
	private MyBatisConnect con;
	
	protected static Consultas consultas = null;
	protected static AnnotationConfigApplicationContext context = null;
	
	private static final Logger log = LoggerFactory.getLogger(NotificacionesService.class);
	
	@Override
	public List<Map<String, Object>> tiempoSalas(Authentication authentication) throws IOException {
		ServicioSalas servSalas = new ServicioSalas();
		
		List<Map<String, Object>> resp = new ArrayList<>();
		
		try {
		   context = con.conectar();
		   consultas = con.crearBeanDeConsultas();
		   resp = consultas.selectHashMap(servSalas.tiempoSalas());
		   return resp;
		   
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

}
