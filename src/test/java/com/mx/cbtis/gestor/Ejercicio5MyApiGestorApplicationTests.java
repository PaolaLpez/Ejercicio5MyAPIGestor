package com.mx.cbtis.gestor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mx.cbtis.gestor.controller.ComentariosController;
import com.mx.cbtis.gestor.controller.ProyectosController;
import com.mx.cbtis.gestor.controller.TareasController;
import com.mx.cbtis.gestor.controller.UsuarioController;
import com.mx.cbtis.gestor.modelo.Comentarios;
import com.mx.cbtis.gestor.modelo.Proyectos;
import com.mx.cbtis.gestor.modelo.Tareas;
import com.mx.cbtis.gestor.modelo.Usuario;

@SpringBootTest
class Ejercicio5MyApiGestorApplicationTests {

	@Autowired
	private UsuarioController usuarioController;

	@Autowired
	private ProyectosController proyectosController;

	@Autowired
	private TareasController tareasController;

	@Autowired
	private ComentariosController comentariosController;

	@Test
	@DisplayName("UT-01: Validar autenticación exitosa con credenciales registradas")
	void testUsuarioLoginExitoso() {
		Usuario credenciales = new Usuario();
		credenciales.setCorreo_electronico("ana22533@cbtis75.edu.mx");
		credenciales.setContrasena("0722");

		Usuario resultado = usuarioController.getUsuarioLogin(credenciales);
		assertNotNull(resultado, "El objeto usuario retornado no debe ser nulo");
		assertNotEquals("000", resultado.getNombre(), "Las credenciales válidas no deben retornar nombre de control '000'");
		assertEquals("ana22533@cbtis75.edu.mx", resultado.getCorreo_electronico());
	}

	@Test
	@DisplayName("UT-02: Validar rechazo seguro con credenciales inválidas (nombre: '000')")
	void testUsuarioLoginFallido() {
		Usuario credencialesFalsas = new Usuario();
		credencialesFalsas.setCorreo_electronico("falso@correo.com");
		credencialesFalsas.setContrasena("passwordInvalido");

		Usuario resultado = usuarioController.getUsuarioLogin(credencialesFalsas);
		assertNotNull(resultado);
		assertEquals("000", resultado.getNombre(), "Un usuario no existente debe retornar el nombre '000'");
	}

	@Test
	@DisplayName("UT-03: Validar alta de proyecto con atributos completos (flag: 1)")
	void testAddProyecto() {
		Proyectos nuevoProyecto = new Proyectos();
		nuevoProyecto.setNombre("Proyecto Automatizado JUnit");
		nuevoProyecto.setDescripcion("Verificación unitaria automatizada");
		nuevoProyecto.setFecha_inicio("2026-10-14");
		nuevoProyecto.setFecha_finalizacion("2026-11-30");
		nuevoProyecto.setEstado_proyecto("En proceso");
		nuevoProyecto.setFondo("/Images/fondo1.png");

		int resultado = proyectosController.addProyecto(nuevoProyecto);
		assertEquals(1, resultado, "El método addProyecto debe retornar 1 confirmando persistencia exitosa");
	}

	@Test
	@DisplayName("UT-04: Consultar y listar los proyectos almacenados en la base de datos")
	void testBuscarProyectos() {
		List<Proyectos> proyectos = proyectosController.getProyectos();
		assertNotNull(proyectos, "La lista de proyectos no debe ser nula");
		assertTrue(proyectos.size() > 0, "Debe existir al menos un proyecto en la base de datos");
	}

	@Test
	@DisplayName("UT-05: Consultar la colección de tareas existentes")
	void testBuscarTareas() {
		List<Tareas> tareas = tareasController.getTareas();
		assertNotNull(tareas, "La lista de tareas no debe ser nula");
	}

	@Test
	@DisplayName("UT-06: Validar registro de comentarios asociados a una tarea")
	void testAddComentario() {
		Comentarios comentario = new Comentarios();
		comentario.setId_tarea(199);
		comentario.setId_usuario("Ana Paola Ramírez Vázquez");
		comentario.setTexto_comentario("Comentario generado por prueba unitaria JUnit");
		comentario.setFecha_hora("2026-10-14 23:00:00");

		int resultado = comentariosController.addComentario(comentario);
		assertEquals(1, resultado, "El método addComentario debe retornar 1 confirmando inserción");
	}

}
