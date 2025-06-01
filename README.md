
# Архитектура: MVVM
* data слой: 
    * источники данных - ApiService и ReposytoryImpl получение и маппинг данных
* domain слой:
    * Repository - интерфейс получения данных, используется в UseCase
    * UseCases - реактивное получение данных Flow
* presentation:
    * Fragment - реализует Ui, только отрисовка без логики
    * ViewModel и UiState - получение данных через UseCases, 
    * модель UiState для хранения состояния данных для фрагмента
    * UiMapper - для финального маппинга данных, используется во ViewModel

# DI Hilt
* di.module: 
    * NetworkModule - для предоставления Retrofit c OkHttp и Moshi
    * ApiModule - для предоставления реализации ApiService
    * BindingModule - для предоставления реализации Repository
    vMediaModule - для предоставления ExoPlayer

# ExoPlayer с поддержкой 
* Полноэкранного режима
* Управления system bars
* Кастомных элементов управления(перемотка, изменение скорости и качества)
* Поворота экрана

# StateViewFlipper
* Для обработки ошибок и состояний загрузки
