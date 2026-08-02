# Walkthrough - Favoritos na Home com Room

Implementei a seção de receitas favoritas na `HomeScreen`. Agora, as receitas que o usuário favorita na tela de resultados são exibidas automaticamente na Home com uma rolagem lateral reativa, utilizando o banco de dados Room.

## Mudanças Realizadas

### Camada de Dados
- **[RecipeRepository](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/domain/repository/RecipeRepository.kt):** Adicionado suporte para buscar a lista completa de receitas favoritas.
- **[RecipeLocalDataSource](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/data/local/RecipeLocalDataSource.kt):** Implementada a conversão de `RecipeEntity` para `Recipe` no fluxo de favoritos.

### Camada de Domínio
- **[ObserveFavoriteRecipesUseCase](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/domain/usecase/favorites/ObserveFavoriteRecipesUseCase.kt):** Novo UseCase que fornece um `Flow<List<Recipe>>` com todas as receitas favoritadas.

### Home Feature
- **[HomeViewModel](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeViewModel.kt):** Atualizado para observar o `ObserveFavoriteRecipesUseCase` e limitar a exibição às **últimas 5 receitas favoritadas**.
- **[HomeFavoriteCard](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/components/HomeFavoriteCard.kt):** Novo componente de card compacto, exibindo emoji da categoria, título e tempo de preparo.

...

### Persistência e Ordenação
- **[RecipeEntity](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/data/local/entity/RecipeEntity.kt):** Adicionado campo `favoritedAt` para registrar o momento em que a receita foi salva.
- **[RecipeDao](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/data/local/dao/RecipeDao.kt):** Consulta de favoritos atualizada para ordenar de forma decrescente pela data de salvamento.
- **[HomeScreen](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeScreen.kt):**
    - Adicionada seção "Favoritos" que aparece apenas quando há receitas salvas.
    - Implementada `LazyRow` para navegação horizontal entre os favoritos.
    - Adicionado link "Ver tudo" para futuras expansões.

## Verificação

- **Reatividade:** Ao favoritar uma receita na tela de geração, ela aparece instantaneamente na Home ao voltar.
- **Visual:** O `HomeFavoriteCard` utiliza o sistema de estilos visuais (`visualStyle`) já estabelecido no projeto para manter a identidade visual.
- **DI:** `HomeViewModel` e o novo UseCase registrados corretamente no Koin.

> [!TIP]
> A seção de favoritos na Home só é exibida se o usuário possuir ao menos uma receita salva, mantendo a interface limpa.
