package com.dicoding.kostkater.model.meals

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MealsResponse(

	@field:SerializedName("data")
	val data: List<Meal>?
) : Parcelable

@Parcelize
data class Nutrisi(

	@field:SerializedName("kalori")
	val kalori: Int,

	@field:SerializedName("karbohidrat")
	val karbohidrat: Int,

	@field:SerializedName("protein")
	val protein: Int,

	@field:SerializedName("lemak")
	val lemak: Int
) : Parcelable

@Parcelize
data class Meal(

	@field:SerializedName("harga")
	val harga: String,

	@field:SerializedName("img_url")
	val imgUrl: String,

	@field:SerializedName("nutrisi")
	val nutrisi: Nutrisi,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("alergi")
	val alergi: List<String>?,

	@field:SerializedName("kehalalan")
	val kehalalan: Boolean
) : Parcelable