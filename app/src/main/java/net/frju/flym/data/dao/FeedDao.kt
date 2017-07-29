package net.frju.flym.data.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import net.frju.flym.data.entities.Feed
import java.util.*


@Dao
interface FeedDao {
    @get:Query("SELECT * FROM feeds")
    val all: List<Feed>

    @get:Query("SELECT * FROM feeds WHERE isGroup=1 OR groupId IS NULL")
    val observeRootItems: LiveData<List<Feed>>

    @Query("SELECT * FROM feeds WHERE id LIKE :arg0 LIMIT 1")
    fun findById(id: String): Feed?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg feeds: Feed) {
        feeds
                .filter { it.id.isEmpty() }
                .forEach { it.id = UUID.randomUUID().toString() }
    }

    @Delete
    fun deleteAll(feeds: Feed)
}