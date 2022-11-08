package com.repo.trending.model

data class TrendRepoResponse(
    val incomplete_results: Boolean?,
    val items: List<Repo>,
    val total_count: Int?
)