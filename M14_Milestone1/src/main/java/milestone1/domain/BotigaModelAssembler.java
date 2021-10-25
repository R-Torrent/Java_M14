package milestone1.domain;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

import milestone1.controllers.Controller;

@Component
public class BotigaModelAssembler implements RepresentationModelAssembler<Botiga, EntityModel<Botiga>> {
	
	@Override
	public EntityModel<Botiga> toModel(Botiga botiga) {
		int id = botiga.getId();
		
		return EntityModel.of(botiga,
				linkTo(methodOn(Controller.class).quadresUnaBotiga(id)).withRel("quadres_exposats"),
				linkTo(methodOn(Controller.class).esborrarQuadresBotiga(id)).withRel("cremar_quadres"),
				linkTo(methodOn(Controller.class).actualitzarBotiga(botiga, id)).withRel("editar_botiga"),
				linkTo(methodOn(Controller.class).esborrarBotiga(id)).withRel("esborrar_botiga"),
				linkTo(methodOn(Controller.class).unaBotiga(id)).withSelfRel(),
				linkTo(methodOn(Controller.class).totesBotigues()).withRel("botigues_totes"));
	}
	
}
