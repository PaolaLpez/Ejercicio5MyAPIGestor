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

import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.repository.TareasRepository;

@RestController
public class TareasController {
	
	@Autowired
	TareasRepository tareastable;
	
	@PostMapping("/getTarea")
	public List<Tareas> getTarea(@RequestBody Tareas id_proyecto) {
		List<Tareas> listaTareas = tareastable.findByIdProyecto(id_proyecto.getId_proyecto());
		return listaTareas;
	}
	
	@GetMapping("/buscarTarea")
	public Tareas getTarea(@RequestParam int id_tarea) {
		
		Tareas buscar = new Tareas();
		Optional<Tareas> tarea = tareastable.findById(id_tarea);
		if(tarea.isPresent()) {
			buscar = tarea.get();
		}
		
		return buscar;
	}
	
	@PostMapping("/addTareas")
	public int addTareas(@RequestBody Tareas tareas) {
		int flag = 0;
		if (tareas != null && (tareas.getNombre() != null || tareas.getId_tarea() != null)) {
			// Sanear fechas vacias para evitar error SQL de MariaDB en columnas tipo DATE
			if (tareas.getFecha_vencimiento() != null && tareas.getFecha_vencimiento().trim().isEmpty()) {
				tareas.setFecha_vencimiento(null);
			}
			if (tareas.getId_tarea() != null && tareas.getId_tarea() != 0 && (tareas.getNombre() == null || tareas.getNombre().trim().isEmpty())) {
				tareastable.findById(tareas.getId_tarea()).ifPresent(existing -> {
					if (tareas.getDescripcion() != null) existing.setDescripcion(tareas.getDescripcion());
					if (tareas.getEstado() != null) existing.setEstado(tareas.getEstado());
					if (tareas.getLista() != null) existing.setLista(tareas.getLista());
					if (tareas.getFecha_vencimiento() != null) existing.setFecha_vencimiento(tareas.getFecha_vencimiento());
					tareastable.save(existing);
				});
			} else {
				tareastable.save(tareas);
			}
			flag = 1;
		}
		return flag;
	}
	
	@PostMapping("/updateTareas")
	public int updateTareas(@RequestBody Tareas tareas) {
		return addTareas(tareas);
	}
	
	@DeleteMapping("/deleteTareas")
	public int deleteTareas(@RequestBody Tareas tareas) {
		int flag = 0;
		if (tareas != null && tareas.getId_tarea() != null && tareas.getId_tarea() != 0) {
			tareastable.deleteById(tareas.getId_tarea());
			flag = 1;
		} else if (tareas != null && tareas.getNombre() != null) {
			tareastable.delete(tareas);
			flag = 1;
		}
		return flag;
	}
	
	@PostMapping("/buscarTareas")
	public List<Tareas> getTareas(){
		List<Tareas> listaTareas = new ArrayList<>();
		tareastable.findAll().forEach(listaTareas :: add);
		return listaTareas;
	}

}
