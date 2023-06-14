package com.dicoding.kostkater.model.meals

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MealsResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>
) : Parcelable

@Parcelize
data class Recipe(

	@field:SerializedName("resep")
	val resep: String? = null,

	@field:SerializedName("bahan Makanan")
	val bahanMakanan: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("harga")
	val harga: String? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("nutrisi")
	val nutrisi: Nutrisi? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("recipe")
	val recipe: Recipe? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("kehalalan")
	val kehalalan: Boolean? = null
) : Parcelable

@Parcelize
data class Nutrisi(

	@field:SerializedName("kalori")
	val kalori: Int? = null,

	@field:SerializedName("karbohidrat")
	val karbohidrat: Int? = null,

	@field:SerializedName("protein")
	val protein: Int? = null,

	@field:SerializedName("lemak")
	val lemak: Int? = null
) : Parcelable
