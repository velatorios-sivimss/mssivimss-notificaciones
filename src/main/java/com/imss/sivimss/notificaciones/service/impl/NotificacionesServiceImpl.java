package com.imss.sivimss.notificaciones.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.imss.sivimss.notificaciones.model.request.SalaDto;
import com.imss.sivimss.notificaciones.model.request.GenericoDto;
import com.imss.sivimss.notificaciones.utils.LogUtil;
import com.imss.sivimss.notificaciones.beans.ServicioSalas;
import com.imss.sivimss.notificaciones.beans.Vehiculos;
import com.imss.sivimss.notificaciones.beans.Mensajes;
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
	
	private static final Integer BALANCE_CAJA = 127;
	
	@Override
	public List<Map<String, Object>> tiempoSalas(Authentication authentication, Integer idFuncionalidad) throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(authentication.getPrincipal());
		UsuarioDto usuario =  gson.fromJson(datosJson,UsuarioDto.class);
		//Permisos permisos = new Permisos();
		ServicioSalas servSalas = new ServicioSalas();
	
		List<Map<String, Object>> respQuery = new ArrayList<>();
		List<Map<String, Object>> respAviso = new ArrayList<>();
	
		try {
		   context = con.conectar();
		   consultas = con.crearBeanDeConsultas();
		   
		   //if (consultas.contar(permisos.getPermiso(usuario.getIdRol(), idFuncionalidad)) > 0) {
		   respQuery = consultas.selectHashMap(servSalas.tiempoSalas(usuario));
		   respAviso.addAll(respQuery);
		   //respAviso.addAll(botonesSalas());
		
		   return respAviso;
		   
		} catch (Exception e) {
			log.error(e.getMessage());
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), e.getMessage(), CONSULTA, authentication);
			return null;
		}
	}

	@Override
	public List<?> avisos(Authentication authentication) throws IOException {
		Gson gson = new Gson();
		String datosJson = String.valueOf(authentication.getPrincipal());
		UsuarioDto usuario =  gson.fromJson(datosJson,UsuarioDto.class);
		List<Map<String, Object>> respQuery = new ArrayList<>();

		ServicioSalas servSalas = new ServicioSalas();
		Vehiculos vehiculos = new Vehiculos();
		Mensajes balanceCaja = new Mensajes();
		
		List<GenericoDto> lstGenericos = new ArrayList<>();
		
		try {
			context = con.conectar();
			consultas = con.crearBeanDeConsultas();
			
			respQuery = consultas.selectHashMap(servSalas.tiempoSalas(usuario));
			
			for (Map<String, Object> sala : respQuery) {
				GenericoDto generico = new GenericoDto();
				generico.setMensaje(sala.get("mensaje").toString());
				if (generico.getMensaje().isEmpty()) {
					continue;
				}
				SalaDto salaDto = new SalaDto();
				salaDto.setIdRegistro((Integer) sala.get("idRegistro"));
				salaDto.setIdSala(sala.get("idSala").toString());
				salaDto.setIndTipoSala((boolean) sala.get("indTipoSala"));
				salaDto.setNombreSala(sala.get("nombreSala").toString());
				salaDto.setTextoBoton("Registrar salida");
				salaDto.setUrl("reservar-salas");
				salaDto.setUsoSala(sala.get("usoSala").toString());
				generico.setBotones(salaDto);
				generico.setCu("9");
				lstGenericos.add(generico);
			}
					
			respQuery = consultas.selectHashMap(vehiculos.verificaInicio(usuario));
            
			for (Map<String, Object> vehiculo : respQuery) {
				GenericoDto generico = new GenericoDto();
				generico.setMensaje(vehiculo.get("total_sin_verficacion").toString());
				if (generico.getMensaje().contains(" 0 ")) {
					continue;
				}
				generico.setCu("40");
				lstGenericos.add(generico);
			}
			
			respQuery = consultas.selectHashMap(vehiculos.programacionMantenimiento(usuario));
			
			for (Map<String, Object> vehiculo : respQuery) {
				GenericoDto generico = new GenericoDto();
				generico.setMensaje(vehiculo.get("total").toString());
				if (generico.getMensaje().contains(" 0 ")) {
					continue;
				}
				generico.setCu("40");
				lstGenericos.add(generico);
			}
			
			respQuery = consultas.selectHashMap(balanceCaja.obtenerMensaje(BALANCE_CAJA));
			
			for (Map<String, Object> mensaje : respQuery) {
				GenericoDto generico = new GenericoDto();
				generico.setMensaje(mensaje.get("msg").toString());
				generico.setCu("69");
			    lstGenericos.add(generico);
			}
			
			return lstGenericos;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			logUtil.crearArchivoLog(Level.SEVERE.toString(), this.getClass().getSimpleName(), this.getClass().getPackage().toString(), e.getMessage(), CONSULTA, authentication);
			return null;
		}		
		
	}

}
