package br.edu.univassouras.api_vet_tadeu.config;

import br.edu.univassouras.api_vet_tadeu.enums.Especie;
import br.edu.univassouras.api_vet_tadeu.enums.PorteAnimal;
import br.edu.univassouras.api_vet_tadeu.enums.StatusAdocao;
import br.edu.univassouras.api_vet_tadeu.model.Animal;
import br.edu.univassouras.api_vet_tadeu.repository.AnimalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final AnimalRepository animalRepository;

    public DataLoader(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (animalRepository.count() == 0) {
            Animal a1 = new Animal(null, "Thor", Especie.CACHORRO, "Golden Retriever", 3,
                    "Macho", PorteAnimal.GRANDE, "Dócil, adora brincar com crianças, castrado e vacinado.", StatusAdocao.DISPONIVEL);

            Animal a2 = new Animal(null, "Luna", Especie.GATO, "Siamês", 2,
                    "Fêmea", PorteAnimal.PEQUENO, "Muito carinhosa, tranquila, vacinada e vermifugada.", StatusAdocao.DISPONIVEL);

            Animal a3 = new Animal(null, "Bob", Especie.CACHORRO, "Vira-lata (SRD)", 1,
                    "Macho", PorteAnimal.MEDIO, "Muito ativo, brincalhão e sociável com outros animais.", StatusAdocao.EM_PROCESSO);

            Animal a4 = new Animal(null, "Pipoca", Especie.GATO, "Persa", 4,
                    "Fêmea", PorteAnimal.PEQUENO, "Calma, perfeita para apartamento, já adotada por uma família.", StatusAdocao.ADOTADO);

            animalRepository.saveAll(Arrays.asList(a1, a2, a3, a4));
            System.out.println(">>> [DataLoader] 4 animais de exemplo inseridos com sucesso no banco H2!");
        }
    }
}
