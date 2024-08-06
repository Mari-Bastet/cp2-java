package checkpointjava.cpjava;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

				return ResponseEntity.ok(bri);

			} else {

				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrou o brinquedo solicitado");

			}

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar brinquedo:" + e);

		}

	}

	@PostMapping
	public ResponseEntity<String> adicionaBrinquedo(@RequestBody Brinquedo brinquedo) {

		try {

			repo.save(brinquedo);

			return ResponseEntity.ok("Brinquedo criado com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro ao inserir brinquedo: " + e);
		}

	}

	@DeleteMapping
	public ResponseEntity<?> deletarBrinquedoPorId(@PathVariable Long id) {

		try {
			repo.deleteById(id);
			;

			return ResponseEntity.status(HttpStatus.OK).body("Brinquedo excluído com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao deletar brinquedo: " + e);

		}

	}
	
	@PutMapping
	public ResponseEntity<?> alterarBrinquedoPorId(@RequestBody Brinquedo brinquedo) {

		try {
			repo.save(brinquedo);
			

			return ResponseEntity.status(HttpStatus.OK).body("Brinquedo atualizado com sucesso");

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar brinquedo: " + e);

		}

	}

}
