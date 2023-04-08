package org.comixedproject.prestige.model.library

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * <code>Library</code> represents a remote ComiXed or other OPDS server.
 *
 * @author Darryl L. Pierce
 */
@Entity(tableName = "libraries")
data class Library(
    @PrimaryKey(autoGenerate = true) var libraryId: Long = 0,
    val name: String,
    val url: String,
    val username: String,
    val password: String
)