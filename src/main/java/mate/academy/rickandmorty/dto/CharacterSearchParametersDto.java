package mate.academy.rickandmorty.dto;

public record CharacterSearchParametersDto(
        String[] name,
        String[] status
) {
}
