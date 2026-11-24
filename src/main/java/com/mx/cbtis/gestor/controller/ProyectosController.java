package com.mx.cbtis.gestor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.repository.ProtectosRepository;

@RestController
public class ProyectosController {
	
	@Autowired 
	ProtectosRepository proyectostable;
	
	@GetMapping("/buscarProyecto")
	public Proyectos getProyecto(@RequestParam int id_proyecto) {
		Proyectos buscar = new Proyectos();
		Optional<Proyectos> proyecto = proyectostable.findById(id_proyecto);
		if(proyecto.isPresent()) {
			buscar = proyecto.get();
		}
		return buscar;
	}
	
	@PostMapping("/addProyecto")
	public int addProyecto(@RequestBody Proyectos proyecto) {
		int flag = 0;
		if(proyecto != null) {
			if (proyecto.getFecha_inicio() != null && proyecto.getFecha_inicio().trim().isEmpty()) {
				proyecto.setFecha_inicio(null);
			}
			if (proyecto.getFecha_finalizacion() != null && proyecto.getFecha_finalizacion().trim().isEmpty()) {
				proyecto.setFecha_finalizacion(null);
			}
			if (proyecto.getDescripcion() != null && proyecto.getDescripcion().length() > 100) {
				proyecto.setDescripcion(proyecto.getDescripcion().substring(0, 100));
			}
			proyectostable.save(proyecto);
			flag = 1;
		}
		return flag;
	}
	
	@PostMapping("/buscarProyectos")
	public List<Proyectos> getProyectos(){
		List<Proyectos> listaProyectos = new ArrayList<>();
		proyectostable.findAll().forEach(listaProyectos::add);
		return listaProyectos;
	}
	
	@DeleteMapping("/deleteProyecto")
	public int deleteProyecto(@RequestBody Proyectos proyecto) {
		int flag = 0;
		if(proyecto != null) {
			if (proyecto.getId_proyecto() != null && proyecto.getId_proyecto() != 0) {
				proyectostable.deleteById(proyecto.getId_proyecto());
				flag = 1;
			} else if (proyecto.getNombre() != null) {
				proyectostable.delete(proyecto);
				flag = 1;
			}
		}
		return flag;
	}
	
	@PostMapping("/updateProyecto")
	public int updateProyecto(@RequestBody Proyectos proyecto) {
		return addProyecto(proyecto);
	}
}
