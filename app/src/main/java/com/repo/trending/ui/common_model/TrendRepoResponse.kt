package com.repo.trending.ui.common_model


data class TrendRepoResponse(
    val incomplete_results: Boolean?,
    val items: List<Repo>,
    val total_count: Int?
)