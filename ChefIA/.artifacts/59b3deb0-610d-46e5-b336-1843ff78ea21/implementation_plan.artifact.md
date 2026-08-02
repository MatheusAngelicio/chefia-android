# Listagem de Favoritos na Home

Este plano visa implementar uma seção de receitas favoritas na `HomeScreen`, permitindo que o usuário visualize rapidamente seus pratos preferidos com uma rolagem lateral e acesso à listagem completa.

## User Review Required

> [!IMPORTANT]
> - A `HomeScreen` passará a observar a lista completa de receitas favoritas do banco de dados Room.
> - Criaremos um novo componente de card (`HomeFavoriteCard`) otimizado para exibição horizontal.
> - A arquitetura seguirá o padrão de UseCases e reatividade com Flow.

## Proposed Changes

### Domain Layer

#### [MODIFY] [RecipeRepository.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/domain/repository/RecipeRepository.kt)
Adicionar o método para obter a lista completa de receitas favoritas:
- `fun getFavoriteRecipes(): Flow<List<Recipe>>`

#### [NEW] [ObserveFavoriteRecipesUseCase.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/domain/usecase/favorites/ObserveFavoriteRecipesUseCase.kt)
UseCase para observar a lista de receitas favoritas.

### Data Layer

#### [MODIFY] [RecipeRepositoryImpl.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/data/repository/RecipeRepositoryImpl.kt)
Implementar o novo método integrando com o `RecipeLocalDataSource`.

### Presentation Layer (Home)

#### [MODIFY] [HomeUiState.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeUiState.kt)
Adicionar `val favoriteRecipes: List<Recipe> = emptyList()` ao estado.

#### [MODIFY] [HomeAction.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeAction.kt)
Adicionar novas ações:
- `data class RecipeClicked(val recipe: Recipe) : HomeAction`
- `data object ViewAllFavoritesClicked : HomeAction`

#### [MODIFY] [HomeViewModel.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeViewModel.kt)
- Injetar o `ObserveFavoriteRecipesUseCase`.
- Observar os favoritos no `init` e atualizar o `UiState`.

#### [NEW] [HomeFavoriteCard.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/components/HomeFavoriteCard.kt)
Criar um card compacto e atraente para a Home, exibindo o emoji da categoria, nome da receita e tempo de preparo.

#### [MODIFY] [HomeScreen.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/feature/home/HomeScreen.kt)
- Atualizar a UI para incluir a seção "Favoritos" abaixo dos cards de ação.
- Implementar a `LazyRow` para a rolagem lateral.
- Adicionar o texto "Ver tudo" clicável.

### DI

#### [MODIFY] [RecipeModule.kt](file:///Users/premiersoft/Documents/kotlin/ChefIA/app/src/main/java/com/example/chefia/di/RecipeModule.kt)
Registrar o novo UseCase no Koin.

## Verification Plan

### Manual Verification
- Favoritar uma receita na tela de resultados.
- Voltar para a Home e verificar se a receita aparece na nova seção.
- Testar a rolagem lateral com múltiplas receitas.
- Verificar se o layout se mantém harmônico com o tema do app.
