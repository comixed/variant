package org.comixedproject.variant.shared.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.comixedproject.variant.shared.model.server.Server
import org.comixedproject.variant.shared.platform.Logger
import org.comixedproject.variant.shared.repositories.ServerRepository

private val TAG = "VariantViewModel"

/**
 * <code>VariantViewModel</code> provides a single source for application state on Android devices.
 *
 * @author Darryl L. Pierce
 */
class VariantViewModel(val serverRepository: ServerRepository) : BaseViewModel() {
    internal val serverList: List<Server>
        get() = serverRepository.servers

    var onServerListUpdated: ((List<Server>) -> Unit)? = null
        set(value) {
            field = value
            onServerListUpdated?.invoke(serverList)
        }

    /**
     * Saves the given server to storage.
     *
     * @param server the server
     */
    fun saveServer(server: Server) {
        Logger.d(TAG, "Saving server: name=${server.name}")
        serverRepository.save(server)
        onServerListUpdated?.invoke(serverList)
    }

    fun deleteServer(server: Server) {
        server.serverId.let { serverId ->
            if (serverId != null) {
                Logger.d(TAG, "Deleting server: name=${server.name}")
                serverRepository.deleteServer(serverId)
                onServerListUpdated?.invoke(serverList)
            }
        }
    }

    private val _currentServer = MutableStateFlow<Server?>(null)
    val currentServer: StateFlow<Server?> = _currentServer

    fun setCurrentServer(server: Server?) {
        this._currentServer.value = server
    }
}