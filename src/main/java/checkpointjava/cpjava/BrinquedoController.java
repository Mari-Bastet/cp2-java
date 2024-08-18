package checkpointjava.cpjava;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brinquedos")
public class BrinquedoController {

	@Autowired
	BrinquedoRepository repo;

	@GetMapping("/{id}")
	public ResponseEntity<?> retornaBrinquedoPorId(@PathVariable Long id) {

		try {
			Optional<Brinquedo> bri = repo.findById(id);
			
			if (bri.isPresent()) {
				
				Brinquedo brinquedo = bri.get();

				EntityModel<Brinquedo> brinquedoResource = EntityModel.of(brinquedo,
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
								.deletarBrinquedoPorId(brinquedo.getId())).withRel("DELETE"));
				
				return ResponseEntity.ok(brinquedoResource);

			} else {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrou o brinquedo solicitado");

			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar brinquedo:" + e);

		}

	}

	@PostMapping("/adicionarBrinquedo")
	public ResponseEntity<?> adicionaBrinquedo(@RequestBody Brinquedo brinquedoEntrada) {

		try {

			
		Brinquedo brinquedo = repo.save(brinquedoEntrada);

			EntityModel<Brinquedo> brinquedoResource = EntityModel.of(brinquedo,
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
							.retornaBrinquedoPorId(brinquedo.getId())).withRel("GET"),
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
							.deletarBrinquedoPorId(brinquedo.getId())).withRel("DELETE"),
					WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
							.alterarBrinquedoPorId(brinquedo)).withRel("POST"));

			return ResponseEntity.ok(brinquedoResource);

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Ocorreu um erro ao inserir brinquedo: " + e);
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarBrinquedoPorId(@PathVariable Long id) {

		try {
			repo.deleteById(id);

			return ResponseEntity.status(HttpStatus.OK).body("Brinquedo excluído com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar brinquedo: " + e);

		}

	}

	  @PutMapping("/atualizarBrinquedo")
	    public ResponseEntity<?> alterarBrinquedoPorId(@RequestBody Brinquedo brinquedo) {
	        try {
	            Optional<Brinquedo> brinquedoExistenteOptional = repo.findById(brinquedo.getId());

	            if (!brinquedoExistenteOptional.isPresent()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Brinquedo não encontrado para o ID: " + brinquedo.getId());
	            }

	            Brinquedo brinquedoExistente = brinquedoExistenteOptional.get();
	            
	            brinquedoExistente.setNome(brinquedo.getNome());
	            brinquedoExistente.setPreco(brinquedo.getPreco());
	            brinquedoExistente.setTamanho(brinquedo.getTamanho());
	            brinquedoExistente.setTipo(brinquedo.getTipo());
	            brinquedoExistente.setClassificacao(brinquedo.getClassificacao());
	            
	            Brinquedo brinquedoAtualizado = repo.save(brinquedoExistente);
	            
	            EntityModel<Brinquedo> brinquedoResource = EntityModel.of(brinquedoAtualizado,
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
								.retornaBrinquedoPorId(brinquedoAtualizado.getId())).withRel("GET"),
						WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BrinquedoController.class)
								.deletarBrinquedoPorId(brinquedoAtualizado.getId())).withRel("DELETE"));
	            
	            return ResponseEntity.ok(brinquedoResource);

	        } catch (Exception e) {

	        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar brinquedo: " + e.getMessage());
	        }
	    }

}
