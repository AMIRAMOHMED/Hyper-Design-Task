package com.example.hyperdesigntask.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hyperdesigntask.data.local.TokenManager
import com.example.hyperdesigntask.data.model.ShippmentsResponse
import com.example.hyperdesigntask.data.repo.AuthRepo
import com.example.hyperdesigntask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val authRepo: AuthRepo,
) : ViewModel() {


    private val _shippments = MutableStateFlow<Resource<ShippmentsResponse>>(Resource.Loading)
    val shippments: StateFlow<Resource<ShippmentsResponse> >get() = _shippments

        fun fetchShippments(page: String) {
            viewModelScope.launch {
                _shippments.value = Resource.Loading

                try {
                    val response = authRepo.getShippments(page)
                    _shippments.value = Resource.Success(response)
                } catch (e: Exception) {
                    _shippments.value = Resource.Error("Error fetching shipments: ${e.localizedMessage}")
                    Log.e("HomeViewModel", "Error fetching shipments", e)
                }
            }
        }

}