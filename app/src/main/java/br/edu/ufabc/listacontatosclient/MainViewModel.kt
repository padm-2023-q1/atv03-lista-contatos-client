package br.edu.ufabc.listacontatosclient

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import br.edu.ufabc.listacontatosclient.model.Contact
import br.edu.ufabc.listacontatosclient.model.Repository

/**
 * The main view model.
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = Repository()

    /**
     * LiveData to signal an ongoing data loading operation.
     */
    val isLoading = MutableLiveData(false)

    /**
     * Class to signal ViewModel status.
     */
    sealed class Status {
        /**
         * Error status.
         * @property e a throwable
         */
        class Error(val e: Exception) : Status()

        /**
         * Success status.
         */
        object Success : Status()
    }

    /**
     * A result that holds a contact list.
     * @property result the result, it any
     * @property status the status
     */
    data class ContactListResult(
        val result: List<Contact>?,
        val status: Status
    )

    /**
     * A result that holds a single contact.
     * @property result the result, if any
     * @property status the status
     */
    data class ContactResult(
        val result: Contact?,
        val status: Status
    )

    /**
     * A result that holds an id.
     * @property result an id, if any
     * @property status the status
     */
    data class IdResult(
        val result: Long?,
        val status: Status
    )

    /**
     * Lists all contacts (async).
     * @return a liveData holding a ContactListResult
     */
    fun allContacts() = liveData {
        try {
            emit(ContactListResult(repository.getAll(), Status.Success))
        } catch (e: Exception) {
            emit(
                ContactListResult(
                    null,
                    Status.Error(Exception("Failed to fetch list of contacts", e))
                )
            )
        }
    }

    /**
     * Retrieves a contact given its id.
     * @return a liveData holding a ContactResult
     */
    fun getById(id: Long) = liveData {
        try {
            emit(ContactResult(repository.getById(id), Status.Success))
        } catch (e: Exception) {
            emit(
                ContactResult(
                    null,
                    Status.Error(Exception("Failed to get item with id $id", e))
                )
            )
        }
    }

    /**
     * Add a contact.
     * @return a liveData holding an IdResult
     */
    fun add(contact: Contact) = liveData {
        try {
            emit(IdResult(repository.add(contact), Status.Success))
        } catch (e: Exception) {
            emit(IdResult(null, Status.Error(Exception("Failed to add contact", e))))
        }
    }


}
