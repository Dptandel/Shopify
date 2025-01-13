package com.app.shopify.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.shopify.models.Product
import com.app.shopify.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {
    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val specialProducts: StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestDealsProducts =
        MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val bestDealProducts: StateFlow<Resource<List<Product>>> = _bestDealsProducts

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.UnSpecified())
    val bestProducts: StateFlow<Resource<List<Product>>> = _bestProducts

    private val pagingInfo = PagingInfo()

    init {
        fetchSpecialProducts()
        fetchBestDealsProducts()
        fetchBestProducts()
    }

    fun fetchBestProducts() {
        if (!pagingInfo.isPagingEnd) {
            viewModelScope.launch {
                _bestProducts.emit(Resource.Loading())
            }
            firestore.collection("products").orderBy("id", Query.Direction.ASCENDING)
                .limit(pagingInfo.bestProductPage * 4).get()
                .addOnSuccessListener { result ->
                    val bestProductList = result.toObjects(Product::class.java)
                    pagingInfo.isPagingEnd = bestProductList == pagingInfo.oldBestProducts
                    pagingInfo.oldBestProducts = bestProductList
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Success(bestProductList))
                    }
                    pagingInfo.bestProductPage++
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _bestProducts.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
    }

    fun fetchBestDealsProducts() {
        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())
        }
        firestore.collection("products").get()
            .addOnSuccessListener { result ->
                val bestDealProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Success(bestDealProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun fetchSpecialProducts() {
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }
        firestore.collection("products").whereEqualTo("category", "Special Products").get()
            .addOnSuccessListener { result ->
                val specialProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductList))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Error(it.message.toString()))
                }
            }
    }
}

internal data class PagingInfo(
    var bestProductPage: Long = 1,
    var oldBestProducts: List<Product> = emptyList(),
    var isPagingEnd: Boolean = false
)