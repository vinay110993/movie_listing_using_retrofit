package com.movie_tmdb.model

import com.google.gson.annotations.SerializedName


data class MovieListingModel(

	@field:SerializedName("status_message")
	val statusMessage: String? = null,

	@field:SerializedName("status_code")
	val statusCode: Int? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("results")
	val results: List<MovieDetailModel> ?= emptyList()

)
