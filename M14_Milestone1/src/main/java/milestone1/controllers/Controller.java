package milestone1.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import milestone1.domain.Botiga;
import milestone1.domain.BotigaModelAssembler;
import milestone1.domain.Quadre;
import milestone1.domain.QuadreModelAssembler;
import milestone1.persistence.RepositoriBotigues;
import milestone1.persistence.RepositoriQuadres;

@RestController
public class Controller {
	
	private final RepositoriBotigues repositoriBotigues;
	private final RepositoriQuadres repositoriQuadres;
	private final BotigaModelAssembler assemblerBotiga;
	private final QuadreModelAssembler assemblerQuadre;
	
	public Controller(RepositoriBotigues repositoriBotigues, RepositoriQuadres repositoriQuadres,
			BotigaModelAssembler assemblerBotiga, QuadreModelAssembler assemblerQuadre) {
		this.repositoriBotigues = repositoriBotigues;
		this.repositoriQuadres = repositoriQuadres;
		this.assemblerBotiga = assemblerBotiga;
		this.assemblerQuadre = assemblerQuadre;
    }
	
	@GetMapping("/botigues")
	public CollectionModel<EntityModel<Botiga>> totesBotigues() {
		List<EntityModel<Botiga>> botigues = repositoriBotigues.findAll().stream()
				.map(assemblerBotiga::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(botigues,
				linkTo(methodOn(Controller.class).totesBotigues()).withSelfRel(),
				linkTo(methodOn(Controller.class).totsQuadres()).withRel("quadres_tots"));
	}
	
	@GetMapping("/quadres")
	public CollectionModel<EntityModel<Quadre>> totsQuadres() {
		List<EntityModel<Quadre>> quadres = repositoriQuadres.findAll().stream()
				.map(assemblerQuadre::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(quadres,
				linkTo(methodOn(Controller.class).totsQuadres()).withSelfRel(),
				linkTo(methodOn(Controller.class).totesBotigues()).withRel("botigues_totes"));
	}
	
	@GetMapping("/botigues/{id}")
	public EntityModel<Botiga> unaBotiga(@PathVariable int id) {
		Botiga botiga = repositoriBotigues.findById(id)
				.orElseThrow(() -> new BotigaNotFoundException(id));
		
		return assemblerBotiga.toModel(botiga);
	}
	
	@GetMapping("/botigues/quadres/{id}")
	public EntityModel<Quadre> unQuadre(@PathVariable int id) {
		Quadre quadre = repositoriQuadres.findById(id)
				.orElseThrow(() -> new QuadreNotFoundException(id));
		
		return assemblerQuadre.toModel(quadre);
	}
	
	@GetMapping("/botigues/{id}/quadres")
	public CollectionModel<EntityModel<Quadre>> quadresUnaBotiga(@PathVariable int id) {
		Botiga botiga = repositoriBotigues.findById(id)
				.orElseThrow(() -> new BotigaNotFoundException(id));
		List<EntityModel<Quadre>> quadres = repositoriQuadres.findAll().stream()
				.filter(q -> q.getBotiga() != null && q.getBotiga().equals(botiga))
				.map(assemblerQuadre::toModel)
				.collect(Collectors.toList());
		
		return CollectionModel.of(quadres,
				linkTo(methodOn(Controller.class).unaBotiga(id)).withRel("botiga"),
				linkTo(methodOn(Controller.class).quadresUnaBotiga(id)).withSelfRel(),
				linkTo(methodOn(Controller.class).totsQuadres()).withRel("quadres_tots"));
	}
	
	@PostMapping("/botigues")
	public ResponseEntity<?> afegirBotiga(@RequestBody Botiga novaBotiga) {
		EntityModel<Botiga> entityModel = assemblerBotiga.toModel(repositoriBotigues.save(novaBotiga));
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@PostMapping("/botigues/{id}/quadres")
	public ResponseEntity<?> afegirQuadre(@RequestBody Quadre nouQuadre,  @PathVariable int id) {
		Botiga botiga = repositoriBotigues.findById(id)
				.orElseThrow(() -> new BotigaNotFoundException(id));
		if(nouQuadre.getBotiga().getCapacitat() >
				repositoriQuadres.findAll().stream()
						.filter(q -> q.getBotiga() != null && q.getBotiga().equals(botiga)).count()) {
			EntityModel<Quadre> entityModel = assemblerQuadre.toModel(repositoriQuadres.save(nouQuadre));
			
			return ResponseEntity
					.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
					.body(entityModel);
		}
		// Límite de cuadros alcanzado
		throw new CapacitatUltrapassadaException(id);
	}
	
	@PutMapping("/botigues/{id}")
	public ResponseEntity<?> actualitzarBotiga(@RequestBody Botiga novesDadesBotiga, @PathVariable int id) {
		Botiga botiga = repositoriBotigues.findById(id)
				.map(b -> {
					if(novesDadesBotiga.getNom() != null)
						b.setNom(novesDadesBotiga.getNom());
					if(novesDadesBotiga.getCapacitat() != (short)0)
						b.setCapacitat(novesDadesBotiga.getCapacitat());
					return repositoriBotigues.save(b);
				})
				.orElseGet(() -> {
					novesDadesBotiga.setId(id);
					return repositoriBotigues.save(novesDadesBotiga);
				});
		EntityModel<Botiga> entityModel = assemblerBotiga.toModel(botiga);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@PutMapping("/botigues/quadres/{id}")
	public ResponseEntity<?> actualitzarQuadre(@RequestBody Quadre novesDadesQuadre, @PathVariable int id) {
		Quadre quadre = repositoriQuadres.findById(id)
				.map(q -> {
					if(novesDadesQuadre.getNom() != null)
						q.setNom(novesDadesQuadre.getNom());
					// Una entrada 'null' en el campo 'autor' es relevante; se entiende que la obra es «anónima»
					q.setAutor(novesDadesQuadre.getAutor());
					if(novesDadesQuadre.getPreu() != 0f)
						q.setPreu(novesDadesQuadre.getPreu());
					if(novesDadesQuadre.getData() != null)
						q.setData(novesDadesQuadre.getData());
					if(novesDadesQuadre.getBotiga() != null)
						q.setBotiga(novesDadesQuadre.getBotiga());
					return repositoriQuadres.save(q);
				})
				.orElseGet(() -> {
					novesDadesQuadre.setId(id);
					return repositoriQuadres.save(novesDadesQuadre);
				});
		EntityModel<Quadre> entityModel = assemblerQuadre.toModel(quadre);
		
		return ResponseEntity
				.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
				.body(entityModel);
	}
	
	@DeleteMapping("/botigues/{id}")
	public ResponseEntity<?> esborrarBotiga(@PathVariable int id) {
		repositoriBotigues.findById(id)
				.orElseThrow(() -> new BotigaNotFoundException(id));
		// Esta opción no elimina los cuadros almacenados en 'repositoriQuadres'
		repositoriQuadres.findAll().stream()
				.filter(q -> q.getBotiga() != null && q.getBotiga().getId() == id)
				.forEach(q -> q.setBotiga(null));
		repositoriBotigues.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/botigues/quadres/{id}") // Si venem un quadre (no ha de quedar registre de res)
	public ResponseEntity<?> esborrarUnQuadre(@PathVariable int id) {
		repositoriQuadres.delete(repositoriQuadres.findById(id)
				.orElseThrow(() -> new QuadreNotFoundException(id)));
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/botigues/{id}/quadres") // Resposta a la batuda policial!
	public ResponseEntity<?> esborrarQuadresBotiga(@PathVariable int id) {
		Botiga botiga = repositoriBotigues.findById(id)
				.orElseThrow(() -> new BotigaNotFoundException(id));
		botiga.getQuadres().forEach(repositoriQuadres::delete); // Eliminamos todos los cuadros...
		
		return ResponseEntity.status(HttpStatus.RESET_CONTENT).build();
	}
	
}
