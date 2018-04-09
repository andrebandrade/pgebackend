package br.gov.ce.pge.cadastrousuario.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.pge.cadastrousuario.entidade.Usuario;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("selective")
public class UsuarioController {

	private Map<Integer, Usuario> usuarios;

	public UsuarioController() {

		this.usuarios = new HashMap<Integer, Usuario>();

		Usuario usr1 = new Usuario(1, "João", "joao", "12345");
		Usuario usr2 = new Usuario(2, "Maria", "maria", "12345");
		Usuario usr3 = new Usuario(3, "Pedro", "pedro", "12345");

		usuarios.put(1, usr1);
		usuarios.put(2, usr2);
		usuarios.put(3, usr3);
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		return new ResponseEntity<List<Usuario>>(new ArrayList<Usuario>(usuarios.values()), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.GET)
	public ResponseEntity<Usuario> localizarUsuario(@PathVariable("id") Integer id) {
		Usuario usr = this.usuarios.get(id);

		if (usr == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<Usuario>(usr, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/users", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletarUsuario(@PathVariable("id") Integer id) {
		Usuario usr = usuarios.remove(id);

		if (usr == null) {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> adicionarUsuario(@RequestBody Usuario usuario) {
		Integer lId = usuarios.size() + 1;
		
		if (usuario != null && usuario.getId() == null) {
			usuario.setId(lId);
		}
		
		usuarios.put(lId, usuario);
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}/users", method = RequestMethod.PUT, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Integer id, @RequestBody Usuario usuario) {
		usuarios.put(id, usuario);
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

}