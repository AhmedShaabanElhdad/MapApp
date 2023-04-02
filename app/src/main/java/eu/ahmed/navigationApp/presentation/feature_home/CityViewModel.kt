package eu.ahmed.navigationApp.presentation.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.ahmed.navigationApp.domain.core.Resource
import eu.ahmed.navigationApp.domain.entity.City
import eu.ahmed.navigationApp.domain.usecase.GetOurCityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(private val useCase: GetOurCityUseCase) :ViewModel(){

    private var _state:MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val state = _state.asStateFlow()


   init {
       getCity()
   }


    private fun getCity(){
      viewModelScope.launch {
            useCase().collect{
                _state.value = when(it){
                    is Resource.Data ->
                        UIState.Success(it.data)
                    is Resource.Error ->
                        UIState.Error(it.message)
                    Resource.Loading ->
                        UIState.Loading
                }
            }
        }
    }

}

sealed class UIState{
    object IDLE :UIState()
    object Loading :UIState()
    data class Success(val city: List<City>) :UIState()
    data class Error(val error: String) :UIState()
}