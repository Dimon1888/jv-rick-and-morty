package mate.academy.rickandmorty.repository.character;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.model.Character; // Працюємо з персонажем
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CharacterSpecificationProviderManager implements
        SpecificationProviderManager<Character> {
    private final List<SpecificationProvider<Character>> characterSpecificationProviders;

    @Override
    public SpecificationProvider<Character> getSpecificationProvider(String key) {
        return characterSpecificationProviders.stream()
                .filter(p -> p.getKey().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find correct specification provider for key: " + key));
    }
}
