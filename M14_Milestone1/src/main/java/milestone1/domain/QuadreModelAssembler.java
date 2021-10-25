package milestone1.domain;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import milestone1.controllers.Controller;

@Component
public class QuadreModelAssembler implements RepresentationModelAssembler<Quadre, EntityModel<Quadre>> {
	
	@Override
	public EntityModel<Quadre> toModel(Quadre quadre) {
		int idQ = quadre.getId();
		int idB = quadre.getBotiga() != null ? quadre.getBotiga().getId() : 0; 
		
		return EntityModel.of(quadre,
				linkTo(methodOn(Controller.class).actualitzarQuadre(quadre, idQ)).withRel("editar_quadre"),
				linkTo(methodOn(Controller.class).esborrarUnQuadre(idQ)).withRel("esborrar_quadre"),
				linkTo(methodOn(Controller.class).unaBotiga(idB)).withRel("botiga"),
				linkTo(methodOn(Controller.class).unQuadre(idQ)).withSelfRel(),
				linkTo(methodOn(Controller.class).totsQuadres()).withRel("quadres_tots"));
	}
	
}
