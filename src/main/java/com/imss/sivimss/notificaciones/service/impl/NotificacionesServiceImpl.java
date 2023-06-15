package com.imss.sivimss.notificaciones.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.imss.sivimss.notificaciones.configuration.MyBatisConnect;
import com.imss.sivimss.notificaciones.configuration.mymapper.Consultas;
import com.imss.sivimss.notificaciones.model.request.UsuarioDto;
import com.imss.sivimss.notificaciones.utils.LogUtil;
import com.imss.sivimss.notificaciones.beans.ServicioSalas;
import com.imss.sivimss.notificaciones.beans.Permisos;
import com.imss.sivimss.notificaciones.service.NotificacionesService;

@Service
public class NotificacionesServiceImpl implements NotificacionesService {
	
	@Autowired
	private MyBatisConnect con;
	
	protected static Consultas consultas = null;
	protected static AnnotationConfigApplicationContext context = null;
	
	@Autowired
	private LogUtil logUtil;
	
	private static final Logger log = LoggerFactory.getLogger(NotificacionesService.class);
	
	private static final String CONSULTA = "consulta";
	
	@Override
	public List<Map<String, Object>> tiempoSalas(Authentication authentication, Integer idFuncionalidad) throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(authentication.getPrincipal());
		UsuarioDto usuario =  gson.fromJson(datosJson,UsuarioDto.class);
		Permisos permisos = new Permisos();
		ServicioSalas servSalas = new ServicioSalas();
		
		List<Map<String, Object>> resp = new ArrayList<>();
	
		try {
		   context = con.conectar();
		   consultas = con.crearBeanDeConsultas();
		   
		   if (consultas.contar(permisos.getPermiso(usuario.getIdRol(), idFuncionalidad)) > 0) {
		       resp = consultas.selectHashMap(servSalas.tiempoSalas());
		   }
		   return resp;
		   
		} catch (Exception e) {
			log.error(e.getMessage());
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), e.getMessage(), CONSULTA, authentication);
			return null;
		}
	}

}
