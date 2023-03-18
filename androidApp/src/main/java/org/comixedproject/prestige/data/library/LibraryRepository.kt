package org.comixedproject.prestige.data.library

import androidx.room.Transaction
import org.comixedproject.prestige.model.library.Library

/**
 * <code>LibraryRepository</code> provides methods for working with persisted instances of {@link Library}.
 */
class LibraryRepository(private val libraryDao: LibraryDao) {
    companion object {
        @Volatile
        private var instance: LibraryRepository? = null

        fun getInstance(libraryDao: LibraryDao) = this.instance ?: synchronized(this) {
            instance ?: LibraryRepository(libraryDao).also {
                instance = it
            }
        }
    }

    /**
     * Returns the list of all libraries.
     */
    fun getLibraries() = libraryDao.loadAll()

    @Transaction
    open suspend fun create(library: Library) {
        this.libraryDao.create(library)
    }

    @Transaction
    open suspend fun update(library: Library) {
        this.libraryDao.update(library)
    }
}