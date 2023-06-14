package com.imss.sivimss.notificaciones.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imss.sivimss.notificaciones.utils.Response;
import com.imss.sivimss.notificaciones.utils.AppConstantes;
import com.imss.sivimss.notificaciones.service.NotificacionesService;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionesController {

	@Autowired
	private NotificacionesService notificacionesService;
	
	@GetMapping("/tiempo-salas/{idPermiso}")
	public Response<Object> tiempoSalas(Authentication authentication, @PathVariable Integer idPermiso) throws IOException {
		
		return new Response<>(false, HttpStatus.OK.value(), AppConstantes.EXITO, notificacionesService.tiempoSalas(authentication, idPermiso));
		
	}
	
}
