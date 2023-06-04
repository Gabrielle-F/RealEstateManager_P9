package com.openclassrooms.realestatemanager.utils

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.injection.DatabaseModule
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.repository.PropertyRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RealEstateManagerContentProvider @Inject constructor(
    private val propertyRepository: PropertyRepository,
    private val databaseModule: DatabaseModule, private val property: Property
) : ContentProvider() {

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri,
        firstArray: Array<out String>?,
        firstString: String?,
        secondArray: Array<out String>?,
        secondString: String?
    ): Cursor? {
        context?.let { context ->
            val id = ContentUris.parseId(uri).toInt()
            val cursor =
                databaseModule.provideAppDatabase(context).propertyDao().getPropertyWithCursor(id)
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? = "vnd.android.cursor.item/$AUTHORITY.$PROPERTY_TABLE"

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        context?.let { context ->
            contentValues?.let {
                var contentUris: Uri? = null
                runBlocking {
                    val property = property.fromContentValues(it)
                    val id =
                        databaseModule.provideAppDatabase(context).propertyDao().insert(property)
                    if (!!id.equals(0L)) {
                        context.contentResolver.notifyChange(uri, null)
                        contentUris = ContentUris.withAppendedId(uri, id)
                    }
                }
                return contentUris
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        p2: String?,
        p3: Array<out String>?
    ): Int {
        context?.let { context ->
            contentValues?.let {
                var count = 0
                runBlocking {
                    count = databaseModule.provideAppDatabase(context).propertyDao()
                        .update(property.fromContentValues(it))
                }
                context.contentResolver.notifyChange(uri, null)
                return count
            }
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }

    companion object {
        const val AUTHORITY =
            "com.openclassrooms.realestatemanager.utils.RealEstateManagerContentProvider"
        private const val PROPERTY_TABLE = "property_table"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PROPERTY_TABLE")
    }
}