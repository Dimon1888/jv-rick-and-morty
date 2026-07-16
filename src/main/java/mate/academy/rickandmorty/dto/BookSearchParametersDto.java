package mate.academy.rickandmorty.dto;

public record BookSearchParametersDto(
        String title,
        String author,
        String isbn
) {
}
