package com.repo.trending.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.*

@Entity(tableName = "repo")
@Parcelize
data class Repo(
    @PrimaryKey
    val id: Long,
    val allow_forking: Boolean?,
    val archive_url: String?,
    val archived: Boolean?,
    val assignees_url: String?,
    val blobs_url: String?,
    val branches_url: String?,
    val clone_url: String?,
    val collaborators_url: String?,
    val comments_url: String?,
    val commits_url: String?,
    val compare_url: String?,
    val contents_url: String?,
    val contributors_url: String?,
    val created_at: String?,
    val default_branch: String?,
    val deployments_url: String?,
    val description: String?,
    val disabled: Boolean?,
    val downloads_url: String?,
    val events_url: String?,
    val fork: Boolean?,
    val forks: Int?,
    val forks_count: Int?,
    val forks_url: String?,
    val full_name: String?,
    val hooks_url: String?,
    val html_url: String?,
    val is_template: Boolean?,
    val issue_comment_url: String?,
    val issue_events_url: String?,
    val issues_url: String?,
    val keys_url: String?,
    val labels_url: String?,
    val name: String?,
    val node_id: String?,
    val notifications_url: String?,
    val open_issues: Int?,
    val open_issues_count: Int?,
    val owner: Owner?,
    val pulls_url: String?,
    val pushed_at: String?,
    val releases_url: String?,
    val score: Double?,
    val size: Int?,
    val tags_url: String?,
    val teams_url: String?,
    val trees_url: String?,
    val updated_at: String?,
    val url: String?,
    val visibility: String?,
    val watchers: Int?,
    val watchers_count: Int?,
    var isChecked: Boolean=false
):Parcelable