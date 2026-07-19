package mate.academy.rickandmorty.repository.character;

import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.CharacterSearchParametersDto; // Імпортуємо наш новий DTO
import mate.academy.rickandmorty.model.Character; // Працюємо з персонажем
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CharacterSpecificationBuilder implements SpecificationBuilder<Character,
        CharacterSearchParametersDto> {
    // Використовуємо менеджер для Character
    private final SpecificationProviderManager<Character> characterSpecificationProviderManager;

    @Override
    public Specification<Character> build(CharacterSearchParametersDto searchParameters) {
        Specification<Character> spec = Specification.where(null);

        // Фільтр за ім'ям (name)
        if (searchParameters.name() != null && searchParameters.name().length > 0) {
            spec = spec.and(characterSpecificationProviderManager
                    .getSpecificationProvider("name") // або NameSpecificationProvider
                    // .KEY, якщо є константа
                    .getSpecification(searchParameters.name()));
        }

        // Фільтр за статусом (status: Alive, Dead, unknown)
        if (searchParameters.status() != null && searchParameters.status().length > 0) {
            spec = spec.and(characterSpecificationProviderManager
                    .getSpecificationProvider("status") // або StatusSpecificationProvider.KEY
                    .getSpecification(searchParameters.status()));
        }

        return spec;
    }
}
